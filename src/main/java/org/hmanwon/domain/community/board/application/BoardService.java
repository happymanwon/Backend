package org.hmanwon.domain.community.board.application;

import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.NOT_FOUND_BOARD;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dao.BoardRepository;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.exception.BoardException;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.infra.image.application.ImageUploader;
import org.hmanwon.infra.image.dao.ImageRepository;
import org.hmanwon.infra.image.entity.Image;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final ImageUploader imageUploader;
    private final ImageRepository imageRepository;

    public List<BoardResponse> getAllBoard() {
        return boardRepository.findAll()
            .stream().map(BoardResponse::fromBoard)
            .collect(Collectors.toList());
    }

    public BoardDetailResponse getBoardDetail(Long boardId) {
        return boardRepository.findById(boardId)
            .map(BoardDetailResponse::fromBoard)
            .orElseThrow(() -> new BoardException(NOT_FOUND_BOARD));
    }

    public Board createBoard(Long memberId, BoardWriteRequest boardWriteRequest) {
        //멤버 서비스로 빼고 예외도 거기서 해야 함
        Member member = memberService.getMemberById(memberId);
        Board board = Board.of(
            boardWriteRequest.content(),
            member
        );

        List<Image> imageList = new ArrayList<>();
        for (String imageUrl : imageUploader.uploadFile("community",
            boardWriteRequest.multipartFiles())) {
            Image image = new Image(imageUrl, board);
            imageRepository.save(image);
            imageList.add(image);
        }

        board.setImages(imageList);

        return boardRepository.save(board);
    }

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
