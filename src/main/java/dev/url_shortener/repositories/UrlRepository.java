package dev.url_shortener.repositories;

import dev.url_shortener.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
