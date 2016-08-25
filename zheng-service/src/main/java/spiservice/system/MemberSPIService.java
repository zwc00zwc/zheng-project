package spiservice.system;

import domain.dao.MemberDao;
import domain.dao.RoleDao;
import domain.dto.MemberDto;
import domain.model.system.Member;
import domain.model.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spi.system.MemberSPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
@Service("memberSPIService")
public class MemberSPIService implements MemberSPI {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private RoleDao roleDao;
    public List<Member> querylist() {
        return memberDao.querylist();
    }

    public List<MemberDto> querylistPage() {
        List<MemberDto> listDto=new ArrayList<MemberDto>();
        List<Member> list= memberDao.querylist();
        for (Member member:list) {
            MemberDto memberDto=new  MemberDto();
            memberDto.setId(member.getId());
            memberDto.setUserName(member.getUserName());
            memberDto.setPhone(member.getPhone());
            memberDto.setImgUrl(member.getImgUrl());
            memberDto.setStatus(member.getStatus());
            memberDto.setUpdateTime(member.getUpdateTime());
            memberDto.setCreateTime(member.getCreateTime());
            List<String> liststr=new ArrayList<String>();
            List<Role> rolelist= roleDao.queryByMemberId(member.getId());
            for (Role role:rolelist) {
                liststr.add(role.getDisplayName());
            }
            memberDto.setRoles(liststr);
            listDto.add(memberDto);
        }
        return listDto;
    }
}
