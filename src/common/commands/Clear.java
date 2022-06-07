package common.commands;

import server.*;

import java.io.Serializable;
import java.sql.SQLException;

public class Clear extends AbsCommand implements Serializable{
    private static final long serialVersionUID = -4507489610617393544L;
    @Override
    public Answer work() throws SQLException {
        return storage.clear(auth);
    }
}
