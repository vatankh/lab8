package common.commands;
import common.collection.*;

import java.io.Serializable;
import java.util.List;

public class Show extends AbsCommand implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;

    @Override
    public String work() throws Exception {
        List<Vehicle> list = storage.getVehiclesList();
        StringBuilder stringBuilder =new StringBuilder("");
        list.forEach(vehicle ->stringBuilder.append(vehicle.toString()).append("\n") );
        return stringBuilder.toString();
    }
}
