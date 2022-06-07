package database;

import common.collection.*;

import common.collection.VehicleType;
import server.*;


import java.sql.*;
import java.util.Stack;

public class UserHandler {
    public Connection connection;
    final private Logic logic;
    public String user;
    public UserHandler(Connection connection){
        this.connection=connection;
        this.logic=new Logic();
    }
    public Answer loginUser(String user, String pass) {
        Statement statement;
        String message="unauthorized-user does not exist";
        Stack<Vehicle> stack =null;
        Answer answer = new Answer();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(logic.GET_USERS);
            while (resultSet.next()) {
                if (resultSet.getString("username").equals(user)) {
                    if (resultSet.getString("password").equals(SHA1Tool.encryptPassToSHA1(pass))){
                        message="Authorized";
                        stack=getVehicles();
                        this.user=user;
                    }else {
                        message ="unauthorized-wrong password";
                    }
                    answer.setMessage(message);
                    answer.setStack(stack);
                    return answer;
                }
            }
            answer.setMessage(message);
             return answer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message="unauthorized-error";
            answer.setMessage(message);
            return answer;
        }
    }

    private Stack<Vehicle> getVehicles( ){
        PreparedStatement preparedStatement;
        ResultSet rs;
        Stack<Vehicle> vehicleStack =new Stack<>();
        try {
            preparedStatement = connection.prepareStatement(logic.GET_ALL_VEHICLES);
            rs=preparedStatement.executeQuery();
            while (rs.next()){

                vehicleStack.push(getVehicle(rs));
            }

        }catch (Exception e){
            System.out.println("ex in get vehicles");
            System.out.println(e.getMessage());
        }
        return vehicleStack;
    }

    public Vehicle getVehicle(ResultSet rs) throws SQLException {
        String name =rs.getString("name");
        Long x =rs.getLong("coordinate_x");
        Integer y =rs.getInt("coordinate_y");
        Double ep =rs.getDouble("engine_power");
        VehicleType vt= VehicleType.valueOf(rs.getString("vehicle_type"));
        FuelType fl=FuelType.valueOf(rs.getString("fuel_type"));
        long id = rs.getLong("id");
        Date date = rs.getDate("creation_date");
        String owner =rs.getString("owner");
        return new Vehicle(id,name,new Coordinates(x,y),date,ep,vt,fl,owner);
    }


    public Answer registerUser(String user,  String pass) {
        PreparedStatement preparedStatement;
        String message;
        Stack<Vehicle> stack;
        Answer answer= new Answer();
        try {
            preparedStatement = connection.prepareStatement(logic.GET_USERS);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString("username").equals(user)){
                    message="unauthorized-user already exist ";
                    answer.setMessage(message);
                    return answer;
                }
            }

            preparedStatement =connection.prepareStatement(logic.ADD_USER_REQUEST);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,SHA1Tool.encryptPassToSHA1(pass));
            preparedStatement.executeUpdate();
            message="Authorized";
            stack=getVehicles();
            answer.setMessage(message);
            answer.setStack(stack);
            return  answer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message ="exception in insert to database";
            answer.setMessage(message);
            return answer;
        }
    }



}
