
package model;

public class Equipment {
    private int id;
    private String name;
    private String status; // AVAILABLE, BOOKED, MAINTENANCE

    public Equipment(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Status: " + status);
    }
}