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
}
