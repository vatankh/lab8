package logic;

import client.Client;
import client.MainClient;
import common.collection.*;
import common.commands.*;
import common.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Stack;

public class LoginLogic {

    @FXML
    private Label wrongLogin;
    @FXML
    private TextField usernameLogin;
    @FXML
    private PasswordField passwordLogin;

    @FXML
    public void login() throws IOException {
        checkLogin();
    }
    @FXML
    public void  goToRegisterPage() throws IOException {
        MainClient m = new MainClient();
        m.changeScene("registerPage.fxml");

    }
    public void checkLogin() throws IOException {
        MainClient m = new MainClient();

        String username =usernameLogin.getText();
        String pass =passwordLogin.getText();
        Login login =new Login();
        Auth  auth =new Auth(username,pass);
        Client.getNetworkUdp().setAuth(auth);
        Client.getNetworkUdp().sendCommand(login);
        Object res =  Client.getNetworkUdp().reciveObj();
        if (res instanceof Answer){
            Answer answer =(Answer) res;
            if (answer.getMessage().equals("Authorized")){
                wrongLogin.setText("successfully login");
                Stack<Vehicle> stack =answer.getStack();
                Client.setObservableList(stack);
                Client.setUser(username);
                m.changeScene("mainTablePage.fxml");
            }else {
                wrongLogin.setText(answer.getMessage());
            }
        }else {
            System.out.println("okay");
        }


    }

    public void makeSpanish(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("es","PA");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("loginPage.fxml");
    }

    public void makeFrench(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("fr","FR");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("loginPage.fxml");
    }

    public void makeRussian(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("ru","RU");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("loginPage.fxml");
    }

    public void makeMacdonian(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("mk","MK");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("loginPage.fxml");
    }
}
