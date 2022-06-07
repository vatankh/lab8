package logic;


import client.Client;
    import client.MainClient;
    import common.utils.Parser;
    import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

    import java.io.IOException;

public class CountGreaterThanEnginePowerLogic {

        @FXML
        private TextField enginePowerForCount;

        @FXML
        private Label result_count;
       @FXML
       private Label wrongEnginePowerC;

        @FXML
        void count(ActionEvent event) {
            String message = Parser.ParseDouble(enginePowerForCount.getText());
            if (!message.isEmpty()){
                wrongEnginePowerC.setText(message);
            }else {
                int count= (int) Client.getObservableList().stream().filter(simpleVehicle -> simpleVehicle.getEnginePower() > Double.parseDouble(enginePowerForCount.getText())).count();
                result_count.setText("The number of Vehicles is "+count);
            }
        }

        @FXML
        void goBackFromCount(ActionEvent event) throws IOException {
            MainClient mainClient = new MainClient();
            mainClient.changeScene("mainTablePage.fxml");
        }

    }


