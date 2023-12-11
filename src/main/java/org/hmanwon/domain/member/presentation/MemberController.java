package org.hmanwon.domain.member.presentation;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.dto.response.MemberResponse;
import org.hmanwon.domain.member.dto.response.MyCommentResponseDto;
import org.hmanwon.domain.member.dto.response.NicknameResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Validated
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/nickname")
    public ResponseEntity<DataBody<NicknameResponse>> getNickname(
        @RequestParam @NotBlank(message = "닉네임을 입력하세요") String nickname) {
        return ResponseDTO.ok(memberService.findNickname(nickname), "닉네임 존재 여부 조회 완료");
    }

    @GetMapping("/comments")
    public ResponseEntity<DataBody<List<MyCommentResponseDto>>> getCommentsByMember(
        @RequestHeader(value = "Authorization") String token) {
        return ResponseDTO.ok(
            memberService.findCommentsByMember(authService.getMemberIdFromValidToken(token)),
            "내 댓글 목록 조회 완료");
    }

    @GetMapping("/boards")
    public ResponseEntity<DataBody<List<BoardResponse>>> getBoardsByMember(
        @RequestHeader(value = "Authorization") String token) {
        return ResponseDTO.ok(
            memberService.findBoardsByMember(authService.getMemberIdFromValidToken(token)),
            "내 게시글 목록 조회 완료");
    }

    @GetMapping
    public ResponseEntity<DataBody<MemberResponse>> getMemberInfo(
        @RequestHeader(value = "Authorization") String token) {
        return ResponseDTO.ok(
            memberService.findMemberInfo(authService.getMemberIdFromValidToken(token)),
            "회원 정보 조회 완료");
    }
}
