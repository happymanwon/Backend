package org.hmanwon.domain.community.board.application;

import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.NOT_FOUND_BOARD;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dao.BoardRepository;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.exception.BoardException;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

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

    //나중에 BoardWriteRequest 검증 하는 로직 추가 하겠습니다 -> 커밋 리뷰에 제가 안 남기면 알려주세요
    public Board createBoard(Long memberId, BoardWriteRequest boardWriteRequest) {
        Member member = memberService.findMemberById(memberId);

        Board board = Board.of(
            boardWriteRequest.content(), member
        );
        Board newBoard = boardRepository.save(board);
        memberService.updateBoardListByMember(member, board);
        return newBoard;
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(()-> new BoardException(NOT_FOUND_BOARD));
    }

    public void updateCommentList(Board board, Comment comment) {
        List<Comment> comments = board.getCommentList();
        comments.add(comment);
        board.setCommentList(comments);
    }
}
