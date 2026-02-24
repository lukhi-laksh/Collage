/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Entity.Person;
import Interface.Interface;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@WebServlet("/DBServlet")
public class NewServlet extends HttpServlet {
    @Inject
    @RestClient
    Interface client;
    @Override 
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
        String action = request.getParameter("action");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        
        if ("insert".equals(action)){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            
            Person p = new Person();
            p.setName(name);
            p.setEmail(email);
            
            String result = client.insertPerson(p);
            
            out.println("<h3>" + result + "</h3>" );
            out.println("<a href='index.html'>Back</a>");
        }
        else if ("update".equals(action)){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            
            Person p = new Person();
            p.setName(name);
            p.setEmail(email);
            
            String result = client.updatePerson(p);
            
            out.println("<h3>" + result + "</h3>" );
            out.println("<a href='index.html'>Back</a>");

        }
        else if ("delete".equals(action)) {
            String email = request.getParameter("email");
            
            String result = client.deletePerson(email);
            
            out.println("<h3>" + result + "</h3>" );
            out.println("<a href='index.html'>Back</a>");
        }
        else if ("view".equals(action)){
            List<Person> persons = client.getAllPersons();
            
            out.println("<h2>Persons List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Email</th></tr>");    
            
            for (Person p : persons) {
                out.println("<tr>");
                out.println("<td>"+ p.getName() + "</td>");
                out.println("<td>"+ p.getEmail() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br><a herf='index.html'>Back</a>");
        }
        out.println("</body></html>");
    }
}