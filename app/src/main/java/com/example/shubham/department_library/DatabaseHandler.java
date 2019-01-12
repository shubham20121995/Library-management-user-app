package com.example.shubham.department_library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shubham on 12/12/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";




    Context l;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        l = context;
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ISSUED_BOOKS_TABLE = "CREATE TABLE " + "issue_table" + "( "
                + "book_id" + " TEXT," + "book_name" + " TEXT," + "date_of_issue" + " TEXT," + "return_date" + " TEXT," + "notification" + " TEXT" + ")";

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + "login_table" + "( "
                + "reg_no" + " TEXT," + "name" + " TEXT" + ")";

        String CREATE_ALL_BOOKS = "CREATE TABLE " + "all_table" + "( "
                + "book_id" + " TEXT," + "book_name" + " TEXT," + "author" + " TEXT," + "publication" + " TEXT," + "edition" + " TEXT," + "quantity" + " TEXT" + ")";


        db.execSQL(CREATE_ISSUED_BOOKS_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_ALL_BOOKS);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "issue_table");
        db.execSQL("DROP TABLE IF EXISTS " + "login_table");
        db.execSQL("DROP TABLE IF EXISTS " + "all_table");

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    long addAll(All_books a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", a.getBook_id());
        values.put("book_name", a.getBook_name());
        values.put("author", a.getAuthor());
        values.put("publication", a.getPublication());
        values.put("edition", a.getEdition());
        values.put("quantity", a.getNo_of_copies());


        // Inserting Row
        long insert = db.insert("all_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }

    long addLogin(String a,String b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reg_no", a);
        values.put("name", b);

        // Inserting Row
        long insert = db.insert("login_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }

    long addIssue(Books a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", a.getBook_id());
        values.put("book_name", a.getBook_name());
        values.put("date_of_issue", a.getDate_of_issue());
        values.put("return_date", a.getReturn_date());
        values.put("notification", a.getNotification());


        // Inserting Row
        long insert = db.insert("issue_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }
    Cursor viewAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM all_table ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }


    void refreshAll(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "all_table");
        String CREATE_ALL_BOOKS = "CREATE TABLE " + "all_table" + "( "
                + "book_id" + " TEXT," + "book_name" + " TEXT," + "author" + " TEXT," + "publication" + " TEXT," + "edition" + " TEXT," + "quantity" + " TEXT" + ")";

        db.execSQL(CREATE_ALL_BOOKS);

    }

    Cursor viewLogin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM login_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }

    void deleteLogin(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "login_table");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + "login_table" + "( "
                + "reg_no" + " TEXT," + "name" + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    Cursor viewIssue() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM issue_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }
    void refreshIssue(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "issue_table");
        String CREATE_ISSUED_BOOKS_TABLE = "CREATE TABLE " + "issue_table" + "( "
                + "book_id" + " TEXT," + "book_name" + " TEXT," + "date_of_issue" + " TEXT," + "return_date" + " TEXT," + "notification" + " TEXT" + ")";

        db.execSQL(CREATE_ISSUED_BOOKS_TABLE);

    }



}
