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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")     //FK 라는거 지정해 주는거!
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   //cascade를 all로 해주면 entity마다 저장할 필요 없이 order하나만 저장하면 다 저장됨!
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")      //FK order쪽에서 잡아 주었지만 delivery에 FK 지정해 줘도 상관은 없음!
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문시간

    @Enumerated(EnumType.STRING)    //Enum 쓸 때 이 어노테이션 넣어야함, 타입은 무조건 string으로 하자..!
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==//     //양방향일 때 편하게 만들어 주는 것!(양쪽의 핵심적으로 컨트롤 하는 쪽에 넣어주는 것이 좋음!)-양방향인거 모두 찾아서 넣기!
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void seDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
