package org.hmanwon.domain.community.board.application;

import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.FORBIDDEN_BOARD;
import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.NOT_FOUND_BOARD;
import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.NOT_FOUND_HASHTAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dao.BoardHashtagRepository;
import org.hmanwon.domain.community.board.dao.BoardRepository;
import org.hmanwon.domain.community.board.dao.HashtagRepository;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.entity.BoardHashtag;
import org.hmanwon.domain.community.board.entity.Hashtag;
import org.hmanwon.domain.community.board.exception.BoardException;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.infra.image.application.ImageUploader;
import org.hmanwon.infra.image.dao.ImageRepository;
import org.hmanwon.infra.image.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final ImageUploader imageUploader;
    private final ImageRepository imageRepository;
    private final HashtagRepository hashtagRepository;
    private final BoardHashtagRepository boardHashtagRepository;

    public List<BoardResponse> getAllBoard() {
        return Optional.ofNullable(boardRepository.findAll())
            .orElse(new ArrayList<>())
            .stream().map(BoardResponse::fromBoard)
            .collect(Collectors.toList());
    }

    public BoardDetailResponse getBoardDetail(Long boardId) {
        return boardRepository.findById(boardId)
            .map(BoardDetailResponse::fromBoard)
            .orElseThrow(() -> new BoardException(NOT_FOUND_BOARD));
    }

    public List<BoardResponse> getBoardHashtag(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByName(hashtagName)
            .orElseThrow(() -> new BoardException(NOT_FOUND_HASHTAG));
        List<BoardHashtag> boardHashtags = boardHashtagRepository.findByHashtag(hashtag)
            .orElseThrow(() -> new BoardException(NOT_FOUND_HASHTAG));

        return boardHashtags.stream()
            .map(boardHashtag -> boardHashtag.getBoard())
            .map(BoardResponse::fromBoard)
            .toList();

    }

    @Transactional
    public BoardDetailResponse createBoard(Long memberId, BoardWriteRequest boardWriteRequest) {
        Member member = memberService.findMemberById(memberId);
        Board board = Board.of(
            boardWriteRequest.content(),
            member,
            boardWriteRequest.shopName(),
            boardWriteRequest.roadName()
        );

        board = saveBoardWithHashtag(boardWriteRequest.hashtagNames(), board);
        board = saveBoardWithImage(boardWriteRequest.multipartFiles(), board);

        memberService.updateBoardListByMember(member, board);

        return BoardDetailResponse.fromBoard(boardRepository.save(board));
    }

    private Board saveBoardWithHashtag(List<String> hashtagNames, Board board) {

        if (board.getBoardHashtags() == null) {
            board.setBoardHashtags(new ArrayList<>());
        }
        board.getBoardHashtags().clear();

        if (hashtagNames != null && !hashtagNames.isEmpty()) {
            for (String hashtagName : hashtagNames) {
                String newHashtagName = hashtagName.replace("[", "").replace("]", "")
                    .replace("\"", "");
                Hashtag hashtag = hashtagRepository.findByName(newHashtagName)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(newHashtagName)));
                BoardHashtag boardHashtag = BoardHashtag.builder()
                    .board(board)
                    .hashtag(hashtag)
                    .build();

                board.getBoardHashtags().add(boardHashtag);
            }
        }
        return boardRepository.save(board);
    }

    private Board saveBoardWithImage(List<MultipartFile> multipartFiles, Board board) {

        if (board.getImages() == null) {
            board.setImages(new ArrayList<>());
        }
        board.getImages().clear();

        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            for (String imageUrl : imageUploader.uploadFile("community", board.getId().toString(),
                multipartFiles)) {
                Image image = new Image(imageUrl, board);
                imageRepository.save(image);
                board.getImages().add(image);
            }
        }
        return boardRepository.save(board);
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardException(NOT_FOUND_BOARD));
    }

    public void updateCommentList(Board board, Comment comment) {
        List<Comment> comments = board.getComments();
        comments.add(comment);
        board.setComments(comments);
        boardRepository.save(board);
    }

    @Transactional
    public BoardDetailResponse updateBoard(Long boardId, Long memberId,
        BoardWriteRequest boardWriteRequest) {
        Board board = findBoardById(boardId);

        if (memberId != board.getMember().getId()) {
            throw new BoardException(FORBIDDEN_BOARD);
        }

        imageUploader.deleteFile("community", boardId.toString());
        imageRepository.deleteAllByBoardId(boardId);
        board = saveBoardWithImage(boardWriteRequest.multipartFiles(), board);

//        if (!board.getBoardHashtags().isEmpty()) {
//            deleteHashtag(board);
//        }
        boardHashtagRepository.deleteAllByBoardId(boardId);
        board = saveBoardWithHashtag(boardWriteRequest.hashtagNames(), board);

        board.updateContent(boardWriteRequest.content());
        board.updateShopName(boardWriteRequest.shopName());
        board.updateRoadName(boardWriteRequest.roadName());

        return BoardDetailResponse.fromBoard(boardRepository.save(board));
    }

//    private void deleteHashtag(Board board) {
//        List<BoardHashtag> boardHashtags = boardHashtagRepository.findByBoard(board);
//
//        for (BoardHashtag boardHashtag : boardHashtags) {
//            Hashtag hashtag = boardHashtag.getHashtag();
//            if (boardHashtagRepository.countByHashtag(hashtag) == 1) {
//                boardHashtagRepository.delete(boardHashtag);
//                hashtagRepository.delete(hashtag);
//            }
//        }
//    }

    public void deleteBoard(Long boardId, Long memberId) {
        Board board = findBoardById(boardId);
        if (memberId != board.getMember().getId()) {
            throw new BoardException(FORBIDDEN_BOARD);
        }

        imageUploader.deleteFile("community", boardId.toString());
        boardRepository.deleteById(boardId);
    }

    /*멤버 처리 하는 기능도 추가 해야 함*/
    public void reportBoard(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.increaseReportCnt();
            boardRepository.save(board);
        } else {
            throw new BoardException(NOT_FOUND_BOARD);
        }
    }
}
