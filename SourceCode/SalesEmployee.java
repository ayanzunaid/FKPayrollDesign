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
}