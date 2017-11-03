package com.mylesandre1124.inventoryandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.mylesandre1124.inventoryandroid.R;

public class AccessDatabase {

    private Database database;

    private SQLiteDatabase sqlDatabase;

    public final static String TOKEN_TABLE="Token";

    public final static String TOKEN="token";
    public final static String USERNAME="username";

    public AccessDatabase(Context context) {
        database = new Database(context);
        sqlDatabase = database.getWritableDatabase();
    }

    public void createRecords(String username, String token)
    {
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(TOKEN, token);
        int code = (int)sqlDatabase.insertWithOnConflict(TOKEN_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if(code == -1)
        {
            sqlDatabase.replace(TOKEN_TABLE, null, values);
        }

    }

    public boolean checkIfLoggedIn()
    {
        String count = "SELECT count(*) FROM " + TOKEN_TABLE;
        Cursor mcursor = sqlDatabase.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        Log.i("AuthServe", "" + icount);
        return icount != 0;
    }

    public void logout()
    {
        sqlDatabase.delete(TOKEN_TABLE, null, null);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {TOKEN};
        Cursor mCursor = /*sqlDatabase.rawQuery("SELECT token FROM Token", null);//*/sqlDatabase.query(TOKEN_TABLE,cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }
    public String getToken()
    {
        Cursor cursor = selectRecords();
        String authorizationToken = cursor.getString(0);
        cursor.close();
        return authorizationToken;
    }



}
