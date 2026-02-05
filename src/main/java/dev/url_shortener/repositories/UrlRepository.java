package dev.url_shortener.repositories;

import dev.url_shortener.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
}
