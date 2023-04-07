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
    @JoinColumn(name = "member_id")
    private Member member;  // 주문 회원

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 주인으로 둔다.
    // 1:1 관계에서는 fk를 어디에 둬도 된다. 이때 보통 엑세스가 많이 발생하는 곳에 fk를 둔다.
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  // 배송 정보

    // LocalDateTime쓰면 하이버네이트가 자동으로 지원해준다.
    private LocalDateTime orderDate;  // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문의 상태 [ORDER, CANCEL]
}
