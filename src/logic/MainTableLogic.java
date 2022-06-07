package logic;

import client.Client;
import client.MainClient;
import client.SimpleVehicle;
import common.commands.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import server.Answer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainTableLogic implements Initializable {

    @FXML
    public Button filter_by_engine_power;

    @FXML
    public Button filterGreaterThanEngine;
    public TableColumn<SimpleVehicle,String> ownerTable;

    @FXML
    private Label serverInfo;

    @FXML
    private Label usernameMain;

    @FXML
    private TableColumn<SimpleVehicle, String> CreationDateTable;

    @FXML
    private TableColumn<SimpleVehicle,String> coordinatesTable;

    @FXML
    private TableColumn<SimpleVehicle, String> FuelTypeTable;

    @FXML
    private TableColumn<SimpleVehicle, Double> enginePowerTable;

    @FXML
    private TableColumn<SimpleVehicle, Long> idTable;

    @FXML
    private TableColumn<SimpleVehicle, String> nameTable;


    @FXML
    private TableColumn<SimpleVehicle, String> vehicleTypeTable;

    @FXML
    private TableView<SimpleVehicle> vehiclesTable;

    public boolean oneClick=false;

    public SimpleVehicle clicled;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("started intilize");
            FuelTypeTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("fuelType"));
            enginePowerTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,Double>("enginePower"));
            idTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,Long>("id"));
            nameTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("name"));
            vehicleTypeTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("type"));
            coordinatesTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("coordinates"));
            ownerTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("owner"));
            CreationDateTable.setCellValueFactory(new PropertyValueFactory<SimpleVehicle,String>("creationDateString"));

            vehiclesTable.setItems(Client.getObservableList());
            usernameMain.setText(Client.getUser());
            if (Client.isFilterBy()){
                filter_by_engine_power.setStyle("-fx-border-color: green; -fx-border-width: 5");
            }
            if (Client.isFilterGreater()){
                filterGreaterThanEngine.setStyle("-fx-border-color: green; -fx-border-width: 5");
            }
        } catch (Exception e){
            System.out.println("exiption found oraa ");
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

    }
    @FXML
    public void updateVehicle() throws IOException {
        SimpleVehicle oldVehicle=vehiclesTable.getSelectionModel().getSelectedItem();
        if (oldVehicle!= null){
            Client.setOldVehicle(oldVehicle);
            MainClient mainClient =new MainClient();
            mainClient.changeScene("updatePage.fxml");

        }else {
            System.out.println("not selected");
        }
    }




    public void goToAddPage() throws IOException {
        MainClient m= new MainClient();
        m.changeScene("addPage.fxml");
    }

    public void remove_by_id(javafx.event.ActionEvent actionEvent) {
        SimpleVehicle oldVehicle=vehiclesTable.getSelectionModel().getSelectedItem();
        Remove_by_id remove_by_id = new Remove_by_id(String.valueOf(oldVehicle.getId()));
        try {
            Client.getNetworkUdp().sendCommand(remove_by_id);
            Answer answer =(Answer) Client.getNetworkUdp().reciveObj();
            if (answer.getMessage().equals("unauthorized")){
            goToUnauthorized();
            }else if (answer.getMessage().equals("authorized")){
                Client.getObservableList().remove(oldVehicle);
                System.out.println("okay");
            }else {
                System.out.println(answer.getMessage());
            }

        }catch (Exception e){

        }
    }

    public void goToCountPage(ActionEvent actionEvent) throws IOException {
        MainClient mainClient = new MainClient();
        mainClient.changeScene("countGreaterThanEnginePowerPage.fxml");
    }

    public void filter_by_engine_power(MouseEvent mouseEvent) throws IOException {
        boolean clicked =filter_by_engine_power.getStyle().contains("green");
        if (clicked){
            filter_by_engine_power.setStyle("");
            Login login = new Login();
            Client.getNetworkUdp().sendCommand(login);
            Answer answer =(Answer) Client.getNetworkUdp().reciveObj();
            Client.setObservableList(answer.getStack());
            vehiclesTable.setItems(Client.getObservableList());
            Client.setFilterBy(false);
        }else {
            MainClient mainClient= new MainClient();
            mainClient.changeScene("filterByEnginePowerPage.fxml");
        }
    }

    public void filter_greater(ActionEvent actionEvent) throws IOException {
        if (Client.isFilterGreater()){
            filterGreaterThanEngine.setStyle("");
            Login login = new Login();
            Client.getNetworkUdp().sendCommand(login);
            Answer answer =(Answer) Client.getNetworkUdp().reciveObj();
            Client.setObservableList(answer.getStack());
            vehiclesTable.setItems(Client.getObservableList());
            Client.setFilterGreater(false);
        }else {
            MainClient mainClient= new MainClient();
            mainClient.changeScene("filterGreaterThanEnginePowerPage.fxml");
        }
    }

    public void clear(ActionEvent actionEvent) throws IOException {
        Clear clear = new Clear();
        Client.getNetworkUdp().sendCommand(clear);
        Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
        Client.setObservableList(answer.getStack());
        vehiclesTable.setItems(Client.getObservableList());
    }

    public void remove_firstT(ActionEvent actionEvent) throws IOException {
        remove_first remove =new remove_first();
        Client.getNetworkUdp().sendCommand(remove);
        Answer answer=(Answer) Client.getNetworkUdp().reciveObj();
        if (answer.getMessage().contains("unauthorized")){
            goToUnauthorized();
        }else {
            long id =answer.getVehicle().getId();
            int index=-1;
            ObservableList<SimpleVehicle> simpleVehicles = vehiclesTable.getItems();
            for (int i=0;i<simpleVehicles.size();i++){
                if (simpleVehicles.get(i).getId() == id){
                    index=i;
                    break;
                }
            }
            if (index!=-1){
                vehiclesTable.getItems().remove(index);
            }
        }
    }
    public void goToUnauthorized() throws IOException {
        MainClient m = new MainClient();
        m.changeScene("unauthorizedPage.fxml");
    }

    public void removeGreater(ActionEvent actionEvent) throws IOException {
        MainClient mainClient =new MainClient();
        mainClient.changeScene("removeGreaterPage.fxml");
    }

    public void goToDrawPage(MouseEvent mouseEvent) throws IOException {
//        if (!oneClick){
//            oneClick=true;
////            clicled=vehiclesTable.getSelectionModel().getSelectedItem();
//        }else {
//            if (clicled.equals(vehiclesTable.getSelectionModel().getSelectedItem())){
//                SimpleVehicle simpleVehicle =vehiclesTable.getSelectionModel().getSelectedItem();
//                Client.setOldVehicle(simpleVehicle);
//                MainClient mainClient = new MainClient();
//                mainClient.changeScene("drawPage.fxml");
//            }
//            oneClick=false;
//
//        }

    }

    public void execute_script(ActionEvent actionEvent) throws IOException {
        MainClient mainClient = new MainClient();
        mainClient.changeScene("executeScriptPage.fxml");
    }

    public void getInfo(ActionEvent actionEvent) throws IOException {
        Info info =new Info();
        Client.getNetworkUdp().sendCommand(info);
        Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
        Client.setMesaage(answer.getMessage());
        MainClient mainClient =new MainClient();
        mainClient.changeScene("infoPage.fxml");
    }

    public void gotoV(ActionEvent actionEvent) throws IOException {
        MainClient mainClient =new MainClient();
        mainClient.changeScene("visualizationPage.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        MainClient mainClient = new MainClient();
        mainClient.changeScene("loginPage.fxml");
    }
}

