package com.mycompany.msahost1.service;

import Entity.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/example")
public class ExampleService {

    @GET
    @Path("/getdata") //View Data
    @Produces (MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons(){
        List<Person> list = new ArrayList<>();
        
        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/karan_db?useSSL=false",
                    "root", 
                    "admin"
            )) {
                PreparedStatement st = 
                        con.prepareStatement("SELECT * FROM tbl_1");
                
                ResultSet rs = st.executeQuery();
                
                while (rs.next()) {
                    Person p = new Person(
                            rs.getString("email"),
                            rs.getString("name")
                    );
                    list.add(p);
                }
            }
            
        } catch (ClassNotFoundException | SQLException e) {
        }
        return list;
    }
    
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    
    public String insertPerson(Person p) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/karan_db?useSSL=false","root", "admin");
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO tbl_1 (email, name) VALUES (?, ?)");
            
            ps.setString(1, p.getEmail());
            ps.setString(2, p.getName());
            
            ps.executeUpdate();
            con.close();
            
            return "Person Inserted Scuccessfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Inserting Person";
        }
    }
    
    //Update:
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
        
    public String updatePerson (Person p) throws ClassNotFoundException{
    
        String sql = "UPDATE tbl_1 SET name=? WHERE LOWER (email) = LOWER (?) ";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try(Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/karan_db?useSSL=false", 
                    "root", 
                    "admin");
                    
                PreparedStatement st = con.prepareStatement(sql)) {
            
                st.setString(1, p.getName().trim());
                st.setString(2, p.getEmail().trim());
                
                int row = st.executeUpdate();
                
                if (row > 0) {
                    return "Person Updated Successfully";
                } else {
                    return "No Person found with this email";
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Updating";
        }
    }
            
    
//    Delete:
    @DELETE
    @Path("/delete/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deletePerson(@PathParam("email") String email){
        
        String sql = "DELETE FROM tbl_1 WHERE email = ?";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try(Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/karan_db?useSSL=false", "root", "admin");
                   PreparedStatement st = con.prepareStatement(sql);
                ) {
                
                st.setString(1, email);
                
                int row = st.executeUpdate();
                
                if (row > 0){
                    return "Person Deleted Successfully";
                } else {
                    return "No Person found with this email";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while deleting";
        }
    }
    
}
    
