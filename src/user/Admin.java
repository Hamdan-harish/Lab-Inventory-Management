package user;

public class Admin extends User {

    public Admin(String userId, String name) {
        super(userId, name);
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Role: Admin");
    }
}