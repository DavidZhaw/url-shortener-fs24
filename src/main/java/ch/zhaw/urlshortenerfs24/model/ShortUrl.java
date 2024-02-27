package ch.zhaw.urlshortenerfs24.model;

public class ShortUrl {
    private String shortUrl;
    private String longUrl;

    public ShortUrl(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
