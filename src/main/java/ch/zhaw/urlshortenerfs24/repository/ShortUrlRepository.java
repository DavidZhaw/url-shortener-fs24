package ch.zhaw.urlshortenerfs24.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.urlshortenerfs24.model.ShortUrl;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String>{
    List<ShortUrl> findByUser(String user);
}
