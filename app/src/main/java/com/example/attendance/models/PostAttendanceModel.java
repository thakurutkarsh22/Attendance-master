package com.example.attendance.models;

public class PostAttendanceModel {

    String dateOfAttendance;
    String AttendanceMark;
    String student_id;
    String Class_id;

    public PostAttendanceModel()
    {

    }

    public String getAttendanceMark() {
        return AttendanceMark;
    }

    public void setAttendanceMark(String attendanceMark) {
        AttendanceMark = attendanceMark;
    }

    public String getDateOfAttendance() {
        return dateOfAttendance;
    }

    public void setDateOfAttendance(String dateOfAttendance) {
        this.dateOfAttendance = dateOfAttendance;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getClass_id() {
        return Class_id;
    }

    public void setClass_id(String class_id) {
        Class_id = class_id;
    }
}
