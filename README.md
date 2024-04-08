# Spring Boot Library Management System

This project is a Library Management System built using Java, Spring Boot, and Maven. It provides a set of RESTful APIs to manage books and their checkouts in a library.

## Features

- **Book Management**: The system allows for the management of books in the library. This includes searching for books by title or category, and retrieving books by their IDs.

- **Checkout Management**: The system allows users to checkout books from the library. It keeps track of the books checked out by each user, and allows for returning the books.

## Technologies Used

- **Java**: The backend of the application is written in Java, a popular object-oriented programming language.

- **Spring Boot**: Spring Boot is used to create stand-alone, production-grade Spring based applications with minimal fuss. It simplifies the setup of Spring applications.

- **Maven**: Maven is a build automation tool used primarily for Java projects. It handles the project's build, reporting, and documentation from a central piece of information.

## Code Structure

The project follows a typical Spring Boot project structure. Here are some of the key files and their roles:

- `BookController.java`: This is the REST controller for managing books. It provides endpoints for checking out books, returning books, and getting information about the books checked out by a user.

- `BookRepository.java`: This is the JPA repository for performing database operations related to books. It provides methods for finding books by title, category, and IDs.

- `CheckoutRepository.java`: This is the JPA repository for performing database operations related to checkouts. It provides methods for finding checkouts by user email and book ID.

## API Endpoints

The application exposes several RESTful endpoints for managing books and checkouts:

- `GET /api/books/secure/checkedout/byuser`: Checks if a book is checked out by a user.
- `PUT /api/books/secure/checkout`: Checks out a book for a user.
- `GET /api/books/secure/checkedout/count`: Gets the count of current loans by a user.
- `GET /api/books/secure/currentloans`: Gets the current loans by a user.
- `PUT /api/books/secure/return`: Returns a book.

## Setup

To run this project, you will need to have Java and Maven installed on your machine. You can then clone the repository and run the application using the following command:

```bash
mvn spring-boot:run
```

This will start the application on port 8080. You can then access the APIs using a tool like curl or Postman.

Please note that you will also need to have a running instance of a database that is compatible with Spring Data JPA. The connection details can be configured in the `application.properties` file.

## Testing

The application includes unit tests for the service layer and integration tests for the controllers. You can run the tests using the following command:

```bash
mvn test
```
