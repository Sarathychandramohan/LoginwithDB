package RegLoginwithDB;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import org.json.JSONObject;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        try {
            BufferedReader reader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONObject requestData = new JSONObject(stringBuilder.toString());
            String username = requestData.getString("username");
            String password = requestData.getString("password");

            try (Connection conn = Database.getConnection();
            	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")){
            	
            	stmt.setString(1, username);
            	stmt.setString(2, password);
            	ResultSet rs = stmt.executeQuery();
            	
            if (rs.next()) {
            	json.put("message", "Login Successful");
            	response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
            	json.put("message","Invalid username or password");
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	}
            } 
        }
        	catch (Exception e) {
        
            json.put("error", e.getMessage());  
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.print(json.toString());
        out.flush();
    }

}
