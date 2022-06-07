package logic;

import client.Client;
import client.MainClient;
import common.collection.*;

import common.collection.VehicleType;
import common.commands.Remove_greater;
import common.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import server.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveGreaterLogic implements Initializable {

    @FXML
    private ChoiceBox<String> fuelChoisere;

    @FXML
    private TextField newVehicleCoordinatesXre;

    @FXML
    private TextField newVehicleCoordinatesYre;

    @FXML
    private TextField newVehicleEnginePowerre;

    @FXML
    private TextField newVehicleNamere;

    @FXML
    private ChoiceBox<String> vehicleChoisere;

    @FXML
    private Label wrongCoordinatesre;

    @FXML
    private HBox wrongEnginePower;

    @FXML
    private Label wrongEnginePowerAdd;

    @FXML
    private Label wrongFuelTypere;

    @FXML
    private Label wrongNamere;

    @FXML
    private Label wrongVehicleTypere;

    private VehicleType vehicleTypeChoise;
    private FuelType fuelTypeChoise;

    List<String> fuelList= Arrays.asList("GASOLINE","MANPOWER","NUCLEAR","PLASMA");
    List<String> vehicleList=Arrays.asList("PLANE","SHIP","MOTORCYCLE","HOVERBOARD");

    public VehicleType getVehicleTypeChoise() {
        return vehicleTypeChoise;
    }

    public FuelType getFuelTypeChoise() {
        return fuelTypeChoise;
    }

    @FXML
    void removeGreaterThan(ActionEvent event) throws IOException {
        boolean work =true;
        if (newVehicleNamere.getText().isEmpty()) {
            wrongNamere.setText("name can not be empty");
            work=false;
        }else {
            wrongNamere.setText("");
        }
        String message = Parser.ParseDouble(newVehicleEnginePowerre.getText());
        if (!message.isEmpty()){
            wrongEnginePowerAdd.setText(message);
            work=false;
        }else {
            wrongEnginePowerAdd.setText("");
        }
        message =Parser.parsLong(newVehicleCoordinatesXre.getText());
        String corRes="";
        if (!message.isEmpty()){
            corRes= "X ->"+message;
            work=false;
        }else  if (Long.parseLong(newVehicleCoordinatesXre.getText()) >911){
            corRes="X cannot be bigger than 911";
            work=false;
        }
        message=Parser.ParseInt(newVehicleCoordinatesYre.getText());
        if (!message.isEmpty()){
            corRes+= " ,Y -> "+message;
            work=false;
        }
        if (!corRes.isEmpty()){
            wrongCoordinatesre.setText(corRes);
        }else {
            wrongCoordinatesre.setText("");
        }
        if (getVehicleTypeChoise() == null){
            wrongVehicleTypere.setText("please choose vehicle type");
            work=false;
        } else {
            wrongVehicleTypere.setText("");
        }
        if (getFuelTypeChoise()==null){
            wrongFuelTypere.setText("please choose fuel type");
            work=false;
        } else {
            wrongFuelTypere.setText("");
        }
        if (work){
            Vehicle vehicle = new Vehicle(newVehicleNamere.getText()
                    ,new Coordinates(Long.parseLong(newVehicleCoordinatesXre.getText()),Integer.parseInt(newVehicleCoordinatesYre.getText())),
                    Double.parseDouble(newVehicleEnginePowerre.getText()),
                    vehicleTypeChoise,fuelTypeChoise);
            Remove_greater remove_greater = new Remove_greater(vehicle);
            Client.getNetworkUdp().sendCommand(remove_greater);
            Answer answer =(Answer) Client.getNetworkUdp().reciveObj();
            Client.setObservableList(answer.getStack());
            MainClient mainClient = new MainClient();
            mainClient.changeScene("mainTablePage.fxml");
        }
    }
    public void setFuelChoisere(ActionEvent actionEvent){
        fuelTypeChoise= FuelType.valueOf(fuelChoisere.getValue());
    }
    public void setVehicleChoisere(ActionEvent actionEvent){
        vehicleTypeChoise=VehicleType.valueOf(vehicleChoisere.getValue());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fuelChoisere.getItems().addAll(fuelList);
        vehicleChoisere.getItems().addAll(vehicleList);
        fuelChoisere.setOnAction(this::setFuelChoisere);
        vehicleChoisere.setOnAction(this::setVehicleChoisere);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        MainClient m = new MainClient();
        m.changeScene("mainTablePage.fxml");
    }
}
