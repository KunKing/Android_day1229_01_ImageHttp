package com.edu.android_day1229_01_imagehttp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edu.android_day1229_01_imagehttp.entity.ItemShit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/1/8.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "qswiki.db";
    private static final int VERSION = 1;
    private static final String ARTICLE_TABLE = "article";
    private static final String ARTICLE_ID = "_id";
    private static final String ARTICLE_CONTENT = "content";
    private static final String ARTICLE_IMAGE = "image";
    private static final String ARTICLE_USER_ID = "user_id";
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_ICON = "icon";

    private static final String CREATE_ARTICLE_TABLE =
            "CREATE TABLE " + ARTICLE_TABLE + "("
                    + ARTICLE_ID + " INTEGER PRIMARY KEY,"
                    + ARTICLE_CONTENT + " TEXT,"
                    + ARTICLE_IMAGE + " TEXT,"
                    + ARTICLE_USER_ID + " INTEGER)";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + "("
                    + USER_ID + " INTEGER PRIMARY KEY,"
                    + USER_NAME + " TEXT,"
                    + USER_ICON + " TEXT)";
    private static final String TAG = "DBHelper";


    private static DBHelper database;

    public static DBHelper getDatabase() {
        return database;
    }

    public static void setDatabase(DBHelper database) {
        DBHelper.database = database;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ARTICLE_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加数据的方法
     *
     * @param list
     */
    public void saveAll(List<ItemShit.ItemsEntity> list) {
        SQLiteDatabase db = getWritableDatabase();
        //开启事物
        db.beginTransaction();
        for (ItemShit.ItemsEntity itemShit : list) {
            save(db, itemShit);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void save(ItemShit.ItemsEntity item) {
        save(getWritableDatabase(), item);
    }

    // 保存到数据库
    private void save(SQLiteDatabase db, ItemShit.ItemsEntity itemShit) {
        ItemShit.ItemsEntity.UserEntity userEntity = itemShit.getUser();
        if (userEntity != null) {
            ContentValues cv = new ContentValues();
            cv.put(USER_ID, userEntity.getId());
            cv.put(USER_NAME, userEntity.getLogin());
            cv.put(USER_ICON, userEntity.getIcon());
            db.replace(USER_TABLE, null, cv);
        }
        ContentValues cv = new ContentValues();
        Log.d(TAG, "save: 这条记录的id"+itemShit.getId());
        cv.put(ARTICLE_ID, itemShit.getId());
        cv.put(ARTICLE_CONTENT, itemShit.getContent());
        cv.put(ARTICLE_IMAGE, itemShit.getImage());
        if (userEntity != null) {
            Log.d(TAG, "save: 这条userid记录的"+userEntity.getId());
            cv.put(ARTICLE_USER_ID, userEntity.getId());
        }
        db.replace(ARTICLE_TABLE, null, cv);
    }

    public List<ItemShit.ItemsEntity> findAll(String selection, String[] selectionArgs,
                                              String orderBy, int limit) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ARTICLE_TABLE, null, selection, selectionArgs, null, null,
                orderBy, limit + "");
        List<ItemShit.ItemsEntity> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ItemShit.ItemsEntity itemsEntity = new ItemShit.ItemsEntity();
            itemsEntity.setContent(cursor.getString(cursor.getColumnIndex(ARTICLE_CONTENT)));
            itemsEntity.setImage(cursor.getString(cursor.getColumnIndex(ARTICLE_IMAGE)));
            int userID = cursor.getInt(cursor.getColumnIndex(ARTICLE_USER_ID));
            Log.d(TAG, "findAll: "+userID);
            if (userID != 0) {
                Cursor query = db.query(USER_TABLE, null, USER_ID + "=?", new String[]{String.valueOf(userID)},
                        null, null, null, "1"
                );
                query.moveToFirst();
                ItemShit.ItemsEntity.UserEntity userEntity = new ItemShit.ItemsEntity.UserEntity();
                Log.d(TAG, "findAll: " +userEntity);
                userEntity.setId(query.getInt(query.getColumnIndex(USER_ID)));
                userEntity.setLogin(query.getString(query.getColumnIndex(USER_NAME)));
                userEntity.setIcon(query.getString(query.getColumnIndex(USER_ICON)));
                itemsEntity.setUser(userEntity);
            }
            list.add(itemsEntity);
        }
        return list;
    }
}
