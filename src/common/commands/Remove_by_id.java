package common.commands;

import control.*;
import server.*;

import java.io.Serializable;

public class Remove_by_id extends AbsCommand implements Serializable {
    String id;
    private static final long serialVersionUID = -4507489610617393544L;

    public Remove_by_id(String id){
        this.id=id;
    }


    @Override
    public Answer work() throws Exception {
        long[] index = VerifyIdInStorage.verify(storage,id);
        if (index[0] ==-1){
            Answer answer = new Answer();
            answer.setMessage("there is no vehicle with this id");
            return answer;
        }else {
            return storage.remove_by_id(Integer.parseInt(id),auth);
        }
    }
}
