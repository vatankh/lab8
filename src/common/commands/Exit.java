package common.commands;

import java.io.*;

public class Exit extends AbsCommand implements Serializable {

    public Exit(){

    }
    @Override
    public String work() throws Exception {
        return "exit";
    }
}
