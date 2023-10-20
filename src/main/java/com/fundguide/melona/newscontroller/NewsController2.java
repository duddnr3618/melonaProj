package com.fundguide.melona.newscontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundguide.melona.news.NaverResultVO2;
import com.fundguide.melona.news.NewsVO2;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class NewsController2 {

    @GetMapping("/news/list2")
    public String list(String text, Model model) {
        String clientId = "JKEwb__YWBvFx15Ol2Ro";
        String clientSecret = "bOhVbjdP8G";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/news.json")
                .queryParam("query", text)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "date")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        ObjectMapper om = new ObjectMapper();
        NaverResultVO2 resultVO2 = null;
        try {
            resultVO2 = om.readValue(resp.getBody(), NaverResultVO2.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (resultVO2 != null) {
            List<NewsVO2> news2 = resultVO2.getItems();
            model.addAttribute("news", news2);
        } else {
            System.out.println("Error: resultVO2 is null");
        }

        return "news/list2";
    }
}
