# IngenicoTransferService
REST API to create a Bank Account and initiate amount transfer between those accounts

# Solution Summary:
1. This is a Java SpringBoot assignment built with Maven, inbuilt H2 database and embedded Tomcat server.
2. The solution is designed to handle Multiple concurrent amount transfer request from one account to another by using JPA provided optimistic lock mechanism. 
3. It also uses SpringBoot's Transactional to roll back in-case of any failure while executing the transaction.

# Running the application:

1. Download and install JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/index.html). If you already have installed JDK 8, you can skip this step.
2. Download and install Maven 8 (http://maven.apache.org/download.html#Installation). If you have already installed Maven , you can skip this step.
3. Go the root directory of project (The one which contains the pom.xml file)
4. Import project to IDE as Existing Maven Project.
5. Run command mvn clean and Maven install
6. Start up your Spring Boot app by running TransferServiceApplication.java as Java Application . During startup, Spring Boot will initialize an in memory database and create our Account table for us.
7. Start your REST Client to perform create and transfer request

# Miscellaneous:
1. DATABASE CONSOLE : The database can be accessed through the browser at http://localhost:8080/h2. No password necessary.
2. RUNNING TESTS: run unit tests by using this command: mvn test
