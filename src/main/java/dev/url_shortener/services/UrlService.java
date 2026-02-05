package dev.url_shortener.services;

import dev.url_shortener.entities.Url;
import dev.url_shortener.repositories.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getOriginalUrl(String shortUrl) throws Exception {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlOptional.isEmpty()) throw new Exception("Resource not found");
        Url url = urlOptional.get();

        if (LocalDateTime.now().isAfter(url.getExpiresAt())) {
            urlRepository.delete(url);
            throw new Exception("Resource expired");

        }
        return urlOptional.get().getOriginalUrl();

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
