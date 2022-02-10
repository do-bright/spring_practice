package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    //Logger log = LoggerFactory.getLogger(getClass())// Loger는 slf4j 꺼를 사용 > @Slf4j 으로 대체
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        return "home"; // home.html의 화면을 뿌려줌
    }
}
