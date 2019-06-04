package com.vucs.api;




import com.vucs.R;

import static com.vucs.App.getContext;

public class ApiCredential {

    private String apiLogin;
    private String apiPass;

    public ApiCredential() {
        this.apiLogin  = getContext().getString(R.string.apiLogin);
        this.apiPass   = getContext().getString(R.string.apiPass);
    }

    public String getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(String apiLogin) {
        this.apiLogin = apiLogin;
    }

    public String getApiPass() {
        return apiPass;
    }

    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

    @Override
    public String toString() {
        return "ApiCredential{" +
                "apiLogin='" + apiLogin + '\'' +
                ", apiPass='" + apiPass + '\'' +
                '}';
    }
}
