package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)    //거울이죵~!, 연관관계 매핑해죠
    private Order order;

    @Embedded   //내장 타입이기 때문에 써줌
    private Address address;

    @Enumerated(EnumType.STRING)    //Enum 쓸 때 이 어노테이션 넣어야함, 타입은 무조건 string으로 하자..!
    private DeliveryStatus status;  //READY, COMP
}
