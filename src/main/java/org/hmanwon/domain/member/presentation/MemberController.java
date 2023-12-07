package org.hmanwon.domain.member.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.dto.MyBoardsResponseDto;
import org.hmanwon.domain.member.dto.MyCommentResponseDto;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/comments")
    public ResponseEntity<DataBody<List<MyCommentResponseDto>>> getCommentsByMember() {
        Long memberId = 1L;
        return ResponseDTO.ok(memberService.findCommentsByMember(memberId), "내 댓글 목록을 조회했습니다.");
    }

    @GetMapping("/boards")
    public ResponseEntity<DataBody<List<MyBoardsResponseDto>>> getBoardsByMember() {
        Long memberId = 1L;
        return ResponseDTO.ok(memberService.findBoardsByMember(memberId), "내 게시글 목록을 조회했습니다.");
    }

}
