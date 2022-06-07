package common.commands;

import common.collection.*;
import server.*;

import java.io.Serializable;

public class Remove_greater extends AbsCommand implements Serializable {
    Vehicle vehicle;
    private static final long serialVersionUID = -4507489610617393544L;

    public Remove_greater(Vehicle vehicle){
        this.vehicle=vehicle;
    }

    @Override
    public Answer work() throws Exception {
        return storage.removeGreater(vehicle.getEnginePower(),auth);
    }
}
