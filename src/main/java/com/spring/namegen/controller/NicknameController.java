package com.spring.namegen.controller;

import com.spring.namegen.dto.CarDTO;
import com.spring.namegen.dto.KeywordDTO;
import com.spring.namegen.dto.ResponseDTO;
import com.spring.namegen.service.NicknameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nickname")
public class NicknameController {

    private final NicknameService service;

    public NicknameController(NicknameService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public ResponseEntity<ResponseDTO> gameNickname(@RequestBody KeywordDTO keywordDTO) {

        ResponseDTO content = service.generateGameNickname(keywordDTO.getKeyword());
        return ResponseEntity.ok(content);
    }

    @PostMapping("/instagram")
    public ResponseEntity<ResponseDTO> instagramNickname(@RequestBody KeywordDTO keywordDTO) {

        ResponseDTO content = service.generateInstagramNickname(keywordDTO.getKeyword());
        return ResponseEntity.ok(content);
    }

    @PostMapping("/car")
    public ResponseEntity<ResponseDTO> carNickname(@RequestBody CarDTO carDTO) {

        ResponseDTO content = service.generateCarNickname(carDTO.getCar(), carDTO.getKeyword());
        return ResponseEntity.ok(content);

    }

}
