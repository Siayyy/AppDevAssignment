# Android Library Management System 😄

## Overview 📘
This Android application is designed as a library management system to facilitate online book borrowing. It supports functionalities for both regular users and administrators, enabling operations such as user registration, book management, and borrowing activities through a user-friendly mobile interface.

## Features 🚀
### For All Users
- **User Registration:** New users can register.
- **Login:** Users can log into the system.
- **Password Management:** Users can change their passwords after logging in.

### For Registered Users
- **Book Browsing:** Users can browse books available for borrowing. 📚
- **Borrowing Books:** Users can request to borrow books.
- **Borrowed Books History:** Users can view a history of the books they have borrowed.

### For Administrators
- **Book Management:** Admins can add or remove books from the library.
- **Borrowing Requests Handling:** Admins can manage borrowing requests from users. 👮
- **Password Management:** Admins can change their passwords.

## Technologies Used 💻
- **Programming Language:** Java
- **Architecture:** MVC (Model-View-Controller) pattern
- **Database:** SQLite for local data storage and quick querying
- **Networking:** HTTP protocol for remote data access and synchronization
- **Development Tools:** Android Studio, Gradle for project building and dependency management

## Setup Instructions 🛠️
1. Download and install Android Studio from the official site.
2. Clone or download this project into your local machine.
3. Open the project in Android Studio.
4. Build the project by navigating to `Build > Make Project`.
5. Run the project on a physical device or emulator.

## Database Configuration 📊
The application uses SQLite to manage local data storage. Here’s an outline of the database schema and initialization:

- **Users Table:** Stores user information including email, password, name, and major.
- **Books Table:** Contains details about the books such as title, author, synopsis, and cover image path.

### Sample SQL Commands
```sql
CREATE TABLE IF NOT EXISTS users (
    email TEXT PRIMARY KEY,
    password TEXT,
    name TEXT,
    major TEXT
);

CREATE TABLE IF NOT EXISTS books (
    bookId INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    author TEXT,
    synopsis TEXT,
    coverImagePath TEXT
);
