import java.util.*;  

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
				
				x.dailyWageUpdate(amount,'D');
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
		
		if (sals < 0)
			 System.out.println("Invalid Amount Aborting....\n");
		 else
		 {
			 x.dailyWageUpdate(x.getComm(sals),'D');
				System.out.println("Done\n");
			 
		 }
		 }
		 else {
			 System.out.println("INVALID EMPLOYEE ID\n");
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
				case 5 : break;
				case 6 : break;
				case 7 : break;
				
			};
			
			
		}while(ch >=1 && ch<=7);
		
		System.out.println("BYE BYE!!");
		
	}
	
}