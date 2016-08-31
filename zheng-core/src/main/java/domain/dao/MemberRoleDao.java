package domain.dao;

import domain.model.system.MemberRole;

import java.util.List;

/**
 * Created by XR on 2016/8/23.
 */
public interface MemberRoleDao {

    String queryRolesByMemberId(Long memberid);

    int insertMemberRole(MemberRole memberRoles);

    int deleteByMemberId(long memberid);
}
