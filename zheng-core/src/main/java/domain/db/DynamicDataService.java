package domain.db;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by alan.zheng on 2017/1/3.
 */
@Aspect
@Component
public class DynamicDataService {
    @Pointcut("@annotation(domain.db.DynamicData)")
    public void methodPointcut(){

    }

    @Before("@annotation(dynamicData)")
    public void before(DynamicData dynamicData){
        if (StringUtils.isNotEmpty(dynamicData.source())){
            DynamicDataSource.changeDataSourceKey(dynamicData.source());
        }
    }
}
