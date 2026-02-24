/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author ASUS
 */
@RegisterRestClient(baseUri="http://localhost:8080/msahost1/rest/example")

public interface Interface {
    @GET
    @Path("/getdata") //View Data
    @Produces (MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons();
    
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertPerson(Person p);
    
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updatePerson (Person p);
    
    @DELETE
    @Path("/delete/{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deletePerson(@PathParam("email") String email);
}
