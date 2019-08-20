package com.in28minutes.springboot.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.in28minutes.springboot.web.controller.CamdenDb;
import com.in28minutes.springboot.web.controller.User;
import com.in28minutes.springboot.web.model.Todo;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    
    private static CamdenDb camdenDb=new CamdenDb();
    private static int todoCount = 3;

    public List<User> retrieveTodos(String user) throws ClassNotFoundException, SQLException {
    	
    	List<User> data=camdenDb.getUserDetails();
    	
        /*List<Todo> filteredTodos = new ArrayList<Todo>();
        for (Todo todo : todos) {
         //   if (todo.getUser().equalsIgnoreCase(user)) {
                filteredTodos.add(todo);
           // }
        }*/
        return data;
    }
    
    public Todo retrieveTodo(int id) {
        for (Todo todo : todos) {
            if (todo.getId()==id) {
                return todo;
            }
        }
        return null;
    }

    public void updateTodo(String[] eventId) throws ClassNotFoundException, SQLException{
    	try {
    CamdenDb camdenDb=new CamdenDb();
    camdenDb.updateEventState(eventId);
    	}catch(Exception e){
    		System.out.println("db exceptiion");
    	}
    	
    }

   /* public void addTodo(String name, String desc, Date targetDate,
            boolean isDone) {
        todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
    }*/

    public void deleteTodo(int id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }

	public void deleteEvents(String[] eventId) {
		// TODO Auto-generated method stub
		try {
		    CamdenDb camdenDb=new CamdenDb();
		    camdenDb.deleteEvnets(eventId);
		    	}catch(Exception e){
		    		System.out.println("db exceptiion");
		    	}
		    	
		    }

	public User getWardenInfo(int eventId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		System.out.println("...........eventId.........."+eventId+"..........");
		Class.forName("org.postgresql.Driver");
		String s="successfully updated";
		String query="select * from app.warden where warden_id='"+eventId+"'";
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
		Statement st= connection.createStatement();
		ResultSet rs=st.executeQuery(query);
		User user = new User();
		while(rs.next()){
		System.out.println(rs.getString("warden_id"));
		user.setName(rs.getString("warden_name"));
		user.setVenue(rs.getString("floor"));
		user.setAlert_type(rs.getString("division"));
		user.setDescription(rs.getString("muster_location"));
		user.setEventId(rs.getLong("warden_id"));
		}
		return user;
	}

	public void updateWarden(User user) throws Exception {

		Class.forName("org.postgresql.Driver");
		String query=" update app.warden set warden_name='"+user.getName()+"',floor='"+user.getVenue()+"',division='"+user.getAlert_type()+"',muster_location='"+user.getDescription()+"'where warden_id='"+user.getEventId()+"'";
		
		System.out.println( ".....update warden query "+query);
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
		Statement st= connection.createStatement();
		boolean rs=st.execute(query);
		
		
	}
	
	
	
	
	
	
	public void insertWarden(User user) throws Exception {

		Class.forName("org.postgresql.Driver");
		String query="inseri into app.warden values('"+user.getEventId()+"','"+user.getName()+"','"+user.getVenue()+"','"+user.getAlert_type()+"','"+user.getDescription()+"')";
		
		System.out.println( ".....update warden query "+query);
		 Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
		Statement st= connection.createStatement();
		boolean rs=st.execute(query);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}