package hbv.web;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class RegServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        response.setContentType("text/plain");
//        Printwriter out = response.getWriter();

        String email = request.getParameter("mail");
        String passwd = request.getParameter("password");

        try{
            Context initCtx = new InitialContext();
            DataSource ds = (DataSource)initCtx.lookup("java:/comp/env/jdbc/mariadb");
            Connection con = ds.getConnection();

            PreparedStatement insert = con.prepareStatement("insert into user(email, passwd) values(?, ?)");
            insert.setString(1,email);
            insert.setString(2,passwd);
            ResultSet s = insert.executeQuery();
//            if (s.next())
//                out.println("Registrierung erfolgreich");
            insert.close();
        }catch(Exception e){e.printStackTrace();}
    }
}
