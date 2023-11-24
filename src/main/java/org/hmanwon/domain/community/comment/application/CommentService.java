package org.hmanwon.domain.community.comment.application;

import javax.transaction.Transactional;
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
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /***
     * 댓글 생성
     * @param memberId 회원 ID
     * @param commentRequestDTO 댓글 요청 DTO
     * @return 댓글 응답 DTO
     */
    public CommentResponseDTO createComment(Long memberId, CommentRequestDTO commentRequestDTO) {
        Comment comment = Comment.builder()
            .board((Board) boardRepository.findById(commentRequestDTO.boardId()).orElseThrow()) //추후 변경 예정
            .member((Member) memberRepository.findById(memberId).orElseThrow()) //추후 변경 예정
            .content(commentRequestDTO.content())
            .build();

        commentRepository.save(comment);

        return CommentResponseDTO.entityFromDTO(comment);
    }

    /***
     * 댓글 삭제
     * @param memberId 회원 ID
     * @param commentId 댓글 ID
     * @return 댓글
     */
    public CommentResponseDTO deleteCommentById(Long memberId, Long commentId) {
        Comment comment = (Comment) commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);

        return CommentResponseDTO.entityFromDTO(comment);
    }
}
