package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm"; // 반환할 html 명시
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, // 여기서 RequestParm 은 orderForm.html - form에서 name으로 사용 가능 (select - memberId)
                        @RequestParam("itemId") Long itemId, // orderForm.html - form에서 사용 가능 (select - name : itemId )
                        @RequestParam("count") int count) { //orderForm.html - form에서 사용 가능 (select - name : count)

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        // modelAttribute ; 모델에 orderSearch 추가 -> model.addAttribute("orderSearch", orderSearch) 문과 같은 뜻
        // model ; 화면 그리기용
        List<Order> orders = orderService.finddOrders(orderSearch);
        model.addAttribute("orders",orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
