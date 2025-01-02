package com.ict.edu3.domain.NewsApiController;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiExamSearchNew {

    @GetMapping("/searchNews")
    public List<Map<String, String>> searchNew() {
        log.info("searchNews--------------------");
        String clientId = "sbwKnTVcaUBjn1A9TRaN"; // 애플리케이션 클라이언트 아이디
        String clientSecret = "I4mc5hlxSi"; // 애플리케이션 클라이언트 시크릿

        String text;
        try {
            text = URLEncoder.encode("바이오", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        // Naver API URL
        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=50";

        log.info("API 요청 URL: {}", apiURL);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        // API 호출 및 크롤링 데이터 추가
        String response = get(apiURL, requestHeaders);
        return parseNewsWithThumbnails(response);
    }

    // 뉴스 데이터 파싱 및 썸네일 추가
    private List<Map<String, String>> parseNewsWithThumbnails(String responseBody) {
        List<Map<String, String>> newsList = new ArrayList<>();
        org.json.JSONObject jsonObject = new org.json.JSONObject(responseBody);
        org.json.JSONArray items = jsonObject.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            org.json.JSONObject item = items.getJSONObject(i);

            Map<String, String> newsItem = new HashMap<>();
            newsItem.put("title", item.getString("title"));
            newsItem.put("link", item.getString("link"));
            newsItem.put("originallink", item.getString("originallink"));
            newsItem.put("description", item.getString("description"));
            newsItem.put("author", item.optString("author", "정보 없음"));

            String pubDate = item.optString("pubDate", "날짜 없음");
            newsItem.put("date", formatPubDate(pubDate));

            // 썸네일 크롤링
            String thumbnail = fetchThumbnail(item.getString("originallink"));
            newsItem.put("thumbnail", thumbnail);

            newsList.add(newsItem);
        }
        return newsList;
    }

    
    // pubDate를 "2024.12.12 화요일" 형식으로 변환하는 메서드
    private String formatPubDate(String pubDate) {
        try {
            // pubDate 형식 예시: "Tue, 12 Dec 2024 12:00:00 +0900"
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = inputFormat.parse(pubDate);

            // 원하는 출력 형식으로 변환: "yyyy.MM.dd E" (예: 2024.12.12 화요일)
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy.MM.dd E", Locale.KOREAN);
            return outputFormat.format(date);
        } catch (Exception e) {
            log.error("pubDate 변환 실패: {}", pubDate, e);
            return pubDate; // 변환 실패시 원본 pubDate 반환
        }
    }

    // OG 이미지 크롤링 메서드
    private String fetchThumbnail(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element ogImage = doc.selectFirst("meta[property=og:image]");
            return ogImage != null ? ogImage.attr("content") : "https://example.com/default-thumbnail.jpg"; // 기본 썸네일
        } catch (IOException e) {
            log.error("OG 이미지 크롤링 실패: {}", url, e);
            return "https://example.com/default-thumbnail.jpg"; // 기본 썸네일
        }
    }

    // GET 요청 메서드 (기존 코드 유지)
    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");

            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    // URL 연결 메서드 (기존 코드 유지)
    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    // 응답 읽기 메서드 (기존 코드 유지)
    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
