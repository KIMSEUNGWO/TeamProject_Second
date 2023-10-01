package aug.laundry.service.login;

import aug.laundry.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface MemberService {
    public Member selectOne(Long memberId);

    public Member selectId(String memberAccount);

    public void checkId(String memberAccount, Map<String, Object> msg);

    public void registerUser(Member memberDto);


    public List<Member> confirmId(String memberName, String memberPhone, String memberAccount);

    public Long updatePassword(Long memberId, Member memberDto);
    public int giveCoupon(Long memberId,Long couponId);
    public void updateAddress(Long memberId, Member member);

    public boolean getPhoneCnt(String memberPhone);

    boolean inviteCodeCheck(String inviteCode);
}
