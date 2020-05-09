import java.util.Date;
import java.sql.*;
class SalaryEmployee extends Employee
{
	float sl;
	Date last_mtr_date;
	SalaryEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float sl)
	{
		super(emp_id, mem_union, uwr,pay_met);
		this.sl = sl;
		this.last_mtr_date = null;
	}
	
	SalaryEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float sl , Date last_mtr_date)
	{
		super(emp_id, mem_union, uwr,pay_met);
		this.sl = sl;
		this.last_mtr_date = last_mtr_date;
	}
	
	void writeToDb()
	{
		try{  
			//Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
			
			String query = " insert into salaryemployee"
        + " values (?, ?, ?, ?, ? ,?)";

			// create the mysql insert preparedstatement
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, emp_id);
			  preparedStmt.setBoolean (2, mem_union == 1 ? true : false);
			  preparedStmt.setFloat   (3, uwr);
			  
			  preparedStmt.setString  (4, Character.toString(pay_met));
			  preparedStmt.setFloat(5, sl);
			  preparedStmt.setDate   (6, null);

			  // execute the preparedstatement
			  preparedStmt.execute();
			
			con.close();  
			}catch(Exception e){ System.out.println(e);} 
		
	}
	
	
	static SalaryEmployee findEmp(String empid)
	{  
		try{  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
		
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from salaryemployee where emp_id = '" + empid + "'");  
		 
		 
		if (!rs.next() )
            return null;
        SalaryEmployee x = new SalaryEmployee(rs.getString("emp_id"),rs.getBoolean("union_member") == true ?(byte) 1 :(byte) 0,rs.getFloat("union_week_rate")
		                    ,rs.getString("payment_method").charAt(0) , rs.getFloat("salary") , rs.getDate("last_mtr_date"));		
		con.close();  
		return x;
		}catch(Exception e){ System.out.println(e); return null;}  
		
	}
	
	
	static void rmEmp(String empid)
	{   try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "DELETE FROM salaryemployee " +
                   "WHERE emp_id = '"   + empid + "'";
			stmt.executeUpdate(sql);
			
			stmt = con.createStatement();
			String sql2 = "DELETE FROM dailywagetrans " +
                   "WHERE emp_id = '"   + empid + "'";
			stmt.executeUpdate(sql2);
			
			con.close();
			}catch (Exception e ) { System.out.println(e);} 
	}
	
	
	
	
}