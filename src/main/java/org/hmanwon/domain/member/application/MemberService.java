package org.hmanwon.domain.member.application;

import static org.hmanwon.domain.member.exception.MemberExceptionCode.NOT_FOUND_MEMBER;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.member.dao.MemberRepository;
import org.hmanwon.domain.member.dto.MyBoardsResponseDto;
import org.hmanwon.domain.member.dto.MyCommentResponseDto;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MyCommentResponseDto> findCommentsByMember(Long memberId) {

        Member member = findMemberById(memberId);
        List<Comment> comments = member.getCommentList();
        List<MyCommentResponseDto> myCommentResponseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            MyCommentResponseDto myCommentResponseDto = MyCommentResponseDto.builder()
                .boardId(comment.getBoard().getId())
                .boardTitle(comment.getBoard().getTitle())
                .memberId(memberId)
                .nickname(member.getNickName())
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
            myCommentResponseDtoList.add(myCommentResponseDto);
        }
        return myCommentResponseDtoList;
    }

    public List<MyBoardsResponseDto> findBoardsByMember(Long memberId) {

        Member member = findMemberById(memberId);
        List<Board> boards = member.getBoardList();
        List<MyBoardsResponseDto> myBoardsResponseDtoList = new ArrayList<>();

        for (Board board : boards) {
            MyBoardsResponseDto myBoardsResponseDto = MyBoardsResponseDto.builder()
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .memberId(memberId)
                .nickname(member.getNickName())
                .createAt(board.getCreatedAt())
                .updateAt(board.getUpdatedAt())
                .build();
            myBoardsResponseDtoList.add(myBoardsResponseDto);
        }
        return myBoardsResponseDtoList;
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }
}
