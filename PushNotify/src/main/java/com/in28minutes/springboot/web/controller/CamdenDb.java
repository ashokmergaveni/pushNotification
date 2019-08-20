package com.in28minutes.springboot.web.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.in28minutes.springboot.web.controller.User;
public class CamdenDb {
	public List<User>  getUserDetails() throws SQLException, ClassNotFoundException
	{
	Class.forName("org.postgresql.Driver");
   List<User> lu=new ArrayList<User>();

	 //Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres") ;
	   Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
	 
	Statement st= connection.createStatement();
	//select email from login where username='ashok'
	ResultSet rs=st.executeQuery("select * from  app.warden ");
	while(rs.next())
	{
		User user = new User();
		System.out.println(rs.getString("warden_id"));
		user.setName(rs.getString("warden_name"));
		user.setVenue(rs.getString("floor"));
		user.setAlert_type(rs.getString("division"));
		user.setDescription(rs.getString("muster_location"));
		user.setEventId(rs.getLong("warden_id"));
		lu.add(user);
	}
	return lu;
	}
	
	
	
	
	public Integer  getEmployeeDetails() throws SQLException ,ClassNotFoundException
	{
	Class.forName("org.postgresql.Driver");
   List<User> lu=new ArrayList<User>();
	// Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres") ;
	   Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden","postgres", "vjedqz)D6kd") ;
	   
	Statement st= connection.createStatement();
	//select email from login where username='ashok'
	int rs=st.executeUpdate("COPY app.eventnotice FROM 'D:\\Employee\\eventnotice.csv' WITH CSV HEADER;");
	System.out.println(rs+"....executed number of records");
	return rs;
	}
	public String updateEventState(String[] eventId) throws SQLException, ClassNotFoundException
	{
	Class.forName("org.postgresql.Driver");
	String s="successfully updated";
	String query=null;
	Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
	Statement st= connection.createStatement();
	//select email from login where username='ashok'
	for(int i=0;i<eventId.length;i++)
	{
	 query="update  app.hqo_company_events set \"isApproved\"='yes' where event_id="+eventId[i];
	 st.addBatch(query);
	}
	st.executeBatch();
	connection.commit();
	
	return s;
	}
	public String deleteEvnets(String[] eventId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Class.forName("org.postgresql.Driver");
		String s="successfully updated";
		String query=null;
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
		Statement st= connection.createStatement();
		//select email from login where username='ashok'
		for(int i=0;i<eventId.length;i++)
		{
			 query="delete from  app.warden where warden_id='"+eventId[i]+"'";
		 st.addBatch(query);
		}
		st.executeBatch();
		connection.commit();
		return s;
	}

	public List<Map<String, Object>> getEmployeeDetailsInfo(String sqlQuery) throws SQLException, ClassNotFoundException {
		List<Map<String, Object>> resultMap = new ArrayList<>();
		ResultSet resultSet;
		Class.forName("org.postgresql.Driver");
		String s="successfully updated";
		String query=null;
	 Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.30.72:5432/postgres", "postgres", "postgres") ;
		Statement statement= connection.createStatement();
		resultSet = statement.executeQuery(sqlQuery);
		if (resultSet != null) {
			List<String> columnList = new ArrayList<>();
			ResultSetMetaData metaDataRS = resultSet.getMetaData();
			if (metaDataRS != null) {
				IntStream.range(1, metaDataRS.getColumnCount() + 1).forEach(i -> {
					try {
						columnList.add(metaDataRS.getColumnName(i));
					    } catch (SQLException e) {
						     e.printStackTrace();
					       }
				});
			 }
			final ResultSet resultSetTemp = resultSet;
			while (resultSet.next()) {
				Map<String, Object> record = new HashMap<>();
				columnList.forEach(column -> {
				try {
						resultSetTemp.getString(column);
						if(!resultSetTemp.wasNull()) {
						  if(column.contains("_")) {
							int r=column.indexOf("_");
							StringBuilder t=new StringBuilder(column);
							t.setCharAt(++r,Character.toUpperCase(column.charAt(r)));
							System.out.println(t);
							String a=t.toString();
							System.out.println("..."+a);
							a=a.replace("_","");
							record.put(a, resultSetTemp.getString(column));
					        }
						   else {
							     record.put(column, resultSetTemp.getString(column));
							     }
					      }
						}
						
					 catch (SQLException e) {
						e.printStackTrace();
					} 
				});
				resultMap.add(record);
			}
		}
		return resultMap;
}
	public String updateStatus(StatusVo statusvo) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("..statusvo  "+statusvo.getEmpUserid());
		ResultSet resultSet;
		System.out.println("++++++35555+++++++++++++++++");
		Class.forName("org.postgresql.Driver");
		String s="successfully updated";
		String userID=statusvo.getEmpUserid().toUpperCase();
		String query="update employee set is_checkin='"+statusvo.getCheckinStatus()+"' where emp_user_id='"+userID+"'";
		System.out.println("...QUERY     "+query);
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.30.72:5432/postgres", "postgres", "postgres") ;
		Statement statement= connection.createStatement();
		int reslt = statement.executeUpdate(query);
		return "successfully updated  ";
	}

	public List<Map<String, Object>> getEmployeeDetails(String query) throws SQLException {
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres") ;
		Statement statement= connection.createStatement();
		ResultSet reslt = statement.executeQuery(query);
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, String> getEmployeestatus(String query) throws SQLException {
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.30.72:5432/postgres", "postgres", "postgres") ;
		Statement statement= connection.createStatement();
		boolean reslt = statement.execute(query);
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void pudhEmployees() throws SQLException, ClassNotFoundException
	{
	Class.forName("org.postgresql.Driver");
	String s="successfully updated";
	String query=null;
	Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-cloudseer3", "postgres", "vjedqz)D6kd") ;
	Statement st= connection.createStatement();
	/*//select email from login where username='ashok'
	for(int i=0;i<eventId.length;i++)
	{
	 query="insert into app.event_notice values(?,?,?,?,?";
	 st.addBatch(query);
	}
	st.executeBatch();
	connection.commit();
	
	return s;*/
	
	ResultSet rs=st.executeQuery("slect * from app.employee_master");
	ResultSetMetaData metadata= rs.getMetaData();
	final int columnCount = metadata.getColumnCount();


	Object[] row = new Object[columnCount];
	for (int i = 1; i <= columnCount; ++i) {
	    row[i - 1] = rs.getString(i); // Or even rs.getObject()
	    System.out.println(rs.getString(i));
	}
	
	
	
	
	}
	
	
	}

	
	
