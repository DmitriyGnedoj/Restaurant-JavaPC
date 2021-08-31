package model;

public class Ingradients {
    private int id;
    private  String name;
    private float count;
    private String unit;
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Ingradients(int id, String name, float count, float price, String unit) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.unit = unit;
    }
}
