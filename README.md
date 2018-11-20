# ReservationSOAPService
An airline reservation web service that receives information regarding the type of seat a customer wishes to reserve
and makes a reservation if such a seat is available.

# Description
A reservation database contains a single table named Seats with columns `location`, `class`, and `taken`.

The reservation web service uses the reservation database and has a single web method `reserve` which searchs the Seats table  to locate a seat matching a user's request. If it finds an appropriate seat, method `reserve` updates the database to make
the reservation and returns true; otherwise, no reservation is made, and the method
returns false.  

## Getting Started
### Prerequisites
- JDK 1.8
- Java EE 7.0
- Java DB - Aparche Derby
- GlassFish Server 4.1

### Installing
- Creating a local respository.
```
git clone git@github.com:lnpeng/ReservationSOAPService.git
cd AddressBookWebapp
```

- Setting up the Database.
  - Open the NetBeans IDE.
  - On the **Services** tab, expand the **Databases** node then right click **Java DB**. Select **Start** server to launch the Java DB server.
  - On the **Services** tab, expand the **Servers** node then right click **GlassFish Server 4.1**. Select **Start** to launch GlassFish.

- Creating the Database.
  - On the **Services** tab, expand the **Databases** node, right click **Java DB** and select **Create Database...**.
  - Specify **Database Name**, **User Name**, **Password**.
  - Click **OK** to create the database.

- Populating the Database with sample data.
  - Expand the jdbc:derby://localhost:1527/reservation node.
  - Right click the **Tables** node and select **Execute Command...** to open a **SQL** editor tab in NetBeans. Add the SQL statements. Right click in the SQL Command editor and select Run File.

## Running the tests
### Test the reservation web service.
- Type the following URL into a web browser
```
http://localhost:8080/Reservation/ReservationService?Tester
```
- ![Reservation](https://github.com/lnpeng/ReservationSOAPService/blob/master/Screen%20Shot%202018-11-19%20at%205.44.23%20PM.png)


## Deployment
Deploy the war file to [GlassFish](https://javaee.github.io/glassfish/) Server.

## Built With
* [Ant](https://ant.apache.org/) - Automating software build process.
