package common.commands;

import common.collection.*;
import server.*;

import java.io.Serializable;

public class Add extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;
    Vehicle vehicle;
    public Add(Vehicle vehicle){
        this.vehicle=vehicle;
    }

    @Override
    public Answer work() {
        return storage.add(vehicle,auth);
    }
}
