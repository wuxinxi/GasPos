package com.szxb.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Tangren on 2017-12-05
 * 包名：com.szxb.entity
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Entity
public class EmpEntity {
    @Id(autoincrement = true)
    private Long id;
    private String empNo;
    private String rmk1;
    private String rmk2;
    private String rmk3;
    @Generated(hash = 2042798526)
    public EmpEntity(Long id, String empNo, String rmk1, String rmk2, String rmk3) {
        this.id = id;
        this.empNo = empNo;
        this.rmk1 = rmk1;
        this.rmk2 = rmk2;
        this.rmk3 = rmk3;
    }
    @Generated(hash = 444083660)
    public EmpEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmpNo() {
        return this.empNo;
    }
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    public String getRmk1() {
        return this.rmk1;
    }
    public void setRmk1(String rmk1) {
        this.rmk1 = rmk1;
    }
    public String getRmk2() {
        return this.rmk2;
    }
    public void setRmk2(String rmk2) {
        this.rmk2 = rmk2;
    }
    public String getRmk3() {
        return this.rmk3;
    }
    public void setRmk3(String rmk3) {
        this.rmk3 = rmk3;
    }

}
