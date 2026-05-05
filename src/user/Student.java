package user;

public class Student extends User {
    private String department;
    private int borrowCount;
    private final int MAX_BORROW = 3;

    public Student(String userId, String name, String department) {
        super(userId, name);
        this.department = department;
        this.borrowCount = 0;
    }

    public boolean canBorrow() {
        return borrowCount < MAX_BORROW;
    }

    public void incrementBorrow() {
        borrowCount++;
    }

    public void decrementBorrow() {
        if (borrowCount > 0) borrowCount--;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Dept: " + department + " | Borrowed: " + borrowCount);
    }
}