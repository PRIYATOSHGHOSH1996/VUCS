package com.vucs.model;



public class RoutineDisplayModel extends RoutineModel {
    private String teacherName;

    public RoutineDisplayModel(int dayNo, long startTime, long endTime, String teacherId, String subject, int course, int sem, String teacherName) {
        super(dayNo, startTime, endTime, teacherId, subject, course, sem);
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "RoutineDisplayModel{" +
                "teacherName='" + teacherName + '\'' +
                '}';
    }
}
