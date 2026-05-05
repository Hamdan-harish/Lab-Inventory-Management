package app;

import service.LabService;
import user.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("   LAB EQUIPMENT MANAGEMENT SYSTEM  ");
        System.out.println("====================================");

        LabService service = new LabService();

        //  Scanner closin Sssystem
        try (Scanner sc = new Scanner(System.in)) {

            // Login
            System.out.print("Enter your User ID: ");
            String loginId = sc.nextLine();
            User currentUser = service.findUser(loginId);

            if (currentUser == null) {
                System.out.println("User not found. Exiting.");
                return;
            }

            System.out.println("Welcome, " + currentUser.getName() + " (" +
                               currentUser.getClass().getSimpleName() + ")");

            while (true) {

                //MEnu
                System.out.println("\n===== MENU =====");
                System.out.println("1. View Labs");
                System.out.println("2. Book Equipment");
                System.out.println("3. Return Equipment");
                System.out.println("4. View Bookings");
                System.out.println("5. Search Equipment");
                System.out.println("6. Show Available Equipment");
                System.out.println("7. Generate Report");

                //Admin Menu
                if (currentUser instanceof Admin) {
                    System.out.println("8. Add User");
                    System.out.println("9. Remove User");
                    System.out.println("10. Add Equipment");
                    System.out.println("11. Remove Equipment");
                    System.out.println("12. Exit");
                } else {
                    System.out.println("8. Exit");
                }
                System.out.println("================");

                int choice;
                try {
                    choice = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // clear invalid input
                    continue;
                }
                sc.nextLine(); 

                try {
                    switch (choice) {
                        case 1:
                            service.displayAllLabs();
                            break;

                        case 2:
                            System.out.println("\n===== BOOK EQUIPMENT =====");
                            service.showAvailableEquipment();

                            System.out.print("Enter Equipment ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();

                            service.bookEquipment(id, currentUser.getUserId());
                            break;

                        case 3:
                            System.out.println("\n===== RETURN EQUIPMENT =====");
                            System.out.print("Enter Equipment ID: ");
                            int rid = sc.nextInt();
                            sc.nextLine();

                            service.returnEquipment(rid);
                            break;

                        case 4:
                            service.showBookings();
                            break;

                        case 5:
                            System.out.println("\n===== SEARCH EQUIPMENT =====");
                            System.out.print("Enter Equipment Name: ");
                            String name = sc.nextLine();

                            service.searchEquipment(name);
                            break;

                        case 6:
                            service.showAvailableEquipment();
                            break;

                        case 7:
                            service.generateReport();
                            break;

                        case 8:
                            if (currentUser instanceof Admin) {
                                // Add User
                                System.out.println("\n===== ADD USER =====");
                                System.out.print("Enter User ID: ");
                                String newUserId = sc.nextLine();

                                System.out.print("Enter Name: ");
                                String newName = sc.nextLine();

                                System.out.print("Enter Role (Student/Professor/Admin): ");
                                String role = sc.nextLine();

                                User newUser;
                                switch (role.toLowerCase()) {
                                    case "student":
                                        System.out.print("Enter Department: ");
                                        String dept = sc.nextLine();
                                        newUser = new Student(newUserId, newName, dept);
                                        break;
                                    case "professor":
                                        System.out.print("Enter Department: ");
                                        dept = sc.nextLine();
                                        newUser = new Professor(newUserId, newName, dept);
                                        break;
                                    case "admin":
                                        newUser = new Admin(newUserId, newName);
                                        break;
                                    default:
                                        System.out.println("Invalid role");
                                        continue;
                                }
                                service.addUser(newUser, currentUser);
                                break;
                            } else {
                                System.out.println("Exiting...");
                                return;
                            }

                        case 9:
                            if (currentUser instanceof Admin) {
                                System.out.println("\n===== REMOVE USER =====");
                                System.out.print("Enter User ID to remove: ");
                                String removeId = sc.nextLine();
                                service.removeUser(removeId, currentUser);
                                break;
                            } else {
                                System.out.println("Invalid choice");
                                break;
                            }

                        case 10:
                            if (currentUser instanceof Admin) {
                                System.out.println("\n===== ADD EQUIPMENT =====");
                                System.out.print("Enter Lab ID: ");
                                int labId = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter Equipment ID: ");
                                int eqId = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter Equipment Name: ");
                                String eqName = sc.nextLine();

                                service.addEquipment(labId, new model.Equipment(eqId, eqName, "AVAILABLE"), currentUser);
                                break;
                            } else {
                                System.out.println("Invalid choice");
                                break;
                            }

                        case 11:
                            if (currentUser instanceof Admin) {
                                System.out.println("\n===== REMOVE EQUIPMENT =====");
                                System.out.print("Enter Lab ID: ");
                                int labId = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter Equipment ID: ");
                                int eqId = sc.nextInt();
                                sc.nextLine();

                                service.removeEquipment(labId, eqId, currentUser);
                                break;
                            } else {
                                System.out.println("Invalid choice");
                                break;
                            }

                        case 12:
                            if (currentUser instanceof Admin) {
                                System.out.println("Exiting...");
                                return;
                            } else {
                                System.out.println("Invalid choice");
                                break;
                            }

                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } // Scanner ccus resourceleak otherwise
    }
}