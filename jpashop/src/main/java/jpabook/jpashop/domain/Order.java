package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")     //FK 라는거 지정해 주는거!
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")      //FK order쪽에서 잡아 주었지만 delivery에 FK 지정해 줘도 상관은 없음!
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문시간

    @Enumerated(EnumType.STRING)    //Enum 쓸 때 이 어노테이션 넣어야함, 타입은 무조건 string으로 하자..!
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
