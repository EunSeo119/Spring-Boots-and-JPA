package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository     //스프링 빈에 등록하여 컴포넌트 스캔 할 수 있게 만들어줌!
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);     //member 집어넣어 저장하게 해주는 명령어, insert 쿼리를 날려줌!
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);       //단건조회! (타입, pk)넣어줌!
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class)        //(jpql이랑-sql은 테이블 조회 jpql은 엔티티 조회 느낌!, 반환타입)
                .getResultList();

        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
