package logic;
import client.Client;
import client.MainClient;
import common.commands.Filter_by_engine_power;
import common.utils.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.*;

import java.io.IOException;

public class FilterByEnginePowerLogic {

        @FXML
        private TextField enginePowerForFilteBy;

        @FXML
        private Label wrongEnginePowerFB;

        @FXML
        void filterBy(ActionEvent event) throws IOException {
            String message = Parser.ParseDouble(enginePowerForFilteBy.getText());
            if (!message.isEmpty()){
                wrongEnginePowerFB.setText(message);
            }else {
                Filter_by_engine_power filter = new Filter_by_engine_power(enginePowerForFilteBy.getText());
                Client.getNetworkUdp().sendCommand(filter);
                Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
                Client.setObservableList(answer.getStack());
                MainClient mainClient=new MainClient();
                Client.setFilterBy(true);
                mainClient.changeScene("mainTablePage.fxml");
            }

        }

        @FXML
        void goBackFromFilterBy(ActionEvent event) throws IOException {
            MainClient mainClient = new MainClient();
            mainClient.changeScene("mainTablePage.fxml");
        }

    }


