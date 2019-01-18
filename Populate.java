import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;


import java.util.Scanner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.*;

public class Populate{
	
	public static Connection Connect(String dbpropertiesfile) throws SQLException, FileNotFoundException
	{
		File dbproperties=new File(dbpropertiesfile);
		Scanner sc=new Scanner(dbproperties);
		String[] temp=sc.nextLine().split(" ");
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://"+temp[0]+":"+temp[1]+"/"+temp[2]+"", ""+temp[3]+"" , ""+temp[4]+"");
		return con;
		
	}
	
	public static void insertincident(Connection con, String incidentfile) throws FileNotFoundException, SQLException {
		File f = new File(incidentfile);
		Scanner sc = new Scanner(f);
		PreparedStatement ps = (PreparedStatement) con.prepareStatement("Truncate table PublicSafety.incident");
		ps.execute();
		while(sc.hasNextLine()) {
		
			String[] temp = sc.nextLine().split(",");
			if(temp[0] == null || temp[1] == null || temp[2] == null || temp[3] == null) {
				System.out.println("Data Inconsistent");
			}
			else {
			String points = "";
			for(int j = 2 ; j<4; j++) {
				if(j%2 == 0) {
						points = points + temp[j].replace(" ", "") + " ";
				}
				else
						points = points +temp[j];
			}
			points = "POINT("+points+")";
			ps=(PreparedStatement) con.prepareStatement("insert into incident values(?,?, st_geomfromtext(?))"); 
			ps.setInt(1, Integer.parseInt(temp[0]));
			ps.setString(2,temp[1]);
			ps.setString(3, points);
			ps.executeUpdate();
			}
		}
		System.out.println("Successfully inserted incident");
	}
	public static void insertofficer(Connection con, String officerfile) throws FileNotFoundException, SQLException {
		File officer = new File(officerfile);
		Scanner sc = new Scanner(officer);
		PreparedStatement ps = (PreparedStatement) con.prepareStatement("Truncate table PublicSafety.officer");
		ps.execute();
		while(sc.hasNextLine()) {
			String[] temp1 = sc.nextLine().split(",");
			if(temp1[0] == null || temp1[1] == null || temp1[2] == null || temp1[3] == null || temp1[4] == null) {
				System.out.println("Data Inconsistent");
			}
			else {
			String points = "";
			for(int j = 3 ; j<5; j++) {
				if(j%2 != 0) {
						points = points + temp1[j].replace(" ", "") + " ";
				}
				else
						points = points +temp1[j];
			}
			points = "POINT("+points+")";
			ps=(PreparedStatement) con.prepareStatement("insert into officer values(?,?,?,st_geomfromtext(?))"); 
			ps.setInt(1, Integer.parseInt(temp1[0]));
			ps.setString(2,temp1[1]);
			ps.setInt(3, Integer.parseInt(temp1[2].replace(" ","")));
			ps.setString(4, points);
			ps.executeUpdate();
			}
		}
		System.out.println("Successfully inserted officer");
	}
	public static void insertroute(Connection con, String routefile) throws FileNotFoundException, SQLException {
		File route = new File(routefile);
		Scanner sc = new Scanner(route);
		PreparedStatement ps = (PreparedStatement) con.prepareStatement("Truncate table PublicSafety.route");
		ps.execute();
		while(sc.hasNextLine()) {
			String[] temp2 = sc.nextLine().split(",");
			if(temp2[0] == null || temp2[1] ==null) {
				System.out.println("Data Inconsistent");
			}
			int count = Integer.parseInt(temp2[1].replace(" ", ""));
			count = count + 1;
			String line = "";
			for(int j = 2 ; j<count*2; j++) {
				if(temp2[j] == null)
					System.out.println("Data inconsistent");
				else {
				if(j%2 != 0) {
					if(j==(count*2)-1)
						line = line + temp2[j];
					else
						line = line + temp2[j] + " ,";
				}
				else
					line = line +temp2[j].replace(" ,", "");
				}
			}
			line = "LineString("+line+")";
			ps=(PreparedStatement) con.prepareStatement("insert into route values(? ,  ? , st_geomfromtext(?))"); 
			ps.setInt(1, Integer.parseInt(temp2[0]));
			ps.setInt(2,Integer.parseInt(temp2[1].replace(" ", "")));
			ps.setString(3, line);
			ps.executeUpdate();
		}
		System.out.println("Successfully inserted route");
		
	}
	public static void insertzone(Connection con, String zonefile) throws FileNotFoundException, SQLException {
		File zone = new File(zonefile);
		Scanner sc = new Scanner(zone);
		PreparedStatement ps = (PreparedStatement) con.prepareStatement("Truncate table PublicSafety.zone");
		ps.execute();
		while(sc.hasNextLine()) {
			String[] temp3 = sc.nextLine().split(",");
			if(temp3[0] == null || temp3[1] ==null) {
				System.out.println("Data Inconsistent");
			}
			int count = Integer.parseInt(temp3[3].replace(" ", ""));
			count = count + 2;
			String polygon = "";
			for(int j = 4 ; j<count*2; j++) {
				if(temp3[j] == null) {
					System.out.println("Data inconsistent");
				}
				else {
				if(j%2 != 0) 
						polygon = polygon + temp3[j] + " ,";
				else
					polygon = polygon +temp3[j].replace(" ,", "");
				}	
			}
			polygon = polygon + temp3[4].replace(",", "") + temp3[5];
			polygon = "Polygon(("+polygon+"))";
			String vertex = Integer.toString(Integer.parseInt(temp3[3].replace(" ", ""))+1);
			ps=(PreparedStatement) con.prepareStatement("insert into zone values(? , ? , ? , ? , st_geomfromtext(?))"); 
			ps.setInt(1, Integer.parseInt(temp3[0]));
			ps.setString(2, temp3[1]);
			ps.setInt(3, Integer.parseInt(temp3[2].replace(" ", "")));
			ps.setInt(4, Integer.parseInt(vertex));
			ps.setString(5, polygon);
			ps.executeUpdate();
		}
		System.out.println("Successfully inserted zone");
		
	}
	

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = null;
		try {
			con=Connect(args[0]);
			if (con != null)              
                System.out.println("Connected");             
            else            
                System.out.println("Not Connected"); 
			insertincident(con,args[1]);
			insertofficer(con,args[2]);
			insertroute(con,args[3]);
			insertzone(con,args[4]);
		}
		
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
	}

}
