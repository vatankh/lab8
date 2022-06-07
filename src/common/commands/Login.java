package common.commands;

import server.*;

import java.io.Serializable;

public class Login extends AbsCommand implements Serializable {


    public Login() {

    }

    @Override
    public Answer work() throws Exception {
        return storage.login(auth);
    }

}
