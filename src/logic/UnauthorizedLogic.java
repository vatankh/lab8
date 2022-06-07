package logic;

import client.MainClient;
import javafx.event.ActionEvent;

import java.io.IOException;

public class UnauthorizedLogic {
    public void goBackFromErorr(ActionEvent actionEvent) throws IOException {
        MainClient mainClient= new MainClient();
        mainClient.changeScene("mainTablePage.fxml");
    }
}
