package common.collection;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Максимальное значение поля: 911, Поле не может быть null
    private Integer y; //Поле не может быть null

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Coordinates(Long x, Integer y){
        this.x=x;
        this.y=y;
    }
}
