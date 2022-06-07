package control;

import common.collection.*;
import common.utils.Parser;

import java.util.List;

public class VerifyIdInStorage {
    public static long[] verify(Storage storage, String id){
        long longId;
        if (!Parser.parsLong(id).equals("")){
            return new long[]{-1,0};
        }else {
            longId=Long.parseLong(id);
        }
        List<Vehicle> vehicles = storage.getVehiclesList();
        int index=-1;
        for (int i=0;i< vehicles.size();i++){
            if (vehicles.get(i).getId() ==longId){
                index=i;
                break;
            }
        }
        return new long[]{index, longId};
    }
}
