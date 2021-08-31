package model;

public class OrderAction {
    private  int id;
    private String dish;
    private int quantity;
    private  String date;
    private  String time;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public String getNameWaiter() {
        return nameWaiter;
    }

    public void setNameWaiter(String nameWaiter) {
        this.nameWaiter = nameWaiter;
    }

    private  int id_table;
    private String nameWaiter;

    public OrderAction(int id, String dish, int quantity, int id_table, String nameWaiter, String date, String time) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.id_table = id_table;
        this.nameWaiter = nameWaiter;
        this.date = date;
        this.time = time;
    }
}
