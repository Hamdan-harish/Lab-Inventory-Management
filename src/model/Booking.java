
package model;
import user.User;

public class Booking {
    private int equipmentId;
    private User user;
    private String status; 

    public Booking(int equipmentId, User user) {
        this.equipmentId = equipmentId;
        this.user = user;
        this.status = "ACTIVE";
    }

    public int getEquipmentId() { return equipmentId; }

    public User getUser() { return user; }

    public String getStatus() { return status; }

    public void returnEquipment() {
        status = "RETURNED";
        if (user instanceof user.Student student) {
            student.decrementBorrow();
        } 
        else if (user instanceof user.Professor professor) {
            professor.decrementBorrow();
        }
    }



    public void display() {
        System.out.print("ID: " + equipmentId + " | ");
        System.out.print("User: " + user.getName() + " | ");
        System.out.print("Status: " + status + " | ");
        System.out.print("Role: " + user.getClass().getSimpleName());
        System.out.println();
    }
}