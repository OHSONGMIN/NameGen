package com.spring.namegen.dto;

import java.util.List;

public class ResponseDTO {

    private List<String> nicknames;

    public ResponseDTO() {}

    public ResponseDTO(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }
}