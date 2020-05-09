import java.util.Date;
import java.sql.*;
class DWEmployee extends Employee
{
	float hr;
	Date last_tr_date;
	DWEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float hr)
	{
		super(emp_id, mem_union, uwr,pay_met);
		this.hr = hr;
		this.last_tr_date = null;
	}
	
	DWEmployee(String emp_id,byte mem_union,float uwr,char pay_met , float hr , Date last_tr_date)
	{
		super(emp_id, mem_union, uwr,pay_met);
		this.hr = hr;
		this.last_tr_date = last_tr_date;
	}
	
	float dailyWage(int hrs)
	{
		 
		 if (hrs <= 8)
			   return ((float)hrs)*this.hr;
		 return (1.5f*((float)(hrs-8))*this.hr )  + 8.0f*this.hr ;
		 
	}
	
	void writeToDb()
	{
		try{  
			//Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
			
			String query = " insert into dailywageemployee"
        + " values (?, ?, ?, ?, ? ,?)";

			// create the mysql insert preparedstatement
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, emp_id);
			  preparedStmt.setBoolean (2, mem_union == 1 ? true : false);
			  preparedStmt.setFloat   (3, uwr);
			  preparedStmt.setFloat(4, hr);
			  preparedStmt.setString  (5, Character.toString(pay_met));
			  preparedStmt.setDate   (6, null);

			  // execute the preparedstatement
			  preparedStmt.execute();
			
			con.close();  
			}catch(Exception e){ System.out.println(e);} 
		
	}
	
	static DWEmployee findEmp(String empid)
	{  
		try{  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
		
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from dailywageemployee where emp_id = '" + empid + "'");  
		 
		 if (!rs.next() )
            return null;

        DWEmployee x = new DWEmployee(rs.getString("emp_id"),rs.getBoolean("union_member") == true ? (byte)1 :(byte) 0,rs.getFloat("union_week_rate")
		                    ,rs.getString("payment_method").charAt(0) , rs.getFloat("hour_rate") , rs.getDate("last_tr_date"));		
		con.close();  
		
		return x;
		}catch(Exception e){ System.out.println(e); return null;}  
		
	}
	
	static void rmEmp(String empid)
	{   try {
		    
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			Statement stmt = con.createStatement();
			String sql = "DELETE FROM dailywageemployee " +
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