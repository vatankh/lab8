package client;

import common.collection.*;

import common.collection.VehicleType;
import common.utils.*;

import java.util.Date;
import java.util.Scanner;

public  class InputHandler {
    Scanner scanner;
    public InputHandler(Scanner scanner){
        this.scanner=scanner;
    }
    public  Vehicle handleVechile(){
        String name=handleName();
        long x=handleLong("Enter Coordinate.X");
        int  y=handleInteger("Enter Coordinate.Y");
        Coordinates coordinates= new Coordinates(x,y);
        Date date =new Date(System.currentTimeMillis());
        double enginePower =handleDouble("enter Engine Power");
        VehicleType vehicleType=handleVehicleType();
        FuelType fuelType=handleFuelType();
        return new Vehicle(name,coordinates,enginePower,vehicleType,fuelType);

    }

    public  long handleLong(String text){
        while (true) {
            System.out.println(text);
            if (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String messege = Parser.parsLong(value);
                if (messege.isEmpty()) {
                    return Long.parseLong(value);
                }
                System.out.println(messege);
            }
        }

    }

    public  int handleInteger(String text){
        while (true) {
            System.out.println(text);
            if (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String messege = Parser.ParseInt(value);
                if (messege.isEmpty()) {
                    return Integer.parseInt(value);
                }
                System.out.println(messege);
            }
        }
    }

    public  String  handleName(){
        String name="";
        while (true){
            System.out.println("enter name:");
            name = scanner.nextLine();
             if (name.isEmpty() || name.trim().isEmpty()){
                 System.out.println("name can not be empty");
             }else {
                 if (name.contains(",")){
                     System.out.println("name can not contain ,");
                 }else {
                     return name;
                 }
            }

        }
    }
    public Double handleDouble(String text){
        while (true) {
            System.out.println(text);
            if (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String messege = Parser.ParseDouble(value);
                if (messege.isEmpty()) {
                    return Double.parseDouble(value);
                }
                System.out.println(messege);
            }
        }
    }
    public VehicleType handleVehicleType(){
        System.out.println("choose VechileType number");
        while (true) {
            VehicleType[] arr = VehicleType.values();
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i] +"["+(i+1)+"]-");
            }
            System.out.println();
            if (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String messege=Parser.ParseInt(value);
                if (messege.isEmpty()){
                    int num = Integer.parseInt(value);
                    if (num >4 || num <1){
                        System.out.println("enter number between 1-4");
                    }
                    switch (num){
                        case 1:
                            return  VehicleType.PLANE;
                        case 2:
                            return  VehicleType.SHIP;
                        case 3:
                            return VehicleType.MOTORCYCLE;
                        case 4:
                            return VehicleType.HOVERBOARD;
                    }
                }else {
                    System.out.println(messege);
                }
            }
        }
    }
    public FuelType handleFuelType(){
        System.out.println("choose fuelType number");
        while (true) {
            FuelType[] arr = FuelType.values();
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i] +"["+(i+1)+"]-");
            }
            System.out.println();
            if (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String messege=Parser.ParseInt(value);
                if (messege.isEmpty()){
                    int num = Integer.parseInt(value);
                    if (num >4 || num <1){
                        System.out.println("enter number between 1-4");
                    }
                    switch (num){
                        case 1:
                            return  FuelType.GASOLINE;
                        case 2:
                            return  FuelType.MANPOWER;
                        case 3:
                            return FuelType.NUCLEAR;
                        case 4:
                            return FuelType.PLASMA;
                    }
                }else {
                    System.out.println(messege);
                }
            }
        }
    }
}
