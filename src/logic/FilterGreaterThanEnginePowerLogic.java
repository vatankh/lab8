package logic;


import client.Client;
import client.MainClient;
import common.commands.Filter_greater_than_engine_power;
import common.utils.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.*;

import java.io.IOException;

public class FilterGreaterThanEnginePowerLogic {

        @FXML
        private TextField enginePowerForFilteGreater;

        @FXML
        private Label wrongEnginePowerFG;

        @FXML
        void filterGreater(ActionEvent event) throws IOException {
            String message = Parser.ParseDouble(enginePowerForFilteGreater.getText());
            if (!message.isEmpty()){
                wrongEnginePowerFG.setText(message);
            }else {
                Filter_greater_than_engine_power filter = new Filter_greater_than_engine_power(enginePowerForFilteGreater.getText());
                Client.getNetworkUdp().sendCommand(filter);
                Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
                Client.setObservableList(answer.getStack());
                MainClient mainClient=new MainClient();
                Client.setFilterGreater(true);
                mainClient.changeScene("mainTablePage.fxml");
            }

        }

        @FXML
        void goBackFromFilterGreater(ActionEvent event) throws IOException {
            MainClient m = new MainClient();
            m.changeScene("mainTablePage.fxml");
        }

    }

