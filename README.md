# 🧪 Lab Equipment Management System

## 📌 Project Overview
This is a **console-based Java application** designed to manage lab equipment in an academic environment.  
The system allows users to **view, book, return, and search equipment**, while also supporting **role-based access control**.

---

## 👥 User Roles
The system supports three types of users:

- **Student**
  - Can book equipment (limited to 3 items)
  - Can return equipment

- **Professor**
  - Can book equipment (limited to 5 items)
  - Can return equipment

- **Admin**
  - Can add/remove users
  - Can add/remove equipment
  - Can view reports
  - Cannot borrow equipment

---

## ⚙️ Features

### 🔹 Core Features
- View all labs and equipment
- Book equipment (if available)
- Return equipment
- Search equipment by name
- View all bookings
- Show available equipment
- Generate lab-wise reports

### 🔹 Admin Features
- Add new users
- Remove existing users
- Add new equipment to labs
- Remove equipment from labs

---

## 🧠 Java Concepts Used

- Classes & Objects
- Encapsulation
- Inheritance
- Polymorphism (Runtime)
- Abstraction (via base class)
- Arrays (for data storage)
- Exception Handling (custom exception)
- Packages (modular structure)

---

## 📁 Project Structure
src/
├── app/
│ └── Main.java
├── model/
│ ├── Lab.java
│ ├── Equipment.java
│ └── Booking.java
├── service/
│ ├── LabService.java
│ └── EquipmentException.java
└── user/
├── User.java
├── Student.java
├── Professor.java
└── Admin.java


---

## ▶️ How to Run

### Compile:
javac -d out src/app/Main.java src/model/*.java src/service/*.java src/user/*.java

## Run
java -cp out app.Main



🔐 Sample Login Users
User ID	  Role
S001	    Student
P001	    Professor
A001	    Admin
