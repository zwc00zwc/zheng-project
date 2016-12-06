package domain.manager;

import domain.dao.MemberRoleDao;
import domain.dao.PermDao;
import domain.dao.RolePermDao;
import domain.model.system.Perm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utility.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XR on 2016/8/31.
 */
@Component
public class PermManager {
    @Autowired
    private PermDao permDao;
    @Autowired
    private MemberRoleDao memberRoleDao;
    @Autowired
    private RolePermDao rolePermDao;
    public List<Perm> queryByMemberIdAndParentId(Long memberid,Long parentid){
        List<Perm> list=new ArrayList<Perm>();
        List<Long> roleids=new ArrayList<Long>();
        String rolestr= memberRoleDao.queryRolesByMemberId(memberid);
        roleids= StringUtility.StringToListLong(rolestr);
        List<String> permstr= rolePermDao.queryPermIdByRoleIds(roleids);
        List<Long> ids=new ArrayList<Long>();
        for (String perm:permstr) {
            ids.addAll(StringUtility.StringToListLong(perm));
        }
        list=permDao.queryByIdsAndParentId(ids,parentid);
        return list;
    }

    public List<Perm> queryByRoleIdAndType(Long roleid,Integer type){
        String perms= rolePermDao.queryPermsByRoleId(roleid);
        List<Long> ids=StringUtility.StringToListLong(perms);
        return permDao.queryByIdsAndType(ids,type);
    }

    public List<String> queryUrlByMemberId(Long memberid){
        List<String> strings=new ArrayList<String>();
        List<Perm> list=new ArrayList<Perm>();
        List<Long> roleids=new ArrayList<Long>();
        String rolestr= memberRoleDao.queryRolesByMemberId(memberid);
        roleids= StringUtility.StringToListLong(rolestr);
        List<String> permstr= rolePermDao.queryPermIdByRoleIds(roleids);
        List<Long> ids=new ArrayList<Long>();
        for (String perm:permstr) {
            ids.addAll(StringUtility.StringToListLong(perm));
        }
        return permDao.queryUrlByListId(ids);
    }

    public List<String> queryIds(){
        List<String> list=new ArrayList<String>();
        list= permDao.queryIds();
        return list;
    }

    public List<Perm> queryList(){
        return permDao.queryList();
    }

    public Perm queryById(Long id){
        return permDao.queryById(id);
    }

    public List<Perm> queryByType(List<Integer> types){
        List<Perm> list=new ArrayList<Perm>();
        list= permDao.queryByType(types);
        return list;
    }

    public List<Perm> queryByParentId(Long id){
        return permDao.queryByParentId(id);
    }

    public int insertPerm(Perm perm){
        return permDao.insertPerm(perm);
    }

    public int updateById(Perm perm){
        return permDao.updateById(perm);
    }

    public int deleteById(Long id){
        return permDao.deleteById(id);
    }
}
