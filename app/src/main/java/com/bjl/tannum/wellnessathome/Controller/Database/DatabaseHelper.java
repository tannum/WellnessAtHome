package com.bjl.tannum.wellnessathome.Controller.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tannum on 22/12/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "WellnessAtHome.db";
    public static final String DBLOCATION = "/data/data/com.bjl.tannum.wellnessathome/databases/";

    public static final String tb_user_login = "user_login";
    public static final String col_id   = "id";
    public static final String col_username = "user_name";
    public static final String col_active = "active";



    private Context mContext;
    private SQLiteDatabase mDatabase;


    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext = context;

    }

    public void InitDB(){

        //Mask: Check exists database
        //File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        File database = mContext.getApplicationContext().getDatabasePath(DBNAME);
        if(database.exists() == false ){

            Log.d("debug","database not exist -> get readable database and copy database");
            getReadableDatabase();

            //Copy db
            if(copyDatabase(mContext)){
                Log.d("debug","Copy database success");
            }
            else{
                Log.d("debug","Coppy datablse error");
            }
        }
        else
        {
            Log.d("debug", "Database exist");
        }
    }

    private boolean copyDatabase(Context context) {
        try {
            Log.d("debug", "Copy Database start");
            InputStream inputStream = context.getAssets().open(DBNAME);
            String outFileName = DBLOCATION + DBNAME;
            Log.d("debug", "Target location = " + outFileName);
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.d("debug", "copy data base success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE);

    }
    public void closeDatabase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }


    public Cursor SqlSelect(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(sql,null);
        return  res;
    }


    public boolean InsertData_tbLoginInfo(int id , String username , int active ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_id,id);
        contentValues.put(col_username,username);
        contentValues.put(col_active,active);
        long result = db.insert(tb_user_login,null,contentValues);
        if(result == (-1)){
            return false;
        }
        return true;
    }

    public boolean UpdateData_tbLoginInfo(int id , String user_name , int active){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_id,id);
        contentValues.put(col_username,user_name);
        contentValues.put(col_active,active);
        db.update(tb_user_login,contentValues,"id = ?",new String[]{String.valueOf(id)});
        return true;
    }


}

