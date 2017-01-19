package spi.system;

import domain.dto.AuthMemberDto;
import domain.dto.AuthPerm;
import domain.dto.MemberDto;
import domain.model.PageModel;
import domain.model.system.Member;
import domain.model.system.query.MemberQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
public interface MemberSPI {
    List<Member> querylist();

    PageModel<MemberDto> queryPageList(MemberQuery query);

    MemberDto queryById(Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    Member queryByUsername(String username);

    List<AuthPerm> queryAuthPerm(Long memberid);

    Member insertMember(Member member,String roleids);

    int deleteMember(Long id);

    int updateStatus(Long memberid,Short status);
}
