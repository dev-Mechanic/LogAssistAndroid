package dev.tm.logassistcore;

import java.util.ArrayList;
import java.util.Calendar;

public class LogAssistSession {

	public int SessionID;
	public Calendar SessionDate;
	public String SessionTag;
	
	public String homeAddress;
	public ArrayList<String> homeList;
	
	public String workAddress;
	public ArrayList<String> workList;
	
	public ArrayList<String> clientList;
	public boolean StartAtAnyWork;
	
	public boolean TravelOnWeekends;
	public Calendar Startdate;
	public Calendar EndDate;
	public int startODO;
	public int endODO;
	
}
