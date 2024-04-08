package ch.zhaw.urlshortenerfs24.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.urlshortenerfs24.model.ShortUrl;
import ch.zhaw.urlshortenerfs24.repository.ShortUrlRepository;

@RestController
@RequestMapping("/go")
public class RedirectController {
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        Optional<ShortUrl> url = shortUrlRepository.findById(shortUrl);
        if (url.isPresent()) {
            String longUrl = url.get().getLongUrl();
            return ResponseEntity.status(302).header("Location", longUrl).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
