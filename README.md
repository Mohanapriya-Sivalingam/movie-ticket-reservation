
# Movie Ticket Reservation System

A console-based application developed using Java and JDBC for managing movie ticket bookings.

---

## 📌 Features

### 👨‍💼 Admin

- Admin Login
- Create new Admin accounts
- Add movies and show details
- Manage movie listings

### 👤 Customer

- New Customer Sign Up
- Existing Customer Login
- Login Validation
- View available movies & show timings
- Book tickets
- Cancel tickets
- View booking details

---

## 🛠️ Tech Stack

- Java
- JDBC
- MySQL

---

## ⚙️ How It Works

- The system runs through a **console interface**
- Admin logs in using credentials and manages movies & shows
- Customers can:
  - Register (Sign Up)
  - Login using existing credentials
  - View available movies and timings
  - Book or cancel tickets
- Application connects to **MySQL database using JDBC**
- Performs **CRUD operations** (Create, Read, Update, Delete)

---

##  Tech Stack
- Java  
- JDBC  
- MySQL  

---
## 🗃️ Database Tables

All database tables are available in the SQL file:

📂 `movieticket.sql`

### ▶️ How to Import

1. Open MySQL
2. Run the SQL file:
```bash
source movieticket.sql;

---

##  How It Works

- Admin & User interacts via console  
- Admin needs to login using name and password
- Admin can create new Admin to access admin controls
- Admin Adds the movie list and its show timings
- New Customer needs to create their account by using name and password
- Existing customer needs to login using created account details, if it's exist they can view movie details
- Customer Can view the movie list and its show timings
- Application connects to database using JDBC  
- Performs CRUD operations  

---
## 📸 Project Highlights

Console-based user-friendly interaction
Role-based access (Admin & Customer)
Real-time database operations using JDBC
Clean and modular Java code structure

---

## 🚀 Future Enhancements

Online payment integration
Seat selection system
Email/SMS ticket confirmation

---

## 👩‍💻 Author

# Mohanapriya Sivalingam

---

## ▶️ How to Run

1. Compile:
```bash
javac Main.java
