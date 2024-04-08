package ch.zhaw.urlshortenerfs24.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
    // https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/*").permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/**").permitAll()           
            )
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
