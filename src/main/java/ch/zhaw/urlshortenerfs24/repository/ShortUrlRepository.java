package ch.zhaw.urlshortenerfs24.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.urlshortenerfs24.model.ShortUrl;
import ch.zhaw.urlshortenerfs24.model.UserAggregationDTO;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String>{
    ShortUrl findByUser(String user);

    @Aggregation("{'$group':{'_id':'$user','url':{'$push':'$longUrl'}}}")
    List<UserAggregationDTO> getAllUserUrls();
}
