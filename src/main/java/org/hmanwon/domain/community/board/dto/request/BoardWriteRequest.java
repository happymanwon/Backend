package org.hmanwon.domain.community.board.dto.request;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record BoardWriteRequest(
    @NotBlank
    String content,
    List<MultipartFile> multipartFiles,
    List<String> hashtagNames,
    String roadName
) {

}
