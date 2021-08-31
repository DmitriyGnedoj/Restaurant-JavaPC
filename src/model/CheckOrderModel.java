package model;

public class CheckOrderModel {
    private int id;
    private String date;
    private  String time;
    private  float sum;

    public CheckOrderModel(int id, String date, String time, float sum, float sumall) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.sum = sum;
        this.sumall = sumall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public float getSumall() {
        return sumall;
    }

    public void setSumall(float sumall) {
        this.sumall = sumall;
    }

    private  float sumall;
}
