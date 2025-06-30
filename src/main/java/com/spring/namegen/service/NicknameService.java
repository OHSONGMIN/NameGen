package com.spring.namegen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.namegen.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NicknameService {

    private final RestTemplate restTemplate;

    public NicknameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    public Object generateGameNickname(String keyword) {
    /*
    TODO: DTO로 수정해야 함
     */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {
                              "role": "system",
                              "content": "너는 최고의 게임 닉네임 생성기야. 사용자 요청에 반드시 JSON 배열 형태로만 응답해야 해. 설명 없이 배열만 출력해야 해."
                            },
                            {
                              "role": "user",
                              "content": "\\"%s\\"라는 단어에서 영감을 받은 2~6자짜리 게임 닉네임 5개를 추천해줘. 형식은 꼭 다음처럼 응답해: [\\"닉1\\", \\"닉2\\", \\"닉3\\", \\"닉4\\", \\"닉5\\"]"
                            }
                    ]
                }
                """.formatted(model, keyword);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                apiUrl,
                entity,
                String.class
        );

        String content = null;
        try {
            content = new ObjectMapper()
                    .readTree(response.getBody())
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            List<String> nicknameLIst = new ObjectMapper()
                    .readValue(content, new TypeReference<List<String>>() {}
            );
            return new ResponseDTO(nicknameLIst);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
