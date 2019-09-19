package com.vucs.api;




import com.vucs.model.CareerModel;
import com.vucs.model.UserModel;

import java.util.List;

public class ApiCareerUpdateModel {

    private List<CareerModel> careerModels;

    public ApiCareerUpdateModel(List<CareerModel> careerModels) {

        this.careerModels = careerModels;

    }

    public List<CareerModel> getCareerModels() {
        return careerModels;
    }

    public void setCareerModels(List<CareerModel> careerModels) {
        this.careerModels = careerModels;
    }


    @Override
    public String toString() {
        return "ApiCareerUpdateModel{" +
                "careerModels=" + careerModels +
                '}';
    }
}
