package com.example.sqlitedeme.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/5.
 */

@Entity
public class FruitsBean {

    @Id(autoincrement = true)
    private Long id;

    private String name;

    private int num;

    @Generated(hash = 867237312)
    public FruitsBean(Long id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
    }

    @Generated(hash = 420745524)
    public FruitsBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
