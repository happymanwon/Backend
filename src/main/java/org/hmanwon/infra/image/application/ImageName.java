package org.hmanwon.infra.image.application;

import static org.hmanwon.infra.image.exception.ImageExceptionCode.IMAGE_EXTENSION;
import static org.hmanwon.infra.image.exception.ImageExceptionCode.IMAGE_FORMAT;
import static org.hmanwon.infra.image.exception.ImageExceptionCode.IMAGE_NAME_BLANK;

import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hmanwon.infra.image.exception.ImageException;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageName {

    private static final String DOT = ".";
    private static final String UNDER_BAR = "_";
    private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpeg", "jpg", "png", "webp", "heic",
        "heif");

    public static String createFileName(final String originalFilename) {
        final String extension = StringUtils.getFilenameExtension(originalFilename);
        validateFileName(originalFilename);
        validateExtension(extension);
        final String fileBaseName = UUID.randomUUID().toString().substring(0, 8);
        return fileBaseName + UNDER_BAR + System.currentTimeMillis() + DOT + extension;
    }

    private static void validateFileName(final String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new ImageException(IMAGE_NAME_BLANK);
        }
    }

    private static void validateExtension(final String extension) {
        if (extension == null) {
            throw new ImageException(IMAGE_FORMAT);
        }
        if (!IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new ImageException(IMAGE_EXTENSION);
        }
    }
}
