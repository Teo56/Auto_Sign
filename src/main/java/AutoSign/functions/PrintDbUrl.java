package AutoSign.functions;

import AutoSign.HomeView;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintDbUrl {
    
    // This method allows to display the whole database table (containing previous submission-video information) in the history html page
    // Given the difficulty in accessing the database directly from the html file using spring, we decided to write a java method to access the database, retrieve the necessary information and create a string of html language
    // Such a string is then returned into the html to be displayed with the desired output
    
    public static String printdburl(){

        // Credentials to access database
        String url = "jdbc:postgresql://" + "ec2-54-170-212-187.eu-west-1.compute.amazonaws.com"+ ':' + "5432/" + "d9p4pv538aoe9l" + "?sslmode=require";
        String user = "nmcnudamsuzpvx";
        String password = "1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433";
        
        // Initialise the string containing html language to create database
        String str = "<div class=\"ex8\">" + "<br>" + "<li class=\"ex5\">" + "URL" + "</li>" + "<li class=\"ex6\">" + "TITLE" + "</li>" + "<li class=\"ex7\">" + "DURATION" + "</li>"  + "</br>" + "</div>";;

        
        // RETRIEVE INFORMATION FROM DATABASE
        // Accessing the db with credentials
        try (Connection con = DriverManager.getConnection(url, user, password);
             // Create statement with a SELECT * FROM queryto retrieve information from the database table
             PreparedStatement pst = con.prepareStatement("SELECT * FROM videos_history");
             // execute query
             ResultSet rs = pst.executeQuery()) {

            int i=0;
            // Read through all rows of the database until the last
            while (rs.next()) {

                // Update the html string with the new database entry (new-rown items) at each iteration
                str += "<div class=\"ex2\">" + "<br>" + "<li class=\"ex3\">" + rs.getString(1) + "</li>" + "<li class=\"ex4\">" + rs.getString(2) + "</li>" + "<li class=\"ex1\">" + rs.getString(3) + "</li>"  + "</br>" + "</div>";
                i++;
            }


            // Catch exception
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeView.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // Print string to check
        System.out.println(str);

        // Return string (method will be called in the histoty.html file)
        return str;
    }

}
