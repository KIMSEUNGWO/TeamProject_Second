package aug.laundry.service.login;

import aug.laundry.domain.*;
import aug.laundry.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Optional;

@Service
public interface LoginService {

    public void naverLogin(HttpServletRequest request, Model model, HttpSession session);

    public void kakaoProcess(String code, HttpSession session);

    public Optional<? extends MemberParent> login(Member member);

    public int keepLogin(String sessionId, Date limit, Long memberId);

    public Member checkUserWithSessionId(String sessionId);

    public int renewLoginTime (Long memberId);


}
