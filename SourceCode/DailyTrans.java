import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

class DailyTrans implements Comparable
{
	String emp_id;
	Date d_date;
	int wd;
	float amt;
	char type;
	
	DailyTrans(String emp_id,Date d_date,int wd,float amt,char type)
	{
		this.emp_id = new String(emp_id);
		this.d_date = d_date;
		this.wd = wd;
		this.amt = amt;
		this.type = type;
	}
	
	
	 @Override
    public int compareTo(Object comparestu) {
        
        return this.d_date.compareTo(((DailyTrans)comparestu).d_date);

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
	
	static ArrayList<DailyTrans> getTrans(String emp_id , Date last_tr_date ,char type)
	{   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<DailyTrans> ans = new ArrayList<>();
		if (last_tr_date == null)
		{
		   try{  
		  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
		
				Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from dailywagetrans where emp_id = '" + emp_id + "' AND "
				                                + "type = '" + Character.toString(type) + "'");  
				while (rs.next())
				{
				 ans.add(new DailyTrans(rs.getString("emp_id") , rs.getDate("d_date") ,rs.getInt("weekday") ,
          				 rs.getFloat("amount") , rs.getString("type").charAt(0) ));
				};

                
				con.close();  
		        if (ans.size()!=0)
					  Collections.sort(ans);
				return ans;
			  }catch(Exception e){ System.out.println(e); return ans; }  	
		}
		
		else
		{
			try{  
		  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/fkpayroll?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");  
		
				Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from dailywagetrans where emp_id = '" + emp_id + "' AND d_date > "
				                        + " '" + df.format(last_tr_date) + "' AND type = '" +  Character.toString(type)+ "'");  
				while (rs.next())
				{
				 ans.add(new DailyTrans(rs.getString("emp_id") , rs.getDate("d_date") ,rs.getInt("weekday") ,
          				 rs.getFloat("amount") , rs.getString("type").charAt(0) ));
				};

                
				con.close();  
		        if (ans.size()!=0)
					  Collections.sort(ans);
				return ans;
			  }catch(Exception e){ System.out.println(e); return ans; }  
			
		}
		
	}	
	
	static DailyTrans getLastFriday(ArrayList<DailyTrans> arrD)
	{
		
		DailyTrans ans = null;
		for (DailyTrans dt : arrD)
		{
			if (dt.wd == 6 && ans == null)
			{
				 ans = dt;
				 
			}
			if (dt.wd == 6 && ans!=null)
			{
				if (dt.d_date.compareTo(ans.d_date) > 0)
				{
					ans = dt;
				}
			}
		}
		
		return ans;
	}
	
	
	
	
	
}