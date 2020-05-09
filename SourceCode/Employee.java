import java.util.*;
import java.sql.*;
class Employee 
{
	String emp_id;
	byte mem_union;
	float uwr;
	char pay_met;
	
	Employee(String emp_id,byte mem_union,float uwr,char pay_met)
	{
		this.emp_id = new String(emp_id);
		this.mem_union = mem_union;
		this.uwr = uwr;
		this.pay_met = pay_met;
		
	}
	
	void dailyWageUpdate(float amt , char type)
	{
		
		Calendar cal = Calendar.getInstance(); 
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
		
		try{  
			//Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
			
			String query = " insert into dailywagetrans"
        + " values (?, ?, ?, ?, ? )";

			// create the mysql insert preparedstatement
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, emp_id);
			  preparedStmt.setTimestamp (2,timestamp);
			  preparedStmt.setInt   (3, cal.get(Calendar.DAY_OF_WEEK) );
			  preparedStmt.setFloat(4, amt);
			  preparedStmt.setString  (5, Character.toString(type));
			 

			  // execute the preparedstatement
			  preparedStmt.execute();
			
			con.close();  
			}catch(Exception e){ System.out.println(e);} 
		
		
		
		
	}
	
	
	
	
}