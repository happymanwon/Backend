package org.hmanwon.domain.community.board.dto.request;

import java.util.List;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

public record BoardWriteRequest(
    String content,
    List<MultipartFile> multipartFiles
) {

}
