package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service    //컴포넌트 스캔의 대상이 되어 자동으로 스프링 빈에 등록!
@Transactional(readOnly = true)      //jpa의 모든 데이터 변경이나 로직들은 transcation안에서 이루어져야한다!(읽기만 할 수 있게 true로해 준다!-리소스 줄일 수 있다!)
@RequiredArgsConstructor        //final 있는 필드만 가지고 생성자를 만들어줌!
public class MemberService {

    private final MemberRepository memberRepository;    //변경할 일 없을 땐 앞에 final을 써줌!

//    @Autowired      //스프링이 스프링 빈에 등록되어 있는 멤버 repository를 인젝션 해줌!(필드 인젝션)
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//@RequiredArgsConstructor 썼기 때문에 없어도 됨! @Autowired는 생성자만 있을 땐 자동으로 붙여줌!

    /**
     * 회원가입
     */
    @Transactional      //얘는 쓰기도 해야하니까 따로 트렌젝션을 다시 넣어준다!(readOnly = false가 디폴트!)
    public Long join(Member member) {

        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();   //id 반환
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
