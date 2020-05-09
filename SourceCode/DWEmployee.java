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
	
}