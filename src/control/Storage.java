package control;

import common.utils.*;
import common.collection.*;
import database.*;

import server.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Storage {
    private Stack<Vehicle> vehicles = new Stack<>();
    public Date date;
    public static long id=1;
    public UserHandler userHandler;
    public UserVehiclesHandler userVehiclesHandler;

    public Storage(Stack<Vehicle> vehicles,UserHandler userHandler){
        this.vehicles=vehicles;
        this.date=new Date(System.currentTimeMillis());

        if (vehicles.size() >1) {
            long max=1;
             max = vehicles.stream().max((a, b) -> {
                return Long.compare(a.getId(), b.getId());
            }).get().getId();
             id =max;
        }
        this.userHandler=userHandler;
        this.userVehiclesHandler=new UserVehiclesHandler(userHandler);

    }

    public synchronized Answer add(Vehicle vehicle, Auth auth){
        Answer answer =userVehiclesHandler.insertVehicle(vehicle,auth);
        vehicles.push(answer.getVehicle());
        return answer;
    }
    public synchronized List<Vehicle> getVehiclesList(){
        return vehicles.stream().sorted(Comparator.comparingLong(value -> value.getId())).collect(Collectors.toList());
    }
//    public String insert_at_index(int index,Vehicle vehicle,Auth auth){
//        vehicle=userVehiclesHandler.insertVehicle(vehicle,auth);
//        vehicles.insertElementAt(vehicle,index);
//        return "inserted successfully";
//    }
    public  int count_greater_than_engine_power(Double power){
        return (int) vehicles.stream().filter(vehicle -> vehicle.getEnginePower() > power).count();
    }

    public Answer filter_by_engine_power(Double power){
        Stack<Vehicle> stack = new Stack<>();
        vehicles.stream().filter((vehicle -> vehicle.getEnginePower().equals(power))).sorted(Comparator.comparingLong(Vehicle::getId)).forEach((stack::push));
        Answer answer = new Answer();
        answer.setStack(stack);
        return answer;
    }
    public Answer filter_greater_than_engine_power(Double power){
        Stack<Vehicle> stack =new Stack<>();
        Answer answer = new Answer();
        vehicles.stream().filter(vehicle -> vehicle.getEnginePower() > power).sorted(Comparator.comparingLong(Vehicle::getId)).forEach(stack::push);
        answer.setStack(stack);
        System.out.println("stack is ready to give withlength "+stack.size());
        return answer;
    }
    public Answer clear(Auth auth) throws SQLException {
        String message =userVehiclesHandler.clear(auth);
        Stack<Vehicle> vehicles=new Stack<>();
        ResultSet ve=userVehiclesHandler.getVehicles();
        while (ve.next()){
            vehicles.push(userHandler.getVehicle(ve));
        }
        this.vehicles = vehicles;
        Answer answer = new Answer();
        answer.setMessage(message);
        answer.setStack(vehicles);
        return answer;
    }
    public Answer remove_by_id(int id,Auth auth) throws SQLException {
        Answer answer = userVehiclesHandler.removeById(id,auth);
        if (answer.getMessage().equals("authorized")){
            Vehicle vehicle = vehicles.stream().filter(vehicle1 -> vehicle1.getId() ==id).findFirst().get();
            vehicles.remove(vehicle);
        }
        return answer;

    }
    public Answer removeFirst(Auth auth) throws SQLException {
        Answer answer = userVehiclesHandler.removeFirst(auth);
        Vehicle vehicle =answer.getVehicle();
        if (vehicle != null){
            Vehicle obj=vehicles.stream().filter(vehicle1 -> vehicle1.getId()==vehicle.getId()).findFirst().get();
            vehicles.remove(obj);
            answer.setMessage("removed successfully");
        }
        return answer;

    }
    public Answer removeGreater(double enginePower,Auth auth) throws SQLException {
        userVehiclesHandler.removeGreater(auth,enginePower);
        ResultSet re = userVehiclesHandler.getVehicles();
        Stack<Vehicle> Vehicles =new Stack<>();
        while (re.next()){
            Vehicle temp =userHandler.getVehicle(re);
            Vehicles.push(temp);
        }
        this.vehicles=Vehicles;
        Answer answer = new Answer();
        answer.setMessage("removed successfully");
        answer.setStack(vehicles);
        return answer;
    }
    public  Answer  updateById(long id,Vehicle vehicle ,Auth auth) throws SQLException {
        Answer answer = userVehiclesHandler.update_by_id(vehicle,id,auth);
        if (answer.getVehicle()!=null){
            Vehicle vehicleRes =answer.getVehicle();
            if (vehicleRes != null){
                List<Vehicle> vehicleList = getVehiclesList();
                int index=0;
                for (int i=0;i< vehicleList.size();i++){
                    if (vehicleList.get(i).getId()==id){
                        index=i;
                        break;
                    }
                }
                vehicles.setElementAt(vehicleRes,index);
                answer.setMessage("updated successfully");
            }
        }
        return answer;
    }
    public Answer login(Auth auth){
        Answer answer=userHandler.loginUser(auth.getUser(), auth.getPass());
        System.out.println(answer.getMessage());
        vehicles=answer.getStack();
        return  answer;
    }
    public Answer register(Auth auth){
        Answer answer =userHandler.registerUser(auth.getUser(),auth.getPass());
        vehicles=answer.getStack();
        return  answer;
    }
}
