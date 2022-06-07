package common.commands;

import common.utils.*;
import server.*;

import java.io.Serializable;

public class Filter_by_engine_power extends AbsCommand implements Serializable {
    String power;
    private static final long serialVersionUID = -4507489610617393544L;

    public Filter_by_engine_power(String power){
        this.power=power;
    }

    @Override
    public Answer work()  {
        messege = Parser.ParseDouble(power);
        Answer answer =new Answer();
        if (messege.isEmpty()){
            return storage.filter_by_engine_power(Double.parseDouble(power));
        }else {
            answer.setMessage(messege);
            return answer;
        }

    }
}
