
# Freelance Contract Tracker JDBC

# Freelance Contract Tracker JDBC

A console-based Java JDBC application for managing freelance project posting, bidding, and contract tracking.

This project simulates a basic freelance marketplace where clients can post projects, freelancers can place bids, and clients can award projects by accepting one bid.

---

## Features

* Register users as `CLIENT` or `FREELANCER`
* Clients can post new projects
* Freelancers can place bids on open projects
* Prevents duplicate active bids
* Clients can accept one bid and award the project
* Other bids are automatically rejected after awarding
* Awarded projects can be marked as completed
* Prevents deletion of users with active projects or bids
* Uses JDBC transactions with commit and rollback

---

## Tech Stack

* Java
* JDBC
* Oracle Database
* SQL Plus
* Eclipse IDE

---

## Architecture

```text
FreelanceMain
    ↓
FreelanceService
    ↓
DAO Classes
    ↓
DBUtil
    ↓
Oracle Database
```

---

## Package Structure

```text
com.freelance.app
com.freelance.bean
com.freelance.dao
com.freelance.service
com.freelance.util
```

---

## Database

The project uses three main tables:

* `USERS_TBL`
* `PROJECT_TBL`
* `BID_TBL`

The complete table creation and sample insert queries are available in:

```text
database_setup.sql
```

---

## Important Concepts Used

* JDBC connection handling
* DAO pattern
* Service layer validation
* PreparedStatement
* ResultSet
* Custom exceptions
* Primary key and foreign key relationships
* Transaction handling using `commit()` and `rollback()`

---

## Sample Output

```text
--- Freelance Project Posting & Bidding Console ---
CLIENT REGISTERED
FREELANCER REGISTERED
PROJECT POSTED
```

---

## How to Run

1. Create Oracle user:

```sql
CREATE USER freelance_user IDENTIFIED BY freelance_pwd;
GRANT CONNECT,RESOURCE TO freelance_user;
ALTER USER freelance_user QUOTA UNLIMITED ON USERS;
```

2. Connect to the user:

```sql
CONN freelance_user/freelance_pwd;
```

3. Run the SQL file:

```text
Run `database_setup.sql` only after connecting to the Oracle user `freelance_user`.
```

4. Add Oracle JDBC driver to the project build path.

5. Update database connection details in `DBUtil.java`.

6. Run:

```text
com.freelance.app.FreelanceMain
```

---

## Author

Developed by Vishal S R

