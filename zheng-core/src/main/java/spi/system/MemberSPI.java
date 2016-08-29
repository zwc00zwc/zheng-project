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

    Member queryByUsername(String username);

    List<AuthPerm> queryAuthPerm(Long memberid);

    Member insertMember(Member member,String roleids);

    int deleteMember(Long id);

    int updateStatus(Long memberid,Short status);
}
