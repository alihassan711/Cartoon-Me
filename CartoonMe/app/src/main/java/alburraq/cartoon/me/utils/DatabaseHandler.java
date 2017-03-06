package alburraq.cartoon.me.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
		private static final int DATABASE_VERSION = 1;
	  	
	    // Database Name
	    private static final String DATABASE_NAME = "linksDatabase";
	 
	    // Contacts table name
	    private static final String TABLE_RECIPES = "links";
	 
	    // Contacts Table Columns names
	    private static final String KEY_ID = "id";
	    private static final String KEY_LINK = "link";
	    
	    public DatabaseHandler(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
	                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINK + " TEXT )";
	        db.execSQL(CREATE_CONTACTS_TABLE);
	    }
	 
	    // Upgrading database
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Drop older table if existed
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
	 
	        // Create tables again
	        onCreate(db);
	    }
	    public void addLink(ImagesLinks links) {
	        SQLiteDatabase db = this.getWritableDatabase();
	     
	        ContentValues values = new ContentValues();
	        values.put(KEY_LINK, links.getLink()); 
	        // Inserting Row
	        db.insert(TABLE_RECIPES, null, values);
	        db.close(); // Closing database connection
	    }
	    public ImagesLinks getLinks(int id) {
	        SQLiteDatabase db = this.getReadableDatabase();
	     
	        Cursor cursor = db.query(TABLE_RECIPES, new String[] { KEY_ID,
	        		KEY_LINK}, KEY_ID + "=?",
	                new String[] { String.valueOf(id) }, null, null, null, null);
	        if (cursor != null)
	            cursor.moveToFirst();
	     
	        ImagesLinks links = new ImagesLinks(Integer.parseInt(cursor.getString(0)),
	                cursor.getString(1));
	        // return contact
	        return links;
	    }
	    public List<ImagesLinks> getAllLinks() {
	        List<ImagesLinks> linkList = new ArrayList<ImagesLinks>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;
	     
	        SQLiteDatabase db = this.getWritableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	ImagesLinks links = new ImagesLinks();
	            	links.setId(Integer.parseInt(cursor.getString(0)));
	            	links.setLink(cursor.getString(1));
	              
	                // Adding contact to list
	            	linkList.add(links);
	            } while (cursor.moveToNext());
	        }
	     
	        // return contact list
	        return linkList;
	    }
	    public int getLinksCount() {
	        String countQuery = "SELECT  * FROM " + TABLE_RECIPES;
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(countQuery, null);
	        cursor.close();
	 
	        // return count
	        return cursor.getCount();
	    }
	    public int updateContact(ImagesLinks contact) {
	        SQLiteDatabase db = this.getWritableDatabase();
	     
	        ContentValues values = new ContentValues();
	        values.put(KEY_LINK, contact.getLink());
	     
	        // updating row
	        return db.update(TABLE_RECIPES, values, KEY_ID + " = ?",
	                new String[] { String.valueOf(contact.getId()) });
	    }
	    public void deleteLink(ImagesLinks contact) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_RECIPES, KEY_LINK + " = ?",
	                new String[] { String.valueOf(contact.getLink()) });
	        db.close();
	    }
	    
	    
	    
}
