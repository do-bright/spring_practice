package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    // 리스트 조회; 회원 목록 뿌리기
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
        //JPQL는 from의 대상이 테이블이 아닌 엔티티
        //List<Member> result = em.createQuery("select m from Member m",Member.class).getResultList();
        //return result; > ctrl alt n ; inline 한 줄로 작성
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
