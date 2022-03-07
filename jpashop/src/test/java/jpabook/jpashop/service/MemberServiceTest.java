package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest                 //위에랑 이 두가지가 있어야 스프링을 인티그레이드 해서 스프링 부트를 올려서 테스트 할 수 있다!(얘 없으면 Autowired 실패함!),스프링 컨테이너 안에서 테스트를 돌림!
@Transactional      //데이터를 롤백(변경)해야하기 때문에 이게 있어야한다! => 테스트가 반복해야하기 때문에 db에 데이터가 남으면 안됨!(테스트에서만 롤백함, 서비스 class나 repository에서는 롤백 x)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;        //쿼리(inset문) 등 보고 싶으면 추가!

    @Test
//    @Rollback(false)    //롤백 안하고 커밋을 하여 등록 쿼리(insert문)을 다 볼 수 있다!, db에서 눈으로 확인 가능!
    public void join() throws Exception {
        //given(이게 주어졌을 떄)
        Member member = new Member();
        member.setName("kim");

        //when(이렇게하면,실행)
        Long saveId = memberService.join(member);

        //then(이렇게 된다, 검증)
        em.flush();         //db에 쿼리 강제로 나감, 영속성 컨택스트에 있는 변경이나 등록 내용 db반영!=>하여 쿼리(insert문)을 다 볼 수 있다!  :롤백 시키면서 가능
        assertEquals(member, memberRepository.findOne(saveId));
     }

     @Test(expected = IllegalStateException.class)  //()안에 있는게 memberService.join(member2);에서의 try catch문의 생략을 도움!
     public void duplicate_member() throws Exception {
         //given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

         //when
         memberService.join(member1);
         memberService.join(member2);   //예외가 발생해야 한다!!(여기서 exception이 터져서 밖으로 나가야함

         //then
         fail("예외가 발생해야 한다.");      //여기로 오면 안됨! 테스트 실패한거!
      }

}