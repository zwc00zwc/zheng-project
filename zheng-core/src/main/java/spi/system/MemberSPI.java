package spi.system;

import domain.dto.AuthMemberDto;
import domain.dto.AuthPerm;
import domain.dto.MemberDto;
import domain.model.system.Member;

import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
public interface MemberSPI {
    List<Member> querylist();

    List<MemberDto> querylistPage();

    Member loginQuery(String username);

    List<AuthPerm> queryAuthPerm(Long memberid);

    Member insertMember(Member member,String roleids);
}
