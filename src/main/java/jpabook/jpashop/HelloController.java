package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello") // hello url 설정
    public String hello(Model model){
        model.addAttribute("data","hello!!!"); // 값 담기
        return "hello"; // resources - templates - hello html 반환해라 -> 만들어 줘야 함
    }
}
