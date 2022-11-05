package com.wisdom.area.configurer.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wisdom.area.core.service.CurrentUserService;
import com.wisdom.area.pojo.vo.UserVO;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

import static com.wisdom.area.pojo.constant.DefaultParameterConstant.*;
import static com.wisdom.area.pojo.constant.DefaultParameterConstant.DEFAULT_NOT_DEL;
import static com.wisdom.area.pojo.constant.DefaultParameterConstant.DEFAULT_USER_ID;
import static com.wisdom.area.pojo.constant.DefaultParameterConstant.DEFAULT_USER_NAME;
import static com.wisdom.area.pojo.constant.DefaultParameterConstant.OPTIMISTIC_LOCK;

/**
 * 自动填充处理类
 *
 * @author zly
 * @see [ https://mp.baomidou.com/guide/auto-fill-metainfo.html ]
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private CurrentUserService currentUserService;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "optimisticLock", OPTIMISTIC_LOCK);
        this.fillStrategy(metaObject, "delFlag", DEFAULT_NOT_DEL);
        this.fillStrategy(metaObject, "createTime", new Date());
        this.fillStrategy(metaObject, "updateTime", new Date());

        UserVO user = currentUserService.getCurrentUser();
        this.fillStrategy(metaObject, "createBy", Objects.isNull(user) ? DEFAULT_USER_ID: user.getId());
        this.fillStrategy(metaObject, "createName", Objects.isNull(user) ? DEFAULT_USER_NAME : user.getName());
        this.fillStrategy(metaObject, "updateBy", Objects.isNull(user) ? DEFAULT_USER_ID: user.getId());
        this.fillStrategy(metaObject, "updateName", Objects.isNull(user) ? DEFAULT_USER_NAME : user.getName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UserVO user = currentUserService.getCurrentUser();
        this.fillStrategy(metaObject, "updateBy", Objects.isNull(user) ? DEFAULT_USER_ID : user.getId());
        this.fillStrategy(metaObject, "updateTime", new Date());
        this.fillStrategy(metaObject, "updateName", Objects.isNull(user) ? DEFAULT_USER_NAME : user.getName());
    }

    /**
     * -- 填充策略,默认有值不覆盖,如果提供的值为null也不填充
     * 填充策略,有值则覆盖
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value of Supplier
     * @return this
     * @since 3.3.0
     */
    @Override
    public MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        setFieldValByName(fieldName, fieldVal, metaObject);
        return this;
    }

}
