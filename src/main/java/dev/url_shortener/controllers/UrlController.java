package dev.url_shortener.controllers;

import dev.url_shortener.dtos.UrlRequestDto;
import dev.url_shortener.dtos.UrlResponseDto;
import dev.url_shortener.entities.Url;
import dev.url_shortener.services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<UrlResponseDto> shortenUrl(@RequestBody UrlRequestDto requestDto) {
        Url url = urlService.createShortUrl(requestDto.url());
        UrlResponseDto responseDto = new UrlResponseDto("http://localhost:8080/" + url.getShortUrl());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String shortUrl) {
        try {
            String originalUrl = urlService.getOriginalUrl(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
