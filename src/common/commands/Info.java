package common.commands;


import server.*;

import java.io.Serializable;

public class Info extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public Answer work() throws Exception {
        Answer answer =new Answer();
        answer.setMessage("type=Stack\n" +
                "date="+storage.date+"\n" +
                "number Of Elements="+ storage.getVehiclesList().size()+"\n");
        return answer;
    }
}
