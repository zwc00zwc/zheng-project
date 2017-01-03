package domain.manager;

import domain.mapper.MemberMapper;
import domain.mapper.MemberRoleMapper;
import domain.model.system.Member;
import domain.model.system.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/31.
 */
@Transactional
@Component
public class MemberManager {
    @Autowired
    private MemberMapper memberDao;
    @Autowired
    private MemberRoleMapper memberRoleDao;

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
