package org.hmanwon.domain.auth.dto;

import lombok.Builder;

@Builder
public record TokenValidationResponse(
    Boolean valid,
    String errorMessage
) {

}
