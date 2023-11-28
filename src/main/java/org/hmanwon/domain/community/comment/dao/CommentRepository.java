package org.hmanwon.domain.community.comment.dao;

import org.hmanwon.domain.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
