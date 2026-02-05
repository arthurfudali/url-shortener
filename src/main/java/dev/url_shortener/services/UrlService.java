package dev.url_shortener.services;

import dev.url_shortener.entities.Url;
import dev.url_shortener.repositories.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository, UrlRepository urlRepository1) {
        this.urlRepository = urlRepository1;
    }

    public Url createShortUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(generateShortUrl(originalUrl));
        url.setViews(0);
        url.setExpiresAt(LocalDateTime.now().plusDays(1));
        urlRepository.save(url);
        return url;
    }

    private String generateShortUrl(String url) {
        return RandomStringUtils.insecure().nextAlphabetic(5, 11);
    }
}
