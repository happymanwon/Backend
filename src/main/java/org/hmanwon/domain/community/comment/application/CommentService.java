package org.hmanwon.domain.community.comment.application;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.board.repository.BoardRepository;
import org.hmanwon.domain.community.comment.dto.request.CommentRequestDTO;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDTO;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.community.comment.dao.CommentRepository;
import org.hmanwon.domain.member.dao.MemberRepository;
import org.hmanwon.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDTO createComment(Long memberId, CommentRequestDTO commentRequestDTO) {
        Comment comment = Comment.builder()
            .board((Board) boardRepository.findById(commentRequestDTO.getBoardId()).orElseThrow())
            .member((Member) memberRepository.findById(memberId).orElseThrow())
            .content(commentRequestDTO.getContent())
            .build();

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }

}
