package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    // Test에 Transactional 어노테이션이 있으면 테스트가 끝난 다음 db rollback을 해준다.
    // 영속성 컨텍스트에서 디비에 flush 자체를 안한다.
    // 디비에서 확인하고 싶으면 Rollback을 끌 수 있다.
    @Test
    @DisplayName("회원가입")
    public void join() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복 회원 예외")
    public void duplicateMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);  // 예외가 발생해야 한다.
        } catch (IllegalStateException e) {
            return;
        }

        //then
        Assertions.fail("예외가 발생해야 한다.");
    }
}