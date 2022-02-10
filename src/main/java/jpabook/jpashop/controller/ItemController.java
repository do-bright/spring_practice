package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) { // item의 Id 에 따라 올 url 이 다르니까 -> @PathVariable 사용
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm(); // 업데이트 (수정)할 때 Book entity를 보내는 게 아니라 Book form을 보낼 것임
        // 내가 수정할 책의 정보 item에 담았으니까 item에서 정보를 get -> form의 정보로 set -> 화면에 뿌려주기
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItemForm(@ModelAttribute("form") BookForm form) {
        //(@PathVariable("itemId") Long itemId, Model model) { // 여기선 PathVariable 안 써줘도 됌 어차피 form 에서 정보(id)가 딸려옴

        // form 에서 입력한 정보를 get -> 책 정보로 set
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items"; // 아이템 목록 페이지로 반환
    }

}
