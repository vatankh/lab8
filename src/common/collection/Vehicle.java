package common.collection;

import java.io.Serializable;
import java.util.Date;

public class Vehicle implements Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double enginePower; //Поле не может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null
    private FuelType fuelType; //Поле может быть null
    private String owner;

    public Vehicle(long id,String name, Coordinates coordinates, Date date, Double enginePower, VehicleType type, FuelType fuelType, String owner){
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=date;
        this.enginePower=enginePower;
        this.type=type;
        this.fuelType=fuelType;
        this.owner=owner;

    }

    public String getOwner() {
        return owner;
    }


    public Vehicle(String name, Coordinates coordinates, Double enginePower, VehicleType type, FuelType fuelType){
        this.name=name;
        this.coordinates=coordinates;
        this.enginePower=enginePower;
        this.type=type;
        this.fuelType=fuelType;
    }

    @Override
    public String toString() {
        System.out.println(creationDate);
        String date=creationDate.toString();
        return "id="+id +","
                +"name="+name+","
                +"coordinates=(x="+coordinates.getX() +",y="+coordinates.getY()+"),"
                +"creationdate="+date+","
                +"enginePower="+enginePower+","
                +"type="+type+","
                +"fuelType="+fuelType+",";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}
