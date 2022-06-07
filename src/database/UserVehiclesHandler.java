package database;

import common.utils.*;
import common.collection.*;
import server.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserVehiclesHandler {
    UserHandler userHandler;
    final Logic logic;
    public UserVehiclesHandler(UserHandler userHandler){
        this.userHandler=userHandler;
        this.logic =new Logic();
    }
    public Answer insertVehicle(Vehicle vehicle,Auth auth) {
        try {
            PreparedStatement ps = userHandler.connection.prepareStatement(logic.ADD_VEHICLE);
            int pls=0;
            ps.setString(++pls,vehicle.getName());
            ps.setLong(++pls,vehicle.getCoordinates().getX());
            ps.setInt(++pls,vehicle.getCoordinates().getY());
            ps.setDouble(++pls,vehicle.getEnginePower());
            ps.setString(++pls,vehicle.getType().name());
            ps.setString(++pls,vehicle.getFuelType().name());
            ps.setString(++pls,auth.getUser());
            ps.executeUpdate();
            ps=userHandler.connection.prepareStatement(logic.GET_LAST_VEHICLE);
            ps.setString(1,auth.getUser());
            ResultSet resultSet=ps.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString("name").equals(vehicle.getName())){
                    Answer answer= new Answer();
                    answer.setVehicle(userHandler.getVehicle(resultSet));
                    answer.setMessage("added successfully");
                    return answer;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("exiption in insert to database");
            System.out.println(e.getMessage());
        }
        return null;
    }
    public  Answer  update_by_id(Vehicle vehicle , long id, Auth auth){
        PreparedStatement preparedStatement;
        Answer answer= new Answer();
        try {

            preparedStatement =userHandler.connection.prepareStatement(logic.GET_VEHICLE_BY_ID);
            preparedStatement.setLong(1,id);
            ResultSet ve = preparedStatement.executeQuery();
            while (ve.next()){
                if (ve.getString("owner").equals(auth.getUser())){
                    Vehicle vehicleOld= userHandler.getVehicle(ve);
                    preparedStatement=userHandler.connection.prepareStatement(logic.UPDATE_VEHICLE_BY_ID);
                    int counter=0;
                    preparedStatement.setString(++counter,vehicle.getName());
                    preparedStatement.setLong(++counter,vehicle.getCoordinates().getX());
                    preparedStatement.setLong(++counter,vehicle.getCoordinates().getY());
                    preparedStatement.setDouble(++counter,vehicle.getEnginePower());
                    preparedStatement.setString(++counter,vehicle.getType().name());
                    preparedStatement.setString(++counter,vehicle.getFuelType().name());
                    preparedStatement.setLong(++counter,id);
                    preparedStatement.setString(++counter,auth.getUser());
                    preparedStatement.executeUpdate();
                    answer.setVehicle(new Vehicle(id,vehicle.getName(), vehicle.getCoordinates(), vehicleOld.getCreationDate(), vehicle.getEnginePower(), vehicle.getType(),vehicle.getFuelType(),vehicleOld.getOwner()));
                    answer.setMessage("Authorized");
                    return answer;
                }
            }
            answer.setMessage("Unauthorized");
            return answer;
        }catch (Exception e){
            System.out.println("error in update by id in database");
            System.out.println(e.getMessage());
        }
        answer.setMessage("Unauthorized-erorr");
        return answer;
    }
    public  String clear(Auth auth){
        PreparedStatement preparedStatement;
        try {
            preparedStatement=userHandler.connection.prepareStatement(logic.CLEAR);
            preparedStatement.setString(1,auth.getUser());
            preparedStatement.executeUpdate();
            return"cleared successfully ";

        }catch (Exception e){
            System.out.println(e.getMessage());
            return "error in clear database";
        }
    }
    public Answer removeById(int id , Auth auth) throws SQLException {
        PreparedStatement preparedStatement=userHandler.connection.prepareStatement(logic.GET_USER_VEHICLES);
        preparedStatement.setLong(1,id);
        ResultSet ve = preparedStatement.executeQuery();
        Answer answer =new Answer();
        while (ve.next()){
            if (ve.getString("owner").equals(auth.getUser())){
                preparedStatement =userHandler.connection.prepareStatement(logic.REMOVE_VEHICLE_BY_ID);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                answer.setMessage("authorized");
                return answer;
            }
        }
        answer.setMessage("unauthorized");
        return answer;
    }
    public ResultSet getVehicles()  {
        try {
            PreparedStatement preparedStatement =userHandler.connection.prepareStatement(logic.GET_ALL_VEHICLES);
            return preparedStatement.executeQuery();
        } catch (Exception e){
            System.out.println("ex in get vehicles");
            System.out.println(e.getMessage());
            return null;
        }

    }
    public Answer removeFirst(Auth auth)  {
        PreparedStatement preparedStatement;
        Answer answer = new Answer();
        try {
            preparedStatement = userHandler.connection.prepareStatement(logic.SELECT_FIRST_VEHICLE);
            ResultSet first = preparedStatement.executeQuery();
            while (first.next()) {
                if (first.getString("owner").equals(auth.getUser())) {
                    Vehicle v = userHandler.getVehicle(first);
                    preparedStatement = userHandler.connection.prepareStatement(logic.DELETE_FIRST_VEHICLE);
                    preparedStatement.executeUpdate();
                    answer.setVehicle(v);
                    return answer;
                }
            }
            answer.setMessage("unauthorized");
            return answer;
        }catch (Exception e){
            System.out.println("exciption in remove_first");
            answer.setMessage("unauthorized-error");
            return answer;
        }
    }
    public String removeGreater(Auth auth,Double enginePower) throws SQLException {
        PreparedStatement preparedStatement =userHandler.connection.prepareStatement(logic.REMOVE_VEHICLES_GREATER_THAN_ENGINE_POWER);
        preparedStatement.setString(1,auth.getUser());
        preparedStatement.setDouble(2,enginePower);
        preparedStatement.executeUpdate();
        return "removed successfully";
    }



}
