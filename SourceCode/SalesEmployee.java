import java.util.Date;
import java.sql.*;
class SalesEmployee extends SalaryEmployee
{
	float cmr;
	Date last_ftr_date;
	SalesEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float sl ,float cmr)
	{
		super(emp_id, mem_union, uwr,pay_met,sl);
		this.cmr = cmr;
		this.last_ftr_date = null;
	}
	
	SalesEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float sl ,float cmr , Date last_mtr_date ,  Date last_ftr_date)
	{
		super(emp_id, mem_union, uwr,pay_met,sl , last_mtr_date);
		this.cmr = cmr;
		this.last_ftr_date = last_ftr_date;
	}
	
	float getComm(float sales)
	{
		 return (cmr/100.0f)*sales;
	}
	void writeToDb()
	{
		try{  
			//Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
			
			String query = " insert into salesemployee"
        + " values (?, ?, ?, ?, ? ,? ,? , ?)";

			// create the mysql insert preparedstatement
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, emp_id);
			  preparedStmt.setBoolean (2, mem_union == 1 ? true : false);
			  preparedStmt.setFloat   (3, uwr);
			  
			  preparedStmt.setString  (4, Character.toString(pay_met));
			  preparedStmt.setFloat(5, sl);
			  preparedStmt.setFloat(6, cmr);
			  preparedStmt.setDate   (7, null);
			  preparedStmt.setDate   (8, null);

			  // execute the preparedstatement
			  preparedStmt.execute();
			
			con.close();  
			}catch(Exception e){ System.out.println(e);} 
		
	}
	
	static SalesEmployee findEmp(String empid)
	{  
		try{  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
		
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from salesemployee where emp_id = '" + empid + "'");  
		 
		if ( !rs.next())
           return null;

        SalesEmployee x =  new SalesEmployee(rs.getString("emp_id"),rs.getBoolean("union_member") == true ?(byte) 1 : (byte)0,rs.getFloat("union_week_rate")
		                    ,rs.getString("payment_method").charAt(0) , rs.getFloat("salary"), 
							rs.getFloat("comm_rate"), rs.getDate("last_mtr_date"),rs.getDate("last_ftr_date"));	   
		con.close(); 
        return x;		
		}catch(Exception e){ System.out.println(e); return null;}  
		
	}
	
	static void rmEmp(String empid)
	{   try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "DELETE FROM salesemployee " +
                   "WHERE emp_id = '"   + empid + "'";
			stmt.executeUpdate(sql);
			
			stmt = con.createStatement();
			String sql2 = "DELETE FROM dailywagetrans " +
                   "WHERE emp_id = '"   + empid + "'";
			stmt.executeUpdate(sql2);
			
			con.close();
			}catch (Exception e ) { System.out.println(e);} 
	}
	
	
	void updateComm(float cmrx)
	{
		this.cmr = cmrx;
		try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "UPDATE salesemployee  SET comm_rate = " +cmrx +
                   "WHERE emp_id = '"   + emp_id + "'";
			stmt.executeUpdate(sql);
			
			
			con.close();
			}catch (Exception e ) { System.out.println(e);} 
		
	}
	
}