package com.spring.namegen.dto;

public class KeywordDTO {

    private String keyword;

    /*
    Spring에서는 JSON 요청 본문을 @RequestBody로 받을 때,
    기본 생성자를 사용해서 객체를 만든 뒤,
    setter 메서드로 값을 주입한다
    즉, 기본 생성자 + setter만 있어도 충분
     */
    public KeywordDTO() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
