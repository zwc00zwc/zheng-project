package spiservice.system;

import domain.dao.PermDao;
import domain.dto.PermLevelDto;
import domain.model.system.Perm;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.PermissionSPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
@Transactional
@Service("permissionSPIService")
public class PermissionSPIService implements PermissionSPI {
    @Autowired
    private PermDao permDao;
    public List<Perm> queryList() {
        return permDao.queryList();
    }

    public int insertPerm(Perm perm) {
        return permDao.insertPerm(perm);
    }

    public int updatePerm(Perm perm) {
        return permDao.updateById(perm);
    }

    public int deletePerm(Long id) {
        return permDao.deleteById(id);
    }

    public Perm queryById(Long id) {
        return permDao.queryById(id);
    }

    public List<PermLevelDto> queryPermByLevel() {
        List<PermLevelDto> list=new ArrayList<PermLevelDto>();
        List<Perm> permList1= permDao.queryByParentId((long)0);
        for (Perm perm1:permList1) {
            PermLevelDto dto1=new PermLevelDto();
            dto1.setId(perm1.getId());
            dto1.setDisplayName(perm1.getDisplayName());
            List<PermLevelDto> list2=new ArrayList<PermLevelDto>();
            List<Perm> permList2= permDao.queryByParentId(perm1.getId());
            for (Perm perm2:permList2) {
                PermLevelDto dto2=new PermLevelDto();
                dto2.setId(perm2.getId());
                dto2.setDisplayName(perm2.getDisplayName());
                List<PermLevelDto> list3=new ArrayList<PermLevelDto>();
                List<Perm> permList3=permDao.queryByParentId(perm2.getId());
                for (Perm perm3:permList3) {
                    PermLevelDto dto3=new PermLevelDto();
                    dto3.setId(perm3.getId());
                    dto3.setDisplayName(perm3.getDisplayName());
                    list3.add(dto3);
                }
                dto2.setPermLevelDtos(list3);
                list2.add(dto2);
            }
            dto1.setPermLevelDtos(list2);
            list.add(dto1);
        }
        return list;
    }

    public List<Perm> queryByType(List<Integer> types) {
        return permDao.queryByType(types);
    }
}
