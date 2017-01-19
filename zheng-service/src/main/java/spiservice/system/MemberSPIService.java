package spiservice.system;

import domain.db.DynamicData;
import domain.dto.AuthPerm;
import domain.dto.MemberDto;
import domain.dto.MemberRoleDto;
import domain.manager.MemberManager;
import domain.manager.PermManager;
import domain.manager.RoleManager;
import domain.model.PageModel;
import domain.model.system.Member;
import domain.model.system.Perm;
import domain.model.system.Role;
import domain.model.system.query.MemberQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.MemberSPI;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by XR on 2016/8/24.
 */
//@Transactional
@Service("memberSPIService")
public class MemberSPIService implements MemberSPI {
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private PermManager permManager;

    @DynamicData(source = "ds2")
    public List<Member> querylist() {
        return memberManager.querylist();
    }

    @DynamicData(source = "ds2")
    public PageModel<MemberDto> queryPageList(MemberQuery query) {
        List<MemberDto> listDto=new ArrayList<MemberDto>();
        List<Member> list= memberManager.queryPagelist(query);
        int count=memberManager.queryCountPage(query);
        for (Member member:list) {
            MemberDto memberDto=new  MemberDto();
            try {
                PropertyUtils.copyProperties(memberDto,member);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            List<MemberRoleDto> liststr=new ArrayList<MemberRoleDto>();
            List<Role> rolelist= roleManager.queryByMemberId(member.getId());
            for (Role role:rolelist) {
                MemberRoleDto memberRoleDto=new MemberRoleDto();
                memberRoleDto.setId(role.getId());
                memberRoleDto.setName(role.getDisplayName());
                liststr.add(memberRoleDto);
            }
            memberDto.setRoles(liststr);
            listDto.add(memberDto);
        }
        PageModel<MemberDto> pageModel=new PageModel<MemberDto>(listDto,query.getCurrPage(),count,query.getPageSize());
        return pageModel;
    }

    public MemberDto queryById(Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        MemberDto memberDto=new MemberDto();
        Member member=memberManager.queryById(id);
        PropertyUtils.copyProperties(memberDto,member);
        //List<Role> rolelist= roleDao.queryByMemberId(id);
        List<Role> rolelist=roleManager.queryByMemberId(id);
        List<MemberRoleDto> memberRoleDtos=new ArrayList<MemberRoleDto>();
        for (Role role:rolelist) {
            MemberRoleDto memberRoleDto=new MemberRoleDto();
            memberRoleDto.setId(role.getId());
            memberRoleDto.setName(role.getDisplayName());
            memberRoleDtos.add(memberRoleDto);
        }
        memberDto.setRoles(memberRoleDtos);
        return memberDto;
    }

    public Member queryByUsername(String username) {
//        AuthMemberDto authMemberDto=new AuthMemberDto();
        //Member member= memberDao.queryByUsername(username);
        Member member=memberManager.queryByUsername(username);
//        if (member!=null){
//            authMemberDto.setId(member.getId());
//            authMemberDto.setPhone(member.getPhone());
//            authMemberDto.setAddress(member.getAddress());
//            authMemberDto.setImgUrl(member.getImgUrl());
//            authMemberDto.setUserName(member.getUserName());
//            authMemberDto.setPassword(member.getPassword());
//            authMemberDto.setStatus(member.getStatus());
//
//            List<Perm> perms= permDao.queryByMemberIdAndParentId(member.getId(),(long)0);
//            List<AuthPerm> authpermlist=new ArrayList<AuthPerm>();
//            for (Perm perm:perms) {
//                AuthPerm authperm=new AuthPerm();
//                authperm.setName(perm.getDisplayName());
//                authperm.setUrl(perm.getUrl());
//                List<Perm> perms2= permDao.queryByMemberIdAndParentId(member.getId(),perm.getId());
//                List<AuthPerm> authpermlist2=new ArrayList<AuthPerm>();
//                for (Perm perm2:perms2) {
//                    AuthPerm authperm2=new AuthPerm();
//                    authperm2.setName(perm2.getDisplayName());
//                    authperm2.setUrl(perm2.getUrl());
//                    authpermlist2.add(authperm2);
//                }
//                authpermlist.add(authperm);
//            }
//            authMemberDto.setRoles(authpermlist);
//        }
        return member;
    }

    public List<AuthPerm> queryAuthPerm(Long memberid) {
        List<AuthPerm> authpermlist=new ArrayList<AuthPerm>();
        List<Perm> perms= permManager.queryByMemberIdAndParentId(memberid,(long)0);
        for (Perm perm:perms) {
            AuthPerm authperm=new AuthPerm();
            authperm.setName(perm.getDisplayName());
            authperm.setUrl(perm.getUrl());
            List<Perm> perms2= permManager.queryByMemberIdAndParentId(memberid,perm.getId());
            List<AuthPerm> authpermlist2=new ArrayList<AuthPerm>();
            for (Perm perm2:perms2) {
                AuthPerm authperm2=new AuthPerm();
                authperm2.setName(perm2.getDisplayName());
                authperm2.setUrl(perm2.getUrl());
                authpermlist2.add(authperm2);
            }
            authperm.setRoles(authpermlist2);
            authpermlist.add(authperm);
        }
        return authpermlist;
    }

    public Member insertMember(Member member, String roleids) {
        memberManager.insertmember(member,roleids);
        return member;
    }

    public int deleteMember(Long id) {
        return memberManager.deleteMember(id);
    }

    public int updateStatus(Long memberid, Short status) {
        return memberManager.updateStatus(memberid,status);
    }
}
