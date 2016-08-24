package spiservice.system;

import domain.dao.MemberDao;
import domain.model.system.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spi.system.MemberSPI;

import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
@Service("memberSPIService")
public class MemberSPIService implements MemberSPI {
    @Autowired
    private MemberDao memberDao;
    public List<Member> querylist() {
        return memberDao.querylist();
    }
}
