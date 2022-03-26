package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //다른 스타일의 생성자 막는 역할(ex 직접생성하는)
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

    //==생성 매서드==//  -이런 복잡한 생성은 별도의 생성 매서드가 있으면 좋다
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {    //...은 여러개를 받을 수 있다는 뜻
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);     //처음 상태는 order로
        order.setOrderDate(LocalDateTime.now());    //현재시간으로 잡음
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {      //배송완료되면(COMP가 배송완료)
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();       //이 클래스는 이거 하나로 간략하게 퉁칠 수도 있음!(alt+enter => sum 어쩌고 => ctrl+alt+N 으로 또 합치기)
    }
}
