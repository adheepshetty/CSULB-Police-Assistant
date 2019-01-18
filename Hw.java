
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Hw{

	public static Connection Connect(String dbpropertiesfile) throws SQLException, FileNotFoundException
	{
		File dbproperties=new File(dbpropertiesfile);
		Scanner sc=new Scanner(dbproperties);
		String[] temp=sc.nextLine().split(" ");
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"+temp[0]+":"+temp[1]+"/"+temp[2]+"", ""+temp[3]+"" , ""+temp[4]+"");
		return con;
		
	}
	
	public static void query1(Connection con,String[] args) throws SQLException {
		try 
		{
		String points= null;
		int i = 3;
		int count = Integer.parseInt(args[2])*2+i;
		if(count!=args.length) {
			if(count > args.length)
				System.out.println("Please add "+(count-args.length)/2+" more coordinates");
			else
				System.out.println("Please remove "+(args.length-count)/2+" coordinates");
		}
		else {
		while(i<count) {
			if(i==3)
				points = args[i] + " ";
			else if(count-1==i)
				points = points + args[i];
			else if(i%2 != 0)
					points = points + args[i] + " ";
			else
					points = points + args[i] + " ,";
			i++;
		}
		if(!(args[3].equals(args[count-2])) || !(args[4].equals(args[count-1]))) {
			points =  points + " ," + args[3] + " " + args[4];
		}
		points = "'POLYGON(("+ points+"))'";
		PreparedStatement ps =null;
		ps=(PreparedStatement) con.prepareStatement("select incidentid , X(incident.loc) as xloc, Y(loc) as yloc , incidenttype " + 
													"from incident " + 
													"where st_contains(geomfromtext("+points+") , incident.loc) \r\n" + 
													"order by incidentid asc" );
		ResultSet rs = (ResultSet) ps.executeQuery();
		if(!rs.next()) {
			System.out.println("Incident Doesn't exist");
		}
		else
		{
			rs.beforeFirst();
			while(rs.next()) {
				int incidentid = rs.getInt("incidentid");
				String x_location = rs.getString("xloc");
				String y_location = rs.getString("yloc");
				String incidenttype = rs.getString("incidenttype");
				System.out.println(incidentid+" "+y_location+","+x_location+incidenttype.replace("\"", ""));
			}
		}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void query2(Connection con ,String[] args){
		try {
		int incidentid = Integer.parseInt(args[2]);
		int distance = Integer.parseInt(args[3]);
		PreparedStatement ps =null;
		ps=(PreparedStatement) con.prepareStatement("select  officer.badgeno , st_distance_sphere(officer.pos, incident.loc) as proximity ,officer.officername " + 
													"from officer , incident " + 
													"where incidentid = "+incidentid+" " + 
													"having proximity<"+distance+" " + 
													"order by proximity asc");
		
		ResultSet rs = (ResultSet) ps.executeQuery();
		if(!rs.next()) {
			System.out.println("Officer Doesn't exist");
		}
		else {
		rs.beforeFirst();
		while(rs.next()) {
			int badgeno = rs.getInt("badgeno");
			int proximity = rs.getInt("proximity");
			String officername = rs.getString("officername");
			System.out.println(badgeno+" "+proximity+"m "+officername.replace("\"", ""));
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void query3(Connection con, String[] args) {
		try {
		int squadnumber = Integer.parseInt(args[2]);
		PreparedStatement ps =null;
		ps=(PreparedStatement) con.prepareStatement("select  zone.zonename, officer.badgeno , "
															+ "Case st_contains(zone.region , pos) when  1 then \"IN\" " + 
													"Else \"OUT\" " + 
													"End As location, officername  " + 
													"from zone , officer " + 
													"where zone.squadno = "+squadnumber+" and zone.squadno = officer.squadno " + 
													"order by badgeno asc");
		ResultSet rs = (ResultSet) ps.executeQuery();
		if(!rs.next()) {
			System.out.println("Doesn't exist");
		}
		else {
		rs.beforeFirst();
		if(rs.next()) {	
			String zonename = rs.getString("zonename");
			System.out.println("Squad "+squadnumber+" is now patrolling at :"+zonename.replace("\"", ""));
			int badgeno = rs.getInt("badgeno");
			String place = rs.getString("location");
			String officername = rs.getString("officername");
			System.out.println(badgeno+" "+place+" "+officername);
		}
		while(rs.next()) {
			int badgeno = rs.getInt("badgeno");
			String place = rs.getString("location");
			String officername = rs.getString("officername");
			System.out.println(badgeno+" "+place+" "+officername);
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void query4(Connection con, String[] args){
		try {
		int routenumber = Integer.parseInt(args[2]);
		PreparedStatement ps =null;
		ps=(PreparedStatement) con.prepareStatement("select zone.zoneid , zone.zonename " + 
													"from route , zone " + 
													"where routeno= "+routenumber+" and st_intersects(route.street , zone.region)");
		ResultSet rs = (ResultSet) ps.executeQuery();
		if(!rs.next()) {
			System.out.println("Zone doesn't Exist");
		}
		else {
		rs.beforeFirst();
		System.out.println("The list of zones with their zoneids through which route "+routenumber+" passes through are:");
		while(rs.next()) {
			int zoneid = rs.getInt("zoneid");
			String zonename= rs.getString("zonename");
			System.out.println(zoneid+" "+zonename.replace(" \"","").replace("\"", ""));
		}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = null;
		Statement st = null;
			con=Connect(args[0]);
			if (con != null)              
                System.out.println("Connected");             
            else            
                System.out.println("Not Connected"); 
			st = (Statement) con.createStatement();
			if(args[1].equals("q1")) {
				query1(con,args);
			}
			if(args[1].equals("q2")) {
				query2(con,args);	
			}
			if(args[1].equals("q3")){
				query3(con,args);
			}
			if(args[1].equals("q4")) {
				query4(con,args);
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
