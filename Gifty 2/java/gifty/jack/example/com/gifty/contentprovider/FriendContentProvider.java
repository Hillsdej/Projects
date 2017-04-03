package gifty.jack.example.com.gifty.contentprovider;

/**
 * Created by Jack on 03/04/2017.
 */
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import gifty.jack.example.com.gifty.database.FriendDatabaseHelper;
import gifty.jack.example.com.gifty.database.FriendTable;


public class FriendContentProvider extends ContentProvider {

    private FriendDatabaseHelper database;

    // used for the UriMacher
    private static final int FRIENDS = 10;
    private static final int FRIEND_ID = 20;

    private static final String AUTHORITY = "gifty.jack.example.com.gifty.contentprovider";

    private static final String BASE_PATH = "friendlist";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/friend";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, FRIENDS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", FRIEND_ID);
    }

    @Override
    public boolean onCreate() {
        database = new FriendDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(FriendTable.TABLE_FRIEND);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FRIENDS:
                break;
            case FRIEND_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(FriendTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor;
        cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int uriType;
        uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id;
        switch (uriType) {
            case FRIENDS:
                id = sqlDB.insert(FriendTable.TABLE_FRIEND, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted;
        switch (uriType) {
            case FRIENDS:
                rowsDeleted = sqlDB.delete(FriendTable.TABLE_FRIEND, selection,
                        selectionArgs);
                break;
            case FRIEND_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FriendTable.TABLE_FRIEND,
                            FriendTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(FriendTable.TABLE_FRIEND,
                            FriendTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        AtomicInteger rowsUpdated = new AtomicInteger();
        switch (uriType) {
            case FRIENDS:
                rowsUpdated.set(sqlDB.update(FriendTable.TABLE_FRIEND,
                        values,
                        selection,
                        selectionArgs));
                break;
            case FRIEND_ID:
                String id = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    rowsUpdated.set(sqlDB.update(FriendTable.TABLE_FRIEND,
                            values,
                            FriendTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs));
                }
                else {
                    rowsUpdated.set(sqlDB.update(FriendTable.TABLE_FRIEND,
                            values,
                            FriendTable.COLUMN_ID + "=" + id,
                            null));
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated.get();
    }

    private void checkColumns(String[] projection) {
        String[] available = { FriendTable.COLUMN_ITEMS, FriendTable.COLUMN_SLASHED, FriendTable.COLUMN_FRIEND_TITLE,
                FriendTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }


}
