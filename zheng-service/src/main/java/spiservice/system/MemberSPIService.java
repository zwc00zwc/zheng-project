package spiservice.system;

import domain.dao.MemberDao;
import domain.dao.PermDao;
import domain.dao.RoleDao;
import domain.dto.AuthMemberDto;
import domain.dto.AuthPerm;
import domain.dto.MemberDto;
import domain.model.system.Member;
import domain.model.system.Perm;
import domain.model.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.MemberSPI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by XR on 2016/8/24.
 */
@Transactional
@Service("memberSPIService")
public class MemberSPIService implements MemberSPI {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermDao permDao;
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

    public AuthMemberDto loginQuery(String username) {
        AuthMemberDto authMemberDto=new AuthMemberDto();
        Member member= memberDao.queryByUsername(username);
        if (member!=null){
            authMemberDto.setId(member.getId());
            authMemberDto.setPhone(member.getPhone());
            authMemberDto.setAddress(member.getAddress());
            authMemberDto.setImgUrl(member.getImgUrl());
            authMemberDto.setUserName(member.getUserName());
            authMemberDto.setPassword(member.getPassword());
            authMemberDto.setStatus(member.getStatus());

            List<Perm> perms= permDao.queryByMemberIdAndParentId(member.getId(),(long)0);
            List<AuthPerm> authpermlist=new ArrayList<AuthPerm>();
            for (Perm perm:perms) {
                AuthPerm authperm=new AuthPerm();
                authperm.setName(perm.getDisplayName());
                authperm.setUrl(perm.getUrl());
                List<Perm> perms2= permDao.queryByMemberIdAndParentId(member.getId(),perm.getId());
                List<AuthPerm> authpermlist2=new ArrayList<AuthPerm>();
                for (Perm perm2:perms2) {
                    AuthPerm authperm2=new AuthPerm();
                    authperm2.setName(perm2.getDisplayName());
                    authperm2.setUrl(perm2.getUrl());
                    authpermlist2.add(authperm2);
                }
                authpermlist.add(authperm);
            }
            authMemberDto.setRoles(authpermlist);
        }
        return authMemberDto;
    }

    public Member insertMember(Member member) {
        memberDao.insertmember(member);
        return member;
    }
}
