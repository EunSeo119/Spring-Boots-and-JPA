package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //어딘가에 내장 될 수 있다
@Getter     //값타입은 getter만 만듬! 변경할 수 없음!(setter제공x), 만들라면 복사하거나 해서 새로 만들어야함
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {   //함부로 new로 생성하면 안되겠네~
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
