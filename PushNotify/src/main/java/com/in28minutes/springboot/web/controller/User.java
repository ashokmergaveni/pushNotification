package com.in28minutes.springboot.web.controller;

public class User {
String name;
String venue;
String alert_type;
String description;
Long eventId;
public Long getEventId() {
	return eventId;
}
public void setEventId(Long eventId) {
	this.eventId = eventId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getVenue() {
	return venue;
}
public void setVenue(String venue) {
	this.venue = venue;
}
public String getAlert_type() {
	return alert_type;
}
public void setAlert_type(String alert_type) {
	this.alert_type = alert_type;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
