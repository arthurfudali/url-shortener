package dev.url_shortener.controllers;

import dev.url_shortener.entities.Url;
import dev.url_shortener.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<Url> shortenUrl(@RequestBody String originalUrl) {
        Url shortUrl = urlService.createShortUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }



}
