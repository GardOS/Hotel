package com.westerdals.gard.hotel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gard on 16.05.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recommendation.db";
    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_SCORE = "score";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY =
                "CREATE TABLE " + TABLE_POSTS + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TEXT + " TEXT, "
                        + COLUMN_SCORE + " INTEGER);";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(db);
    }

    public void createPost(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_SCORE, 0);
        db.insert(TABLE_POSTS, null, values);
        db.close();
    }

    public void updateScore(String id, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        db.update(TABLE_POSTS, values, COLUMN_ID + " = ?", new String[] {id});
        db.close();
    }

    public Post getLatestPost(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", null);
        cursor.moveToLast();
        Post post = new Post();
        post.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        post.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
        post.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
        db.close();
        cursor.close();
        return post;
    }

    public List<Post> getPostByScore(){
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS + " ORDER BY " + COLUMN_SCORE + " DESC;", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Post post = new Post();
            post.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            post.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
            post.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
            posts.add(post);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return posts;
    }

    public List<Post> getPostByTime(){
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS + " ORDER BY id DESC;", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Post post = new Post();
            post.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            post.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
            post.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
            posts.add(post);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return posts;
    }

    public void removePostById(int postId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS, COLUMN_ID + " = ?", new String[]{String.valueOf(postId)});
        db.close();
    }
}