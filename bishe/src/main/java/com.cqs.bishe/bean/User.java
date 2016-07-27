package com.cqs.bishe.bean;

import com.cqs.bishe.tool.MD5Tool;

import java.util.Date;

/**
 * Created by chen on 2015/12/1.
 */


public class User {

    public static enum UserType {
        normal(0), admin(1);

        UserType(int type) {
            this.id = type;
        }

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public static UserType getById(int id) {
            for (UserType userType : UserType.values()) {
                if (userType.getId() == id) {
                    return userType;
                }
            }
            return null;
        }
    }

    public static class Builder {
        private User user;

        public Builder start() {
            user = new User();
            return this;
        }

        public User end() {
            user.setCreateTime(new Date());
            return user;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setPwd(String pwd) {
            user.setPwd(MD5Tool.encodePwdByMd5(pwd));
            return this;
        }

        public Builder setCertNo(String certNo) {
            user.setCertNo(certNo);
            return this;
        }

        public Builder setType(int type) {
            user.setType(type);
            return this;
        }

        public Builder setMobile(String mobile) {
            user.setMobile(mobile);
            return this;
        }

    }

    private Long id;
    private String name;
    private String pwd;
    private String certNo;
    private Date createTime;
    private String mobile;
    // user type
    // 0 normal,1 admin
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", certNo='" + certNo + '\'' +
                ", createTime=" + createTime +
                ", mobile='" + mobile + '\'' +
                ", type=" + type +
                '}';
    }
}
