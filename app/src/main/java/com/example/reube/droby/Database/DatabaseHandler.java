package com.example.reube.droby.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.reube.droby.Fragments.Social.TrendingFragment;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.reube.droby.Fragments.ClothesFragment.clothes;

/**
 * Created by reube on 21/6/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
    private MobileServiceSyncTable<Clothes> clothesMobileServiceSyncTable;
    // Database Name
    private static final String DATABASE_NAME = "droby_database";

    // Table names
    private static final String TABLE_USER = "appuser";
    private static final String TABLE_CATEGORY ="category";
    public static final String TABLE_CLOTHES ="clothes";
    private static final String TABLE_IMAGE ="image";
    private static final String TABLE_OUTFIT ="outfit";
    private static final String TABLE_TAG ="tag";
    private static final String TABLE_FREQUENCY = "frequency";

    //Common Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CLOTHES_ID = "clothes_id";

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
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT" + ")";
        String CREATE_CLOTHES_TABLE = "CREATE TABLE " + TABLE_CLOTHES + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " INTEGER," + KEY_CATEGORY_ID + " INTEGER," + KEY_IMAGE_BLOB + " BLOB,"  + KEY_NAME + " TEXT,"+ KEY_DESCRIPTION + " TEXT,"+ KEY_CREATED_DATE + " DATETIME," + KEY_FREQUENCY + " TEXT,"+ KEY_LOCATION+" TEXT"+")";
        //String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IMAGE_BLOB + " BLOB" + ")";
        String CREATE_OUTFIT_TABLE = "CREATE TABLE " + TABLE_OUTFIT + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_OUTFIT_ID + " INTEGER, " + KEY_USER_ID + " INTEGER, "+ KEY_CLOTHES_ID + " INTEGER, "+ KEY_NAME+" TEXT "+")";
        String CREATE_TAG_TABLE = "CREATE TABLE " + TABLE_TAG + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"+ KEY_USER_ID + " INTEGER," + KEY_CLOTHES_ID + " INTEGER," + KEY_CATEGORY_ID + " INTEGER," + KEY_IMAGE_ID + " INTEGER" +")";
        String CREATE_FREQUENCY_TABLE = "CREATE TABLE " + TABLE_FREQUENCY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CLOTHES_ID + " INTEGER,"+ KEY_DATE_WORN + " DATETIME "+ ")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CLOTHES_TABLE);
       // db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_OUTFIT_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
        db.execSQL(CREATE_FREQUENCY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
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
                clothesList += clothes.getDescription() +" "+ clothes.getId() + "\n";

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
                clothes.setCategory_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
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
                clothes.setCategory_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
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

    //get clothes of a user
    public ArrayList<Clothes> getAllClothes(User user) {
        ArrayList<Clothes> clothesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLOTHES + " WHERE "+ KEY_USER_ID + " = '"+new String[] { String.valueOf(user.getId()) }+"'";

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
                //clothes.setImage(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                clothes.setCategory_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
                clothes.setCreated_date(new Date(c.getLong(c.getColumnIndex(KEY_CREATED_DATE))*1000));
                clothes.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)).charAt(0));
                // Adding clothes to list
                clothesList.add(clothes);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        // return contact list
        return clothesList;
    }
    //update one piece of clothing
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
        db.close();
    }

    // get image of clothes , way to use is to setImageBitmap(bitmap)
//    public Bitmap getClothesImage(Clothes clothes){
//        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE + " WHERE "+ KEY_ID + " = '"+ new String[] { String.valueOf(clothes.getImage_id()) }+"'";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//        if (c != null)
//            c.moveToFirst();
//        byte[] imgByte = c.getBlob(c.getColumnIndex(KEY_IMAGE_BLOB));
//        db.close();
//        c.close();
//        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
//
//
//    }
    //for converting bitmap to byte to store
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    // Category table methods
    public Category getCategory(Clothes clothes){
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " WHERE "+ KEY_ID + " = '"+ new String[] { String.valueOf(clothes.getCategory_id()) }+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount()<=0){
            c.close();
            db.close();
            return null;
        }
        if (c != null)
            c.moveToFirst();
        Category category = new Category();
        category.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        category.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        c.close();
        db.close();

        return category;
    }

    public void createCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, category.getId());
        values.put(KEY_NAME, category.getName());

        // insert row
        db.insert(TABLE_CATEGORY, null, values);
        db.close();

    }

    public void deleteCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CATEGORY, KEY_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
        db.close();
    }

    //get frequency of clothes worn, just need to getcount of arraylist
    public ArrayList<Date> getFrequency(Clothes clothes){
        ArrayList<Date> frequency = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FREQUENCY + " WHERE "+ KEY_CLOTHES_ID + " = '"+ new String[] { String.valueOf(clothes.getId()) }+"'";

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
    public ArrayList<Tag> getTags(Clothes clothes){
        ArrayList<Tag> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG + " WHERE "+ KEY_CLOTHES_ID + " = '"+ new String[] { String.valueOf(clothes.getId()) }+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                tag.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                tag.setCategory_id(c.getInt(c.getColumnIndex(KEY_CATEGORY_ID)));
                tag.setUser_id(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                tag.setClothes_id(c.getInt(c.getColumnIndex(KEY_CLOTHES_ID)));

                tags.add(tag);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return tags;
    }

    public void createTag(Tag tag){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, category.getId());
        values.put(KEY_NAME, tag.getName());
        values.put(KEY_CATEGORY_ID, tag.getCategory_id());
        values.put(KEY_USER_ID, tag.getUser_id());
        values.put(KEY_CLOTHES_ID, tag.getClothes_id());

        // insert row
        db.insert(TABLE_TAG, null, values);
        db.close();
    }

    public void deleteTag(Tag tag){
        SQLiteDatabase db = this.getWritableDatabase();

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
      String selectQuery = "SELECT  * FROM " + TABLE_OUTFIT + " WHERE "+ KEY_USER_ID + " = '"+ new String[] { String.valueOf(user.getId()) } + "' ORDER BY "+ KEY_OUTFIT_ID+" ASC";

      SQLiteDatabase db = this.getWritableDatabase();
      Cursor c = db.rawQuery(selectQuery, null);
      if (c.moveToFirst()) {
          do {
              Outfit outfit = new Outfit();
              outfit.setId(c.getInt(c.getColumnIndex(KEY_OUTFIT_ID)));
              outfit.setName(c.getString(c.getColumnIndex(KEY_NAME)));
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
      String selectQuery = "SELECT COUNT（DISTINCT "+KEY_OUTFIT_ID+") FROM " + TABLE_OUTFIT + " WHERE "+ KEY_USER_ID + " = '"+ new String[] { String.valueOf(outfit.getUser_id()) } + "' ORDER BY "+ KEY_OUTFIT_ID+" ASC";
      Cursor c = db.rawQuery(selectQuery, null);
      int count = 0;
      if (c.moveToFirst()){
          count +=c.getInt(0);
      }
      for (int i =0; i<outfit.getClothes().size();i++){
          ContentValues values = new ContentValues();
          values.put(KEY_NAME, outfit.getName());
          values.put(KEY_USER_ID, outfit.getUser_id());
          values.put(KEY_OUTFIT_ID, count );
          values.put(KEY_CLOTHES_ID, outfit.getClothes().get(i).getId());

          // insert row
          db.insert(TABLE_TAG, null, values);
      }

      db.close();
  }

  public void deleteOutfit(Outfit outfit){
      SQLiteDatabase db = this.getWritableDatabase();

      db.delete(TABLE_OUTFIT, KEY_OUTFIT_ID+ " = ?",
              new String[] { String.valueOf(outfit.getOutfit_id()) });
      db.close();
  }


}
