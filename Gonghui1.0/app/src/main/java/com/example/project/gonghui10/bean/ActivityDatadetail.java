package com.example.project.gonghui10.bean;

/**
 * Created by Meng on 2016/8/21.
 */
public class ActivityDatadetail {
    private String id;
    private String sTitle;
    private String sDesc;
    private String sPubtime;
    private String sPosition;
    private String sGatherPosition;
    private String sSignupBtime;
    private String sSignupEtime;
    private String sBtime;
    private String sEtime;
    private String sNumber;
    private String sCurrentNumber;
    private String praise;
    private String collection;
    private String comment;
    private String transmit;
    private String isTransmit;
    private String transmitComment;
    private String username;
    private String sex;
    private String face;
    private String cName;
    private String sName;

    public ActivityDatadetail(String id, String sTitle, String sDesc, String sPubtime,
                              String sPosition, String sGatherPosition, String
                                      sSignupBtime, String sSignupEtime, String sBtime,
                              String sEtime, String sNumber, String sCurrentNumber,
                              String praise, String collection, String comment, String transmit,
                              String isTransmit, String transmitComment, String username,
                              String sex, String face, String cName, String sName) {
        this.id = id;
        this.sTitle = sTitle;
        this.sDesc = sDesc;
        this.sPubtime = sPubtime;
        this.sPosition = sPosition;
        this.sGatherPosition = sGatherPosition;
        this.sSignupBtime = sSignupBtime;
        this.sSignupEtime = sSignupEtime;
        this.sBtime = sBtime;
        this.sEtime = sEtime;
        this.sNumber = sNumber;
        this.sCurrentNumber = sCurrentNumber;
        this.praise = praise;
        this.collection = collection;
        this.comment = comment;
        this.transmit = transmit;
        this.isTransmit = isTransmit;
        this.transmitComment = transmitComment;
        this.username = username;
        this.sex = sex;
        this.face = face;
        this.cName = cName;
        this.sName = sName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsDesc() {
        return sDesc;
    }

    public void setsDesc(String sDesc) {
        this.sDesc = sDesc;
    }

    public String getsPubtime() {
        return sPubtime;
    }

    public void setsPubtime(String sPubtime) {
        this.sPubtime = sPubtime;
    }

    public String getsPosition() {
        return sPosition;
    }

    public void setsPosition(String sPosition) {
        this.sPosition = sPosition;
    }

    public String getsGatherPosition() {
        return sGatherPosition;
    }

    public void setsGatherPosition(String sGatherPosition) {
        this.sGatherPosition = sGatherPosition;
    }

    public String getsSignupBtime() {
        return sSignupBtime;
    }

    public void setsSignupBtime(String sSignupBtime) {
        this.sSignupBtime = sSignupBtime;
    }

    public String getsSignupEtime() {
        return sSignupEtime;
    }

    public void setsSignupEtime(String sSignupEtime) {
        this.sSignupEtime = sSignupEtime;
    }

    public String getsBtime() {
        return sBtime;
    }

    public void setsBtime(String sBtime) {
        this.sBtime = sBtime;
    }

    public String getsEtime() {
        return sEtime;
    }

    public void setsEtime(String sEtime) {
        this.sEtime = sEtime;
    }

    public String getsNumber() {
        return sNumber;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public String getsCurrentNumber() {
        return sCurrentNumber;
    }

    public void setsCurrentNumber(String sCurrentNumber) {
        this.sCurrentNumber = sCurrentNumber;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public String getIsTransmit() {
        return isTransmit;
    }

    public void setIsTransmit(String isTransmit) {
        this.isTransmit = isTransmit;
    }

    public String getTransmitComment() {
        return transmitComment;
    }

    public void setTransmitComment(String transmitComment) {
        this.transmitComment = transmitComment;
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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    @Override
    public String toString() {
        return "ActivityDatadetail{" +
                "id='" + id + '\'' +
                ", sTitle='" + sTitle + '\'' +
                ", sDesc='" + sDesc + '\'' +
                ", sPubtime='" + sPubtime + '\'' +
                ", sPosition='" + sPosition + '\'' +
                ", sGatherPosition='" + sGatherPosition + '\'' +
                ", sSignupBtime='" + sSignupBtime + '\'' +
                ", sSignupEtime='" + sSignupEtime + '\'' +
                ", sBtime='" + sBtime + '\'' +
                ", sEtime='" + sEtime + '\'' +
                ", sNumber='" + sNumber + '\'' +
                ", sCurrentNumber='" + sCurrentNumber + '\'' +
                ", praise='" + praise + '\'' +
                ", collection='" + collection + '\'' +
                ", comment='" + comment + '\'' +
                ", transmit='" + transmit + '\'' +
                ", isTransmit='" + isTransmit + '\'' +
                ", transmitComment='" + transmitComment + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", face='" + face + '\'' +
                ", cName='" + cName + '\'' +
                ", sName='" + sName + '\'' +
                '}';
    }
}
