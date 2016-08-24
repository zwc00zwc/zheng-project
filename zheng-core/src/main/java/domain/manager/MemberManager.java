package domain.manager;

import domain.dao.MemberDao;
import domain.model.system.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by XR on 2016/8/24.
 */
public interface MemberManager {
    List<Member> querylist();
}
