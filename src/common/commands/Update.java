package common.commands;

import control.*;
import common.collection.*;
import server.*;

import java.io.Serializable;


public class Update extends AbsCommand implements Serializable {
    Vehicle vehicle;
    String id;
    public Update(Vehicle vehicle,String id){
        this.vehicle=vehicle;
        this.id =id;
    }

    @Override
    public Answer work() throws Exception {
        long[] index = VerifyIdInStorage.verify(storage,id);
        Answer answer;
        if (index[0] ==-1){
            answer= new Answer();
            System.out.println("there is no vehicle with this id");
            answer.setMessage("there is no vehicle with this id");
        }else {
            vehicle.setId(Long.parseLong(id));
            answer= storage.updateById(Long.parseLong(id),vehicle,auth);
        }
        return answer;
    }
}
