package com.cqs.bishe.bean.response;


import com.cqs.bishe.bean.Fund;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cqs on 16-6-19.
 */
public class FundList implements Serializable{
    private int id;
    List<Fund> lists;

    public FundList() {
    }

    public FundList(int id, List<Fund> lists) {
        this.id = id;
        this.lists = lists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Fund> getLists() {
        return lists;
    }

    public void setLists(List<Fund> lists) {
        this.lists = lists;
    }
}
