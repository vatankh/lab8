package client;

import common.collection.*;
import common.commands.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;


public class Client<T extends AbsCommand> {
    private DatagramSocket socket;
    private static  ClientNetworkUdp networkUdp;
    private  static ObservableList<SimpleVehicle> observableList;
    private static String user;
    private static SimpleVehicle oldVehicle;
    private static boolean filterBy;
    private static boolean filterGreater;
    private static String message;
    private static Locale locale=new Locale("ru","RU");
    private static SimpleVehicle simpleVehicle;

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        Client.locale = locale;
    }

    public static SimpleVehicle getOldVehicle() {
        return oldVehicle;
    }
    public  static void setFilterBy(boolean b){
        filterBy=b;
    }

    public static boolean isFilterGreater() {
        return filterGreater;
    }

    public static void setFilterGreater(boolean b){
        filterGreater=b;
    }

    public static boolean isFilterBy() {
        return filterBy;
    }

    public static void setOldVehicle(SimpleVehicle oldVehicle) {
        Client.oldVehicle = oldVehicle;
    }

    public  Client(DatagramSocket datagramSocket)  {
        this.socket=datagramSocket;
        networkUdp=new ClientNetworkUdp(socket);
    }
    public static void setUser(String username){
        user=username;
    }
    public static String getUser(){
        return user;
    }

    public static ClientNetworkUdp getNetworkUdp() {
        return networkUdp;
    }

    public static ObservableList<SimpleVehicle> getObservableList() {
        return observableList;
    }

    public static void setObservableList(Stack<Vehicle> vehicles) {
        try {
            Stack<SimpleVehicle> simpleVehicles = new Stack<SimpleVehicle>();
            vehicles.forEach(vehicle -> simpleVehicles.push(new SimpleVehicle(vehicle)));
            observableList = FXCollections.observableList(new ArrayList<>(simpleVehicles));
        } catch (Exception e){
            System.out.println("ex in client");
            System.out.println(e.getMessage());
        }

    }

    public static String getMessage() {
        return message;
    }

    public static void setMesaage(String m) {
        message=m;
    }

    public static SimpleVehicle getSimpleVehicle() {
        return simpleVehicle;
    }

    public static void setSimpleVehicle(SimpleVehicle simpleVehicle) {
        Client.simpleVehicle = simpleVehicle;
    }

    public void start() throws Exception {

    }
}
