package ch.zhaw.urlshortenerfs24.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.urlshortenerfs24.model.ShortUrl;
import ch.zhaw.urlshortenerfs24.model.ShortUrlDTO;
import ch.zhaw.urlshortenerfs24.model.UserAggregationDTO;
import ch.zhaw.urlshortenerfs24.repository.ShortUrlRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class UrlController {
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortUrlDTO shortUrlDTO) {
        String shortUrl = UUID.randomUUID().toString().substring(0, 8);
        String longUrl = shortUrlDTO.getLongUrl();
        String user = shortUrlDTO.getUser();
        if (longUrl.startsWith("http://")) {
            shortUrlRepository.save(new ShortUrl(shortUrl, longUrl, System.currentTimeMillis(),user));
            return ResponseEntity.ok(shortUrl);
        } else {
            return ResponseEntity.badRequest().body("URL must start with http://");
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getMethodName(@PathVariable String shortUrl) {
        Optional<ShortUrl> url = shortUrlRepository.findById(shortUrl);
        if (url.isPresent()) {
            String longUrl = url.get().getLongUrl();
            return ResponseEntity.status(302).header("Location", longUrl).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    

    @GetMapping("/urls")
    public List<ShortUrl> getMethodName() {
        return shortUrlRepository.findAll();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserAggregationDTO>> getAllUserUrls() {
        List<UserAggregationDTO> userAggregationDTO = shortUrlRepository.getAllUserUrls();
        return ResponseEntity.ok(userAggregationDTO);
    }
}
