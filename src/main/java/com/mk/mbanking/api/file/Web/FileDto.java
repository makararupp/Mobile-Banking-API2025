package com.mk.mbanking.api.file.Web;

import lombok.Builder;

@Builder
public record FileDto(
        String name,
        String extension,
        Long size,
        String readableSize,
        String url,
        String downloadUrl
) {
}
