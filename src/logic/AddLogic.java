package logic;

import client.*;
import common.collection.*;
import common.commands.*;
import common.utils.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddLogic implements Initializable {

    @FXML
    private ChoiceBox<String> fuelChoise;

    @FXML
    private ChoiceBox<String> vehicleChoise;

    @FXML
    private TextField newVehicleCoordinatesX;

    @FXML
    private TextField newVehicleCoordinatesY;

    @FXML
    private TextField newVehicleEnginePower;

    @FXML
    private TextField newVehicleName;

    @FXML
    private Label wrongCoordinatesAdd;



    @FXML
    private Label wrongEnginePowerAdd;

    @FXML
    private Label wrongFuelType;

    @FXML
    private Label wrongNameAdd;

    @FXML
    private Label wrongVehicleType;
    private VehicleType vehicleTypeChoise;
    private FuelType fuelTypeChoise;

    List<String> fuelList= Arrays.asList("GASOLINE","MANPOWER","NUCLEAR","PLASMA");
    List<String> vehicleList=Arrays.asList("PLANE","SHIP","MOTORCYCLE","HOVERBOARD");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fuelChoise.getItems().addAll(fuelList);
        vehicleChoise.getItems().addAll(vehicleList);
        fuelChoise.setOnAction(this::setFuelChoise);
        vehicleChoise.setOnAction(this::setVehicleChoise);
    }

    private void setVehicleChoise(ActionEvent actionEvent) {
        vehicleTypeChoise=VehicleType.valueOf(vehicleChoise.getValue());
    }
    private VehicleType getVehicleChoise(){
        return vehicleTypeChoise;
    }
    private FuelType getFuelTypeChoise(){
        return fuelTypeChoise;
    }


    private void setFuelChoise(ActionEvent actionEvent) {
        fuelTypeChoise=FuelType.valueOf(fuelChoise.getValue());
    }

    @FXML
    public void addVehicle() {
        boolean work =true;
        if (newVehicleName.getText().isEmpty()) {
            wrongNameAdd.setText("name can not be empty");
            work=false;
        }else {
            wrongNameAdd.setText("");
        }
        String message = Parser.ParseDouble(newVehicleEnginePower.getText());
        if (!message.isEmpty()){
            wrongEnginePowerAdd.setText(message);
            work=false;
        }else {
            wrongEnginePowerAdd.setText("");
        }
        message =Parser.parsLong(newVehicleCoordinatesX.getText());
        String corRes="";
        if (!message.isEmpty()){
            corRes= "X ->"+message;
            work=false;
        }else  if (Long.parseLong(newVehicleCoordinatesX.getText()) >911){
            corRes="X cannot be bigger than 911";
            work=false;
        }
        message=Parser.ParseInt(newVehicleCoordinatesY.getText());
        if (!message.isEmpty()){
            corRes+= " ,Y -> "+message;
            work=false;
        }
        if (!corRes.isEmpty()){
            wrongCoordinatesAdd.setText(corRes);
        }else {
            wrongCoordinatesAdd.setText("");
        }
        if (getVehicleChoise() == null){
            wrongVehicleType.setText("please choose vehicle type");
            work=false;
        } else {
            wrongVehicleType.setText("");
        }
        if (getFuelTypeChoise()==null){
            wrongFuelType.setText("please choose fuel type");
            work=false;
        } else {
            wrongFuelType.setText("");
        }
        if (work){
            String name = newVehicleName.getText();
            Double enginePower = Double.parseDouble(newVehicleEnginePower.getText());
            Coordinates coordinates= new Coordinates(Long.parseLong(newVehicleCoordinatesX.getText()),Integer.parseInt(newVehicleCoordinatesY.getText()));
            VehicleType vehicleType= getVehicleChoise();
            FuelType fuelType=getFuelTypeChoise();
            Vehicle vehicle = new Vehicle(name,coordinates,enginePower,vehicleType,fuelType);
            try {
                Client.getNetworkUdp().sendCommand(new Add(vehicle));
                Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
                vehicle= answer.getVehicle();
                Client.getObservableList().add(new SimpleVehicle(vehicle));
                MainClient m =new MainClient();
                m.changeScene("mainTablePage.fxml");
            }catch (Exception e){
                System.out.println("er in add");
                e.printStackTrace();
            }
        }
    }
    @FXML
    void goBack(ActionEvent event) throws IOException {
        MainClient mainClient=new MainClient();
        mainClient.changeScene("mainTablePage.fxml");
    }
}
