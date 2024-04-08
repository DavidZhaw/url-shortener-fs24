package ch.zhaw.urlshortenerfs24.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Document("ShortUrl")
public class ShortUrl {
    @Id private String shortUrl;
    private String longUrl;
    private Long creationDate;
    private String user;
    private String description;
}
