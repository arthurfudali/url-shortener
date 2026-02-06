package dev.url_shortener.services;

import dev.url_shortener.entities.Url;
import dev.url_shortener.repositories.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getOriginalUrl(String shortUrl) throws ResponseStatusException {

        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (LocalDateTime.now().isAfter(url.getExpiresAt())) {
            urlRepository.delete(url);
            throw new ResponseStatusException(HttpStatus.GONE, "Resource expired");

        }
        return url.getOriginalUrl();

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
        return RandomStringUtils.insecure().nextAlphanumeric(5, 11);
    }
}
