package com.itcast.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//声明类并继承自 SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT)";

    //定义构造函数
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //实现 onCreate() 和 onUpgrade() 方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //实现插入用户数据的 insertUser() 方法
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    //实现修改密码的 changePassword() 方法
    public boolean changePassword(String oldPassword, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, newPassword);
        String whereClause = COLUMN_PASSWORD + " = ?";
        String[] whereArgs = {oldPassword};
        int result = db.update(TABLE_USERS, contentValues, whereClause, whereArgs);
        return result > 0;
    }

    //实现获取所有数据的 getAllData() 方法
    public String getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD};
        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);
        StringBuilder stringBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            stringBuilder.append("用户名：").append(username).append(", 密码：").append(password).append("\n");
        }

        cursor.close();
        return stringBuilder.toString();
    }

    //实现删除数据的 deleteData() 方法
    public void deleteData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};
        db.delete(TABLE_USERS, whereClause, whereArgs);
    }
}
//实现增删改查的功能