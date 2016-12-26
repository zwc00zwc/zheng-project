package domain.mapper;

import domain.model.system.MemberRole;

/**
 * Created by XR on 2016/8/23.
 */
public interface MemberRoleMapper {

    String queryRolesByMemberId(Long memberid);

    int insertMemberRole(MemberRole memberRoles);

    int deleteByMemberId(long memberid);
}
