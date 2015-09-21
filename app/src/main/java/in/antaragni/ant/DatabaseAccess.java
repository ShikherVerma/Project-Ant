package in.antaragni.ant;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import in.antaragni.ant.DatabaseOpenHelper;
import in.antaragni.ant.datamodels.Contact;
import in.antaragni.ant.datamodels.Event;
import in.antaragni.ant.datamodels.Venue;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    private Venue getVenue(String loc){
        String query = "SELECT * FROM venue WHERE location='" + loc +"';";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        LatLng latLng = new LatLng(cursor.getDouble(1),cursor.getDouble(2));
        Venue v = new Venue(loc,latLng);
        cursor.close();
        return v;
    }

    private Contact getContact(String eventname){
        String query = "SELECT * FROM contacts WHERE eventname='" + eventname +"';";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        Contact contact = new Contact(cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3));
        cursor.close();
        return contact;
    }

    public List<Event> getEvent(int day) {
        String query = "SELECT * FROM eventdetails WHERE day=" + String.valueOf(day) + " ORDER BY start_time;";
        List<Event> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            GregorianCalendar start_time = new GregorianCalendar(2015,10+(day/4),(28+day+(day/4))%32,cursor.getInt(5)/100,cursor.getInt(5)%100);
            GregorianCalendar end_time = new GregorianCalendar(2015,10+(day/4),(28+day+(day/4))%32,cursor.getInt(4)/100,cursor.getInt(4)%100);
            Event event = new Event(cursor.getString(7),cursor.getString(1),start_time,end_time,day,getVenue(cursor.getString(3)),cursor.getString(2),getContact(cursor.getString(1)));
            list.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Event> getCategory(String category) {
        String query = "SELECT * FROM eventdetails WHERE category='" + category + "' ORDER BY start_time;";
        List<Event> list = new ArrayList<>();
        int day;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            day = cursor.getInt(6);
            GregorianCalendar start_time = new GregorianCalendar(2015,10+(day/4),(28+day+(day/4))%32,cursor.getInt(5)/100,cursor.getInt(5)%100);
            GregorianCalendar end_time = new GregorianCalendar(2015,10+(day/4),(28+day+(day/4))%32,cursor.getInt(4)/100,cursor.getInt(4)%100);
            Event event = new Event(category,cursor.getString(1),start_time,end_time,day,getVenue(cursor.getString(3)),cursor.getString(2),getContact(cursor.getString(1)));
            list.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Contact> getContact() {
        String query = "SELECT * FROM contacts;";
        List<Contact> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = new Contact(cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3));
            list.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}