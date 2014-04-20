package dev.tm.logassistdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LogAssistDB extends SQLiteOpenHelper {


  private static final String DATABASE_NAME = "logassist.db";
  private static final int DATABASE_VERSION = 1;
  public static final String Sessions_Table = "Session";
  public static final String SessionStore_Table = "SessionStore";
  public static final String LogBook_Table = "LogBook";
  // Database creation sql statement
  
  // Table Sessions - (SessionID int, SessionDate date, SessionTag varchar)
  private static final String Session_CREATE = "create table "
	      + " Session ( SessionID integer primary key autoincrement, " 
	      			+ "  SessionDate date not null,"
	      			+ "  SessionTag text null);";
  
  // Table SessionValues - (SessionID int, ElementTag varchar, ElementValue varchar)
  private static final String SessionStore_CREATE = "create table "
	      + " SessionStore ( StoreID integer primary key autoincrement, "
		  			+ "  SessionID integer not null"
	      			+ "  ElementTag text not null,"
	      			+ "  ElementValue text not null,"
	      			+ "  FOREIGN KEY(SessionID) REFERENCES Sessions(SessionID) ON DELETE CASCADE);";
  
  //// Enable foreign key constraints
  /*if (!db.isReadOnly()) {
  	db.execSQL("PRAGMA foreign_keys = ON;");
  }
  return this;
  */
  
  // Table LogBook - Date date, Trip varchar, Total Kms, Total Deductable, Classifier 
  private static final String LogBook_CREATE = "create table "
	      + " LogBook (  LogID integer primary key autoincrement, "
		  			+ "  SessionID integer not null"
	      			+ "  LogDate date not null,"
	      			+ "  LogTrip text null,"
	      			+ "  LogTotalKMs float not null,"
	      			+ "  LogTotalDeductable float not null,"
	      			+ "  LogPersonalTravel float null,"
	      			+ "  FOREIGN KEY(SessionID) REFERENCES Session(SessionID) ON DELETE CASCADE);";
  
  
  
  
  
  public LogAssistDB(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(Session_CREATE);
    database.execSQL(SessionStore_CREATE);
    database.execSQL(LogBook_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(LogAssistDB.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS Session");
    db.execSQL("DROP TABLE IF EXISTS SessionStore");
    db.execSQL("DROP TABLE IF EXISTS LogBook");
    onCreate(db);
  }

} 
