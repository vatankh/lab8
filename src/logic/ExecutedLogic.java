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

public class ExecutedLogic implements Initializable {

    @FXML
    private Label messageFE;

    @FXML
    void goToMainn(ActionEvent event) throws IOException {
        MainClient m =new MainClient();
        m.changeScene("mainTablePage.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageFE.setText(Client.getMessage());
    }
}
