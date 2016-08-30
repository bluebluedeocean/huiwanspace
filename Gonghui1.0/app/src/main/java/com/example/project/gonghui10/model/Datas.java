package com.example.project.gonghui10.model;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.net.URL;

/**
 * Created by Yu on 2016/8/27 0027.
 */
public class Datas {

    private String id;
    private String username;
    private String sex;
    private String about;
    private long experience;
    private String email;
    private String phone;
    private URL face;
    private String sId;
    private String cId;
    private String regTime;
    private int activeFlag;
    private long follower;
    private long attention;
    private String lastLoginTime;
    private String lastJoinTime;
    private String lastPariseTime;
    private String lastCollectTime;
    private String latPubTime;
    private String lastCommentTime;
    private String sName;
    private String cName;
    private int level;

    public long getFollower() {
        return follower;
    }

    public void setFollower(long follower) {
        this.follower = follower;
    }

    public long getAttention() {
        return attention;
    }

    public void setAttention(long attention) {
        this.attention = attention;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastJoinTime() {
        return lastJoinTime;
    }

    public void setLastJoinTime(String lastJoinTime) {
        this.lastJoinTime = lastJoinTime;
    }

    public String getLastPariseTime() {
        return lastPariseTime;
    }

    public void setLastPariseTime(String lastPariseTime) {
        this.lastPariseTime = lastPariseTime;
    }

    public String getLastCollectTime() {
        return lastCollectTime;
    }

    public void setLastCollectTime(String lastCollectTime) {
        this.lastCollectTime = lastCollectTime;
    }

    public String getLatPubTime() {
        return latPubTime;
    }

    public void setLatPubTime(String latPubTime) {
        this.latPubTime = latPubTime;
    }

    public String getLastCommentTime() {
        return lastCommentTime;
    }

    public void setLastCommentTime(String lastCommentTime) {
        this.lastCommentTime = lastCommentTime;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getRemainXP() {
        return remainXP;
    }

    public void setRemainXP(long remainXP) {
        this.remainXP = remainXP;
    }

    public long getLackXP() {
        return lackXP;
    }

    public void setLackXP(long lackXP) {
        this.lackXP = lackXP;
    }

    public long getLevelXP() {
        return levelXP;
    }

    public void setLevelXP(long levelXP) {
        this.levelXP = levelXP;
    }

    private long remainXP;
    private long lackXP;
    private long levelXP;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URL getFace() {
        return face;
    }

    public void setFace(URL face) {
        this.face = face;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }
}
