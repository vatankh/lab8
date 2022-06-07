package common.commands;

import common.utils.*;
import server.*;

import java.io.Serializable;

public class Filter_greater_than_engine_power extends AbsCommand implements Serializable {
    String power;
    private static final long serialVersionUID = -4507489610617393544L;

    public  Filter_greater_than_engine_power(String power){
        this.power=power;
    }


    @Override
    public Answer work() {
        messege = Parser.ParseDouble(power);
        Answer answer = new Answer();
        if (messege.isEmpty()){
            System.out.println("empy");
            return  storage.filter_greater_than_engine_power(Double.parseDouble(power));
        }else {
            System.out.println("nopp");
            answer.setMessage(messege);
            return answer;
        }
    }
}
