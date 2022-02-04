package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback; insert문 보는 방법 1
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.EntityManager; insert문 보는 방법 2

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // @Autowired EntityManager em; insert문 보는 방법 2

    @Test
    //@Rollback(false) insert문 보는 방법 1
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        // em.flush(); insert문 보는 방법 2
        assertEquals(member,memberRepository.findOne(saveId)); // 아이디 같은 지 확인
    }


    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when

        memberService.join(member1);
        // try catch 문 없으면 예외가 발생함
        // w? 중복 회원 예외에서 중복 회원이면 예외가 발생하게 코드를 썼으니까 !
        // 그래서 테스트에서 예외를 잡을 수 있도록 try catch를 써줘야함 > 테스트에서 예외 처리 없이 실행시켜야 하니까
        // memberService.join(member2); // 예외 발생 하는 걸 볼 수 있음 !

        /*
        아래 줄을 위에 코드(@Test(expected = IllegalStateException.class))로 바꿀 수 있음
        try{
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }
         */
        memberService.join(member2);

        //then
        fail("예외가 발생해야 하는데 안됌 fail."); // 여기로 오면 fail -> 테스트 실패 / 위에서 예외가 발생했어야함
    }


}