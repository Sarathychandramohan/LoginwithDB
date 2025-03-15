package RegLoginwithDB;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import org.json.JSONObject;

@WebServlet("/register")
public class Register extends HttpServlet{
	private static final long serialVersionUID=1L;
		
	protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		PrintWriter out=response.getWriter();
		JSONObject json = new JSONObject();
		
		try{
			BufferedReader reader = request.getReader();
			StringBuilder stringbuilder=new StringBuilder();
			String line;
			
			while ((line=reader.readLine())!=null){
			stringbuilder.append(line);		
			}		
			
			JSONObject requestData = new JSONObject(stringbuilder.toString());
			String username=requestData.getString("username");
			String password=requestData.getString("password");
			
			try (Connection conn = Database.getConnection();
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)")){
				
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.executeUpdate();
				
				json.put("message", "user registered successfully");
				response.setStatus(HttpServletResponse.SC_CREATED);
			}	}	
		catch(Exception e){
			json.put("Error",e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		out.print(json.toString());
		out.flush();}
	}
		
