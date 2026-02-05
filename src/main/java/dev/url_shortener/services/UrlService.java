package dev.url_shortener.services;

import dev.url_shortener.repositories.UrlRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository, UrlRepository urlRepository1) {
        this.urlRepository = urlRepository1;
    }

}
