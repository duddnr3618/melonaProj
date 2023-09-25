package com.news.news.search;

// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class BingNewsSearch {

    // Bing Search V7 구독 키를 환경 변수에 추가하세요.
    static String subscriptionKey = System.getenv("BING_SEARCH_V7_SUBSCRIPTION_KEY");

    // Bing Search V7 엔드포인트를 환경 변수에 추가하세요.
    static String endpoint = System.getenv("BING_SEARCH_V7_ENDPOINT") + "/bing/v7.0/news/search";

    static String searchTerm = "Microsoft";

    public static void main(String[] args) {
        try {
            System.out.println("Searching the Web for: " + searchTerm);

            SearchResults result = SearchNews(searchTerm);

            System.out.println("\nRelevant HTTP Headers:\n");
            for (String header : result.relevantHeaders.keySet())
                System.out.println(header + ": " + result.relevantHeaders.get(header));

            System.out.println("\nJSON Response:\n");
            System.out.println(prettify(result.jsonResponse));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public static SearchResults SearchNews (String searchQuery) throws Exception {
        // 검색 요청의 URL 구성 (엔드포인트 + 쿼리 문자열)
        URL url = new URL(endpoint + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

        // JSON 본문 수신
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        String response  = scanner.useDelimiter("\\A").next();

        // 반환할 결과 객체 구성
        SearchResults results = new SearchResults(new HashMap<String, String>(), response);

        // Bing 관련 HTTP 헤더 추출
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String header : headers.keySet()) {
            if (header == null) continue;     // 키가 null일 수 있음
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }

        scanner.close();
        stream.close();

        return results;
    }

    // JSON을 예쁘게 출력하기 위한 함수; GSON 파서를 사용하여 파싱하고 재직렬화
    public static String prettify(String json_text) {
        JsonObject json = JsonParser.parseString(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}

// 검색 결과를 담는 컨테이너 클래스; 관련 헤더와 JSON 데이터를 캡슐화
class SearchResults{
    HashMap<String, String> relevantHeaders;
    String jsonResponse;
    SearchResults(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}