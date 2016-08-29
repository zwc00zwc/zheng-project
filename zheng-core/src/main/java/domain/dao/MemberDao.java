package domain.dao;

import domain.model.system.Member;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface MemberDao {
    List<Member> querylist();

    Member queryByUsername(String username);

    int insertmember(Member member);

    int deleteMember(Long id);
}
