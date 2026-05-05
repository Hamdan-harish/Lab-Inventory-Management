
package model;

public class Lab {
    private int labId;
    private String labName;
    private Equipment[] equipments = new Equipment[50];
    private int count = 0;

    public Lab(int labId, String labName) {
        this.labId = labId;
        this.labName = labName;
    }

    public void addEquipment(Equipment e) {
        if (count < equipments.length) {
            equipments[count++] = e;
        } else {
            System.out.println("Lab full");
        }
    }

    public void displayEquipments() {
        System.out.println("\nLab: " + labName);
        for (int i = 0; i < count; i++) {
            equipments[i].display();
        }
    }

    public Equipment findEquipment(int id) {
        for (int i = 0; i < count; i++) {
            if (equipments[i].getId() == id) {
                return equipments[i];
            }
        }
        return null;
    }

    public Equipment[] getEquipments() {
    return equipments;
    }

    public int getCount() {
        return count;
    }
    public String getLabName() {
        return labName;
    }
    public int getLabId() {
        return labId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}