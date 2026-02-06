package dev.url_shortener.controllers;

import dev.url_shortener.dtos.UrlRequestDto;
import dev.url_shortener.dtos.UrlResponseDto;
import dev.url_shortener.entities.Url;
import dev.url_shortener.resources.UrlMapper;
import dev.url_shortener.services.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService urlService;
    private final UrlMapper urlMapper;

    public UrlController(UrlService urlService, UrlMapper urlMapper) {
        this.urlService = urlService;
        this.urlMapper = urlMapper;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<UrlResponseDto> shortenUrl(@RequestBody UrlRequestDto requestDto) {
        Url url = urlService.createShortUrl(requestDto.url());
        UrlResponseDto responseDto = urlMapper.toResponseDto(url);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String shortUrl) {
        var urlOptional = urlService.getOriginalUrl(shortUrl);

        if (urlOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlOptional.get())).build();
        }
        return ResponseEntity.notFound().build();
    }


}
