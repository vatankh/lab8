package logic;

import client.*;

import common.collection.*;

import common.commands.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ExecuteScriptLogic {

    @FXML
    private TextField path;

    @FXML
    private Label wronPath;

    final List<String> commands= Arrays.asList("add","clear","remove_first","remove_greater"
            ,"update","remove_by_id","count_greater_than_engine_power","filter_greater_than_engine_power","filter_by_engine_power");

    @FXML
    void execute(ActionEvent event) throws IOException {
        String filePath = path.getText();
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()){
            wronPath.setText("file does not exist");
        } else if (!file.canRead()){
            wronPath.setText("no permissions for reading file");
        }else {
            wronPath.setText("");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String command;
            Answer answer=new Answer();
            while ((command = br.readLine()) != null) {
                int len = command.split(" ").length;
                if (len == 1) {
                    if (isCommand(command)) {
                        switch (command) {
                            case "clear":
                                Clear clear = new Clear();
                                Client.getNetworkUdp().sendCommand(clear);
                                answer = (Answer) Client.getNetworkUdp().reciveObj();
                                break;
                            case "add":
                                Add add = new Add(getV(br));
                                Client.getNetworkUdp().sendCommand(add);
                                answer = (Answer) Client.getNetworkUdp().reciveObj();
                                break;
                            case "remove_first":
                                remove_first removeFirst = new remove_first();
                                Client.getNetworkUdp().sendCommand(removeFirst);
                                answer = (Answer) Client.getNetworkUdp().reciveObj();
                                break;
                            case "remove_greater":
                                Remove_greater remove_greater = new Remove_greater(getV(br));
                                Client.getNetworkUdp().sendCommand(remove_greater);
                                answer = (Answer) Client.getNetworkUdp().reciveObj();
                                break;
                        }
                    }
                } else if (len == 2 ) {
                    String[] splited = command.split(" ");
                    String theCommand = splited[0];
                    String theValue = splited[1];
                    switch (theCommand) {
                        case "update":
                            System.out.println("here");
                            Vehicle vehicle = getV(br);
                            Update update = new Update(vehicle, theValue);
                            Client.getNetworkUdp().sendCommand(update);
                            answer = (Answer) Client.getNetworkUdp().reciveObj();
                            break;
                        case "remove_by_id":
                            Remove_by_id remove_by_id = new Remove_by_id(theValue);
                            Client.getNetworkUdp().sendCommand(remove_by_id);
                            answer = (Answer) Client.getNetworkUdp().reciveObj();
                            break;
                        case "count_greater_than_engine_power":
                            Count_greater_than_engine_power cgtep = new Count_greater_than_engine_power(theValue);
                            Client.getNetworkUdp().sendCommand(cgtep);
                            answer = (Answer) Client.getNetworkUdp().reciveObj();
                            break;
                        case "filter_greater_than_engine_power":
                            Filter_greater_than_engine_power filterGreater = new Filter_greater_than_engine_power(theValue);
                            Client.getNetworkUdp().sendCommand(filterGreater);
                            answer = (Answer) Client.getNetworkUdp().reciveObj();
                            break;
                        case "filter_by_engine_power":
                            Filter_by_engine_power filter = new Filter_by_engine_power(theValue);
                            Client.getNetworkUdp().sendCommand(filter);
                            answer = (Answer) Client.getNetworkUdp().reciveObj();
                            break;
                    }
                }
            }
            Stack<Vehicle> vehicleStack= answer.getStack();
            if (vehicleStack!=null){
                Client.setObservableList(vehicleStack);
            }else {
                Client.getNetworkUdp().sendCommand(new Login());
                Answer answer1 = (Answer) Client.getNetworkUdp().reciveObj();
                Client.setObservableList(answer1.getStack());
            }
            String message =answer.getMessage();
            if (message==null ||message.isEmpty()){
                message="finished successfully";
            }
            Client.setMesaage(message);
            MainClient mainClient=new MainClient();
            mainClient.changeScene("executedPage.fxml");
        }

    }

    public boolean isCommand(String command){
        int len =command.split(" ").length;
        if (len ==1){
            return commands.contains(command.trim());
        }else if (len ==2){
            String com = command.split(" ")[0];
            return commands.contains(com);
        }
        return command.split(" ")[0].equals("execute_script");
    }

    public Vehicle getV(BufferedReader br) throws IOException {
        String name = br.readLine();
        Double ep=Double.parseDouble(br.readLine());
        int y=Integer.parseInt(br.readLine());
        long x =Long.parseLong(br.readLine());
        Coordinates coordinates=new Coordinates(x,y);
        VehicleType vt=VehicleType.valueOf(br.readLine().toUpperCase());
        FuelType ft=FuelType.valueOf(br.readLine().toUpperCase());
        return new Vehicle(name,coordinates,ep,vt,ft);
    }


    @FXML
    void goBackFromExe(ActionEvent event) throws IOException {
        MainClient m = new MainClient();
        m.changeScene("mainTablePage.fxml");
    }

}
