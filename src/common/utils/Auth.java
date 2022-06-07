package common.utils;

import java.io.Serializable;

public class Auth implements Serializable {
    String user;
    String pass;
    private static final long serialVersionUID = -4507489610617333544L;


    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public Auth(String user, String pass) {
        this.user=user;
        this.pass=pass;
    }
}
