package org.hmanwon.domain.community.board.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

public record BoardWriteRequest(
    @NotBlank
    String content,
    List<MultipartFile> multipartFiles
) {

}
