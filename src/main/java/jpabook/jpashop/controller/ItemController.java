package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new") // url 관련 (페이지에 들어오게 + html에서 사용 할 수 있게)
    public String createForm(Model model){
        model.addAttribute("form",new BookForm()); // html에서 "form"으로 객체 사용 가능
        return "items/createItemForm"; // items/createItemForm.html 페이지 반환
    }

    @PostMapping("/items/new") // (페이지에서 생성할 것)
    public String create(BookForm form){ // 만든 BookForm 활용
        Book book = new Book(); // BookForm 생성 ( 책 객체 생성 )
        book.setName(form.getName()); // 이름을 BookForm을 통해 설정
        book.setPrice(form.getPrice());
        book.setStockQuantity((form.getStockQuantity()));
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book); // 책을 itemService 에 저장
        return "redirect:/items"; // 저장된 책 목록 페이지로 반환환
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList"; // 화면 반환 = 아이템 목록을 뿌려줄 화면
    }

}
