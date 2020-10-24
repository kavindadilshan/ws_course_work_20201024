package lk.ijse.webservice.resource_access.api;

import com.google.gson.Gson;
import lk.ijse.webservice.resource_access.modal.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class MessageAccessRest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Application Scope Name " + req.getSession().getServletContext().getAttribute("name"));
        resp.getWriter().println("Application Scope Name " + req.getSession().getServletContext().getAttribute("name"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            System.out.println(e);
        }

        String payloadString = jb.toString();
        Gson gson = new Gson();
        Message message = gson.fromJson(payloadString, Message.class);


        req.getSession().getServletContext().setAttribute("name", message.getMessage());

        req.getSession().setAttribute("session_key", System.nanoTime());

        System.out.println("MessageSession Key : " + req.getSession().getAttribute("session_key"));
        System.out.println("MessageUser"+req.getSession().getAttribute("name"));
        resp.getWriter().println("OK");
        System.out.println(message.getMessage());
    }


}
