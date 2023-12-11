package org.hmanwon.domain.member.application;

import static org.hmanwon.domain.member.exception.MemberExceptionCode.NOT_FOUND_MEMBER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.member.dao.MemberRepository;
import org.hmanwon.domain.member.dto.response.MemberResponse;
import org.hmanwon.domain.member.dto.response.MyCommentResponseDto;
import org.hmanwon.domain.member.dto.response.NicknameResponse;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse addMember(HashMap<String, Object> memberInfoByKakao) {
        String email = String.valueOf(memberInfoByKakao.get("email"));
        String nickname = String.valueOf(memberInfoByKakao.get("nickname"));

        Member member = Member.builder()
            .email(email)
            .nickname(nickname)
            .point(0L)
            .build();

        memberRepository.save(member);

        return MemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

    public MemberResponse findMemberInfo(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

    public List<MyCommentResponseDto> findCommentsByMember(Long memberId) {

        Member member = findMemberById(memberId);
        List<Comment> comments = member.getCommentList();
        List<MyCommentResponseDto> myCommentResponseDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            MyCommentResponseDto myCommentResponseDto = MyCommentResponseDto.builder()
                .boardId(comment.getBoard().getId())
                .memberId(memberId)
                .nickname(member.getNickname())
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
            myCommentResponseDtoList.add(myCommentResponseDto);
        }
        return myCommentResponseDtoList;
    }

    public List<BoardResponse> findBoardsByMember(Long memberId) {

        Member member = findMemberById(memberId);
        List<Board> boards = member.getBoardList();
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board board : boards) {
            boardResponses.add(BoardResponse.fromBoard(board));
        }
        return boardResponses;
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }

    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return MemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

    public NicknameResponse findNickname(String nickname) {
        NicknameResponse nicknameResponse;
        if (memberRepository.existsByNickname(nickname)) {
            nicknameResponse = NicknameResponse.builder().nickname(nickname).exists(true).build();
        } else {
            nicknameResponse = NicknameResponse.builder().nickname(nickname).exists(false).build();
        }
        return nicknameResponse;
    }

    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void updateBoardListByMember(Member member, Board board) {
        List<Board> boards = member.getBoardList();
        boards.add(board);
        member.setBoardList(boards);
    }

    public void updateCommentListByMember(Member member, Comment comment) {
        List<Comment> comments = member.getCommentList();
        comments.add(comment);
        member.setCommentList(comments);
    }

    public List<MemberResponse> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = new ArrayList<>();
        for(Member member: members) {
            memberResponses.add(findMemberInfo(member.getId()));
        }
        return memberResponses;
    }
}
