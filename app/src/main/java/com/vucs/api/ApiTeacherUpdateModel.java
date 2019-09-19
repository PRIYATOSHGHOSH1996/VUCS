package com.vucs.api;




import com.vucs.model.RoutineModel;
import com.vucs.model.TeacherModel;

import java.util.List;

public class ApiTeacherUpdateModel {

    private List<TeacherModel> teacherModels;

    public ApiTeacherUpdateModel(List<TeacherModel> teacherModels) {
        this.teacherModels = teacherModels;

    }


    @Override
    public String toString() {
        return "ApiTeacherUpdateModel{" +
                "teacherModels=" + teacherModels +
                '}';
    }

    public List<TeacherModel> getTeacherModels() {
        return teacherModels;
    }

    public void setTeacherModels(List<TeacherModel> teacherModels) {
        this.teacherModels = teacherModels;
    }
}
