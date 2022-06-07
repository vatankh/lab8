package database;

public class Logic {
    final String ADD_USER_REQUEST = "INSERT INTO users (username, password) VALUES (?, ?)";
    final String GET_USERS="select  * from users;";
    final String GET_ALL_VEHICLES="select * from vehicles";
    final  String ADD_VEHICLE="insert into vehicles(name, coordinate_x, coordinate_y, engine_power, vehicle_type, fuel_type, owner)" +
            "values (?,?,?,?,?,?,?);";
    final String GET_LAST_VEHICLE="SELECT * FROM vehicles WHERE id = (SELECT MAX (id) FROM vehicles where owner=?);";
    final String GET_VEHICLE_BY_ID ="select * from vehicles where id=?;";
    final String UPDATE_VEHICLE_BY_ID="update vehicles set  name=?, coordinate_x=?, coordinate_y=?, engine_power=? ,vehicle_type=?,fuel_type=?  where id=? and owner=?;";
    final String CLEAR="DELETE from vehicles where owner=?";
    final String GET_USER_VEHICLES="select * from vehicles where id=?;";
    final String REMOVE_VEHICLE_BY_ID ="DELETE FROM vehicles WHERE id = ?::bigint;";
    final String SELECT_FIRST_VEHICLE="SELECT * FROM vehicles WHERE id = (SELECT min (id) FROM vehicles);";
    final String DELETE_FIRST_VEHICLE="delete FROM vehicles WHERE id = (SELECT min (id) FROM vehicles);";
    final String REMOVE_VEHICLES_GREATER_THAN_ENGINE_POWER ="delete  from  vehicles where owner=? and engine_power >?;";

    public Logic() {
    }


}
