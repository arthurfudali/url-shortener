package dev.url_shortener.resources;

import dev.url_shortener.dtos.UrlResponseDto;
import dev.url_shortener.entities.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public UrlResponseDto toResponseDto(Url url) {
        if (url == null) return null;

        String fullShortUrl = "http://localhost:8080/" + url.getShortUrl();

        return new UrlResponseDto(fullShortUrl);
    }
}
