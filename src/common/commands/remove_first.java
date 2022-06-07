package common.commands;
import server.*;

import java.io.Serializable;

public class remove_first extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public Answer work() throws Exception {
        Answer answer =new Answer();
        if (storage.getVehiclesList().size() ==0){
            answer.setMessage("stack is already empty");
            return answer;
        }else {
            return storage.removeFirst(auth);
        }
    }
}
