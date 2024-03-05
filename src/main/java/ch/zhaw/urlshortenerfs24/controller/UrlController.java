package ch.zhaw.urlshortenerfs24.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.urlshortenerfs24.model.ShortUrl;
import ch.zhaw.urlshortenerfs24.model.ShortUrlDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UrlController {
    HashMap<String, ShortUrl> urlMap = new HashMap<>();

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortUrlDTO shortUrlDTO) {
        String shortUrl = UUID.randomUUID().toString().substring(0, 8);
        String longUrl = shortUrlDTO.getLongUrl();
        if (longUrl.startsWith("http://")) {
            urlMap.put(shortUrl, new ShortUrl(shortUrl, shortUrlDTO.getLongUrl(), System.currentTimeMillis()));
            return ResponseEntity.ok(shortUrl);
        } else {
            return ResponseEntity.badRequest().body("URL must start with http://");
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getMethodName(@PathVariable String shortUrl) {
        if (urlMap.containsKey(shortUrl)) {
            String longUrl = urlMap.get(shortUrl).getLongUrl();
            return ResponseEntity.status(302).header("Location", longUrl).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    

    @GetMapping("/urls")
    public Map<String, ShortUrl> getMethodName() {
        return urlMap;
    }
}
