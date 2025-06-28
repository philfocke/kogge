package hbv.web;
import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class BuchungErstellenServlet extends HttpServlet {

  protected void doGet(HttpServletRequest  request,
      HttpServletResponse response)
      throws IOException, ServletException {

      response.setContentType("text/plain");
      PrintWriter out = response.getWriter();
      try {
        // Naming Context
        Context initCtx = new InitialContext();
        DataSource ds = (DataSource)initCtx.lookup("java:/comp/env/jdbc/mariadb");

        // get connection ...
        Connection connection = ds.getConnection();

        String userId = request.getParameter("userId");
        String datum = request.getParameter("datum");
        String zentrumId = request.getParameter("zentrumId");
        PreparedStatement slotIdStmt = connection.prepareStatement(
            "with buchungen as (select slot_id as bSlot, count(slot_id) as cSlot from buchung group by slot_id, datum having datum = STR_TO_DATE(?, '%Y-%m-%d')), slots as (select zeitslot.slot_id as zSlot, zeitslot.zeit, impfzentrum.kapazität from zeitslot join impfzentrum on zeitslot.zent_id = impfzentrum.zent_id where impfzentrum.zent_id = ?), hilfe as (select * from slots left join buchungen on slots.zSlot = buchungen.bSlot) select min(zSlot) as endSlot from hilfe where coalesce(hilfe.cSlot, 0) < hilfe.kapazität;");
        slotIdStmt.setString(1, datum);
        slotIdStmt.setString(2, zentrumId);
       
        ResultSet slotSet = slotIdStmt.executeQuery();
 
        String slotId = "";
        if(slotSet.next()){
          slotId += slotSet.getString("endSlot"); 
        }        

          PreparedStatement insertBuchung = connection.prepareStatement(
            "insert into buchung(user_id, slot_id, datum) values (?,?,?);");
        insertBuchung.setString(1,userId);
        insertBuchung.setString(2,slotId);
        insertBuchung.setString(3,datum);

        if(!slotId.equals("null")){
          insertBuchung.executeUpdate();
          out.write("Buchung erfolgreich");
        }else{
          out.write("An dem gewählten Tag stehen keine freien Termine zur Verfügung");
        } 
        
        slotSet.close();
        slotIdStmt.close(); 
        insertBuchung.close(); 

        connection.close();

      } catch(Exception e){
        out.println(e);
        e.printStackTrace(out);
        throw new RuntimeException(e);
      }

      out.flush();
      out.close();

  }
}
