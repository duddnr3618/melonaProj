package com.news.news.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.news.vo.NaverResultVO;
import com.news.news.vo.NewsVO;
import org.springframework.ui.Model;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class NewsController {

    @GetMapping("/news/list")
    public String list(String text, Model model) {

        // 네이버 검색 API 요청
        //네이버 클라이언트 id(추후 수정 필요)
        String clientId = "JKEwb__YWBvFx15Ol2Ro";
        //네이버 클라이언트 secret
        String clientSecret = "bOhVbjdP8G";

        // 주석 처리된 부분. 네이버 검색 API의 블로그 검색 주소 예시입니다.
        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과

        // UriComponentsBuilder를 사용하여 네이버 검색 API의 URI를 생성합니다.
        URI uri = UriComponentsBuilder
                // 기본 URL을 설정합니다.
                .fromUriString("https://openapi.naver.com")
                // 경로를 추가합니다. 여기서는 뉴스 검색을 위한 경로입니다.
                .path("/v1/search/news.json")
                // 검색어를 쿼리 파라미터로 추가
                .queryParam("query", text)
                // 검색 결과의 표시 개수를 설정
                .queryParam("display", 10)
                // 검색 결과의 시작 인덱스를 첫번째 결과 부터 시작하는 걸로 설정
                .queryParam("start", 1)
                // 검색 결과를 날짜순으로 정렬(date)
                .queryParam("sort", "date")
                // URI에 포함된 특수 문자나 공백 등을 올바른 형식으로 인코딩.
                .encode(StandardCharsets.UTF_8)
                // 지금까지의 설정을 바탕으로 UriComponents 객체를 생성.
                .build()
                // UriComponents 객체를 Java의 URI 객체로 변환.
                .toUri();

        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                //네이버 클라이언트 id
                .header("X-Naver-Client-Id", clientId)
                //네이버 클라이언트 secret
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        // Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
        ObjectMapper om = new ObjectMapper();
        NaverResultVO resultVO = null;
        try {
            resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

// resultVO의 null 여부를 확인
        if (resultVO != null) {
            List<NewsVO> news = resultVO.getItems();
            model.addAttribute("news", news);
        } else {
            // 여기서는 resultVO가 null일 때의 처리 로직을 추가할 수 있습니다.
            // 예: 로그를 남기거나, 오류 페이지로 리다이렉트하는 등의 처리
            System.out.println("Error: resultVO is null");
        }

        return "/news/list";

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
    }

}