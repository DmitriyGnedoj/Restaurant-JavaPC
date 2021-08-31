package model;

public class TypeDish {
    private  int  id;
    private  String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TypeDish(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
