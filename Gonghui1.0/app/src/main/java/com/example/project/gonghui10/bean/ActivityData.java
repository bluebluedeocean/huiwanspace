package com.example.project.gonghui10.bean;

/**
 * Created by 19950 on 2016/7/12.
 */
public class ActivityData {


    String id,title,face,nick_name,publish_time,publish_location,sign_up_begin_time,sign_up_end_time,activity_start_time,
            activity_finish_time,activity_location,numsign,numsigned,firstImage;

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public ActivityData(String id, String title, String face, String nick_name, String publish_time, String publish_location, String sign_up_begin_time,
                        String sign_up_end_time, String activity_start_time, String activity_finish_time, String activity_location, String numsign,
                        String numsigned, String firstImage) {

        this.id = id;
        this.title = title;
        this.face = face;

        this.nick_name = nick_name;
        this.publish_time = publish_time;
        this.publish_location = publish_location;
        this.sign_up_begin_time = sign_up_begin_time;
        this.sign_up_end_time = sign_up_end_time;
        this.activity_start_time = activity_start_time;
        this.activity_finish_time = activity_finish_time;
        this.activity_location = activity_location;
        this.numsign = numsign;
        this.numsigned = numsigned;
        this.firstImage = firstImage;
    }


    public void setNumsign(String numsign) {
        this.numsign = numsign;
    }

    public void setNumsigned(String numsigned) {
        this.numsigned = numsigned;
    }

    public String getNumsign() {
        return numsign;
    }

    public String getNumsigned() {
        return numsigned;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {

        return face;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public String getPublish_location() {
        return publish_location;
    }

    public String getSign_up_begin_time() {
        return sign_up_begin_time;
    }

    public String getSign_up_end_time() {
        return sign_up_end_time;
    }

    public String getActivity_start_time() {
        return activity_start_time;
    }

    public String getActivity_finish_time() {
        return activity_finish_time;
    }

    public String getActivity_location() {
        return activity_location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setPublish_location(String publish_location) {
        this.publish_location = publish_location;
    }

    public void setSign_up_begin_time(String sign_up_begin_time) {
        this.sign_up_begin_time = sign_up_begin_time;
    }

    public void setSign_up_end_time(String sign_up_end_time) {
        this.sign_up_end_time = sign_up_end_time;
    }

    public void setActivity_start_time(String activity_start_time) {
        this.activity_start_time = activity_start_time;
    }

    public void setActivity_finish_time(String activity_finish_time) {
        this.activity_finish_time = activity_finish_time;
    }

    public void setActivity_location(String activity_location) {
        this.activity_location = activity_location;
    }

    @Override
    public String toString() {
        return "ActivityData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", publish_location='" + publish_location + '\'' +
                ", sign_up_begin_time='" + sign_up_begin_time + '\'' +
                ", sign_up_end_time='" + sign_up_end_time + '\'' +
                ", activity_start_time='" + activity_start_time + '\'' +
                ", activity_finish_time='" + activity_finish_time + '\'' +
                ", activity_location='" + activity_location + '\'' +
                '}';
    }
}
