package org.hmanwon.domain.community.comment.application;

import static org.hmanwon.domain.community.comment.exception.CommentExceptionCode.NOT_FOUND_COMMENT;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.community.board.application.BoardService;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.dao.CommentRepository;
import org.hmanwon.domain.community.comment.dto.request.CommentRequestDto;
import org.hmanwon.domain.community.comment.dto.request.CommentUpdateRequestsDto;
import org.hmanwon.domain.community.comment.dto.response.CommentResponseDto;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.community.comment.exception.CommentException;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    /***
     * 댓글 생성
     * @param memberId 회원 ID
     * @param commentRequestDTO 댓글 요청 DTO
     * @return 댓글 응답 DTO
     */
    public CommentResponseDto createComment(Long memberId, CommentRequestDto commentRequestDTO) {
        Member member = memberService.findMemberById(memberId);
        Board board = boardService.findBoardById(commentRequestDTO.boardId());
        Comment comment = Comment.builder()
            .board(board)
            .member(member)
            .content(commentRequestDTO.content())
            .build();
        commentRepository.save(comment);
        memberService.updateCommentListByMember(member, comment);
        boardService.updateCommentList(board, comment);
        return CommentResponseDto.entityFromDTO(comment);
    }

    /***
     * 댓글 내용 수정
     * @param memberId 회원 ID
     * @param commentId 댓글 ID
     * @param commentUpdateRequestsDto 댓글 수정 요청 DTO
     * @return 댓글 응답 DTO
     */
    public CommentResponseDto updateContent(Long memberId, Long commentId,
        CommentUpdateRequestsDto commentUpdateRequestsDto) {
        Comment comment = getComment(commentId);
        comment.updateContent(commentUpdateRequestsDto.content());
        commentRepository.save(comment);
        return CommentResponseDto.entityFromDTO(comment);
    }

    /***
     * 댓글 Entity 조회
     * @param commentId 댓글 ID
     * @return 댓글 Entity
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentException(NOT_FOUND_COMMENT));
    }

    /***
     * 댓글 삭제
     * @param memberId 회원 ID
     * @param commentId 댓글 ID
     * @return 댓글
     */
    public CommentResponseDto deleteCommentById(Long memberId, Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);

        return CommentResponseDto.entityFromDTO(comment);
    }
}
