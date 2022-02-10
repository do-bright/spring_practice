package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // form의 정보 post
    public String create(@Valid MemberForm form, BindingResult result) { // 파라미터 MemberForm 받음
        // 회원이름을 필수로 사용하고 싶음 -> @Valid 표시 ; 해당 표시로 form에 있는 NotEmpty를 쓸 수 있음 (Validation - NotEmpty)
        // BindingResult ; 스프링 제공, 오류 있으면 오류를 담을 수 (가지고 있을 수) 있음 (원래라면 그냥 오류 정보가 튕겨 나감)

        if (result.hasErrors()) {
            return "members/createMemberForm";
        } // 에러가 있으면 members/createMemberForm를 return // 원래 페이지에서 MemberForm 에 명시했던 회원 이름은 필수 입니다 메세지가 나옴
        // BindingResult로 에러를 가지고 있음 + createMemberForm.html에 에러 관련 코드 작성 + NotEmpty를 인식함

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode()); // Memberform사용해서 주소 세팅

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member); // 저장 끝
        return "redirect:/"; // 저장 끝나고 갈 page 명시 ; 보통은 저장하면 redirect 로 홈으로 보냄 -> redirect:/
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers(); // 모든 멤버 조회
        model.addAttribute("members", members); // members 를 꺼냄
        return "members/memberList"; // 화면 출력
    }
}
