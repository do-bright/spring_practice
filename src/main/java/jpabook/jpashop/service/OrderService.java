package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional // 변경하는 거라 readOnly = false 여야 함 (Transactional 기본 값)
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // 왜 리포지토리를 거쳐서 안하고 이거 하나만 하냐 ?
        // 원래라면 delivery 저장한다고 치면 deliveryRepository 있어서 delivery.save로 해주는 게 우선임
        // OrderItem도 마찬가지로 OrderItemRepository 있어서 OrderItem.save 해줘야함 (레포지토리를 통해서 값을 저장해줘야 한다는 의미)
        // -> cascade 때문임 (Order.java 중 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) 참고 )
        // -> order를 persist 하면 OrderItem도 다 persist 해줌
        // (Delivery도 가서 보면 다 All cascade 되어 있음)
        // 그래서 OrderItem, Delivery 가 해당 한줄로도 다 persist 가 된 것임

        // 그럼 이걸(Cascade = all) 사용해야 할 때는 언제인가?
        // 사용이 적을 때 ! 사용이 많은데 all 로 걸려있으면 뜻하지 않은 수정 및 삭제가 될 수 있음 -> 그땐 별도의 repository르 생성해야 함
        // 여기선 order만 OrderItem, delivery를 사용해서 ㄱㅊ은 것임
        // 정 모르겠으면 repository를 각각 만들다가 > 특정 한 게 사용이 적을 때 cascade를 적용해볼까? 하고 해주는 것도 방법임

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색
    public List<Order> finddOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
