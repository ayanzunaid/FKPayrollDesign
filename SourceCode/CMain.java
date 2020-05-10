import java.util.*;  
//java -classpath .;C:\Users\HP\Desktop\JP\mysql-connector-java-8.0.20.jar CMain
class CMain{
	
	private static Scanner sc;
    private static void addEmp()
	{
		
		System.out.println("Enter Employee ID (6 character code) :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		System.out.println("Enter Payment Method Choice: ");
		System.out.println("1. postal address 2. paymaster 3.bank account ");
		
		int pm = sc.nextInt();
		char pay_met = (pm == 1) ? 'A' : (pm == 2)? 'P' : 'B' ;
  		System.out.println("Member of Union ?(1/0)");
		byte mem = sc.nextByte();
		float uwr = 0.0f;
		float hr = 0.0f;
		float sl = 0.0f;
		float cmr = 0.0f;
		if (mem == 1)
		{
			System.out.println("Enter union week due rate: ");
			uwr = sc.nextFloat();
		}
		
		System.out.println("Enter Employee Type : ");
		System.out.println("1. Daily Wage Employee 2.Salary Employee 3. Sales Employee ");
		int emp_t = sc.nextInt();
		if (emp_t == 1)
			{
				System.out.println("Enter Hour Rate: ");
				hr = sc.nextFloat();
				DWEmployee emp = new DWEmployee(empid,mem,uwr,pay_met,hr );
				emp.writeToDb();
				System.out.println("Done\n");
			}
		
		else if (emp_t == 2)
			{
				System.out.println("Enter Salary: ");
				sl = sc.nextFloat();
				SalaryEmployee emp = new SalaryEmployee(empid,mem,uwr,pay_met,sl);
				emp.writeToDb();
				System.out.println("Done\n");
			}
			
	    else if (emp_t == 3)
		    {
			    System.out.println("Enter Salary: ");
				sl = sc.nextFloat();
				System.out.println("Enter Commision Rate: ");
				cmr = sc.nextFloat();
				SalesEmployee emp = new SalesEmployee(empid,mem,uwr,pay_met,sl,cmr);
				emp.writeToDb();
				System.out.println("Done\n");
				
		    }
			
	    else 
			{
			System.out.println("Invalid Employee type aborting..");
				
			}
			
			
	}
	
	private static void rmEmp()
	{
		System.out.println("Enter Employee ID to be removed:");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		if (DWEmployee.findEmp(empid) != null)
			
			{  DWEmployee.rmEmp(empid); System.out.println("DONE\n");}
		 
		else if (SalaryEmployee.findEmp(empid) != null)
			{ SalaryEmployee.rmEmp(empid); System.out.println("DONE\n"); }
		
		else if (SalesEmployee.findEmp(empid)!= null)
			{ SalesEmployee.rmEmp(empid); System.out.println("DONE\n"); }
		else
			{ System.out.println("INVALID EMPLOYEE ID\n");}
		
		
	}
	
	private static void postTimeCard()
	{
		System.out.println("Enter Employee ID :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		DWEmployee x = DWEmployee.findEmp(empid);
		if (x != null)
		{
			System.out.println("Enter Hours Worked :");
			sc = new Scanner(System.in);
			int hrs = sc.nextInt();
			
			if (hrs > 24 || hrs < 0)
				System.out.println("Invalid Hour Range aborting...");
            else 
			{
				float amount = x.dailyWage(hrs);
				System.out.println("Enter Date(yyyy-mm-dd) :");
				sc = new Scanner(System.in);
				String date= sc.nextLine();
				
				System.out.println("Enter WeekDay:");
				sc = new Scanner(System.in);
				int wd = sc.nextInt();
				
				x.dailyWageUpdate(amount,'D' ,date , wd);
				System.out.println("Done\n");
			}				

		}
		
		else
			{ System.out.println("INVALID EMPLOYEE ID\n");}
		
	}
	
	private static void postSalesReceipt()
	
	{
		System.out.println("Enter Employee ID :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		SalesEmployee x = SalesEmployee.findEmp(empid);
		 if (x!=null) {
		System.out.println("Enter Sales Made:");
			sc = new Scanner(System.in);
			float sals = sc.nextFloat();
		
		if (sals < 0.0f)
			 System.out.println("Invalid Amount Aborting....\n");
		 else
		 {   
	         System.out.println("Enter Date(yyyy-mm-dd) :");
							sc = new Scanner(System.in);
							String date= sc.nextLine();
				
							System.out.println("Enter WeekDay:");
							sc = new Scanner(System.in);
							int wd = sc.nextInt();
							
			 x.dailyWageUpdate(x.getComm(sals),'D' , date , wd);
				System.out.println("Done\n");
			 
		 }
		 }
		 else {
			 System.out.println("INVALID EMPLOYEE ID\n");
		 }
	}
	
	
	
	private static void postServiceCharge()
	
	{
		System.out.println("Enter Employee ID :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		System.out.println("Enter Date(yyyy-mm-dd) :");
							sc = new Scanner(System.in);
							String date= sc.nextLine();
				
							System.out.println("Enter WeekDay:");
							sc = new Scanner(System.in);
							int wd = sc.nextInt();
		
		
		DWEmployee x = DWEmployee.findEmp(empid);
		SalaryEmployee y = SalaryEmployee.findEmp(empid);
		SalesEmployee z = SalesEmployee.findEmp(empid);
		if (x != null)
			
			{  if (x.isUnionMember())
				{
				  System.out.println("Enter Service Charge:");
					sc = new Scanner(System.in);
					float sv = sc.nextFloat();
					if (sv < 0.0f)
					{
						System.out.println("Invalid Amount Aborting....\n");
					}
					else{
						    
						
							x.dailyWageUpdate(-1.0f*sv , 'S' ,date , wd);
							System.out.println("DONE\n");
					}
				}
				
			   else 
			   {
				   System.out.println("Not a Union Member\n");	 
			   }
			}
		 
		else if ( y != null)
			{ 
		      if (y.isUnionMember())
				{ System.out.println("Enter Service Charge:");
					sc = new Scanner(System.in);
					float sv = sc.nextFloat();
					if (sv < 0.0f)
					{
						System.out.println("Invalid Amount Aborting....\n");
					}
					else{
				  y.dailyWageUpdate(-1.0f*sv , 'S' ,date , wd );
				  System.out.println("DONE\n");
					}				  
				}
				
			   else 
			   {
				   System.out.println("Not a Union Member\n");	 
			   }
     		}
		
		else if (z != null)
			{
				if (z.isUnionMember())
				{ System.out.println("Enter Service Charge:");
					sc = new Scanner(System.in);
					float sv = sc.nextFloat();
					if (sv < 0.0f)
					{
						System.out.println("Invalid Amount Aborting....\n");
					}
					else{
				  z.dailyWageUpdate(-1.0f*sv , 'S' ,date , wd);
				  System.out.println("DONE\n");	
					}
				}
				
			   else 
			   {
				   System.out.println("Not a Union Member\n");	 
			   }
		    }
		else
			{ System.out.println("INVALID EMPLOYEE ID\n");}
	}
	
	
	
	private static void editDetails()
	{
		
		System.out.println("Enter Employee ID :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		
		DWEmployee x = DWEmployee.findEmp(empid);
		SalaryEmployee y = SalaryEmployee.findEmp(empid);
		SalesEmployee z = SalesEmployee.findEmp(empid);
		
		if (x != null)
			
			{ 
			   System.out.println("1. Hour Rate 2.Payment Method");
			   if (x.isUnionMember() )
			   {
				   System.out.println(" 3. Leave Union 4.Union Due Rate");
				   
			   }
			   else 
			   {
				    System.out.println(" 3. Join Union");
			   }
			   
			   sc = new Scanner(System.in);
			   int ch = sc.nextInt();
			   if (ch == 1)
			   {   System.out.println("Enter new Hour Rate : ");
				   sc = new Scanner(System.in);
				   float hrx = sc.nextFloat();
				   x.updateHourRate(hrx);
			   }
		       else if (ch == 2) {
				   System.out.println("1. postal address 2. paymaster 3.bank account ");
		            sc = new Scanner(System.in);
					int pmx = sc.nextInt();
					char pay_metx = (pmx == 1) ? 'A' : (pmx == 2)? 'P' : 'B' ;
				    x.updatePay(pay_metx , "dailywageemployee");
			   }
			   else if  ( ch==3 && x.isUnionMember())
			       {
					   x.updateMember(0 , 0.0f , "dailywageemployee");
				   }
				
		       else if ((ch == 4 && x.isUnionMember()) || (ch == 3 && !x.isUnionMember()))
			       {
					  System.out.println("Enter new Weekly Due Rate : ");
						sc = new Scanner(System.in);
						float uwrx = sc.nextFloat();
					  x.updateMember(1,uwrx,"dailywageemployee" );  
				   }
				else {}
			    System.out.println("DONE\n");
			}
		 
		else if (y != null)
			{  
		        System.out.println("1. Salary 2.Payment Method");
			   if (y.isUnionMember() )
			   {
				   System.out.println(" 3. Leave Union 4.Union Due Rate");
				   
			   }
			   else 
			   {
				    System.out.println(" 3. Join Union");
			   }
			   
			   sc = new Scanner(System.in);
			   int ch = sc.nextInt();
			   if (ch == 1)
			   {   System.out.println("Enter new Salary : ");
				   sc = new Scanner(System.in);
				   float sal = sc.nextFloat();
				   y.updateSalary(sal , "salaryemployee");
			   }
		       else if (ch == 2) {
				   System.out.println("1. postal address 2. paymaster 3.bank account ");
		            sc = new Scanner(System.in);
					int pmx = sc.nextInt();
					char pay_metx = (pmx == 1) ? 'A' : (pmx == 2)? 'P' : 'B' ;
				    y.updatePay(pay_metx , "salaryemployee");
			   }
			   else if  ( ch==3 && y.isUnionMember())
			       {
					   y.updateMember(0 , 0.0f , "salaryemployee");
				   }
				
		       else if ((ch == 4 && y.isUnionMember()) || (ch == 3 && !y.isUnionMember()))
			       {
					  System.out.println("Enter new Weekly Due Rate : ");
						sc = new Scanner(System.in);
						float uwrx = sc.nextFloat();
					  y.updateMember(1,uwrx,"salaryemployee" );  
				   }
				else {}
        		System.out.println("DONE\n"); 
				
			}
		
		else if (z != null)
			{   		        System.out.println("1. Salary 2.Payment Method");
			   if (z.isUnionMember() )
			   {
				   System.out.println(" 3. Leave Union 4.Union Due Rate");
				   
			   }
			   else 
			   {
				    System.out.println(" 3. Join Union");
			   }
			   
			   System.out.println(" 5. Commission Rate");
			   
			   sc = new Scanner(System.in);
			   int ch = sc.nextInt();
			   if (ch == 1)
			   {   System.out.println("Enter new Salary : ");
				   sc = new Scanner(System.in);
				   float sal = sc.nextFloat();
				   z.updateSalary(sal, "salesemployee");
			   }
		       else if (ch == 2) {
				   System.out.println("1. postal address 2. paymaster 3.bank account ");
		            sc = new Scanner(System.in);
					int pmx = sc.nextInt();
					char pay_metx = (pmx == 1) ? 'A' : (pmx == 2)? 'P' : 'B' ;
				    z.updatePay(pay_metx , "salesemployee");
			   }
			   else if  ( ch==3 && z.isUnionMember())
			       {
					   z.updateMember(0 , 0.0f , "salesemployee");
				   }
				
		       else if ((ch == 4 && z.isUnionMember()) || (ch == 3 && !z.isUnionMember()))
			       {
					  System.out.println("Enter new Weekly Due Rate : ");
						sc = new Scanner(System.in);
						float uwrx = sc.nextFloat();
					  z.updateMember(1,uwrx,"salesemployee" );  
				   }
			    else if (ch == 5)
				{
					 System.out.println("Enter new Commission Rate : ");
				   sc = new Scanner(System.in);
				   float cmrx = sc.nextFloat();
				   z.updateComm(cmrx); 
				}
				else {}
        		
				
        		System.out.println("DONE\n"); }
		else
			{ System.out.println("INVALID EMPLOYEE ID\n");}
		
        		
	}
	
	
	private static void runPayroll()
	{
		System.out.println("Enter Employee ID :");
		sc = new Scanner(System.in);
		String empid = sc.nextLine();
		
		DWEmployee x = DWEmployee.findEmp(empid);
		SalaryEmployee y = SalaryEmployee.findEmp(empid);
		SalesEmployee z = SalesEmployee.findEmp(empid);
		if (x!=null)
		{  
	       ArrayList<DailyTrans> arrD = DailyTrans.getTrans(empid , x.last_tr_date ,'D');
		   
	       DailyTrans lfr = DailyTrans.getLastFriday(arrD);
		   
	        if (arrD.size() == 0 || lfr == null)
				{
					 System.out.println("PAYOUT : 0 No credits so far \n");
				}
			else
			{
				float payout = 0.0f;
				ArrayList<DailyTrans> arrS = DailyTrans.getTrans(empid , x.last_tr_date ,'S');
				if (arrS.size() != 0 )
				{	
					for (DailyTrans dt : arrS)
					{   if (dt.d_date.compareTo(lfr.d_date)<=0)
						payout += dt.amt;
					}
				}
				for (DailyTrans dt : arrD)
					{    if (dt.d_date.compareTo(lfr.d_date) <=0)
						payout += dt.amt;
					}
				
				if (payout <= 0.0f)
				System.out.println("PAYOUT : 0 MORE DEBIT THAN CREDIT");
			    else 
				{
				 System.out.println("PAYOUT : " + payout);
				 
			     x.updateLastTrDate(lfr.d_date);
			     System.out.println("DONE");
				}
				
		    }
		}
		else if (y !=null)
		{   float sall = 0.0f;
	        ArrayList<DailyTrans> arrS = DailyTrans.getTrans(empid ,y.last_mtr_date ,'S');
			Date lmtr = y.getLastMonthDay();
			if (y.last_mtr_date == null)
			{
				
				sall = y.sl;
				}
		  
	  
			  else
			  {
				  
				  Calendar startCalendar = new GregorianCalendar();
					startCalendar.setTime(y.last_mtr_date);
					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(lmtr);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
					
				   sall = (float)diffMonth*y.sl;
				   
			  }
			  
			  y.updateLastMonthDay(lmtr,"salaryemployee");
			  
              
				if (arrS.size() != 0 )
				{	
					for (DailyTrans dt : arrS)
					{   if (dt.d_date.compareTo(lmtr)<=0)
						sall += dt.amt;
					}
				}		
				
			  if (sall <= 0.0f)	
              System.out.println("PAYOUT : 0 MORE DEBIT THAN CREDIT / NO CREDIT");
		      else 
			  System.out.println("PAYOUT : " + sall);
		  
				
			  System.out.println("DONE\n");
		}
		else if (z!=null)
		{    float sall = 0.0f;
	          ArrayList<DailyTrans> arrD = DailyTrans.getTrans(empid , z.last_ftr_date ,'D');
		      
		  
				DailyTrans lfr = DailyTrans.getLastFriday(arrD);
				
				
				Date lmtr = z.getLastMonthDay();
				
				if (z.last_mtr_date == null)	
				   sall = z.sl;
		  
	  
			  else
			  {
				  
				  Calendar startCalendar = new GregorianCalendar();
					startCalendar.setTime(z.last_mtr_date);
					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(lmtr);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
					
				   sall = (float)diffMonth*y.sl;
				   
			  }
			  
			  
			  
			 if (lfr != null)
			 {				 
			 for (DailyTrans dt : arrD)
					{    if (dt.d_date.compareTo(lfr.d_date) <=0)
						sall += dt.amt;
					}
					
			 }
			 
			 Date cutoff;
			 if (z.last_ftr_date == null && z.last_mtr_date == null)
				  cutoff = null;
			  else if (z.last_mtr_date == null)
				   cutoff = z.last_ftr_date;
			  else if (z.last_ftr_date == null)
				   cutoff = z.last_mtr_date;
			   else 
			   {
				    cutoff = z.last_mtr_date.compareTo(z.last_ftr_date) >=0 ? z.last_mtr_date : z.last_ftr_date;
			   }
			   
			 ArrayList<DailyTrans> arrS = DailyTrans.getTrans(empid , cutoff,'S');
															  
			 if (arrS.size() != 0 )
				{	 if (lfr != null)
			        cutoff = lmtr.compareTo(lfr.d_date) >= 0? lmtr : lfr.d_date;
			        else 
						cutoff = lmtr;
					
					for (DailyTrans dt : arrS)
					{   if (dt.d_date.compareTo(cutoff)<=0)
						sall += dt.amt;
					}
				}	
			 
			 if (sall <=0.0f)
			 {   System.out.println("PAYOUT : 0 MORE DEBIT THAN CREDIT / NO CREDIT"); }
		     else 
			 {
				 System.out.println("PAYOUT : "  + sall);
                 z.updateLastMonthDay(lmtr , "salesemployee");
				 if (lfr != null)
					  z.updateLastFriday(lfr.d_date);
				  System.out.println("Done\n");
 			 }
				
			
		}
		else 
	    {
			System.out.println("Invalid Employee ID \n");
		}
		
		
	}
	public static void main(String [] args)
	{
		
		int  ch = 0;
		
		do 
		{   sc = new Scanner(System.in);
			System.out.println("FK PAYROLL SOFTWARE:");
			System.out.println("1.Add Employee");
			System.out.println("2.Remove Employee");
			System.out.println("3.Post Time Card");
			System.out.println("4.Post Sales receipt");
			System.out.println("5.Post Union Service Charge");
			System.out.println("6.Edit Employee Details");
			System.out.println("7.Run Payroll");
			System.out.println("Enter Choice :");
			ch = sc.nextInt();
			
			switch (ch)
			{
				case 1 : addEmp();break;
				case 2 : rmEmp();break;
				case 3 : postTimeCard();break;
				case 4 : postSalesReceipt();break;
				case 5 : postServiceCharge();break;
				case 6 : editDetails();break;
				case 7 : runPayroll();break;
				
			};
			
			
		}while(ch >=1 && ch<=7);
		
		System.out.println("BYE BYE!!");
		
	}
	
}