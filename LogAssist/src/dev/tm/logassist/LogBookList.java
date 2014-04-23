package dev.tm.logassist;

import java.util.ArrayList;

import org.joda.time.DateTime;

import entity.DayRoute;
import entity.LogRecord;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class LogBookList extends ListFragment {
	
	ArrayList<LogRecord> dataList;
	StableArrayAdapter listAdapter;
	
	public void LogBookList()
	{
		dataList = new ArrayList<LogRecord>();
		//dataList.add(new LogRecord(new DayRoute(),DateTime.now()));
	}
	
	public void SetLogBookList(ArrayList<LogRecord> dataSet)
	{
		dataList = dataSet;
	}
	
	public void ChangeList(ArrayList<LogRecord> dataSet)
	{
		listAdapter.changeData(dataSet);
		
		
	}
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  listAdapter = new StableArrayAdapter(this.getActivity(),dataList); 
	  setListAdapter(listAdapter);
	  
	 }
	  
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  return inflater.inflate(R.layout.list_holder, container, false);
	 }
	 
	 
	 private class StableArrayAdapter extends ArrayAdapter<LogRecord>  {
			private ArrayList<LogRecord> dataList;
			private final Context context;

		    public StableArrayAdapter(Context context,ArrayList<LogRecord> objects) {
		    	super(context, R.layout.log_view, objects);
		        this.context = context;
		    	dataList = objects;
		    }

		    public void changeData(ArrayList<LogRecord> data) {
	            dataList = data;
	            notifyDataSetChanged();
	        }

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		      System.out.println(" ROW VIEW ");
		      LayoutInflater inflater = (LayoutInflater) context
		    	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	    View rowView = inflater.inflate(R.layout.log_view, parent, false);
		      TextView tripDesc = (TextView) rowView.findViewById(R.id.TripDesc);
		      TextView tripTotal = (TextView) rowView.findViewById(R.id.TotalDist);
		      TextView tripClaim = (TextView) rowView.findViewById(R.id.DeductableDist);
		      TextView tripDate = (TextView) rowView.findViewById(R.id.TripDate);
		      TextView personal = (TextView) rowView.findViewById(R.id.PersonalTravel);
		      TextView tripType = (TextView) rowView.findViewById(R.id.TripType);
		      
			      tripDesc.setText(dataList.get(position).GetTrip());
			      tripTotal.setText(String.valueOf(Math.round(dataList.get(position).GetDistance())));
			      tripClaim.setText(String.valueOf(Math.round(dataList.get(position).GetDeductableDistance())));
			      tripDate.setText(dataList.get(position).GetDate().toString("dd-MMM-yyyy"));
			      personal.setText(String.valueOf(Math.round(dataList.get(position).GetPersonalTravel())));
			     
			      
			      if(Math.round(dataList.get(position).GetDeductableDistance()) > 0)
			      {
			    	  tripType.setText("Work Trip");
			      }
			      else
			      {
			    	  tripType.setText("Personal Trip");
			      }
			      
		      return rowView;
		    }
		    
		    @Override
	        public int getCount() {
		    	if(dataList!=null)
		    	{
		    		return dataList.size();
		    	}
		    	else
		    	{
		    		return 0;
		    	}
	        }
		    
		    @Override
	        public LogRecord getItem(int position) {
	            return dataList.get(position);
	        }

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

		  }
	 
}
