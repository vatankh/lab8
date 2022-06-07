package client;

import java.io.InputStream;

public class HandelObject {
    boolean Continue;
    boolean work;
    InputStream inputStream=null;
    public HandelObject(boolean Continue,boolean work){
        this.work=work;
        this.Continue=Continue;
    }
    public HandelObject(boolean Continue,boolean work,InputStream inputStream){
        this.work=work;
        this.Continue=Continue;
        this.inputStream=inputStream;
    }
}
