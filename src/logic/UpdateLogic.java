package logic;

import client.Client;
import client.MainClient;
import client.SimpleVehicle;
import common.collection.*;
import common.collection.Vehicle;
import common.collection.VehicleType;
import common.commands.Update;
import common.utils.Parser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.Answer;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateLogic implements Initializable {


    @FXML
    private ChoiceBox<String> fuelChoiseUpd;

    @FXML
    private TextField updVehicleCoordinatesX;

    @FXML
    private TextField updVehicleCoordinatesY;

    @FXML
    private TextField updVehicleEnginePower;

    @FXML
    private TextField updVehicleName;

    @FXML
    private ChoiceBox<String> vehicleChoiseUpd;

    @FXML
    private Label wrongCoordinatesUpd;

    @FXML
    private Label wrongEnginePowerUpd;

    @FXML
    private Label wrongFuelTypeUpd;

    @FXML
    private Label wrongNameUpd;

    @FXML
    private Label wrongVehicleTypeUpd;
    @FXML
    private Label unautorizedUpdate;

    private VehicleType vehicleTypeChoise;

    private FuelType fuelTypeChoise;
    String owner;

    List<String> fuelList= Arrays.asList("GASOLINE","MANPOWER","NUCLEAR","PLASMA");
    List<String> vehicleList=Arrays.asList("PLANE","SHIP","MOTORCYCLE","HOVERBOARD");
    @FXML
    void updVehicle(ActionEvent event) {
        boolean work =true;
        if (updVehicleName.getText().isEmpty()) {
            wrongNameUpd.setText("name can not be empty");
            work=false;
        }else {
            wrongNameUpd.setText("");
        }
        String message = Parser.ParseDouble(updVehicleEnginePower.getText());
        if (!message.isEmpty()){
            wrongEnginePowerUpd.setText(message);
            work=false;
        }else {
            wrongEnginePowerUpd.setText("");
        }
        message =Parser.parsLong(updVehicleCoordinatesX.getText());
        String corRes="";
        if (!message.isEmpty()){
            corRes= "X ->"+message;
            work=false;
        }else  if (Long.parseLong(updVehicleCoordinatesX.getText()) >911){
            corRes="X cannot be bigger than 911";
            work=false;
        }
        message=Parser.ParseInt(updVehicleCoordinatesY.getText());
        if (!message.isEmpty()){
            corRes+= " ,Y -> "+message;
            work=false;
        }
        if (!corRes.isEmpty()){
            wrongCoordinatesUpd.setText(corRes);
        }else {
            wrongCoordinatesUpd.setText("");
        }
        if (getVehicleChoise() == null){
            wrongVehicleTypeUpd.setText("please choose vehicle type");
            work=false;
        } else {
            wrongVehicleTypeUpd.setText("");
        }
        if (getFuelTypeChoise()==null){
            wrongFuelTypeUpd.setText("please choose fuel type");
            work=false;
        } else {
            wrongFuelTypeUpd.setText("");
        }
        if (work) {
            String name = updVehicleName.getText();
            Double enginePower = Double.parseDouble(updVehicleEnginePower.getText());
            Coordinates coordinates = new Coordinates(Long.parseLong(updVehicleCoordinatesX.getText()), Integer.parseInt(updVehicleCoordinatesY.getText()));
            VehicleType vehicleType = getVehicleChoise();
            FuelType fuelType = getFuelTypeChoise();
            Vehicle vehicle = new Vehicle(name, coordinates, enginePower, vehicleType, fuelType);
            String id = String.valueOf(Client.getOldVehicle().getId());
            Update update = new Update(vehicle,id);
            try {
                Client.getNetworkUdp().sendCommand(update);
                Object obj =Client.getNetworkUdp().reciveObj();
                if (obj instanceof  Answer){
                    Answer answer=(Answer) obj;
                    if (answer.getMessage().toLowerCase().contains("successfully")){
                        Vehicle resVehicle = answer.getVehicle();
                        ObservableList<SimpleVehicle> simpleVehicles=Client.getObservableList();
                        for (int i=0;i<simpleVehicles.size();i++){
                            if (simpleVehicles.get(i).getId() ==resVehicle.getId()){
                                Client.getObservableList().set(i,new SimpleVehicle(resVehicle));
                                MainClient m = new MainClient();
                                m.changeScene("mainTablePage.fxml");
                                break;
                            }
                        }
                    }else {
                        unautorizedUpdate.setText(answer.getMessage());
                    }

                }
            }catch (Exception e){
                System.out.println("erorr in update");
                e.printStackTrace();
            }

        }
    }

    private void setVehicleChoise(ActionEvent actionEvent) {
        vehicleTypeChoise=VehicleType.valueOf(vehicleChoiseUpd.getValue());
    }

    private VehicleType getVehicleChoise(){
        return vehicleTypeChoise;
    }

    private FuelType getFuelTypeChoise(){
        return fuelTypeChoise;
    }


    private void setFuelChoise(ActionEvent actionEvent) {
        fuelTypeChoise=FuelType.valueOf(fuelChoiseUpd.getValue());
    }

    @FXML
    private void goBack() throws IOException {
        MainClient m =new MainClient();
        m.changeScene("mainTablePage.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleChoiseUpd.getItems().addAll(vehicleList);
        fuelChoiseUpd.getItems().addAll(fuelList);
        SimpleVehicle oldVehicle= Client.getOldVehicle();
        owner=oldVehicle.getOwner();
        updVehicleName.setText(oldVehicle.getName());
        updVehicleEnginePower.setText(String.valueOf(oldVehicle.getEnginePower()));
        vehicleChoiseUpd.setValue(oldVehicle.getType());
        fuelChoiseUpd.setValue(oldVehicle.getFuelType());
        updVehicleCoordinatesX.setText(String.valueOf(oldVehicle.getX()));
        updVehicleCoordinatesY.setText(String.valueOf(oldVehicle.getY()));
        fuelTypeChoise=FuelType.valueOf(oldVehicle.getFuelType());
        vehicleTypeChoise=VehicleType.valueOf(oldVehicle.getType());
        fuelChoiseUpd.setOnAction(this::setFuelChoise);
        vehicleChoiseUpd.setOnAction(this::setVehicleChoise);
    }

}
