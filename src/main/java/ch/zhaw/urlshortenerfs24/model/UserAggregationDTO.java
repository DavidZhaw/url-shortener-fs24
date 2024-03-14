package ch.zhaw.urlshortenerfs24.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAggregationDTO {
    private String id;
    private List<String> url;
}
