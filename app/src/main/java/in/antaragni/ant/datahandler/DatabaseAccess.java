package in.antaragni.ant.datahandler;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import in.antaragni.ant.datamodels.Contact;
import in.antaragni.ant.datamodels.Event;
import in.antaragni.ant.datamodels.Food;
import in.antaragni.ant.datamodels.Venue;

public class DatabaseAccess
{
  private SQLiteOpenHelper openHelper;
  private SQLiteDatabase database;
  private static DatabaseAccess instance;

  private DatabaseAccess(Context context)
  {
    this.openHelper = new DatabaseOpenHelper(context);
  }

  public static DatabaseAccess getInstance(Context context)
  {
    if (instance == null)
    {
      instance = new DatabaseAccess(context);
    }
    return instance;
  }


  public void open()
  {
    this.database = openHelper.getWritableDatabase();
  }

  public void close()
  {
    if (database != null)
    {
      this.database.close();
    }
  }

  private Contact getContact(String eventname)
  {
    String query = "SELECT * FROM contacts WHERE eventname='" + eventname + "';";
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3));
    cursor.close();
    return contact;
  }

  public List<Event> getEvent(int day)
  {
    String query = "SELECT * FROM eventdetails WHERE day=" + String.valueOf(day) + " ORDER BY start_time;";
    List<Event> list = new ArrayList<>();
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      GregorianCalendar start_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(5) / 100, cursor.getInt(5) % 100);
      GregorianCalendar end_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(4) / 100, cursor.getInt(4) % 100);
      Event event = new Event(cursor.getString(7), cursor.getString(1), start_time, end_time, day, getVenue(cursor.getString(3)), cursor.getString(2), getContact(cursor.getString(1)));
      list.add(event);
      cursor.moveToNext();
    }
    cursor.close();
    return list;
  }

  public Event getParticularEvent(String name)
  {
    String query = "SELECT * FROM eventdetails WHERE name='" + name + "';";
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    int day = Integer.parseInt(cursor.getString(6));
    GregorianCalendar start_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(5) / 100, cursor.getInt(5) % 100);
    GregorianCalendar end_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(4) / 100, cursor.getInt(4) % 100);
    Event event = new Event(cursor.getString(7), cursor.getString(1), start_time, end_time, day, getVenue(cursor.getString(3)), cursor.getString(2), getContact(cursor.getString(1)));
    cursor.moveToNext();
    cursor.close();
    return event;
  }

  public Venue getVenue(String loc)
  {
    String query = "SELECT * FROM venue WHERE location='" + loc + "';";
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    LatLng latLng = new LatLng(cursor.getDouble(1), cursor.getDouble(2));
    Venue v = new Venue(loc, latLng);
    cursor.close();
    return v;
  }

  public List<Event> getCategory(String category)
  {
    String query = "SELECT * FROM eventdetails WHERE category='" + category + "' ORDER BY start_time;";
    List<Event> list = new ArrayList<>();
    int day;
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      day = cursor.getInt(6);
      GregorianCalendar start_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(5) / 100, cursor.getInt(5) % 100);
      GregorianCalendar end_time = new GregorianCalendar(2015, 10 + (day / 4), (28 + day + (day / 4)) % 32, cursor.getInt(4) / 100, cursor.getInt(4) % 100);
      Event event = new Event(category, cursor.getString(1), start_time, end_time, day, getVenue(cursor.getString(3)), cursor.getString(2), getContact(cursor.getString(1)));
      list.add(event);
      cursor.moveToNext();
    }
    cursor.close();
    return list;
  }


  public List<Contact> getContact()
  {
    String query = "SELECT * FROM contacts;";
    List<Contact> list = new ArrayList<>();
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3));
      list.add(contact);
      cursor.moveToNext();
    }
    cursor.close();
    return list;
  }

  public List<Food> getFood()
  {
    String query = "SELECT * FROM food_courts;";
    List<Food> list = new ArrayList<>();
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      Food food = new Food(cursor.getString(1), getVenue(cursor.getString(2)));
      list.add(food);
      cursor.moveToNext();
    }
    cursor.close();
    return list;
  }

  public String getResult(String eventname)
  {
    String query = "SELECT * FROM eventdetails WHERE name='" + eventname + "';";
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    String result = cursor.getString(8);
    cursor.close();
    return result;
  }

  public List<String> getnotification()
  {
    String query = "SELECT * FROM notification ORDER BY id DESC;";
    List<String> list = new ArrayList<>();
    Cursor cursor = database.rawQuery(query, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      String notify = cursor.getString(1);
      list.add(notify);
      cursor.moveToNext();
    }
    cursor.close();
    return list;
  }

  public void updateinfo(String event_name,int star_time,int end_time)
  {
    String query1 = "UPDATE eventdetails SET start_time=" + star_time + " WHERE name='" + event_name + "';";
    database.execSQL(query1);
    String query2 = "UPDATE eventdetails SET end_time=" + end_time + " WHERE name='" + event_name + "';";
    database.execSQL(query2);
  }

  public void updateinfo(String event_name, String venue)
  {
    String query = "UPDATE eventdetails SET venue='" + venue + "' WHERE name='" + event_name + "';";
    database.execSQL(query);
  }

  public void updateresult(String event_name,String result)
  {
    String query = "UPDATE eventdetails SET result='" + result + "' WHERE name='" + event_name + "';";
    database.execSQL(query);
  }

  public void addnotification(String notification, String type)
  {
    String query = "INSERT INTO notification (notification,type) VALUES ('" + notification +  "','" + type + "');";
    database.execSQL(query);
  }

}