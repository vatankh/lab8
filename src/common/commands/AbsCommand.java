package common.commands;

import common.utils.*;
import control.*;

import java.io.Serializable;

public abstract class AbsCommand implements Serializable {
    Storage storage;
    Auth auth;
    String messege="command worked";
    private static final long serialVersionUID = -4507489610617393544L;

    public abstract Object work() throws Exception;
    public void loadStorage(Storage storage){
        this.storage=storage;
    }
    public void loadAuth(Auth auth){
        this.auth=auth;
    }
}
