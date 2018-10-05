# IngenicoTransferService
REST API to create bank account and initiate transfer 

REST API to create bank account and initiate transfer .

This is a SpringBoot project built with Maven , inbuilt H2 database and embedded Tomcat server.

RUNNING THE APPLICATION:

Download and install JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/index.html). If you already have installed JDK 8, you can skip this step.

Download and install Maven 8 (http://maven.apache.org/download.html#Installation). If you have already installed Maven , you can skip this step.

Go the root directory of project (The one which contains the pom.xml file)

Import project to IDE as Existing Maven Project.

Run command mvn clean and Maven install

Start up your Spring Boot app by running TransferServiceApplication.java as Java Application . During startup, Spring Boot will initialize an in memory database and create our Account table for us.

Start your browser and go to the location: http://localhost:8080

DATABASE CONSOLE : The database can be accessed through the browser at http://localhost:8080/h2. No password necessary.

RUNNING TESTS:
 run unit tests by using this command: mvn test
