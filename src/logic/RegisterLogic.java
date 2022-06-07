package logic;

import client.Client;
import client.MainClient;
import common.utils.*;
import common.commands.Register;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.Answer;

import java.io.IOException;
import java.util.Locale;

public class RegisterLogic {
    @FXML
    private Label wrongRegister;
    @FXML
    private TextField usernameRegister;
    @FXML
    private PasswordField passwordRegister;


    public void checkRegister() throws IOException {
        MainClient m = new MainClient();
        if (usernameRegister.getText().isEmpty()){
            wrongRegister.setText("name can not be empty");
        }else if (passwordRegister.getText().isEmpty()){
            wrongRegister.setText("password can not be empty");
        }else {
            String username =usernameRegister.getText();
            String pass =passwordRegister.getText();
            Auth  auth = new Auth(username,pass);
            Client.getNetworkUdp().setAuth(auth);
            Register register = new Register();
            Client.getNetworkUdp().sendCommand(register);
            Answer answer = (Answer) Client.getNetworkUdp().reciveObj();
            if (answer.getMessage().equals("Authorized")){
                wrongRegister.setText("successfully login");
                Client.setObservableList(answer.getStack());
                Client.setUser(username);
                m.changeScene("mainTablePage.fxml");
            }else {
                wrongRegister.setText(answer.getMessage());
            }
        }


    }

    public  void goToLoginPage() throws IOException {
        MainClient m = new MainClient();
        m.changeScene("loginPage.fxml");
    }
    public void register() throws IOException {
        checkRegister();
    }

    public void makeRussian(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("ru","RU");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("registerPage.fxml");
    }

    public void makeSpanish(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("es","PA");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("registerPage.fxml");
    }

    public void makeFrench(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("fr","FR");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("registerPage.fxml");
    }

    public void makeMacdonian(ActionEvent actionEvent) throws IOException {
        Locale locale =new Locale("mk","MK");
        Client.setLocale(locale);
        MainClient mainClient= new MainClient();
        mainClient.changeScene("registerPage.fxml");
    }
}
