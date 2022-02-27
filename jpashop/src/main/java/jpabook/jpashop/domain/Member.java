package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded   //내장 타입을 포함했다 어노테이션
    private Address address;

    @OneToMany(mappedBy = "member")  //mappedBy는 '나는 주인이 아니에요, 맵핑된거에요'라는 뜻(읽기전용), 주인은 걍 냅두면 됨
    private List<Order> orders = new ArrayList<>();
}
