package boot3.jwtAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public void test() {
        // 1. 생성(Create)
        memberRepository.save(new Member(1L, "A"));

        // 2. 조회(Read)
        Optional<Member> member = memberRepository.findById(1L);
        List<Member> all = memberRepository.findAll();

        // 3. 삭제(Delete)
        memberRepository.deleteById(1L);
    }
}