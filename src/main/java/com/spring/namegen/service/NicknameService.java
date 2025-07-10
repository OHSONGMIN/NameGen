package com.spring.namegen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<String> requestNicknamesFromOpenAi(String systemPrompt, String userPrompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {
                            "role": "system",
                            "content": "%s"
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }
                """.formatted(model, systemPrompt, userPrompt);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                apiUrl,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String content = objectMapper
                    .readTree(response.getBody())
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText();

            List<String> nicknameList = objectMapper
                    .readValue(content, new TypeReference<List<String>>() {}
                    );
            return nicknameList;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseDTO generateGameNickname(String keyword) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {
                          "role": "system",
                          "content": "너는 재치 넘치고 기발한 게임 닉네임을 만들어내는 최고의 생성기야. 사용자 요청에 반드시 JSON 배열 형태로만 응답해야 해. 설명 없이 배열만 출력해야 해."
                        },
                        {
                          "role": "user",
                          "content": "\\"%s\\"에서 연상되는 단어를 활용해 2~6글자 닉네임 5개를 추천해줘. 의외의 조합이나 언어유희를 써도 좋아. 말장난, 줄임말, 짧은 소리 위주로 만들어줘. 살짝 B급 감성도 괜찮아. 단, 귀엽거나 임팩트 있게. 출력 형식은 반드시 [\\"닉1\\", \\"닉2\\", \\"닉3\\", \\"닉4\\", \\"닉5\\"] 만 보여줘."
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
            List<String> nicknameList = new ObjectMapper()
                    .readValue(content, new TypeReference<List<String>>() {}
                    );
            return new ResponseDTO(nicknameList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseDTO generateInstagramNickname(String keyword) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {
                            "role": "system",
                            "content": "너는 감각적이고 센스 있는 인스타그램 아이디를 만들어주는 최고의 생성기야. 사용자 요청에 반드시 JSON 배열 형태로만 응답해야 해. 설명 없이 배열만 출력해야 해."
                        },
                        {
                            "role": "user",
                              "content": "\\"%s\\"에서 연상되는 단어를 활용해 감각적이고 센스 있는 인스타그램 아이디 5개를 추천해줘. 의외의 단어 조합이나 센스 있는 말장난도 좋아. 줄임말이나 짧고 간결한 느낌이면 더 좋아. 부드럽고 감각적인 분위기를 유지해줘. 트렌디하고 기억에 남는 느낌으로 만들어줘. 알파벳, 숫자, 밑줄(_)만 사용 가능해. 출력 형식은 반드시 [\\"아이디1\\", \\"아이디2\\", \\"아이디3\\", \\"아이디4\\", \\"아이디5\\"] 만 보여줘. 근데 밑줄(_) 넣을거면 감각적으로 넣어줘 냅다 넣지 말고. 그렇다고 모든 단어에 밑줄을 넣는게 아니라, 요즘 인기 있는 인스타 아이디처럼 감각적으로 만들어달라는 말이야"
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
            List<String> nicknameList = new ObjectMapper()
                    .readValue(content, new TypeReference<List<String>>() {}
                    );
            return new ResponseDTO(nicknameList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseDTO generateCarNickname(String car, String keyword) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String condition = Stream.of(
                car != null && !car.isBlank() ? "차종 \\\"" + car.trim() + "\\\"" : null,
                keyword != null && !keyword.isBlank() ? "키워드 \\\"" + keyword.trim() + "\\\"" : null
        )
                .filter(value -> value != null)
                .collect(Collectors.joining("와(과) "));

        String body = """
                {
                    "model": "%s",
                    "messages": [
                        {
                            "role": "system",
                            "content": "너는 창의적이고 감각적인 자동차 애칭을 만들어주는 최고의 생성기야. 사용자 요청에 반드시 JSON 배열 형태로만 응답해야 해. 설명 없이 배열만 출력해야 해."
                        },
                        {
                            "role": "user",
                            "content": "%s을(를) 바탕으로 자동차 애칭 5개를 추천해줘. 줄임말, 말장난, 센스 있는 조합도 괜찮고, 짧고 인상 깊은 이름이면 더 좋아. 출력은 반드시 [\\\"애칭1\\\", \\\"애칭2\\\", \\\"애칭3\\\", \\\"애칭4\\\", \\\"애칭5\\\"] 형식의 JSON 배열만 보여줘."
                        }
                    ]
                }
                
                """.formatted(model, condition);

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
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            List<String> nicknameList = new ObjectMapper()
                    .readValue(content, new TypeReference<List<String>>() {}
                    );
            return new ResponseDTO(nicknameList);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
