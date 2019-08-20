package com.in28minutes.springboot.web.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.service.TodoService;


@Controller
public class TodoController {
	
    private static CamdenDb camdenDb=new CamdenDb();

	@Autowired
	private HttpServletRequest request;
	@Autowired
	TodoService service;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping(value = "/warden-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) throws ClassNotFoundException, SQLException {
		//String name = getLoggedInUserName(model);
		model.put("todos", service.retrieveTodos(""));
		return null;
	}

	@RequestMapping(value = "/warden-update", method = RequestMethod.POST)
	public String showTodos(HttpServletRequest req) throws Exception {
		//String name = getLoggedInUserName(model);
		User user=new User();
		user.setName(req.getParameter("name"));
		user.setVenue(req.getParameter("venue"));
		user.setAlert_type(req.getParameter("alert_type"));
		user.setDescription(req.getParameter("description"));
		user.setEventId(Long.valueOf(req.getParameter("eventId")));
		service.updateWarden(user);
		
		return "redirect:/warden-todos";
	}
	
	@RequestMapping(value = "/warden-insert", method = RequestMethod.POST)
	public String insertWarden(HttpServletRequest req) throws Exception {
		//String name = getLoggedInUserName(model);
		User user=new User();
		user.setName(req.getParameter("name"));
		user.setVenue(req.getParameter("venue"));
		user.setAlert_type(req.getParameter("alert_type"));
		user.setDescription(req.getParameter("description"));
		user.setEventId(Long.valueOf(req.getParameter("eventId")));
		service.insertWarden(user);
		
		return "redirect:/warden-todos";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}

	@RequestMapping(value = "/add-warden", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model,@RequestParam  int eventId ) throws ClassNotFoundException, SQLException {
		
		model.put("todo",service.getWardenInfo(eventId));
		
		/*User t1=new User();
		t1.setAlert_type("2");
		t1.setVenue("1stFloor");
		t1.setName("Ashok");
		t1.setDescription("Muste location");
		
		model.addAttribute("todo",t1 );*/
		return "todo";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {

		if(id==1)
			throw new RuntimeException("Something went wrong");
		
		service.deleteTodo(id);
		return "redirect:/warden-todos";
	}

	/*@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}*/

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String updateTodo(/*@RequestParam int eventId*/) throws ClassNotFoundException, SQLException {
		
		System.out.println("  submitt  ...action.."+request.getParameter("submit"));
		
    Integer eventIds[] = {}; 
    if(request.getParameter("submit").equals("Delete"))
    {
    	String[] eventId=request.getParameterValues("eventId");
	service.deleteEvents(eventId);
    }
    if(request.getParameter("submit").equals("Add"))
    {
    	ModelMap model=new ModelMap();
		model.put("todos",service.getWardenInfo(3));

		
		/*User t1=new User();
		t1.setAlert_type("2");
		t1.setVenue("1stFloor");
		t1.setName("Ashok");
		t1.setDescription("Muste location");
		
		model.addAttribute("todo",t1 );*/
		return "todo";
    }
	return null;
    
		//return "redirect:/warden-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	@ResponseBody
	public List<User> addTodo(ModelMap model, @Valid Todo todo, BindingResult result,@RequestParam int req1) throws ClassNotFoundException, SQLException {

		if (result.hasErrors()) {
			return null;
		}
		List<User> user=service.retrieveTodos("");

		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		return user;
	}
	@RequestMapping(value = "/pushEmployees", method = RequestMethod.GET)
	@ResponseBody
	public Integer moveEmployee() throws ClassNotFoundException, SQLException {
		Integer user=camdenDb.getEmployeeDetails();
		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		return user;
	}
	@RequestMapping(value = "/getEmployeesList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getEmployeesList(@RequestParam String wardenId) throws ClassNotFoundException, SQLException {
		//List<Map<String, Object>> user=camdenDb.getEmployeeDetailsInfo("select * from \"Employee\"");
		List<Map<String, Object>> user=camdenDb.getEmployeeDetailsInfo("SELECT  e.emp_user_id ,e.emp_phone,e.is_checkin as checkin_Status ,e.emp_id,e.division ,e.floor, e.emp_name " + 
				"    FROM employee  b" + 
				"    JOIN employee e ON b.emp_user_id = e.warden_id where b.emp_user_id='"+wardenId.toUpperCase()+"'");
		
		
		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		return user;
	}
	@RequestMapping(value = "/updateCheckinStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setStatus(@RequestBody StatusVo statusvo) throws Exception    {
		String user=camdenDb.updateStatus(statusvo);
		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		Map<String, String> result=new HashMap<String, String> ();
		result.put("status_code", "200");
		result.put("status_message","successfully updated");
		if(statusvo.getCheckinStatus()==null || statusvo.getEmpUserid()==null)
		{
			/*result.put("status_code","401");
			result.put("status_message", "checkinStatus/userId manadatory field");*/
			throw new ServiceException("userid/checkinstatus manadatory field","validation","500");
		}
		return result;
	}
	@RequestMapping(value = "/getCheckinStatus", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllEmployees(@RequestParam String empUserid) throws ClassNotFoundException, SQLException {
		//List<Map<String, Object>> user=camdenDb.getEmployeeDetailsInfo("select * from \"Employee\"");
		
		List<Map<String, Object>> user=camdenDb.getEmployeeDetailsInfo("SELECT  emp_name,is_checkin,warden_id from employee where emp_user_id='"+empUserid.toUpperCase()+"'");
		
		Map<String, Object> result=new HashMap<String, Object> ();
		Map<String, Object> mu=user.get(0);
		System.out.println(mu.size()+"...........wardenId sze");
		if(mu.size()!=0)
		{
		String na= (String) mu.get("wardenId");
			result.put("isWarden", "Y");
		}
		
		/*mu.forEach((k,v)->{
			if(v.equals("NA")){
				result.put("isWarden", "YES");
			}else {
				result.put("isWarden", "N");
			}
		
		String checkin=(String) mu.get("isCheckin");
		result.put("isCheckin", checkin);
		System.out.println(checkin +"..............");*/
		              /*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		return result;
	}
	
	
	
	@RequestMapping(value = "/mapEmpWarden", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mapWarden(@RequestBody StatusVo statusvo) throws Exception    {
		String user=camdenDb.updateStatus(statusvo);
		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
				false);*/
		Map<String, String> result=new HashMap<String, String> ();
		result.put("status_code", "200");
		result.put("status_message","successfully updated");
		if(statusvo.getCheckinStatus()==null || statusvo.getEmpUserid()==null)
		{
			/*result.put("status_code","401");
			result.put("status_message", "checkinStatus/userId manadatory field");*/
			throw new ServiceException("userid/checkinstatus manadatory field","validation","500");
		}
		return result;
	}
	
	@RequestMapping(value = "/pushMatserEmployees", method = RequestMethod.GET)
	@ResponseBody
	public void pudhEmployees() throws SQLException, ClassNotFoundException
	{
		List<EmployeeData> empList=new ArrayList<EmployeeData>();
		
	Class.forName("org.postgresql.Driver");
	String s="successfully updated";
	String query=null;
	Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-cloudseer3", "postgres", "vjedqz)D6kd") ;
	Statement st= connection.createStatement();
	/*//select email from login where username='ashok'
	for(int i=0;i<eventId.length;i++)
	{
	 query="update  app.hqo_company_events set \"isApproved\"='yes' where event_id="+eventId[i];
	 st.addBatch(query);
	}
	st.executeBatch();
	connection.commit();
	
	return s;*/
	
	ResultSet rs=st.executeQuery("select * from app.employee_master");
	ResultSetMetaData metadata= rs.getMetaData();
	final int columnCount = metadata.getColumnCount();
	while(rs.next()){
	
	String full_name=rs.getString("full_name");
	String personal_number=rs.getString("personnel_number");
	
	EmployeeData e=new EmployeeData();
	e.setEmailId(rs.getString("email_id"));
	e.setFullName(rs.getString("full_name"));
	e.setLocationId(rs.getString("location_id"));
	e.setPersonalNumber(rs.getString("personnel_number"));
	e.setUserId(rs.getString("user_id"));
	e.setUserId(rs.getString("business_cell"));
	
	empList.add(e);
	System.out.println(full_name);
	System.out.println(personal_number);
	System.out.println(empList.size()+".... number");
}
	/*
	Object[] row = new Object[columnCount];
	for (int i = 1; i <= columnCount; ++i) {
	    row[i - 1] = rs.getString(i); // Or even rs.getObject()
	  //  System.out.println(rs.getString(i));
	}*/
	Class.forName("org.postgresql.Driver");
	Connection connection2 = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
	connection2.setAutoCommit(false);
	Statement statement2= connection2.createStatement();
	//select email from login where username='ashok'
	for(int i=0;i<empList.size();i++)
	{
		StringBuilder sqlQuery =new StringBuilder("insert into  app.event_notice values(");
		sqlQuery.append(0);
		sqlQuery.append(",'"+empList.get(i).getPersonalNumber()+"'");
		sqlQuery.append(",'"+empList.get(i).getFullName()+"'");
		sqlQuery.append(",'"+empList.get(i).getLocationId()+"'");
		sqlQuery.append(",''");
		sqlQuery.append(",'"+empList.get(i).getEmailId()+"'");
		sqlQuery.append(",''");
		sqlQuery.append(",''");
		sqlQuery.append(",''");
		sqlQuery.append(",'"+empList.get(i).getUserId()+"')");
		try{
		statement2.addBatch(sqlQuery.toString());
		}
		catch(Exception excep){
			System.out.println(excep);  
			}
		System.out.println(sqlQuery+".............sql query");
	}
	statement2.executeBatch();
	connection2.commit();
  }
	
	
	@RequestMapping(value = "/pushEvents", method = RequestMethod.GET)
	@ResponseBody
	public void pushEvents() throws SQLException, ClassNotFoundException
	{
		List<EventsData> empList=new ArrayList<EventsData>();
		
	Class.forName("org.postgresql.Driver");
	String s="successfully updated";
	String query=null;
	Connection connection = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden", "postgres", "vjedqz)D6kd") ;
	Statement st= connection.createStatement();
	/*//select email from login where username='ashok'
	for(int i=0;i<eventId.length;i++)
	{
	 query="update  app.hqo_company_events set \"isApproved\"='yes' where event_id="+eventId[i];
	 st.addBatch(query);
	}
	st.executeBatch();
	connection.commit();
	
	return s;*/
	
	
	ResultSet rs=st.executeQuery("select * from app.hqo_company_events");
	ResultSetMetaData metadata= rs.getMetaData();
	final int columnCount = metadata.getColumnCount();
	while(rs.next()){
	
	System.out.println(rs.getString("name")+"  ................name......");
	EventsData ed=new EventsData();
		
	ed.setName(rs.getString("name"));
	ed.setAlertType(rs.getString("alert_type"));
	
	ed.setVenue(rs.getString("venue"));
	ed.setDescription(rs.getString("description"));
	ed.setStartDate(rs.getString("start_date"));
	ed.setStartTime(rs.getString("start_time"));
	ed.setEndDate(rs.getString("end_date"));
	ed.setEndTime(rs.getString("end_time"));
	ed.setEmployeeId(rs.getString("employee_id"));
	ed.setIsApproved(rs.getString("isApproved"));
	ed.setEmail(rs.getString("email"));
	ed.setEmployeeName(rs.getString("employee_name"));
	System.out.println("..rs.getString(\"employee_name\")..."+rs.getString("employee_name"));
	ed.setEventId(rs.getString("event_id"));

	/*EmployeeData e=new EmployeeData();
	e.setEmailId(rs.getString("email_id"));
	e.setFullName(rs.getString("full_name"));
	e.setLocationId(rs.getString("location_id"));
	e.setPersonalNumber(rs.getString("personnel_number"));
	e.setUserId(rs.getString("user_id"));
	e.setUserId(rs.getString("business_cell"));*/
	
	empList.add(ed);
}
	/*
	Object[] row = new Object[columnCount];
	for (int i = 1; i <= columnCount; ++i) {
	    row[i - 1] = rs.getString(i); // Or even rs.getObject()
	  //  System.out.println(rs.getString(i));
	}*/
	Class.forName("org.postgresql.Driver");
	Connection connection2 = DriverManager.getConnection("jdbc:postgresql://awsnlcld001.amwaternp.net:5432/ao-aw-camden-evacuation", "postgres", "vjedqz)D6kd") ;
	connection2.setAutoCommit(false);
	Statement statement2= connection2.createStatement();
	//select email from login where username='ashok'
	for(int i=0;i<empList.size();i++)
	{
		String empName=empList.get(i).getEmployeeName();
		String empMail=empList.get(i).getEmail();
		if(empMail.contains("'"))
		{
			empMail=empMail.replace("'","");
		}
		
		StringBuilder sqlQuery =new StringBuilder("insert into  hqo.hqo_company_events values(");
		sqlQuery.append("'"+empList.get(i).getName()+"'");
		sqlQuery.append(",'"+empList.get(i).getAlertType()+"'");
		sqlQuery.append(",'"+empList.get(i).getVenue()+"'");
		sqlQuery.append(",'"+empList.get(i).getDescription()+"'");
		sqlQuery.append(",'"+empList.get(i).getStartDate()+"'");
		sqlQuery.append(",'"+empList.get(i).getEndDate()+"'");
		sqlQuery.append(",'"+empList.get(i).getStartTime()+"'");
		sqlQuery.append(",'"+empList.get(i).getEndTime()+"'");
		sqlQuery.append(",'"+empList.get(i).getEmployeeId()+"'");
		sqlQuery.append(",'"+empList.get(i).getIsApproved()+"'");
		sqlQuery.append(",'"+empMail+"'");
		sqlQuery.append(",'"+empName+"'");
		sqlQuery.append(",'"+Integer.parseInt(empList.get(i).getEventId())+"')");
		
		System.out.println("...SQL QUERY "+sqlQuery);
		try{
		statement2.addBatch(sqlQuery.toString());
		}
		catch(Exception excep){
			System.out.println(excep);  
			}
		System.out.println(sqlQuery+".............sql query");
	}
	statement2.executeBatch();
	connection2.commit();
  }
	
	
	

	@PostMapping(value = "/camden-hqo/notify")
	@ResponseBody
	public String sendNotification(@RequestBody PushMessageVo vo) throws ClassNotFoundException, SQLException, FirebaseMessagingException {
		String topic = "highScores";
		// See documentation on defining a message payload.
	/*	Message message = Message.builder()
		    .putData("alert", "Hey Krishna evacuate building")
		    .putData("badge", "9")
		    .putData("sound","default")
		    .setTopic("evacuation")
		    .build();
		// Send a message to the devices subscribed to the provided topic.
		String response = FirebaseMessaging.getInstance().send(message);*/
		/*
		Message message = Message.builder().setApnsConfig(ApnsConfig.builder()
	            .setAps(Aps.builder()
	                .setBadge(42).setAlert(vo.getMessage()).setSound("default")
	                .build())
	            .build())
	        .setTopic("evacuation")
	        .build();
		
		String response = FirebaseMessaging.getInstance().send(message);
		*/
		
		Message message = Message.builder()
			    .setNotification(new Notification(
			        "alert","sravanthi evacuate"
			        ))
			    .setAndroidConfig(AndroidConfig.builder()
			        .setTtl(3600 * 1000)
			        .setNotification(AndroidNotification.builder()
			            .setIcon("stock_ticker_update")
			            .setColor("#f45342")
			            .build())
			        .build())
			    .setApnsConfig(ApnsConfig.builder()
			        .setAps(Aps.builder()
			            .setBadge(42).setAlert("hey krishna evacuate").setSound("deafult")
			            .build())
			        .build())
			    .setTopic("evacuation")
			    .build();
		String response = FirebaseMessaging.getInstance().send(message);
		// Response is a message ID string.
	//	System.out.println("Successfully sent message: " + response);
		return "successfully";
	}
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
