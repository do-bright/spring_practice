package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    // alt enter ; 클래스 Item 생성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order; // 주문 -> 주문상세 : 일대다 주문상세 -> 주문 : 다대일

    private int orderPrice; //주문 가격
    private int count; //주문 수량


    // 주문상세 엔티티대로 작성 ; id item:Item order:Order orderPrice count

}
