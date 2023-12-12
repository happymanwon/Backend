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

    /***
     * 회원 가입
     * @param memberInfoByKakao 카카오 회원 정보
     * @return 회원 응답 DTO
     */
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

    /***
     * 특정 회원 정보 조회
     * @param memberId 회원 ID
     * @return 회원 응답 DTO
     */
    public MemberResponse findMemberInfo(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

    /***
     * 특정 회원이 작성한 댓글 목록 조회
     * @param memberId 회원 ID
     * @return 댓글 응답 DTO 리스트
     */
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

    /***
     * 특정 회원이 작성한 게시글 목록 조회
     * @param memberId 회원 ID
     * @return 게시글 응답 DTO 리스트
     */
    public List<BoardResponse> findBoardsByMember(Long memberId) {

        Member member = findMemberById(memberId);
        List<Board> boards = member.getBoardList();
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board board : boards) {
            boardResponses.add(BoardResponse.fromBoard(board));
        }
        return boardResponses;
    }

    /***
     * 회원 Entity 조회
     * @param memberId 회원 ID
     * @return 회원
     */
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }

    /***
     * 이메일로 회원 정보 조회
     * @param email 이메일
     * @return 회원 응답 DTO
     */
    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return MemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .point(member.getPoint())
            .build();
    }

    /***
     * 닉네임 존재 여부 조회
     * @param nickname 닉네임
     * @return 닉네임 응답 DTO
     */
    public NicknameResponse findNickname(String nickname) {
        NicknameResponse nicknameResponse;
        if (memberRepository.existsByNickname(nickname)) {
            nicknameResponse = NicknameResponse.builder().nickname(nickname).exists(true).build();
        } else {
            nicknameResponse = NicknameResponse.builder().nickname(nickname).exists(false).build();
        }
        return nicknameResponse;
    }

    /***
     * 이메일 존재 여부 조회
     * @param email 이메일
     * @return 존재 여부
     */
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    /***
     * 특정 회원이 작성한 게시글 목록 수정
     * @param member 회원 Entity
     * @param board 게시글 Entity
     */
    public void updateBoardListByMember(Member member, Board board) {
        List<Board> boards = member.getBoardList();
        boards.add(board);
        member.setBoardList(boards);
    }

    /***
     * 특정 회원이 작성한 댓글 목록 수정
     * @param member 회원 Entity
     * @param comment 댓글 Entity
     */
    public void updateCommentListByMember(Member member, Comment comment) {
        List<Comment> comments = member.getCommentList();
        comments.add(comment);
        member.setCommentList(comments);
    }

    /***
     * 전체 회원 정보 조회
     * @return 회원 응답 DTO 리스트
     */
    public List<MemberResponse> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = new ArrayList<>();
        for(Member member: members) {
            memberResponses.add(findMemberInfo(member.getId()));
        }
        return memberResponses;
    }
}
