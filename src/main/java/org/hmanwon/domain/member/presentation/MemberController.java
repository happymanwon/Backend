package org.hmanwon.domain.member.presentation;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.dto.response.BoardResponse;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.dto.response.MyCommentResponseDto;
import org.hmanwon.domain.member.dto.response.NicknameResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/nickname")
    public ResponseEntity<DataBody<NicknameResponse>> getNickname(@RequestParam @NotBlank String nickname) {
        return ResponseDTO.ok(memberService.findNickname(nickname), "닉네임 존재여부를 조회했습니다.");
    }

    @GetMapping("/comments")
    public ResponseEntity<DataBody<List<MyCommentResponseDto>>> getCommentsByMember() {
        Long memberId = 1L;
        return ResponseDTO.ok(memberService.findCommentsByMember(memberId), "내 댓글 목록을 조회했습니다.");
    }

    @GetMapping("/boards")
    public ResponseEntity<DataBody<List<BoardResponse>>> getBoardsByMember() {
        Long memberId = 1L;
        return ResponseDTO.ok(memberService.findBoardsByMember(memberId), "내 게시글 목록을 조회했습니다.");
    }

}
