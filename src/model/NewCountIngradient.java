package model;

public class NewCountIngradient {
    private  String name;
    private  float quantity;

    public NewCountIngradient(String name,  float count,float quantity) {
        this.name = name;
        this.quantity = quantity;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    private  float count;

}
