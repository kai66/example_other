package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kai on 2018/6/11.
 */

public class MutilModel implements Serializable{

    private ArrayList<String> list ;

    public String name ;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
