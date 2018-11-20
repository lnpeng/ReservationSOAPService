package com.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.sql.DataSource;

// define the data source
@DataSourceDefinition(
    name = "java:global/jdbc/reservation",  
    className = "org.apache.derby.jdbc.ClientDataSource",
    url = "jdbc:derby://localhost:1527/reservation",
    databaseName = "reservation",
    user = "APP",
    password = "APP")

@WebService()
public class Reservation
{
    // allow the server to inject the DataSource
    @Resource(lookup="java:global/jdbc/reservation")
    DataSource dataSource;

    // a WebMethod that can reserve a seat
    @WebMethod(operationName = "reserve")
    public boolean reserve(@WebParam(name = "seatType") String seatType,
        @WebParam(name = "classType") String classType)
    {
        Connection connection = null;
        PreparedStatement lookupSeat = null;
        PreparedStatement reserveSeat = null;
        
        try
        {
            // check whether dataSource was injected by the server
            if (dataSource == null)
            {
                throw new SQLException("Unable to obtain DataSource");
            }
            connection = dataSource.getConnection();
            
            // check whether connection was successful
            if (connection == null)
            {
                throw new SQLException("Unable to connect to DataSource");
            }
            
            lookupSeat = connection.prepareStatement(
                "SELECT \"number\" FROM \"seats\" WHERE (\"taken\" = 0) " +
                "AND (\"location\" = ?) AND (\"class\" = ?)");
            lookupSeat.setString(1, seatType);
            lookupSeat.setString(2, classType);
            ResultSet resultSet = lookupSeat.executeQuery();

            // if requested seat is available, reserve it
            if (resultSet.next())
            {
                int seat = resultSet.getInt(1);
                reserveSeat = connection.prepareStatement(
                    "UPDATE \"seats\" SET \"taken\"=1 WHERE \"number\"=?");
                reserveSeat.setInt(1, seat);
                reserveSeat.executeUpdate();
                return true;
             }

              return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                lookupSeat.close();
                reserveSeat.close();
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }
}
