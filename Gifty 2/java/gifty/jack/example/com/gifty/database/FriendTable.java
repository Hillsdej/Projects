package gifty.jack.example.com.gifty.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jack on 03/04/2017.
 */
public class FriendTable {

    public static final String TABLE_FRIEND = "friend";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_SLASHED = "slashed";
    public static final String COLUMN_FRIEND_TITLE = "noteTitle";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FRIEND
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SLASHED + " text not null, "
            + COLUMN_FRIEND_TITLE + " text not null, "
            + COLUMN_ITEMS
            + " text not null"
            + ");";

    static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(FriendTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        onCreate(database);
    }

}
