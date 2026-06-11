
# Freelance Contract Tracker JDBC

A console-based Java JDBC application that simulates the core workflow of a freelance project marketplace. The system allows clients to post projects, freelancers to place bids, and clients to award projects by accepting one bid. It also tracks the contract lifecycle until project completion.

This project is built using Java, JDBC, Oracle SQL, DAO-Service architecture, custom exceptions, and transaction handling.

---

## Project Overview

The Freelance Contract Tracker JDBC project is designed to manage a simplified freelance platform where:

* Clients can register and post freelance projects.
* Freelancers can register and place bids on open projects.
* A client can accept one bid for a project.
* Once a bid is accepted, other bids for the same project are rejected.
* The awarded project can later be marked as completed.
* Users cannot be removed if they have active projects or active bids.

The main focus of this project is to demonstrate JDBC database connectivity, layered architecture, SQL operations, validation, and transaction management.

---

## Features

### User Management

* Register new users as `CLIENT` or `FREELANCER`
* View user details
* View all registered users
* Remove users only after checking active engagements

### Project Management

* Clients can post new projects
* View all projects
* View only open projects
* Track project status:

  * `OPEN`
  * `AWARDED`
  * `COMPLETED`
  * `CANCELLED`

### Bidding System

* Freelancers can place bids on open projects
* Duplicate active bids are prevented
* Bids are linked to projects and freelancers
* Bid statuses include:

  * `PENDING`
  * `ACCEPTED`
  * `REJECTED`
  * `WITHDRAWN`

### Contract Tracking

* Client can award a project by accepting one bid
* Accepted bid becomes `ACCEPTED`
* Other pending bids for the same project become `REJECTED`
* Project status changes from `OPEN` to `AWARDED`
* Awarded project can be marked as `COMPLETED`

### Transaction Handling

The project uses JDBC transactions for important multi-step operations such as:

* Placing a bid
* Awarding a project
* Marking a project as completed

If any step fails, the transaction is rolled back to maintain database consistency.

---

## Technologies Used

* Java
* JDBC
* Oracle Database
* SQL Plus
* Eclipse IDE
* DAO-Service Layer Architecture

---

## Project Architecture

The project follows a layered architecture.

```text
FreelanceMain
     |
     v
FreelanceService
     |
     v
DAO Classes
     |
     v
DBUtil
     |
     v
Oracle Database
```

---

## Package Structure

```text
com.freelance.app
    FreelanceMain.java

com.freelance.bean
    User.java
    Project.java
    Bid.java

com.freelance.dao
    UserDAO.java
    ProjectDAO.java
    BidDAO.java

com.freelance.service
    FreelanceService.java

com.freelance.util
    DBUtil.java
    ValidationException.java
    ProjectAwardingException.java
    ActiveEngagementsExistException.java
```

---

## Database Tables

The project uses three main database tables.

### USERS_TBL

Stores both clients and freelancers.

| Column                   | Description                             |
| ------------------------ | --------------------------------------- |
| USER_ID                  | Primary key for each user               |
| FULL_NAME                | Name of the user                        |
| EMAIL                    | Email address                           |
| MOBILE                   | Contact number                          |
| ROLE                     | CLIENT or FREELANCER                    |
| PRIMARY_SKILL_OR_COMPANY | Freelancer skill or client company name |
| STATUS                   | ACTIVE or INACTIVE                      |

---

### PROJECT_TBL

Stores projects posted by clients.

| Column              | Description                            |
| ------------------- | -------------------------------------- |
| PROJECT_ID          | Primary key for each project           |
| CLIENT_USER_ID      | Client who posted the project          |
| PROJECT_TITLE       | Title of the project                   |
| PROJECT_DESCRIPTION | Description of the work                |
| BUDGET_MIN          | Minimum budget                         |
| BUDGET_MAX          | Maximum budget                         |
| POSTED_DATE         | Date of posting                        |
| STATUS              | OPEN, AWARDED, COMPLETED, or CANCELLED |
| AWARDED_BID_ID      | Accepted bid ID                        |

---

### BID_TBL

Stores freelancer bids.

| Column             | Description                               |
| ------------------ | ----------------------------------------- |
| BID_ID             | Primary key for each bid                  |
| PROJECT_ID         | Project linked to the bid                 |
| FREELANCER_USER_ID | Freelancer who placed the bid             |
| BID_AMOUNT         | Amount quoted by the freelancer           |
| DELIVERY_DAYS      | Estimated delivery time                   |
| COVER_LETTER       | Proposal message                          |
| BID_DATE           | Date of bid                               |
| BID_STATUS         | PENDING, ACCEPTED, REJECTED, or WITHDRAWN |

---

## Main Operations

### 1. Register User

A user can register as either a client or freelancer. The service layer validates mandatory fields such as user ID, name, email, and role.

### 2. Post Project

Only an active client can post a new project. The system validates the project title, description, and budget details.

### 3. Place Bid

Only an active freelancer can place a bid on an open project. The system prevents duplicate active bids by the same freelancer for the same project.

### 4. Award Project

The client awards the project by selecting one pending bid. This operation is transactional.

Steps involved:

```text
Accept selected bid
Reject other pending bids
Update project status to AWARDED
Store awarded bid ID
Commit transaction
```

If any step fails, the transaction is rolled back.

### 5. Mark Project Completed

An awarded project can be marked as completed. This represents the end of the contract lifecycle.

### 6. Remove User

A user can be removed only if there are no active projects or active bids linked to that user.

---

## Custom Exceptions

The project uses custom exceptions for cleaner error handling.

| Exception                       | Purpose                                       |
| ------------------------------- | --------------------------------------------- |
| ValidationException             | Handles invalid input and validation failures |
| ProjectAwardingException        | Handles errors during project awarding        |
| ActiveEngagementsExistException | Prevents deletion of users with active work   |

---

## Sample Output

```text
--- Freelance Project Posting & Bidding Console ---
CLIENT REGISTERED
FREELANCER REGISTERED
PROJECT POSTED
```

---

## How to Run the Project

### 1. Create Oracle User

```sql
CREATE USER freelance_user IDENTIFIED BY freelance_pwd;
GRANT CONNECT, RESOURCE TO freelance_user;
ALTER USER freelance_user QUOTA UNLIMITED ON USERS;
```

### 2. Connect to the User

```sql
CONN freelance_user/freelance_pwd;
```

### 3. Create Tables

Create the required tables:

```text
USERS_TBL
PROJECT_TBL
BID_TBL
```

### 4. Insert Sample Records

Insert sample users, projects, and bids into the database.

### 5. Configure Database Connection

Update `DBUtil.java` with your Oracle database connection details.

Example:

```java
Connection con = DriverManager.getConnection(
    "jdbc:oracle:thin:@localhost:1521:xe",
    "freelance_user",
    "freelance_pwd"
);
```

For newer Oracle XE versions, the URL may be:

```java
jdbc:oracle:thin:@localhost:1521/XEPDB1
```

### 6. Run the Application

Run the main class:

```text
com.freelance.app.FreelanceMain
```

---

## Key Learning Outcomes

Through this project, the following concepts are implemented:

* JDBC connectivity
* Oracle SQL integration
* DAO design pattern
* Service layer validation
* Java bean classes
* Custom exception handling
* PreparedStatement usage
* ResultSet processing
* Transaction management using commit and rollback
* Foreign key-based relational database design

---

## Future Enhancements

* Add a complete menu-driven console interface
* Add freelancer bid withdrawal option
* Add project cancellation feature
* Add login authentication
* Add search and filter options
* Convert the project into a web application using JSP, Servlets, or Spring Boot
* Add reporting features for projects, bids, and completed contracts

---

## Author

Developed by Vishal S R

---

## Repository Purpose

This repository is created as a JDBC-based academic project to demonstrate database-driven application development using Java and Oracle SQL.
