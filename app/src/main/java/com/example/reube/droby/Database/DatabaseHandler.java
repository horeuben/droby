package com.example.reube.droby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.reube.droby.Fragments.Social.TrendingFragment;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.operations.TableOperation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;
import static com.example.reube.droby.Fragments.ClothesFragment.clothes;

/**
 * Created by reube on 21/6/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;
    private MobileServiceSyncTable<Clothes> clothesMobileServiceSyncTable;
    // Database Name
    private static final String DATABASE_NAME = "droby_database";

    // Table names
    private static final String TABLE_USER = "appuser";
    private static final String TABLE_CATEGORY ="category";
    public static final String TABLE_CLOTHES ="clothes";
    private static final String TABLE_OUTFIT ="outfit";
    private static final String TABLE_TAG ="tag";
    private static final String TABLE_FREQUENCY = "frequency";

    //Common Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CLOTHES_ID = "clothes_id";
    private static final String KEY_IS_DELETED = "is_deleted";

    // User Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    // get arraylist of all clothes

    // Category Table Column names all common

    // Clothes Table Column names
    private static final String KEY_IMAGE_ID = "image_id";
    public static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CREATED_DATE = "created_date";
    private static final String KEY_FREQUENCY = "frequency";
    private static final String KEY_LOCATION = "location";
    // get arraylist of tags that a clothes have

    // Image Table Column names
    public static final String KEY_IMAGE_BLOB = "image_blob";

    // Outfit Table Column names
    // get arraylist of clothes that make up an outfit, the rest all common
    private static final String KEY_OUTFIT_ID = "outfit_id";
    // Tag Table Column names all common

    // Frequency Table Column names
    private static final String KEY_DATE_WORN = "date_worn";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"+ KEY_PASSWORD + " TEXT,"+ KEY_EMAIL + " TEXT" + ")";
        String CREATE_CLOTHES_TABLE = "CREATE TABLE " + TABLE_CLOTHES + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " INTEGER," + KEY_CATEGORY_ID + " TEXT," + KEY_IMAGE_BLOB + " BLOB,"  + KEY_NAME + " TEXT,"+ KEY_DESCRIPTION + " TEXT,"+ KEY_CREATED_DATE + " DATETIME," + KEY_LOCATION+" INTEGER"+")";
        String CREATE_OUTFIT_TABLE = "CREATE TABLE " + TABLE_OUTFIT + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_OUTFIT_ID + " INTEGER, " + KEY_USER_ID + " INTEGER, "+ KEY_CLOTHES_ID + " INTEGER, "+ KEY_CATEGORY_ID + " TEXT, "+ KEY_NAME+" TEXT, " + KEY_IS_DELETED + " BOOLEAN " + ")";
        String CREATE_TAG_TABLE = "CREATE TABLE " + TABLE_TAG + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"+ KEY_USER_ID + " INTEGER," + KEY_CLOTHES_ID + " INTEGER," + KEY_IS_DELETED + " TEXT "+ ")";
        String CREATE_FREQUENCY_TABLE = "CREATE TABLE " + TABLE_FREQUENCY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CLOTHES_ID + " INTEGER,"+ KEY_DATE_WORN + " DATETIME "+ ")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CLOTHES_TABLE);
        db.execSQL(CREATE_OUTFIT_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_FREQUENCY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FREQUENCY);
        // Create tables again
        onCreate(db);
    }

    // CRUD here
    public long createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NAME, user.getNickname());

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);
        db.close();
        return user_id;
    }
    //returns null if no match, otherwise get user
    public User getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_EMAIL + " = '" + email + "'";


        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount()<=0){
            c.close();
            db.close();
            return null;
        }
        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setNickname((c.getString(c.getColumnIndex(KEY_NAME))));
        user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        c.close();
        db.close();
        return user;
    }
    public User getUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_EMAIL + " = '" + email + "' AND "+ KEY_PASSWORD + " = '"+password+"'";


        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount()<=0){
            c.close();
            db.close();
            return null;
        }
        if (c != null)
            c.moveToFirst();

        User user = new User();
        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setNickname((c.getString(c.getColumnIndex(KEY_NAME))));
        user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        c.close();
        db.close();
        return user;
    }

    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getNickname());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }
    // Deleting user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

//-->////////////////////////////////////////////////////////////////////////////////////////////////
    public String getClothes() {
         String clothesList = new String();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setTags(this.getTags(clothes));
                String a ="";
                for(int i =0;i<clothes.getTags().size();i++){
                    a+= clothes.getTags().get(i).getName()+" ";
                }
                clothesList += clothes.getDescription() +" "+ clothes.getId() +" "+clothes.getCategory_id()+" "+a+ clothes.getLocation()+ "\n";

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;
    }

    public ArrayList<Clothes> getAllClothesTest() {
        ArrayList<Clothes> clothesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
                clothes.setImage(imgByte);
                //clothes.setImage(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                //clothes.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)).charAt(0));
                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;
    }

    public ArrayList<Clothes> getSelectedClothesIdTest(ArrayList<String> items){
        ArrayList<Clothes> clothesList = new ArrayList<Clothes>();
        String s = new String();
        for (int i=0; i<items.size(); i++){
            if(i< items.size()-1){
                s += items.get(i) + ",";
            }
            else{
                s += items.get(i);
            }

        }


        SQLiteDatabase db = this.getReadableDatabase();
//        String[] projection = {KEY_ID, KEY_USER_ID, KEY_CATEGORY_ID, KEY_IMAGE_BLOB, KEY_NAME, KEY_DESCRIPTION, KEY_CREATED_DATE, KEY_FREQUENCY, KEY_LOCATION};
//        Cursor c = db.query(TABLE_CLOTHES, projection, KEY_ID + " IN(?)", new String[]{"1","2"},null,null,null);
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES + " WHERE " + KEY_ID + " IN " + "("+ s + ")";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
                clothes.setImage(imgByte);
                //clothes.setImage(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                //clothes.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)).charAt(0));
                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;

    }
    //////////////////////////////////////////////////////////////////////////<--

    //get clothes of a user, with clothes id = ?
    public ArrayList<Clothes> getAllClothes(User user, ArrayList<String> clothes_ids) {
        ArrayList<Clothes> clothesList = new ArrayList<>();
        String s = new String();
        for (int i=0; i<clothes_ids.size(); i++){
            if(i< clothes_ids.size()-1){
                s +=  clothes_ids.get(i) + ",";
            }
            else{
                s += clothes_ids.get(i);
            }

        }
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES + " WHERE "+ KEY_USER_ID + " = '"+user.getId()+"' AND "+ KEY_ID + " IN " + "("+ s + ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
                clothes.setImage(imgByte);
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                clothes.setLocation(c.getInt(c.getColumnIndex(KEY_LOCATION)));
                clothes.setTags(this.getTags(clothes));

                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;
    }
    //get all clothes where user = ? and category_id = ? as well as its tags
    public ArrayList<Clothes> getAllClothes(User user, String category_id) {
        ArrayList<Clothes> clothesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES + " WHERE "+ KEY_USER_ID + " = '"+user.getId()+"' AND "+ KEY_CATEGORY_ID + " = '"+category_id+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
                clothes.setImage(imgByte);
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                clothes.setLocation(c.getInt(c.getColumnIndex(KEY_LOCATION)));
                clothes.setTags(this.getTags(clothes));

                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;
    }
    //get all clothes of a user as well as its tags
    public ArrayList<Clothes> getAllClothes(User user) {
        ArrayList<Clothes> clothesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES + " WHERE "+ KEY_USER_ID + " = '"+user.getId()+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Clothes clothes = new Clothes();
                clothes.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                clothes.setName(c.getString((c.getColumnIndex(KEY_NAME))));
                clothes.setDescription(c.getString((c.getColumnIndex(KEY_DESCRIPTION))));
                clothes.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
                clothes.setImage(imgByte);
                clothes.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                clothes.setLocation(c.getInt(c.getColumnIndex(KEY_LOCATION)));
                clothes.setTags(this.getTags(clothes));
                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return clothes list
        return clothesList;
    }
    //update one piece of clothing as well as its tags

    public void updateClothes(Clothes clothes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, clothes.getName());
        values.put(KEY_DESCRIPTION, clothes.getDescription());
        values.put(KEY_CATEGORY_ID, clothes.getCategory_id());
        values.put(KEY_LOCATION,  String.valueOf(clothes.getLocation()));

        // updating row
        db.update(TABLE_CLOTHES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(clothes.getId()) });
        //get old tags first
        ArrayList<Tag> oldTags = new ArrayList<>();
        oldTags.addAll(this.getTags(clothes));
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.addAll(clothes.getTags());
        ArrayList<Tag> removedTags = new ArrayList<>();
        ArrayList<Tag> addedTags = new ArrayList<>();
        removedTags.addAll(oldTags);
        removedTags.removeAll(newTags);
        addedTags.addAll(newTags);
        addedTags.removeAll(oldTags);
        for (int i =0; i <removedTags.size();i++){
            //remove all unwanted tags
            this.deleteTag(removedTags.get(i));
        }
        for (int i = 0; i<addedTags.size();i++){
            //add new tags
            this.createTag(addedTags.get(i));
        }
        db.close();
    }


    //for converting bitmap to byte to store
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    //get frequency of clothes worn, just need to getcount of arraylist
    public ArrayList<Date> getFrequency(Clothes clothes){
        ArrayList<Date> frequency = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FREQUENCY + " WHERE "+ KEY_CLOTHES_ID + " = '"+ clothes.getId()+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Date date = new Date(c.getLong(c.getColumnIndex(KEY_DATE_WORN)));
                frequency.add(date);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return frequency;
    }
    //wear clothes once
    public void insertDateWorn(Date date,Clothes clothes){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLOTHES_ID, clothes.getId());
        values.put(KEY_DATE_WORN, date.toString());

        // insert row
        db.insert(TABLE_FREQUENCY, null, values);
        db.close();
    }

    //Tag stuff
    // NOTE: DO NOT USE THIS METHOD AS ITS MEANT TO BE USED IN GETCLOTHES
    public ArrayList<Tag> getTags(Clothes clothes){
        ArrayList<Tag> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG + " WHERE "+ KEY_CLOTHES_ID + " = '"+ clothes.getId()+"'";// AND "+KEY_IS_DELETED+ " = '"+false+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                tag.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                tag.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                tag.setClothes_id(c.getInt(c.getColumnIndex(KEY_CLOTHES_ID)));

                tags.add(tag);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return tags;
    }
    // NOTE: DO NOT USE THIS METHOD AS ITS MEANT TO BE USED WITHIN UPDATECLOTHES
    public void createTag(Tag tag){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, tag.getName());
        values.put(KEY_USER_ID, tag.getUser_id());
        values.put(KEY_CLOTHES_ID, tag.getClothes_id());
        values.put(KEY_IS_DELETED, false);
        // insert row
        db.insert(TABLE_TAG, null, values);
        db.close();
    }
    // NOTE: DO NOT USE THIS METHOD AS ITS MEANT TO BE USED WITHIN UPDATECLOTHES
    public void deleteTag(Tag tag){
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_IS_DELETED, "true");
//        db.update(TABLE_TAG, values, KEY_ID + " = ?",
          //      new String[] { String.valueOf(tag.getId()) });
        db.delete(TABLE_TAG, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
        db.close();
    }

    //Outfit stuff
  public ArrayList<Outfit> getOutfits(User user){
      List<Outfit> outfits = new ArrayList<>();
      // need to do 2 select statements. first is to get the distinct outfit id that belongs to a user
      // then next is to do a for loop on the number of outfit id, and select clothes_id from outfit that belongs to user,
      // then need to select clothes where clothes_id = ? from table_clothes and add to the arraylist in an outfit
      // i can already get all clothes, so juz need the clothes_id then
      ArrayList<Clothes> clothes = this.getAllClothes(user);
      String selectQuery = "SELECT  * FROM " + TABLE_OUTFIT + " WHERE "+ KEY_USER_ID + " = '"+ user.getId() + "'"/** AND " + KEY_IS_DELETED + " = '" + false **/+ " ORDER BY "+ KEY_OUTFIT_ID+" ASC";

      SQLiteDatabase db = this.getWritableDatabase();
      Cursor c = db.rawQuery(selectQuery, null);
      if (c.moveToFirst()) {
          do {
              Outfit outfit = new Outfit();
              outfit.setId(c.getInt(c.getColumnIndex(KEY_OUTFIT_ID)));
              outfit.setName(c.getString(c.getColumnIndex(KEY_NAME)));
              outfit.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
              outfit.setCategory_id(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)));
              int clothes_id = c.getInt(c.getColumnIndex(KEY_CLOTHES_ID));
              ArrayList<Clothes> outfitClothes = new ArrayList<>();
              for (int i =0; i<clothes.size();i++){
                  if (clothes.get(i).getId() == clothes_id){
                      outfitClothes.add(clothes.get(i));
                  }
              }
              outfit.setClothes(outfitClothes);
              outfits.add(outfit);
          } while (c.moveToNext());
      }
      c.close();
      db.close();
      Set<Outfit> clearDuplicates = new HashSet<>();
      clearDuplicates.addAll(outfits);
      ArrayList<Outfit> noDupsOutfit = new ArrayList<>();
      noDupsOutfit.addAll(clearDuplicates);
      return noDupsOutfit;
  }

  public void createOutfit(Outfit outfit){
      SQLiteDatabase db = this.getWritableDatabase();
      String selectQuery = "SELECT COUNT(DISTINCT "+KEY_OUTFIT_ID+") FROM " + TABLE_OUTFIT + " WHERE "+ KEY_USER_ID + " = '"+ outfit.getUser_id()+ "'";;
      Cursor c = db.rawQuery(selectQuery, null);

      int count = 0;
      if (c.moveToFirst()){
          count +=c.getInt(0);
      }
      if(outfit.getClothes()!=null){
      for (int i =0; i<outfit.getClothes().size();i++){
          ContentValues values = new ContentValues();
          values.put(KEY_NAME, outfit.getName());
          values.put(KEY_USER_ID, outfit.getUser_id());
          values.put(KEY_OUTFIT_ID, count );
          values.put(KEY_CLOTHES_ID, outfit.getClothes().get(i).getId());
          values.put(KEY_CATEGORY_ID, outfit.getCategory_id());
        //  values.put(KEY_IS_DELETED, outfit.isDeleted());
          // insert row
          db.insert(TABLE_OUTFIT, null, values);
      }}
      c.close();
      db.close();
  }

  public void deleteOutfit(Outfit outfit){
      SQLiteDatabase db = this.getWritableDatabase();
//      ContentValues values = new ContentValues();
//      values.put(KEY_IS_DELETED, outfit.isDeleted());
//      db.update(TABLE_OUTFIT, values, KEY_ID + " = ?",
//              new String[] { String.valueOf(outfit.getId()) });
      db.delete(TABLE_OUTFIT, KEY_OUTFIT_ID+ " = ?",
              new String[] { String.valueOf(outfit.getOutfit_id()) });
      db.close();
  }

// NOTE: WE WILL ONLY BE USING syncCLothes() on app start to get the clothes from database
  // For use in syncing with online database
  // Use DatabaseUtilities to interface with cloud database
  // Sync new clothes from online to phone, sync descriptions of clothes from phone to online, thus update from phone one by one to online then select * from online to phone.
    public void syncClothes(){
        //need to insert into cloud database first

        //this part is to sync from cloud to phone
        String statement = "select * from Clothes";
        String result = DatabaseUtilities.getResult(statement);
        //ArrayList<Clothes> allClothes = new ArrayList<>();
        if (result != null){
            try{
                JSONArray clothes = new JSONArray(result);
                for (int i = 0; i<clothes.length();i++){
                    JSONObject c = clothes.getJSONObject(i);
                    Clothes cloth = new Clothes();
                    cloth.setId(c.getInt(KEY_ID));
                    cloth.setUser_id(c.getInt(KEY_USER_ID));
                    cloth.setCategory_id(c.getString(KEY_CATEGORY_ID));
                    cloth.setCreated_date(new Date(c.getLong(KEY_CREATED_DATE)*1000));
                    cloth.setName(c.getString(KEY_NAME));
                    cloth.setLocation(c.getInt(KEY_LOCATION));
                    cloth.setDescription(c.getString(KEY_DESCRIPTION));
                    byte[] imgByte = c.getString(KEY_IMAGE_BLOB).getBytes();
                    cloth.setImage(imgByte);
                    //allClothes.add(cloth);
                    String query = "INSERT OR REPLACE INTO "+ TABLE_CLOTHES + "("+KEY_ID+","+KEY_USER_ID+","+KEY_CATEGORY_ID+","+KEY_CREATED_DATE+","+KEY_NAME+","+KEY_LOCATION+","+KEY_DESCRIPTION+","+KEY_IMAGE_BLOB+") VALUES ("+cloth.getId()+","+cloth.getUser_id()+","+cloth.getCategory_id()+","+cloth.getCreated_date()+","+cloth.getName()+","+cloth.getLocation()+","+cloth.getDescription()+","+imgByte+")";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor cursor = db.rawQuery(query, null);
                    cursor.close();
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }
  // Sync User -> from phone to cloud insert, then select * from cloud
    public void syncUsers(){
        String statement = "select * from AppUser";
        String result = DatabaseUtilities.getResult(statement);
        if (result!=null){
            try{
                JSONArray users = new JSONArray(result);
                for (int i = 0; i <users.length();i++){
                    JSONObject u = users.getJSONObject(i);
                    User user = new User();
                    user.setId(u.getInt(KEY_ID));
                    user.setNickname(u.getString(KEY_NAME));
                    user.setPassword(u.getString(KEY_PASSWORD));
                    user.setEmail(u.getString(KEY_EMAIL));

                    String query = "INSERT OR REPLACE INTO "+ TABLE_USER + " (" + KEY_ID + "," + KEY_NAME + "," + KEY_PASSWORD+ ","+ KEY_EMAIL +") VALUES ("+user.getId()+ ","+user.getNickname()+ ","+user.getPassword()+ ","+user.getEmail()+")";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor cursor = db.rawQuery(query, null);
                    cursor.close();
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }

    //Get clothesid of colours from database, type is top,bottom,etc, if no cloth_id, set to -1
    public ArrayList<String> syncColour(String type,int cloth_id){
        String statement = "colour";
        if (cloth_id ==-1){
            statement +="?"+type;
        }
        else{
            statement +="?"+type+"="+cloth_id;
        }

        String result = DatabaseUtilities.getResult(statement);
        ArrayList<String> clothes_id = new ArrayList<>();
        if (result!=null){
            try{
                JSONArray clothesid = new JSONArray(result);
                for (int i = 0; i <clothesid.length();i++){
                    int clothe_id = clothesid.getInt(i);
                    // need tag to get what i wan
                    clothes_id.add(Integer.toString(clothe_id));
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return clothes_id;
    }
    //Get clothesid of styles from database, if field is empty do put in ""
    public ArrayList<String> syncStyles(String style, String weather){
        String statement = "styles?";
        if (style.equals("") && weather.equals("")) {
            //statement is default
        }
        else if (!style.equals("") && weather.equals("")){
            statement += "style="+style;
        }
        else if (style.equals("") && !weather.equals("")){
            statement += "weather="+weather;
        }
        else if(!style.equals("") && !weather.equals("")){
            statement += "style="+style+"&weather="+weather;
        }
        String result = DatabaseUtilities.getResult(statement);
        ArrayList<String> clothes_id = new ArrayList<>();
        if (result!=null){
            try{
                JSONArray clothesid = new JSONArray(result);
                for (int i = 0; i <clothesid.length();i++){
                    int clothe_id = clothesid.getInt(i);
                    // need tag to get what i wan
                    clothes_id.add(Integer.toString(clothe_id));
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return clothes_id;
    }
    //get location of clothes from database
    public void syncLocation(){
        String statement = "select * from rfid";
        String result = DatabaseUtilities.getResult(statement);
        if (result!=null){
            try{
                JSONArray locations = new JSONArray(result);
                for (int i = 0; i <locations.length();i++){
                    JSONObject cloth_location = locations.getJSONObject(i);
                    int clothes_id = cloth_location.getInt("clothes_id");
                    int location = cloth_location.getInt("location");

                    ContentValues values = new ContentValues();
                    values.put(KEY_LOCATION,  String.valueOf(location));

                    SQLiteDatabase db = this.getWritableDatabase();
                    // updating row
                    db.update(TABLE_CLOTHES, values, KEY_ID + " = ?",
                            new String[] { String.valueOf(clothes_id) });
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }
  // Sync Outfit: update from phone to cloud, update/insert, then cloud to phone
  // outfit might need a deleted column, so all outfits are saved, but only show those with deleted column not true
    public void syncOutfit(){
        String statement = "Select * from Outfit";
        String result = DatabaseUtilities.getResult(statement);
        if (result!=null) {
            try {
                JSONArray outfits = new JSONArray(result);
                for (int i = 0; i < outfits.length(); i++) {
                    JSONObject o = outfits.getJSONObject(i);
                    Outfit outfit = new Outfit();
                    outfit.setId(o.getInt(KEY_ID));
                    outfit.setUser_id(o.getInt(KEY_USER_ID));
                    outfit.setName(o.getString(KEY_NAME));
                    outfit.setOutfit_id(o.getInt(KEY_OUTFIT_ID));
                    outfit.setClothes_id(o.getInt(KEY_CLOTHES_ID));
                    outfit.setCategory_id(o.getString(KEY_CATEGORY_ID));
                    outfit.setDeleted(o.getBoolean(KEY_IS_DELETED));

                    String query = "INSERT OR REPLACE INTO "+ TABLE_OUTFIT + " (" + KEY_ID + ","+KEY_USER_ID+ "," + KEY_NAME + "," + KEY_OUTFIT_ID+ ","+ KEY_CLOTHES_ID +","+ KEY_CATEGORY_ID +", " + KEY_IS_DELETED+") VALUES ("+outfit.getId()+ ","+outfit.getUser_id()+ ","+outfit.getName()+ ","+outfit.getOutfit_id()+ ","+outfit.getClothes_id()+"," + outfit.getCategory_id()+"," + outfit.isDeleted()+ ")";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor cursor = db.rawQuery(query, null);
                    cursor.close();
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }
    }

    // same goes for Tag
    public void syncTags(){
        String statement = "Select * from Tag";
        String result = DatabaseUtilities.getResult(statement);
        if (result!=null) {
            try {
                JSONArray tags = new JSONArray(result);
                for (int i = 0; i < tags.length(); i++) {
                    JSONObject o = tags.getJSONObject(i);
                    Tag tag = new Tag();
                    tag.setId(o.getInt(KEY_ID));
                    tag.setUser_id(o.getInt(KEY_USER_ID));
                    tag.setName(o.getString(KEY_NAME));
                    tag.setClothes_id(o.getInt(KEY_CLOTHES_ID));
                    tag.setDeleted(o.getBoolean(KEY_IS_DELETED));
                    String query = "INSERT OR REPLACE INTO "+ TABLE_TAG + " (" + KEY_ID + ","+KEY_USER_ID+ "," + KEY_NAME + "," + KEY_CLOTHES_ID +","+ KEY_IS_DELETED+ ") VALUES ("+tag.getId()+ ","+tag.getUser_id()+ ","+tag.getName()+ ","+ tag.getClothes_id()+"," + tag.isDeleted()+ ")";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor cursor = db.rawQuery(query, null);
                    cursor.close();
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }
    }
    // Frequency: Just get the latest from cloud to phone
    public void syncFrequency(){
        String statement = "Select * from Frequency";
        String result = DatabaseUtilities.getResult(statement);
        if (result != null){
            try {
                JSONArray frequencies = new JSONArray(result);
                for (int i =0; i<frequencies.length();i++){
                    JSONObject o = frequencies.getJSONObject(i);
                    Frequency frequency = new Frequency();
                    frequency.setId(o.getInt(KEY_ID));
                    frequency.setClothes_id(o.getInt(KEY_CLOTHES_ID));
                    frequency.setDate_worn(new Date(o.getLong(KEY_DATE_WORN)*1000));

                    String query = "INSERT OR REPLACE INTO "+ TABLE_FREQUENCY + " (" + KEY_ID + ","+ KEY_CLOTHES_ID +","+ KEY_DATE_WORN+ ") VALUES ("+frequency.getId()+ ","+ frequency.getClothes_id()+"," + frequency.getDate_worn()+ ")";
                    SQLiteDatabase db = this.getWritableDatabase();
                    Cursor cursor = db.rawQuery(query, null);
                    cursor.close();
                    db.close();
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }
    }
    // test to get image.... not working lol
    public ArrayList<byte[]> syncTest(){
        String statement = "Select * from TEST";
        String result = DatabaseUtilities.getResult(statement);
        ArrayList<byte[]> pics = new ArrayList<>();
        if (result !=null){
            try {
                JSONArray tests = new JSONArray(result);
                for (int i =0; i<tests.length();i++){
                    JSONObject o = tests.getJSONObject(i);
                    int id  = o.getInt("id");
                    String pic = o.getString("pic");
                    byte [] imgBytes = Base64.decode(pic, Base64.DEFAULT);;
                    pics.add(imgBytes);
                    //JSONArray picarray = o.getJSONArray("pic");
                    //pics.add(picarray.toString().getBytes());
//                    String hexIn = o.getString("pic");
//
//                    byte [] byteOut = new byte [hexIn.length() / 2];
//                    int j = 0;
//                    for (int k = 0; k < hexIn.length(); k += 2)
//                    {
//                        byteOut[j++] = Byte.parseByte(hexIn.substring(k, k + 2), 16);
//                    }
//                    pics.add(byteOut);
                }
            } catch (JSONException e){
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        } return pics;
    }

}
