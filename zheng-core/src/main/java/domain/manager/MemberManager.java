package domain.manager;

import domain.dao.MemberDao;
import domain.dao.MemberRoleDao;
import domain.model.system.Member;
import domain.model.system.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/31.
 */
@Component
public class MemberManager {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberRoleDao memberRoleDao;

    public List<Member> querylist(){
        return memberDao.querylist();
    }

    public Member queryByUsername(String username){
        return memberDao.queryByUsername(username);
    }

    public Member queryById(Long id){
        return memberDao.queryById(id);
    }

    public boolean insertmember(Member member,String ids){
        if (memberDao.insertmember(member)>0){
            MemberRole memberRole=new MemberRole();
            memberRole.setRoleIds(ids);
            memberRole.setMemberId(member.getId());
            memberRole.setCreateTime(new Date());
            memberRoleDao.insertMemberRole(memberRole);
            return true;
        }
        return false;
    }

    public int deleteMember(Long id){
        return memberDao.deleteMember(id);
    }

    public int updateStatus(Long memberid,Short status){
        return memberDao.updateStatus(memberid,status);
    }
}
