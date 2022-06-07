package logic;

import client.Client;
import client.SimpleVehicle;
import common.collection.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class VInfoLogic implements Initializable {
    @FXML
    public DialogPane dialogPane;

    @FXML
    private TextField X;

    @FXML
    private TextField Y;

    @FXML
    private TextField enginePower;

    @FXML
    private ChoiceBox<String> fuelType;

    @FXML
    private Label id;

    @FXML
    private TextField name;

    @FXML
    private Label owner;

    @FXML
    private ChoiceBox<String> vehicleType;
    private VehicleType vehicleTypeChoise;
    private  FuelType fuelTypeChoise;

    @FXML
    private Label vreationDate;
    List<String> fuelList= Arrays.asList("GASOLINE","MANPOWER","NUCLEAR","PLASMA");
    List<String> vehicleList=Arrays.asList("PLANE","SHIP","MOTORCYCLE","HOVERBOARD");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleVehicle simpleVehicle = Client.getSimpleVehicle();
        id.setText(String.valueOf(simpleVehicle.getId()));
        name.setText(simpleVehicle.getName());
        owner.setText(simpleVehicle.getOwner());
        vreationDate.setText(simpleVehicle.getCreationDateString());
        enginePower.setText(String.valueOf(simpleVehicle.getEnginePower()));
        vehicleType.getItems().addAll(vehicleList);
        fuelType.getItems().addAll(fuelList);
        vehicleType.setValue(simpleVehicle.getType());
        fuelType.setValue(simpleVehicle.getFuelType());
        X.setText(String.valueOf(simpleVehicle.getX()));
        Y.setText(String.valueOf(simpleVehicle.getY()));
        vehicleType.setOnAction(this::setVehicleChoise);
        fuelType.setOnAction(this::setVehicleChoise);
        if (!owner.getText().equals(Client.getUser())){
            System.out.println("ora22");
            Button button = (Button) dialogPane.lookupButton(ButtonType.APPLY);
            if (button != null){
                System.out.println("not null");
                button.setDisable(true);
                Node node1=dialogPane.lookup("VBox");
                int counter=0;
                if (node1 != null){
                    VBox vBox = (VBox) node1;
                    ObservableList<Node> HBoxes = vBox.getChildren();
                    for(int i=0;i<HBoxes.size();i++){
                        if (HBoxes.get(i) instanceof HBox){
                            HBox hBox= (HBox) HBoxes.get(i);
                            ObservableList<Node> hBoxChildren = hBox.getChildren();
                            switch (counter){
                                case 0:
                                case 1:
                                    TextField  textFieldName = (TextField) hBoxChildren.get(1);
                                    textFieldName.setDisable(true);
                                    break;
                                case 2:
                                    VBox corrrdinates=(VBox) hBoxChildren.get(1);
                                    HBox hBoxCoordinates=(HBox) corrrdinates.getChildren().get(1);
                                    TextField textFieldX =(TextField) hBoxCoordinates.getChildren().get(0);
                                    textFieldX.setDisable(true);
                                    TextField textFieldY=(TextField) hBoxCoordinates.getChildren().get(1);
                                    textFieldY.setDisable(true);
                                    break;
                                case 3:
                                case 4:
                                    ChoiceBox<String> choiceBoxVType =(ChoiceBox<String>) hBoxChildren.get(1);
                                    choiceBoxVType.setDisable(true);
                                    break;
                            }
                            counter++;
                        }
                    }
                }
            }
        }

    }
    private void setVehicleChoise(ActionEvent actionEvent) {
        vehicleTypeChoise= VehicleType.valueOf(vehicleType.getValue());
    }

    private VehicleType getVehicleChoise(){
        return vehicleTypeChoise;
    }

    private FuelType getFuelTypeChoise(){
        return fuelTypeChoise;
    }


    private void setFuelChoise(ActionEvent actionEvent) {
        fuelTypeChoise=FuelType.valueOf(fuelType.getValue());
    }
}
