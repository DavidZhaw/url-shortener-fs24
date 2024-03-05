package ch.zhaw.urlshortenerfs24.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortUrl {
    private String shortUrl;
    private String longUrl;
}
