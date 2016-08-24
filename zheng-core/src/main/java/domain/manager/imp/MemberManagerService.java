package domain.manager.imp;

import domain.dao.MemberDao;
import domain.manager.MemberManager;
import domain.model.system.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
@Service("memberManagerService")
public class MemberManagerService implements MemberManager {
    @Autowired
    private MemberDao memberDao;
    public List<Member> querylist() {
        return memberDao.querylist();
    }
}
