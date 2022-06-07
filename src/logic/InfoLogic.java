package logic;

import client.Client;
import client.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoLogic implements Initializable {

    @FXML
    private Label info;
    public void goback(ActionEvent actionEvent) throws IOException {
        MainClient mainClient = new MainClient();
        mainClient.changeScene("mainTablePage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String pattern= Client.getMessage();
        info.setText(Client.getMessage());
    }
}
