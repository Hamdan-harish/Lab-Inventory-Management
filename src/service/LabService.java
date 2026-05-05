
package service;

import model.*;
import user.*;

public class LabService {

    private Lab[] labs = new Lab[2];
    private Booking[] bookings = new Booking[100];
    private int bookingCount = 0;

    private User[] users = new User[50];
    private int userCount = 0;


    public LabService() {
        labs[0] = new Lab(1, "Physics Lab");
        labs[1] = new Lab(2, "Computer Lab");

        labs[0].addEquipment(new Equipment(101, "Microscope", "AVAILABLE"));
        labs[0].addEquipment(new Equipment(102, "Oscilloscope", "AVAILABLE"));
        labs[0].addEquipment(new Equipment(103, "IC Trainer Kit", "AVAILABLE"));

        labs[1].addEquipment(new Equipment(201, "PC", "AVAILABLE"));
        labs[1].addEquipment(new Equipment(202, "Printer", "AVAILABLE"));

        users[userCount++] = new Student("S001", "Cuddy", "Physics");
        users[userCount++] = new Professor("P001", "Wilson", "Physics");  
        users[userCount++] = new Admin("A001",  "AdminForeman");      
    }

    public void addUser(User user, User operator) {
        if (!(operator instanceof Admin)) {
            throw new EquipmentException("Only Admin can add users");
        }
        users[userCount++] = user;
        System.out.println("User added: " + user.getName());
    }

    public void removeUser(String userId, User operator) {
        if (!(operator instanceof Admin)) {
            throw new EquipmentException("Only Admin can remove users");
        }

        for (int i = 0; i < userCount; i++) {
            if (users[i].getUserId().equals(userId)) {
                System.out.println("Removed user: " + users[i].getName());
                users[i] = users[userCount - 1]; // overwrite with last user
                users[userCount - 1] = null;
                userCount--;
                return;
            }
        }
        System.out.println("User not found");
    }

    public User findUser(String userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUserId().equals(userId)) {
                return users[i];
            }
        }
        return null;
    }


    public void displayAllLabs() {
        for (int i = 0; i < labs.length; i++) {
            labs[i].displayEquipments();
        }
    }

    public void bookEquipment(int id, String userId) {
        User user = findUser(userId);
        if (user == null) {
            System.out.println("User not found");
            return;
        }

        // Check borrow limits for  Student or Professor (ADMINS CANT BORROQW)
        if (user instanceof Student student && !student.canBorrow()) {
            System.out.println("Student borrow limit reached!");
            return;
        }
        if (user instanceof Professor professor && !professor.canBorrow()) {
            System.out.println("Professor borrow limit reached!");
            return;
        }

        for (Lab lab : labs) {
            Equipment e = lab.findEquipment(id);
            if (e != null) {
                if (!e.getStatus().equals("AVAILABLE")) {
                    System.out.println("Equipment already booked");
                    return;
                }
                e.setStatus("BOOKED");
                bookings[bookingCount++] = new Booking(id, user);

                if (user instanceof Student s) s.incrementBorrow();
                if (user instanceof Professor p) p.incrementBorrow();

                System.out.println(user.getClass().getSimpleName() + " booked successfully!");
                return;
            }
        }

        System.out.println("Equipment not found");
    }

    public void returnEquipment(int id) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getEquipmentId() == id && bookings[i].getStatus().equals("ACTIVE")) {
                bookings[i].returnEquipment();

                for (Lab lab : labs) {
                    Equipment e = lab.findEquipment(id);
                    if (e != null) e.setStatus("AVAILABLE");
                }
                System.out.println("Returned successfully!");
                return;
            }
        }
        System.out.println("Booking not found");
    }

    public void showBookings() {
        for (int i = 0; i < bookingCount; i++) {
            bookings[i].display();
        }
    }

    public void searchEquipment(String name) {
        boolean found = false;

        for (Lab lab : labs) {
            Equipment[] eqs = lab.getEquipments();

            for (int i = 0; i < lab.getCount(); i++) {
                if (eqs[i].getName().equalsIgnoreCase(name)) {
                    eqs[i].display();
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Equipment not found");
        }
    }

    public void showAvailableEquipment() {
        boolean found = false;
        for (Lab lab : labs) {
            System.out.println("\nLab: " + lab.getLabName());
            Equipment[] eqs = lab.getEquipments();
            for (int i = 0; i < lab.getCount(); i++) {
                if (eqs[i].getStatus().equals("AVAILABLE")) {
                    eqs[i].display();
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No available equipment");
    }
    public void generateReport() {
        int available = 0, booked = 0;
        for (Lab lab : labs) {
            Equipment[] eqs = lab.getEquipments();
            for (int i = 0; i < lab.getCount(); i++) {
                if (eqs[i].getStatus().equals("AVAILABLE")) available++;
                else if (eqs[i].getStatus().equals("BOOKED")) booked++;
            }
            System.out.println("\n================ " + lab.getLabName() + " REPORT ================");
            System.out.println("Available Equipment: " + available);
            System.out.println("Booked Equipment: " + booked);
            System.out.println("=====================================================");
        }
    }

    public void addEquipment(int labId, Equipment eq, User operator) {
        if (!(operator instanceof Admin)) {
            throw new EquipmentException("Only Admin can add equipment");
        }
        Lab lab = findLab(labId);
        if (lab != null) {
            lab.addEquipment(eq);
            System.out.println("Equipment added: " + eq.getName() + " to " + lab.getLabName());
        } else {
            System.out.println("Lab not found");
        }
    }

    public void removeEquipment(int labId, int eqId, User operator) {
        if (!(operator instanceof Admin)) {
            throw new EquipmentException("Only Admin can remove equipment");
        }
        Lab lab = findLab(labId);
        if (lab != null) {
            boolean removed = false;
            Equipment[] eqs = lab.getEquipments();
            for (int i = 0; i < lab.getCount(); i++) {
                if (eqs[i].getId() == eqId) {
                    // Shift remaining equipments left
                    for (int j = i; j < lab.getCount() - 1; j++) {
                        eqs[j] = eqs[j + 1];
                    }
                    eqs[lab.getCount() - 1] = null;
                    lab.setCount(lab.getCount() - 1);
                    System.out.println("Equipment removed: ID " + eqId);
                    removed = true;
                    break;
                }
            }
            if (!removed) System.out.println("Equipment not found in lab");
        } else {
            System.out.println("Lab not found");
        }
    }

    private Lab findLab(int labId) {
        for (Lab lab : labs) {
            if (lab.getLabId() == labId) return lab;
        }
        return null;
    }
}