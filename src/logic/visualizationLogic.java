package logic;

import client.Client;
import client.MainClient;
import client.SimpleVehicle;
import common.collection.*;
import common.collection.VehicleType;
import common.commands.Update;
import common.utils.Parser;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import server.Answer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class visualizationLogic  implements Initializable {
    @FXML
    public Canvas canvas;
    GraphicsContext g ;
    List<MyShape> shapes= new ArrayList<>();;

     boolean work=true;
     boolean work2 =true;
     String name;
     String stringEnginePower;
     String stringX;
     String stringY;
     String stringVehicleType;
     String stringFuelType;
     Label wrongName;
     Label wrongEnginePower;
     Label wrongCoordinates;
    boolean cont;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        g= canvas.getGraphicsContext2D();
        Client.getObservableList().forEach(simpleVehicle -> {
            if (VehicleType.valueOf(simpleVehicle.getType()).equals(VehicleType.SHIP)){
                shapes.add(new Ship(simpleVehicle));
            }else if (VehicleType.valueOf(simpleVehicle.getType()).equals(VehicleType.HOVERBOARD)) {
                shapes.add(new Hoverboard(simpleVehicle));
            }else if (VehicleType.valueOf(simpleVehicle.getType()).equals(VehicleType.PLANE)){
                shapes.add(new Airplane(simpleVehicle));
            }else if (VehicleType.valueOf(simpleVehicle.getType()).equals(VehicleType.MOTORCYCLE)){
                shapes.add(new Motor(simpleVehicle));
            }
        });
        Timeline animation =new Timeline(new KeyFrame(Duration.millis(500), e -> update()));
        animation.setCycleCount(Timeline.INDEFINITE);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (work) {
                    update();
                }
            }
        };
        animationTimer.start();
        animation.play();

    }

    public void backk(ActionEvent actionEvent) throws IOException {
        MainClient mainClient= new MainClient();
        mainClient.changeScene("mainTablePage.fxml");
    }

    public void stop2(ActionEvent actionEvent) {
        work2=!work2;
    }

    public void slow(ActionEvent actionEvent) {
        work =!work;
    }

    public void getObjectInfo(MouseEvent mouseEvent) throws IOException {
        double x=mouseEvent.getX();
        double y=mouseEvent.getY();
        System.out.println("x="+mouseEvent.getX());
        System.out.println("y="+mouseEvent.getY());
        for(int i=shapes.size()-1;i>0;i--){
            MyShape m = shapes.get(i);
            m.update();
            if (m.leftBound <x &&m.rightBound >x &&m.upperBound<y &&m.underBound>y){
                loadinfo(m);
                break;
            }
        }
    }

    private void loadinfo(MyShape m) throws IOException {
        Client.setSimpleVehicle(m.simpleVehicle);
        FXMLLoader fxmlLoader = new FXMLLoader();
        ResourceBundle bundle=ResourceBundle.getBundle("labels",Client.getLocale());
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(getClass().getResource("/vInfoPage.fxml"));
        DialogPane infoDialogPane=fxmlLoader.load();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(infoDialogPane);
        System.out.println("ora");
        dialog.setTitle("object info");
        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btOk.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    validateAndStore(dialog.getDialogPane());
                    if (!cont){
                        event.consume();
                    }else {
                        updateVehicle();
                    }
                    // Check whether some conditions are fulfilled
                }
        );
        Optional<ButtonType> clickedButton= dialog.showAndWait();
        if (clickedButton.get() ==ButtonType.CANCEL){
            System.out.println("cancel");
        }else  if (clickedButton.get() ==ButtonType.APPLY){
            System.out.println("hit applay");
        }
    }

    private void updateVehicle() {
        Double enginePower = Double.parseDouble(stringEnginePower);
        Coordinates coordinates = new Coordinates(Long.parseLong(stringX), Integer.parseInt(stringX));
        VehicleType vehicleType = VehicleType.valueOf(stringVehicleType);
        FuelType fuelType = FuelType.valueOf(stringFuelType);
        Vehicle vehicle = new Vehicle(name, coordinates, enginePower, vehicleType, fuelType);
        String id = String.valueOf(Client.getSimpleVehicle().getId());
        Update update = new Update(vehicle,id);
        try {
            Client.getNetworkUdp().sendCommand(update);
            Object obj =Client.getNetworkUdp().reciveObj();
            if (obj instanceof Answer){
                Answer answer=(Answer) obj;
                Vehicle resVehicle = answer.getVehicle();
                ObservableList<SimpleVehicle> simpleVehicles=Client.getObservableList();
                for (int i=0;i<simpleVehicles.size();i++){
                    if (simpleVehicles.get(i).getId() ==resVehicle.getId()){
                        Client.getObservableList().set(i,new SimpleVehicle(resVehicle));
                            MainClient m = new MainClient();
                            m.changeScene("visualizationPage.fxml");
                            break;
                        }
                    }
            }else {
                System.out.println("there is no answer");
            }
        }catch (Exception e){

        }
    }

    private boolean validateAndStore(DialogPane dialogPane) {
        System.out.println("start valdiate and store");
        ObservableList<javafx.scene.Node> values= dialogPane.getChildren();
        Node node1=dialogPane.lookup("VBox");
        int counter=0;
        int counter2=0;
        VBox vBox = (VBox) node1;
        ObservableList<Node> HBoxes = vBox.getChildren();
        for(int i=0;i<HBoxes.size();i++){
            if (HBoxes.get(i) instanceof HBox){
                HBox hBox= (HBox) HBoxes.get(i);
                ObservableList<Node> hBoxChildren = hBox.getChildren();
                switch (counter){
                    case 0:
                        TextField  textFieldName = (TextField) hBoxChildren.get(1);
                        name =textFieldName.getText();
                        break;
                    case 1:
                        TextField textFieldEnginePower=(TextField) hBoxChildren.get(1);
                        stringEnginePower=textFieldEnginePower.getText();
                        break;
                    case 2:
                        VBox corrrdinates=(VBox) hBoxChildren.get(1);
                        HBox hBoxCoordinates=(HBox) corrrdinates.getChildren().get(1);
                        TextField textFieldX =(TextField) hBoxCoordinates.getChildren().get(0);
                        stringX=textFieldX.getText();
                        TextField textFieldY=(TextField) hBoxCoordinates.getChildren().get(1);
                        stringY=textFieldY.getText();
                        break;
                    case 3:
                        ChoiceBox<String> choiceBoxVType =(ChoiceBox<String>) hBoxChildren.get(1);
                        stringVehicleType=choiceBoxVType.getValue();
                        break;
                    case 4:
                        ChoiceBox<String> choiceBoxFType=(ChoiceBox<String>) hBoxChildren.get(1);
                        stringFuelType=choiceBoxFType.getValue();
                        break;
                }
                counter++;
            }else if (HBoxes.get(i) instanceof Label){
                switch (counter2){
                    case 0:
                        wrongName =(Label) HBoxes.get(i);
                        break;
                    case 1:
                        wrongEnginePower=(Label) HBoxes.get(i);
                        break;
                    case 2:
                        wrongCoordinates=(Label) HBoxes.get(i);
                        break;
                }
                counter2++;
            }
        }
         cont =true;
        if (name.isEmpty()){
            wrongName.setText("name can not be empty");
            cont=false;
        }else {
            wrongName.setText("");
        }
        String message = Parser.ParseDouble(stringEnginePower);
        if (!message.isEmpty()){
            wrongEnginePower.setText(message);
            cont=false;
        }else {
            wrongEnginePower.setText("");
        }
        message =Parser.parsLong(stringX);
        String corRes="";
        if (!message.isEmpty()){
            corRes= "X ->"+message;
        }else  if (Long.parseLong(stringX) >911){
            corRes="X cannot be bigger than 911";
        }
        message=Parser.ParseInt(stringY);
        if (!message.isEmpty()){
            corRes+= " ,Y -> "+message;
        }else if (Long.parseLong(stringY) >911){
            corRes+=" Y cannot be bigger than 911";
        }
        if (!corRes.isEmpty()){
            wrongCoordinates.setText(corRes);
            cont=false;
        }else {
            wrongCoordinates.setText("");
        }
        return cont;
    }

    public class Ship extends MyShape{

        public Ship(SimpleVehicle simpleVehicle){
            super(simpleVehicle);
            rightBound=startX+x;
            leftBound=startX;
            underBound=(startY+y/2.7)+y/3;
            upperBound=startY;
        }

        @Override
        public void make() {
            g.setFill(color);
            g.fillPolygon(new double[]{startX,startX+x, (startX+x- 0.175*x),  (startX+0.175*x)} ,new double[]{(startY+y/2.7),(startY+y/2.7),(startY+y/2.7)+y/3,(startY+y/2.7)+y/3},4);
            g.setFill(Color.LIGHTGRAY);
            g.fillPolygon(new double[]{ (startX+0.175*x),  (startX+0.175*x)+(0.65*x*0.115),  (startX+x- 0.175*x)-(0.65*x*0.115),(startX+x- 0.175*x)},new double[]{(startY+y/2.7) , (startY+y/5), (startY+y/5) ,startY+y/2.7},4);
            g.setFill(Color.DARKGRAY);
            g.strokeLine(startX+x/3, (int) (startY+y/2.7),startX+x/3,startY);
            g.setFill(color);
            g.fillPolygon(new double[]{startX+x/3,startX+x/2,startX+x/3},new double[]{ startY, startY+y/18, startY+y/9},3);
        }

        @Override
        public void move() {
            if (goRight){
                rightBound=startX+x;
                if (rightBound +change < width){
                    startX+=change;
                }else {
                    goRight=false;
                }
            }else  {
                leftBound=startX;
                if (leftBound -change > 0){
                    startX-=change;
                }else {
                    goRight=true;
                }
            }
            if (goUp){
                upperBound=startY;
                if (upperBound -change >0){
                    startY-=change;
                }else {
                    goUp=false;
                }
            }else {
                underBound=(startY+y/2.7)+y/3;
                if (underBound +change <height){
                    startY+=change;
                }else {
                    goUp=true;
                }
            }

        }

        @Override
        public void update() {
            rightBound=startX+x;
            leftBound=startX;
            underBound=(startY+y/2.7)+y/3;
            upperBound=startY;
        }


    }
    public class Hoverboard extends MyShape{
        public Hoverboard(SimpleVehicle simpleVehicle){
            super(simpleVehicle);
            rightBound=startX +x/2;
            leftBound=startX;
            underBound=startY+y/6;
            upperBound=startY;
        }

        @Override
        public void make() {
            int diff= (int) (x/2.5);
            int W= (int) (x/9);
            int H=y/9;
            g.setFill(Color.BLACK);
            g.fillArc(startX,startY+H/2,W,H,180,180, ArcType.CHORD);
            g.fillArc(startX+diff,startY+H/2,W,H,180,180,ArcType.OPEN);
            g.setFill(color);
            g.fillArc(startX,startY,W*2,H*2,90,90,ArcType.ROUND);
            g.fillRect(startX+W,startY,diff-W,H);
            g.fillArc(startX+diff-W,startY,W*2,H*2,0,90,ArcType.ROUND);
            g.setFill(Color.BLACK);
            g.fillRect((int) (startX+W*1.2),startY+H*0.17, (int) (diff-W*1.3),H/2);
        }

        @Override
        public void move() {
            if (goRight){
                rightBound=startX +x/2;
                if (rightBound < width){
                    startX+=change;
                }else {
                    goRight=false;
                }
            }else  {
                leftBound=startX;
                if (leftBound -change > 0){
                    startX-=change;
                }else {
                    goRight=true;
                }
            }
            if (goUp){
                upperBound=startY;
                if (upperBound -change >0){
                    startY-=change;
                }else {
                    goUp=false;
                }
            }else {
                underBound=startY+y/6;
                if ( underBound+change <height){
                    startY+=change;
                }else {
                    goUp=true;
                }
            }
        }

        @Override
        public void update() {
            rightBound=startX +x/2;
            leftBound=startX;
            underBound=startY+y/6;
            upperBound=startY;
        }

    }
    public class Airplane extends MyShape{
        public Airplane(SimpleVehicle simpleVehicle){
            super(simpleVehicle);
            rightBound=startX +x/1.8;
            leftBound=startX -(x/8)-(x/8)*3;
            upperBound=startY -y/4;
            underBound=startY +y/2.4;

        }

        @Override
        public void make() {
            int baseWidth= (int) (x/8);
            int baseHeight=y/6;


            g.setFill(color);


            g.fillArc(startX +baseWidth*2, startY -baseHeight/2,baseWidth/2,baseHeight,180,180, ArcType.OPEN);
            g.fillArc(startX -baseWidth, startY -baseHeight/2,baseWidth/2,baseHeight,180,180,ArcType.OPEN);
            g.setFill(javafx.scene.paint.Color.LIGHTGRAY);
            g.fillPolygon(new double[]{startX +baseWidth,
                            startX +baseWidth+baseWidth*3,
                            (int) (startX +baseWidth+baseWidth*3.5),
                            startX +baseWidth},
                    new double[]{startY,
                            startY +baseHeight/2,
                            startY +baseHeight,
                            startY +baseHeight*2/3},4);
            g.fillPolygon(new double[]{startX, startX -baseWidth*3,  (startX -baseWidth*3.5), startX},new double[]{startY, startY +baseHeight/2, startY +baseHeight, startY +baseHeight*2/3},4);
            g.setFill(color);//here
            g.fillRect(startX, startY,baseWidth,baseHeight);
            g.fillArc(startX, startY -y/4,baseWidth,y/2,0,180,ArcType.OPEN);
            g.fillArc(startX, startY -y/10,baseWidth, y/2,180,180,ArcType.OPEN);
            g.fillPolygon(new double[]{ (startX +baseWidth/2.5), startX -baseWidth/3, startX +baseWidth+baseWidth/3,  (startX +baseWidth/1.6)},new double[]{(startY +y/2.7),  (startY +y/2.4),  (startY +y/2.4),  (startY +y/2.7)},4);
            g.fillPolygon(new double[]{startX,  (startX +baseWidth*0.46),  (startX +baseWidth*0.54), startX +baseWidth},new double[]{startY,  (startY +y/2.4),  (startY +y/2.4), startY},4);
            g.setFill(Color.WHITE);
            g.fillArc(startX +baseWidth/3, (startY -y/4.5),baseWidth/3,baseWidth/5,0,180,ArcType.OPEN);
        }

        @Override
        public void move() {
            if (goRight){
                rightBound=startX +change+x/1.8;
                if (rightBound < width){
                    startX +=change;
                }else {
                    goRight=false;
                }
            }else  {
                leftBound=startX -(x/8)-(x/8)*3;
                if (leftBound -change > 0){
                    startX -=change;
                }else {
                    goRight=true;
                }
            }
            if (goUp){
                upperBound=startY -y/4;
                if (upperBound -change >0){
                    startY -=change;
                }else {
                    goUp=false;
                }
            }else {
                underBound=startY +y/2.4;
                if (underBound +change <height){
                    startY +=change;
                }else {
                    goUp=true;
                }
            }

        }

        @Override
        public void update() {
            rightBound=startX +x/1.8;
            leftBound=startX -(x/8)-(x/8)*3;
            upperBound=startY -y/4;
            underBound=startY +y/2.4;
        }


    }
    public class Motor extends MyShape{
        public Motor(SimpleVehicle simpleVehicle){
            super(simpleVehicle);
            rightBound=startX+x/2;
            leftBound=startX;
            underBound=startY+y/9;
            upperBound=startY-y/9;
        }

        @Override
        public void make() {
            int diff= (int) (x/3);
            g.setFill(Color.BLACK);
            g.fillOval(startX,startY,x/9,y/9);
            g.setFill(Color.WHITE);
            g.fillOval(startX+x/80,startY+y/80,x/11,y/11);
            g.setFill(Color.BLACK);
            g.fillOval(startX+diff,startY,x/9,y/9);
            g.setFill(Color.WHITE);
            g.fillOval(startX+diff+x/80,startY+y/80,x/11,y/11);
            g.setFill(color);
            g.fillRect(startX+x/18,startY+y/18,x/9,y/90);
            g.fillPolygon(new double[]{startX+diff+x/15,startX+x/9,startX+diff,startX+diff+x/90}, new double[]{startY+y/15,startY,startY-y/20,startY+y/80-10},4);
            g.fillRect(startX+x/9,startY,diff-x/9,y/11);
            g.setFill(Color.GRAY);
            g.fillOval(startX+diff-x/15,startY-y/14,x/14,y/14);
            g.setFill(color);
            g.fillRect(startX+diff-x/15,startY-y/14,x/15,y/14);
            g.setFill(Color.BLACK);
            g.fillRect(startX+x/8,startY-y/10,x/40,y/10);
            g.fillRect(startX+x/8,startY-y/60,x/10,y/30);
            g.fillOval(startX+diff-x/16,startY-y/15,x/31,y/31);
            g.setFill(Color.WHITE);
            g.fillOval(startX+diff-x/16+x/100,startY-y/15+y/100,x/62,y/62);
            g.setFill(color);
            g.strokeArc(startX+diff,startY-y/25,x/10,y/5,80,90,ArcType.OPEN);
            g.strokeArc(startX+x/45,startY-y/25,x/10,y/5,10,80,ArcType.OPEN);
        }

        @Override
        public void move() {
            if (goRight){
                rightBound=startX+x/2;
                if (rightBound+change < width){
                    startX+=change;
                }else {
                    goRight=false;
                }
            }else  {
                leftBound=startX;
                if (leftBound-change > 0){
                    startX-=change;
                }else {
                    goRight=true;
                }
            }
            if (goUp){
                upperBound=startY-y/9;
                if (upperBound -change >0){
                    startY-=change;
                }else {
                    goUp=false;
                }
            }else {
                underBound=startY+y/9;
                if (underBound +change <height){
                    startY+=change;
                }else {
                    goUp=true;
                }
            }
        }
        public  void update(){
            rightBound=startX+x/2;
            leftBound=startX;
            underBound=startY+y/9;
            upperBound=startY-y/9;
        }
    }

    public abstract class MyShape{
        int x;
        int y;
        int startX;
        int startY;
        double upperBound;
        double underBound;
        double leftBound;
        double rightBound;
        final int change=10;
        final double width = canvas.getWidth();
        final double height =canvas.getHeight();
        boolean goRight=true;
        boolean goUp=true;
        Color color;
        SimpleVehicle simpleVehicle;
        public MyShape(SimpleVehicle simpleVehicle){
            this.simpleVehicle=simpleVehicle;
            this.x= Math.toIntExact(simpleVehicle.getX());
            this.y=simpleVehicle.getY();
            startX = (int) (600*Math.random());
            startY = (int) (400*Math.random());
            color=getColor(simpleVehicle.getOwner());
        }
        abstract public void make();
        abstract public void move();
        abstract public void update();
    }
    public void update(){
        if (work2){
            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapes.forEach(shape -> {
                shape.move();
                shape.make();
            });
        }



    }
    public Color getColor(String name){
        int numbers[] = new int[3];
        boolean goBig= true;
        boolean goDown = true;
        if (name.length() ==1){
            numbers[0]=name.charAt(0);
            numbers[1]=name.charAt(0);
            numbers[2]=name.charAt(0);
        }else if (name.length() ==2){
            numbers[0]=name.charAt(0);
            numbers[1]=name.charAt(1);
            numbers[2]=name.charAt(1);
        }else if (name.length() == 3){
            numbers[0]=name.charAt(0);
            numbers[1]=name.charAt(1);
            numbers[2]=name.charAt(2);
        }else if (name.length() > 3){
            numbers[0]=name.charAt(0);
            numbers[1]=name.charAt(1);
            numbers[2]=name.charAt(2);
            for (int i=2,j=0;i<name.length();i++,j++){
                int currentNum = numbers[j];
                int currentStr = name.charAt(i);
                if (i%2 ==0){
                    if (goBig &&currentNum +currentStr <255){
                        goBig=true;
                        numbers[j]=currentNum+currentStr;
                    }
                    else{
                        goBig=false;
                        if (currentNum-currentStr >0){
                            numbers[j]=currentNum-currentStr;
                        }else {
                            goBig=true;
                        }
                    }
                }
                else {
                    if (goDown && currentNum - currentStr >0){
                        goDown=true;
                        numbers[j]=currentNum-currentStr;
                    }
                    else{
                        goDown=false;
                        if (currentNum+currentStr <255){
                            numbers[j]=currentNum+currentStr;
                        }else {
                            goDown=true;
                        }
                    }
                }
                if (j>=2){
                    j=0;
                }

            }
        }
        return Color.rgb(numbers[0],numbers[1],numbers[2],1);
    }

}
