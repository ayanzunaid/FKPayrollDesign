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
	
	boolean isUnionMember()
	{
		return mem_union == 1 ? true : false;
	}
	
	void updatePay(char pmx , String name)
	{
		this.pay_met = pmx;
		try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "UPDATE " + name + "  SET payment_method = '" + Character.toString(pmx) +
                   "' WHERE emp_id = '"   + emp_id + "'";
			stmt.executeUpdate(sql);
			
			
			con.close();
			}catch (Exception e ) { System.out.println(e);} 
		
	}
	
	
	void updateMember(int mx , float uwrx , String name)
	{
		if (mx == 0 )
		{
			 this.mem_union = 0;
			 this.uwr = 0.0f;
		}
		
		else
		{
			this.mem_union = 1;
			this.uwr = uwrx;
		}
		
		
		try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "UPDATE " + name + "  SET union_member = " + this.mem_union +
                   " , union_week_rate = " +this.uwr + " WHERE emp_id = '"   + emp_id + "'";
			stmt.executeUpdate(sql);
			
			
			con.close();
			}catch (Exception e ) { System.out.println(e);} 
		
	}
	
	
	void dailyWageUpdate(float amt , char type , String date , int wd)
	{
		
		
        java.sql.Date datex =  java.sql.Date.valueOf(date);
		
		try{  
			//Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
			
			String query = " insert into dailywagetrans"
        + " values (?, ?, ?, ?, ? )";

			// create the mysql insert preparedstatement
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, emp_id);
			  preparedStmt.setDate (2, datex);
			  preparedStmt.setInt   (3, wd );
			  preparedStmt.setFloat(4, amt);
			  preparedStmt.setString  (5, Character.toString(type));
			 

			  // execute the preparedstatement
			  preparedStmt.execute();
			
			con.close();  
			}catch(Exception e){ System.out.println(e);} 
		
		
		
		
	}
	
	
	
	
	
	
	
}