package dev.url_shortener.services;

import dev.url_shortener.entities.Url;
import dev.url_shortener.repositories.UrlRepository;
import dev.url_shortener.resources.exceptions.InvalidUrlException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Optional<String> getOriginalUrl(String shortUrl) throws ResponseStatusException {

        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (LocalDateTime.now().isAfter(url.getExpiresAt())) {
            urlRepository.delete(url);
            throw new ResponseStatusException(HttpStatus.GONE, "Resource expired");
        }
        return url.getOriginalUrl();

    }

    public Url createShortUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            throw new InvalidUrlException("A URL is required");
        }
        if (!originalUrl.startsWith("https://") && !originalUrl.startsWith("http://")) {
            throw new InvalidUrlException("A URL must start with 'http://' or 'https://'");
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(generateShortUrl());
        url.setViews(0);
        url.setExpiresAt(LocalDateTime.now().plusDays(1));

        return urlRepository.save(url);
    }

    private String generateShortUrl() {
        return RandomStringUtils.insecure().nextAlphanumeric(5, 11);
    }
}
