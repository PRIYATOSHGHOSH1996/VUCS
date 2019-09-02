package com.vucs.api;




import com.vucs.model.BlogModel;
import com.vucs.model.RoutineModel;

import java.util.List;

public class ApiRoutineUpdateModel {

    private List<RoutineModel> routineModels;

    public ApiRoutineUpdateModel(List<RoutineModel> routineModels) {
        this.routineModels = routineModels;

    }


    @Override
    public String toString() {
        return "ApiRoutineUpdateModel{" +
                "routineModels=" + routineModels +
                '}';
    }

    public List<RoutineModel> getRoutineModels() {
        return routineModels;
    }

    public void setRoutineModels(List<RoutineModel> routineModels) {
        this.routineModels = routineModels;
    }
}
