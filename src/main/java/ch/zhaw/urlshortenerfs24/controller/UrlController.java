package ch.zhaw.urlshortenerfs24.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.urlshortenerfs24.model.ShortUrl;
import ch.zhaw.urlshortenerfs24.model.ShortUrlDTO;
import ch.zhaw.urlshortenerfs24.repository.ShortUrlRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortUrlDTO shortUrlDTO, @AuthenticationPrincipal Jwt jwt) {
        // check if the user in request body is the same as the one in the JWT
        String user = shortUrlDTO.getUser();
        String userJwt = jwt.getClaimAsString("email");
        if (!user.equals(userJwt)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not match JWT");
        }
        String shortUrl = UUID.randomUUID().toString().substring(0, 8);
        String longUrl = shortUrlDTO.getLongUrl();
        String description = shortUrlDTO.getDescription();
        // check if URL starts with http:// or https://
        if (longUrl.startsWith("http://") || longUrl.startsWith("https://")) {
            // save URL
            shortUrlRepository.save(new ShortUrl(shortUrl, longUrl, System.currentTimeMillis(),user,description));
            return ResponseEntity.ok(shortUrl);
        } else {
            return ResponseEntity.badRequest().body("URL must start with http:// or https://");
        }
    }

    @GetMapping("/urls")
    public ResponseEntity<List<ShortUrl>> getUrls(@RequestParam(required = false) String user, @AuthenticationPrincipal Jwt jwt) {
        // check if user is admin
        List<String> userRoles = jwt.getClaimAsStringList("user_roles");
        if (userRoles.contains("admin")) {
            // return all URLs
            return ResponseEntity.ok(shortUrlRepository.findAll());
        }
        // check if user in request parameter is the same as the one in the JWT
        if (user != null) {
            String userJwt = jwt.getClaimAsString("email");
            if (user.equals(userJwt)) {
                // return URLs of user
                return ResponseEntity.ok(shortUrlRepository.findByUser(user));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @DeleteMapping("/urls/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortUrl, @AuthenticationPrincipal Jwt jwt) {
        // check if short URL exists
        ShortUrl url = shortUrlRepository.findById(shortUrl).orElse(null);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        // check if user is admin
        List<String> userRoles = jwt.getClaimAsStringList("user_roles");
        if (userRoles.contains("admin")) {
            // delete URL
            shortUrlRepository.deleteById(shortUrl);
            return ResponseEntity.noContent().build();
        }
        // check if user of short URL is the same as the one in the JWT
        String userJwt = jwt.getClaimAsString("email");
        if (url.getUser().equals(userJwt)) {
            // delete URL
            shortUrlRepository.deleteById(shortUrl);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

}
