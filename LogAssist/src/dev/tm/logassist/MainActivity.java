package dev.tm.logassist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.joda.time.DateTime;

import entity.DayRoute;
import entity.LogRecord;
import logbook.LogBookCore;
import logbook.LogGenerator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	
	
	//LogBook Variables
	String homeAddress;
	ArrayList<String> homeList;
	
	String workAddress;
	ArrayList<String> workList;
	ArrayList<String> clientList;
	
	boolean StartAtAny;
	boolean WorkOnWeekends;
	
	String startDate,endDate;
	int startODO,endODO;
	float percentClaim;
	
	ArrayList<LogRecord> logBookHandle;
	LogBookList listFrag;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getSupportActionBar();
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		
		System.out.println("Load Complete");
//		
//		// Set up the action bar.
//
////		//actionBar.hide();
//		
//		
		setContentView(R.layout.activity_main);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(6);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		
		
		
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		System.out.println(" CURRENT TAB " + tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "Home Address";
			case 1:
				return "Work Address";
			case 2:
				return "Client Address";
			case 3:
				return "Fine Tune";
			case 4:
				return "Some Extras";
			case 5:
				return "Generate!";
			
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
			int frameID = 0;
			System.out.println(" Section Number : " + sectionNumber);
			switch(sectionNumber)
			{
			case 1:
				frameID = R.layout.home_address;break;
			case 2:
				frameID = R.layout.work_address;break;
			case 3:
				frameID = R.layout.client_address;break;
			case 4:
				frameID = R.layout.fine_tune;break;
			case 5:
				frameID = R.layout.final_preview;break;
			case 6:
				frameID = R.layout.generate_log;break;
			}
			
			System.out.println(" FRAME : " + frameID);
			
			View rootView = inflater.inflate(frameID, container,
					false);
//			if(frameID == R.layout.generate_log)
//			{
//				Fragment fr = getFragmentManager().findFragmentById(R.id.fragment1);
//				getFragmentManager().beginTransaction().hide(fr).commit();
//			}
			
//			TextView textView = (TextView) rootView
//					.findViewById(R.id.section_label);
//			textView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	
	public void showDatePickerDialog(View v) {
		System.out.println(" CALLED BY : " + v.getId());
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), Integer.toString(v.getId()));
	}
	
	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current date as the default date in the picker
	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);

	// Create a new instance of DatePickerDialog and return it
	return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
	// Do something with the date chosen by the user
		System.out.println(" Set for " + year + ":" + month + ":" + day + ":" + this.getId() + ":" + this.getTag());
		int srcID = Integer.parseInt(this.getTag());
		Button sourceButton = (Button) this.getActivity().findViewById(srcID);
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		
		
		sourceButton.setText(day + "-" + c.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.ENGLISH) + "-" + year);
		
	}
	}
	
	
	
	
	
	
	//LogGeneration - Input Validation - Log Export
	
	public void buildAList(int id,String type)
	{
		String temp = ((TextView)this.findViewById(id)).getText().toString(); 
		if(!temp.isEmpty())
		{
			switch(type)
			{
			case "Home"   : homeList.add(temp); 
						 break;
						 
			case "Work"   : workList.add(temp);
						 break;
			
			case "Client" : clientList.add(temp);
						 break;
			
			}
		}
		

	}
	
	
	public boolean validateInput()
	{
		
//		System.out.println(" FRAG COUNT : " + this.getSupportFragmentManager().getFragments().size());
////		View myInflatedView = this.getLayoutInflater().inflate(R.layout.home_address,,false);
////
////	    // Set the Text to try this out
////	    TextView t = (TextView) myInflatedView.findViewById(R.id.homeAddress);
////	    t.setText("Text to Display");
//		
//		ViewGroup testParent = (ViewGroup)this.findViewById(R.id.pager);
//		if(testParent != null)
//		{
//			System.out.println("VIEW TES " + testParent.getId());
//		}
//		
//		View testChild = this.findViewById(R.id.homeAddress);
//		if(testChild != null)
//		{
//			System.out.println("VIEW HOmE TES " + testChild.getId());
//		}
//		
//		
//		for(Fragment fr : this.getSupportFragmentManager().getFragments())
//		{
//			System.out.println(" Frame : " + fr.getId() + ":");
//			
//			View v = fr.getView();
//			
//			if( v == null)
//			{
//				System.out.println(" Empty View");
//			}
//			else
//			{
//				System.out.println(" Got Something..." + ((ViewGroup)v).getChildCount());
//				for(int i=0;i<((ViewGroup)v).getChildCount();i++)
//				{
//					ViewGroup ch = (ViewGroup) ((ViewGroup)v).getChildAt(i);
//					System.out.println(" Child : " + ch.getChildCount()+ " :  ");
//					for(int j=0;j<ch.getChildCount();j++)
//					{
//						View chInner =  ch.getChildAt(j);
//						System.out.println(" Child : " + chInner.getId() + " : " + R.id.percentClaim + " :  ");
//						
//					}
//					
//				}
//			}
//			
//		}
//		
//		//System.out.println(" Frame View " + this.getSupportFragmentManager().findFragmentById(R.layout.home_address).getView().findViewById(R.id.homeAddress).getId());
		homeAddress = ((TextView)this.findViewById(R.id.homeAddress)).getText().toString();
		workAddress = ((TextView)this.findViewById(R.id.workAddress)).getText().toString();
		
		homeList = new ArrayList<String>();
		workList = new ArrayList<String>();
		clientList = new ArrayList<String>();
		//Additional HAs
		this.buildAList(R.id.la1,"Home");
		this.buildAList(R.id.la2,"Home");
		this.buildAList(R.id.la3,"Home");
		this.buildAList(R.id.la4,"Home");
		this.buildAList(R.id.la5,"Home");

		
		//Additional WAs
		this.buildAList(R.id.wa1,"Work");
		this.buildAList(R.id.wa2,"Work");
		this.buildAList(R.id.wa3,"Work");
		this.buildAList(R.id.wa4,"Work");
		this.buildAList(R.id.wa5,"Work");

		
		//Client Addresses
		this.buildAList(R.id.ca1,"Client");
		this.buildAList(R.id.ca2,"Client");
		this.buildAList(R.id.ca3,"Client");
		this.buildAList(R.id.ca4,"Client");
		this.buildAList(R.id.ca5,"Client");

		
		startDate = ((Button)this.findViewById(R.id.FromDate)).getText().toString();
		endDate = ((Button)this.findViewById(R.id.ToDate)).getText().toString();
		
		TextView startODOC = (TextView)this.findViewById(R.id.ODOStart);
		System.out.println("Start Value Check : "+ startODOC.getText());
		
		startODOC = (TextView)this.findViewById(R.id.endODO);
		System.out.println("End Value Check : "+ startODOC.getText());
		
		startODOC = (TextView)this.findViewById(R.id.claimPercent);
		System.out.println(" PC :  " + startODOC.getText());
		
		startODO = Integer.parseInt(((TextView)this.findViewById(R.id.ODOStart)).getText().toString());
		endODO = Integer.parseInt(((TextView)this.findViewById(R.id.endODO)).getText().toString());
		
		StartAtAny = ((CheckBox)this.findViewById(R.id.StartAtAny)).isChecked();
		WorkOnWeekends = ((CheckBox)this.findViewById(R.id.workOnWeekend)).isChecked();
		
		percentClaim = Float.parseFloat(((TextView)this.findViewById(R.id.claimPercent)).getText().toString());
		
		System.out.println("Value Check");
		
		return true;
	}
	
	
	public void GenerateLogBook(View v) {
		System.out.println(" Generate CALLED BY : " + v.getId() + ":" + v.getParent().toString());
		
		validateInput();
		listFrag = new LogBookList();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, listFrag).commit();
		getSupportFragmentManager().beginTransaction().hide(listFrag).commit();
		//listFrag.setListShown(false);
    	
    	
		
		LogAssistCoreTask runTask = new LogAssistCoreTask();
		runTask.execute();
	    
	}
	
	public void ExportLogBook(View v) {
		System.out.println(" Export CALLED BY : " + v.getId());
		
	}
	
	
	
	private class LogAssistCoreTask extends AsyncTask<Void, Void, ArrayList<LogRecord>> {
	    @Override
	    protected ArrayList<LogRecord> doInBackground(Void... nu) {
	    	ArrayList<LogRecord> lg = LogBookCore.GetLogBook(homeAddress,homeList,
					workAddress,workList,
	                clientList, 
	                StartAtAny,
	                WorkOnWeekends,
	                startDate, endDate,
	                startODO,endODO,
	                percentClaim,
	                3,
	                "DISTHOPS");
	    	
	    	return lg;
	    }

	    @Override
	    protected void onPostExecute(ArrayList<LogRecord> lg) {
	      //textView.setText(result);
	    	logBookHandle = lg;
	    	System.out.println(" RESULTS : " + logBookHandle.size());
	    	listFrag.ChangeList(logBookHandle);
	    	//listFrag.setListShown(true);
	    	getSupportFragmentManager().beginTransaction().show(listFrag).commit();
	    	//adapter.changeData(logBookHandle.GetRecords());
	    	
	    }
	  }
	
	
	
	
	
	

	
	

}
