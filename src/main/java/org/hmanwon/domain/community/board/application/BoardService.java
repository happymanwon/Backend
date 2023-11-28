package org.hmanwon.domain.community.board.application;

import static org.hmanwon.domain.community.board.exception.BoardExceptionCode.NOT_FOUND_BOARD;
import static org.hmanwon.domain.member.exception.MemberExceptionCode.NOT_FOUND_MEMBER;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dao.BoardRepository;
import org.hmanwon.domain.community.board.dto.request.BoardWriteRequest;
import org.hmanwon.domain.community.board.dto.response.BoardDetailResponse;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.exception.BoardException;
import org.hmanwon.domain.member.dao.MemberRepository;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.member.exception.MemberException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository; //나중에 service로 바꿔야 함-

    public Page<BoardResponse> getAllBoard(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardResponse::fromBoard);
    }

    public BoardDetailResponse getBoardDetail(Long boardId) {
        return boardRepository.findById(boardId)
            .map(BoardDetailResponse::fromBoard)
            .orElseThrow(() -> new BoardException(NOT_FOUND_BOARD));
    }

    //나중에 BoardWriteRequest 검증 하는 로직 추가 하겠습니다 -> 커밋 리뷰에 제가 안 남기면 알려주세요
    public Board createBoard(Long memberId, BoardWriteRequest boardWriteRequest) {
        //멤버 서비스로 빼고 예외도 거기서 해야 함
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        Board board = Board.of(
            boardWriteRequest.title(), boardWriteRequest.content(), member
        );
        return boardRepository.save(board);
    }
}
