package com.fundguide.melona.newscontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundguide.melona.news.NaverResultVO3;
import com.fundguide.melona.news.NewsVO3;
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
public class NewsController3 {

    @GetMapping("/news/list3")
    public String list(String text, Model model) {
        String clientId = "JKEwb__YWBvFx15Ol2Ro";
        String clientSecret = "bOhVbjdP8G";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/news.json")
                .queryParam("query", text)
                .queryParam("display", 20)
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
        NaverResultVO3 resultVO3 = null;
        try {
            resultVO3 = om.readValue(resp.getBody(), NaverResultVO3.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (resultVO3 != null) {
            List<NewsVO3> news3 = resultVO3.getItems();
            model.addAttribute("news", news3);
        } else {
            System.out.println("Error: resultVO3 is null");
        }

        return "news/list3";
    }
}


//        // ObjectMapper 객체를 사용하여 JSON 문자열을 Java 객체로 변환한다.
//        try {
//            // resp.getBody()는 API 응답의 본문을 가져온다.
//            // 본문의 JSON 내용을 NaverResultVO 클래스 형태로 변환한다.
//            resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
//        } catch (JsonMappingException e) {
//            // JsonMappingException: JSON 문자열의 구조가 NaverResultVO 클래스와 맞지 않을 때 발생하는 예외
//            e.printStackTrace();// 예외 발생 시, 예외의 스택 트레이스를 출력한다.
//        } catch (JsonProcessingException e) {
//            // JsonProcessingException: JSON 처리 중 일반적인 예외 발생 시
//            e.printStackTrace();// 예외 발생 시, 예외의 스택 트레이스를 출력한다.
//        }
//
//        // resultVO 객체에서 뉴스 목록을 가져온다. (NaverResultVO 클래스 내의 getItems 메서드를 사용)
//        List<NewsVO> news =resultVO.getItems();	// books를 list.html에 출력 -> model 선언
//        // news 변수를 'news'라는 이름으로 모델에 추가한다.
//        // 이렇게 추가된 데이터는 뷰(예: list.html)에서 사용할 수 있다.
//        model.addAttribute("news", news);
//        // "/news/list" 뷰를 반환한다. 해당 뷰는 실제로 뉴스 목록을 표시하는 HTML 페이지를 나타낸다.
//        return "/news/list";
