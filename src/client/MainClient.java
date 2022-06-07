package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainClient extends Application {
    public static  Stage stage;


    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    Locale locale = Client.getLocale();

    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket =new DatagramSocket();
        datagramSocket.setSoTimeout(5000);
        Client client =new Client(datagramSocket);
        client.start();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage =primaryStage;

        ResourceBundle bundle=ResourceBundle.getBundle("labels",locale);
        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"),bundle);
        stage.setTitle("Vehicles app");
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }
    public void changeScene(String fxmlFile) throws IOException {
        ResourceBundle bundle=ResourceBundle.getBundle("labels",locale);
        Parent root = FXMLLoader.load(getClass().getResource("/" +fxmlFile),bundle);
        stage.getScene().setRoot(root);
    }
//    public void update() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        ResourceBundle bundle=ResourceBundle.getBundle("labels",locale);
//        fxmlLoader.setResources(bundle);
//        fxmlLoader.setLocation(getClass().getResource("/updatePage.fxml"));
//        DialogPane updateDialogPane = fxmlLoader.load();
//        Dialog<ButtonType> dialog=new Dialog<>();
//        dialog.setDialogPane(updateDialogPane);
//        dialog.setTitle("update page");
//        Optional<ButtonType> clickedButtoun =dialog.showAndWait();
//        if (clickedButtoun.get() ==ButtonType.CLOSE){
//            System.out.println("cancel");
//        }
//    }

    public static Stage getStage() {
        return stage;
    }
}
