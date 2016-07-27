package com.cqs.bishe.dao.mysqldb;

import com.cqs.bishe.bean.Fund;
import com.cqs.bishe.bean.FundOrder;
import com.cqs.bishe.bean.FundSelectCondition;
import com.cqs.bishe.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cqs on 16-6-13.
 */


@Repository
public interface FundMapper {
    public boolean insert(@Param("fund") Fund fund);

    public boolean start(@Param("company") String company);

    public boolean stop(@Param("company") String company);

    public List<Fund> select(@Param("condition")FundSelectCondition condition,RowBounds rowBounds);

    public int count(@Param("condition")FundSelectCondition condition);

    public boolean purchase(@Param("order")FundOrder order);
}
