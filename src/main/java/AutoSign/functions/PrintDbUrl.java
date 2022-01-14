package AutoSign.functions;

import AutoSign.HomeView;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintDbUrl {
    public static String printdburl(){

        // RETRIEVE INFORMATION FROM DATABASE
        String url = "jdbc:postgresql://" + "ec2-54-170-212-187.eu-west-1.compute.amazonaws.com"+ ':' + "5432/" + "d9p4pv538aoe9l" + "?sslmode=require";
        //String url ="postgresql://nmcnudamsuzpvx:1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433@ec2-54-170-212-187.eu-west-1.compute.amazonaws.com:5432/d9p4pv538aoe9l";
        //String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nmcnudamsuzpvx";
        //String user = "postgres";
        String password = "1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433";
        //String password = "Arius135";
        String str = "<div class=\"ex8\">" + "<br>" + "<li class=\"ex5\">" + "URL" + "</li>" + "<li class=\"ex6\">" + "TITLE" + "</li>" + "<li class=\"ex7\">" + "DURATION" + "</li>"  + "</br>" + "</div>";;

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement("SELECT * FROM videos_history");
             ResultSet rs = pst.executeQuery()) {

            int i=0;
            while (rs.next()) {

                //System.out.println(rs.getString(1));
                str += "<div class=\"ex2\">" + "<br>" + "<li class=\"ex3\">" + rs.getString(1) + "</li>" + "<li class=\"ex4\">" + rs.getString(2) + "</li>" + "<li class=\"ex1\">" + rs.getString(3) + "</li>"  + "</br>" + "</div>";
                i++;
            }

            //String.innerHTML = str;

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HomeView.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        System.out.println(str);

        return str;
    }

}
