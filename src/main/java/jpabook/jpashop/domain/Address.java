package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

// Address - 임베디드 타입
@Embeddable // 주소 = 내장타입이라 표시 (Member.java - address - @Embedded)
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 값 타입은 기본적으로 값이 변경이 되면 안됌
    // 좋은 설계 : 생성할 때만 값이 세팅 + setter 를 아예 제공 x > 변경 불가능


    protected Address(){ // 값 타입이다 = 수정을 안 해도 된다, 변경이 불가능하다 표시 !
    }

    public Address(String city, String street, String zipcode)
    {
        this.city=city;
        this.street=street;
        this.zipcode=zipcode;
    }

}
