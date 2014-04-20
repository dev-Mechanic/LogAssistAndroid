package dev.tm.logassistdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import logbook.LogBook;
import dev.tm.logassistcore.LogAssistSession;
import entity.LogRecord;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LogAssistDbHandler {

  // Database fields
  private SQLiteDatabase database;
  private LogAssistDB dbHelper;
  

  public LogAssistDbHandler(Context context) {
    dbHelper = new LogAssistDB(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    database.execSQL("PRAGMA foreign_keys = ON;");
    
  }

  public void close() {
    dbHelper.close();
  }

  
  public long createSession()
  {
	  ContentValues values = new ContentValues();
	  values.put("SessionTag", "LogBook");
	  Calendar dateHolder = Calendar.getInstance();
	  values.put("SessionDate", dateHolder.get(Calendar.DAY_OF_MONTH) + "-" 
			  					+ dateHolder.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.ENGLISH) + "-" 
			  					+ dateHolder.get(Calendar.YEAR));
	  long insertId = database.insert(LogAssistDB.Sessions_Table, null, values);
	  return insertId;
  }
  
  public void createSessionStore(String tagName,String tagValue,int SessionID)
  {
	  ContentValues values = new ContentValues();
	  values.put("SessionID", SessionID);
	  values.put("ElementTag", tagName);
	  values.put("ElementValue", tagValue);
	  
	  database.insert(LogAssistDB.SessionStore_Table, null, values);
  }
  
  
  public void storeLogBook(int SessionID,LogBook lb)
  {
	  ArrayList<LogRecord> lrs = lb.GetBook();
	  ContentValues value = new ContentValues();
	  for(LogRecord lr : lrs)
	  {
		  value.clear();
		  value.put("SessionID", SessionID);
		  value.put("LogDate", lr.GetDate().toString());
		  value.put("LogTrip", lr.GetTrip());
		  value.put("LogTotalKMs", lr.GetDistance());
		  value.put("LogTotalDeductable", lr.GetDeductableDistance());
		  value.put("LogPersonalTravel", lr.GetPersonalTravel());
		  
		  database.insert(LogAssistDB.LogBook_Table, null, value);
		 
	  }
	  
	  
  }
  
//  public String createComment(String comment) {
//    ContentValues values = new ContentValues();
////    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
////    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
////        values);
////    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
////        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
////        null, null, null);
////    cursor.moveToFirst();
////    Comment newComment = cursorToComment(cursor);
////    cursor.close();
////    return newComment;
//    
//    return null;
//  }

//  public void deleteComment(Comment comment) {
//    long id = comment.getId();
//    System.out.println("Comment deleted with id: " + id);
//    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//        + " = " + id, null);
//  }

//  public List<Comment> getAllComments() {
//    List<Comment> comments = new ArrayList<Comment>();
//
//    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
//        allColumns, null, null, null, null, null);
//
//    cursor.moveToFirst();
//    while (!cursor.isAfterLast()) {
//      Comment comment = cursorToComment(cursor);
//      comments.add(comment);
//      cursor.moveToNext();
//    }
//    // make sure to close the cursor
//    cursor.close();
//    return comments;
//  }

//  private Comment cursorToComment(Cursor cursor) {
//    Comment comment = new Comment();
//    comment.setId(cursor.getLong(0));
//    comment.setComment(cursor.getString(1));
//    return comment;
//  }
} 
