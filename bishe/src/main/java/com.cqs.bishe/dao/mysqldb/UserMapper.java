package com.cqs.bishe.dao.mysqldb;


import com.cqs.bishe.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by chen on 2015/12/1.
 */


@Repository
public interface UserMapper {
    public boolean insert(@Param("user") User user);

    public boolean delete(@Param("user") User user);

    public User getByMobile(@Param("mobile") String mobile);
}
