package com.example.attendance.models;

import com.google.gson.annotations.SerializedName;

public class AttendanceModel {
    boolean AttendanceMark;
    String studend_id;
    String class_id;
    @SerializedName("stu_name")
    String stu_name;
    String date;

    public AttendanceModel()
    {

    }
    public AttendanceModel(boolean attendanceMark, String studend_id, String class_id, String stu_name,String date) {
        AttendanceMark = attendanceMark;
        this.studend_id = studend_id;
        this.class_id = class_id;
        this.stu_name = stu_name;
        this.date = date;
    }


    public boolean isAttendanceMark() {
        return AttendanceMark;
    }

    public void setAttendanceMark(boolean attendanceMark) {
        AttendanceMark = attendanceMark;
    }

    public String getStudend_id() {
        return studend_id;
    }

    public void setStudend_id(String studend_id) {
        this.studend_id = studend_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
