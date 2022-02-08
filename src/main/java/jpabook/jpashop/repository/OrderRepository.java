package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    /**
     *  최종 방법 ; Querydsl ; 추후에 설명 및 변경 예정
     */
/*
    public List<Order> findAllByCriteria(OrderSearch orderSearch){

        QOrder oder = QOrder.order;
        QMember member = QMember.member;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();

    }
 */
    /** JPA Criteria 표준
     * 세번쨰로 짠 코드 -> JPA에서 제공을 해주나 유지 보수가 안 좋음, 쿼리가 직관적이지 않음
     * */

    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder(); // 엔티티 매니저 - getCriteriaBuilder
        CriteriaQuery<Order> cq = cb.createQuery(Order.class); // 응답 타입 설정

        Root<Order> o = cq.from(Order.class); // 시작하는 엔티티
        Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 조인

        List<Predicate> criteria = new ArrayList<>(); // 동적 쿼리에 대한 조건 조합을 함

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }


    /**
     * 두번째로 짠 코드 -> JPQL를 조건에 맞춰 문자로 나눠 생성
    But, 번거로움 + 버그 찾기 힘듬
     **/
/*
    public List<Order> findAll(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) { // 값이 있으면
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }
 */


    /**
     * 처음으로 짰던 코드 -> 동적 처리가 필요해서 수정 필요
     * */
/*
    public List<Order> findAll(OrderSearch orderSearch) {
        List<Order> resultList = em.createQuery("select o from Order o join o.member m" +
                        //"where o.status=:status" +
                        //"and m.name like :name",
                        // 원래라면 위 처럼 써야되는데 But, 상태랑 이름이 선택되어 있지 않으면 다 가져와야함 > 동적처리는 아직 선택되어 있지 않음
                        Order.class)
                .setParameter("status",orderSearch.getOrderStatus())
                .setParameter("name",orderSearch.getMemberName())
                //.setFirstResult(100) // 시작점 100부터 지정(필요시 사용)
                .setMaxResults(1000)// 결과 제한 1000개까지만
                .getResultList();// order 조회 후 멤버를 join (여기선 참조하는 스타일로 join)
    }
*/



}
