package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기엔 readOnly 넣기 (데이터 변경 x)// Transactional; spring 제공
// readOnly가 많아서 전체에 적용
// -> readOnly가 아닌 부분에 @Transactional 명시해주기 (default가 false라서 읽기 전용 아닌 부분에 명시해주면 됌)
// 헷갈리면 그냥 readOnly인 부분에 각각 적용해주면 됌 !
@RequiredArgsConstructor
// final 이 있는 필드만 가지고 생성자를 만들어줌, 이거 써서 아래 부분 생략 가능
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

public class MemberService {

    private final MemberRepository memberRepository;
    // final ; 변경할 일이 없다

//    @Autowired // 생성자가 하나만 있는 경우 자동으로 인식해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional// default (readOnly = false)로 설정이 됌
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //중복 회원이면 예외 처리
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // @Transactional(readOnly = true) -> 전체로 설정
    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // @Transactional(readOnly = true) -> 전체로 설정
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
