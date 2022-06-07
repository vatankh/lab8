package common.commands;

import server.*;

import java.io.Serializable;

public class Register extends AbsCommand implements Serializable {



    public Register() {

    }

    @Override
    public Answer work() throws Exception {
        return storage.register(auth);
    }
}
