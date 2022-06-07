package common.commands;

import common.utils.*;

import java.io.Serializable;

public class Count_greater_than_engine_power extends AbsCommand implements Serializable {
    String enginepower;
    private static final long serialVersionUID = -4507489610617393544L;

    public Count_greater_than_engine_power(String enginepower){
        this.enginepower=enginepower;
    }



    @Override
    public String work() {
        messege= Parser.ParseDouble(enginepower);
        if (messege.equals("")){
            int count = storage.count_greater_than_engine_power(Double.parseDouble(enginepower));
            return "the number of vechiles that have greater power is "+ count;
        }else {
            return messege;
        }
    }
}
