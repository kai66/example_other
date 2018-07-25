package entity;

import java.io.Serializable;

/**
 * Created by kai on 2018/6/11.
 */

public class DetailInfo implements Serializable{

  private  int type;
  private  String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
