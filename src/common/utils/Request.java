package common.utils;

import common.commands.*;

import java.io.Serializable;

public class Request implements Serializable {
    AbsCommand command;
    Auth auth;
    private static final long serialVersionUID = -4507489610617393524L;


    public Request(AbsCommand command, Auth auth) {
        this.command = command;
        this.auth = auth;
    }

    public AbsCommand getCommand() {
        return command;
    }

    public Auth getAuth() {
        return auth;
    }
}
