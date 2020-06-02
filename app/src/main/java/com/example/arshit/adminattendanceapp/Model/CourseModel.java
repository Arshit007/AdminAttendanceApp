package com.example.arshit.adminattendanceapp.Model;

public class CourseModel {

    private String CourseCode;
    private String CourseName;

    public CourseModel() {
    }

    public CourseModel(String courseCode, String courseName) {
        CourseCode = courseCode;
        CourseName = courseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }


}
