package domain.mapper;

import domain.model.system.Member;
import domain.model.system.query.MemberQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface MemberMapper {
    List<Member> querylist();

    List<Member> queryPageList(@Param("query") MemberQuery query);

    int queryCountPage(@Param("query") MemberQuery query);

    Member queryByUsername(String username);

    Member queryById(Long id);

    int insertmember(Member member);

    int deleteMember(Long id);

    int updateStatus(@Param("memberid") Long memberid, @Param("status") Short status);
}
