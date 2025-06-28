package hbv.web;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class SQLServlet extends HttpServlet {

  protected void doGet(HttpServletRequest  request,
      HttpServletResponse response)
      throws IOException, ServletException {

      response.setContentType("text/plain");
      PrintWriter out = response.getWriter();
      long start = System.currentTimeMillis();
      try {
        // Naming Context
        Context initCtx = new InitialContext();
        DataSource ds = (DataSource)initCtx.lookup("java:/comp/env/jdbc/mariadb");

        // get connection ...
        Connection connection = ds.getConnection();

        // preparedstatement to prevent sql-injection
/*        PreparedStatement ps = connection.prepareStatement(
            "insert into demo (name) values(?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,"whatevercomes...");
        ps.executeUpdate();

        // get last generated Key
        ResultSet mrs=ps.getGeneratedKeys();
        if(mrs.next()){
          long id=mrs.getLong(1);
          out.println("lastid:"+id);
        }
        ps.close();*/

	

        // simple select statement is ok
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT zeitslot.zeit, impfzentrum.ort, buchung.datum FROM buchung JOIN zeitslot ON buchung.slot_id = zeitslot.slot_id JOIN impfzentrum ON zeitslot.zent_id = impfzentrum.zent_id WHERE buchung.user_id = ?");
        String userId = request.getParameter("userId");
        stmt.setString(1,userId);
//          stmt.setString(1,"1");
        ResultSet rs = stmt.executeQuery();

        // cursor pattern
        while(rs.next()){
          String zeit = rs.getString("zeitslot.zeit");
          String ort = rs.getString("impfzentrum.ort");
          String datum = rs.getString("buchung.datum");
//          String zent = rs.getString("zent_id");
          out.print(zeit + " ");
          out.print(datum + " ");
          out.println(ort);
//          out.println(zent);
//          out.println(zeit);
        }
        rs.close();
        stmt.close();
	

        connection.close();

      } catch(Exception e){
        out.println(e);
        e.printStackTrace(out);
        throw new RuntimeException(e);
      }
      long ende = System.currentTimeMillis();
      //out.println((ende-start)+"ms");

      ServletContext context = getServletContext();
  }
}
