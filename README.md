# Car Sharing
Car sharing app using a simple Database structure with JDBC, H2 Database for testing purposes is connected by default.
data of rented cars will be saved in `database/carsharing.mv.db` by default, if the database does not exist it will be created in root directory.


# Requirement
- Java version 17+ <a href="https://www.oracle.com/de/java/technologies/downloads/">Java download Link</a>

# Build - Run Project
- Clone repository and navigate into repo's directory
- Run project with `$ ./gradlew run`

# Using Program
After launching the program with `./gradlew run` you will see output in terminal, simply follow the instructions,
The first instruction phase is choosing to log on as manager, customer or create a customer.

- Choosing options by giving the specific option number, and not type in the command itself. more in the examples bellow.
- Manager will be able to create companies, list companies, check their cars and add cars to each company.
- Customer will be able to Rent, return and check the current rented cars.
  No two customers can rent the same car, once car is rented it becomes unavailable until it is returned.