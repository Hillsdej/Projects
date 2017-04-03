package gifty.jack.example.com.gifty;

/**
 * Created by Jack on 03/04/2017.
 */
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import gifty.jack.example.com.gifty.Adapters.FinishedItemsArrayAdapter;
import gifty.jack.example.com.gifty.Adapters.ItemsArrayAdapter;
import gifty.jack.example.com.gifty.contentprovider.FriendContentProvider;
import gifty.jack.example.com.gifty.database.FriendTable;


public class FriendActivity extends AppCompatActivity {

    private final static String TAG = FriendActivity.class.getSimpleName();
    public final static int SLASHED = 1;
    public final static int UNSLASHED = 0;
    ItemsArrayAdapter myItemsArrayAdapter;
    FinishedItemsArrayAdapter myFinishedItemsArrayAdapter;
    EditText myNewItemText;
    DynamicListView myItemsListView;
    ListView myFinishedItemsListView;
    ArrayList<String> myItems;
    ArrayList<String> myFinishedItems;
    //    FloatingActionButton fab;
    private Uri friendUri;
    public ArrayList<String> slashes;
    EditText myFriendTitle;
    ActionBar myActionBar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_friend);
        slashes = new ArrayList<>();

        myFinishedItems = new ArrayList<>();
        myFinishedItemsArrayAdapter = new FinishedItemsArrayAdapter(this, myFinishedItems, true);
        myFinishedItemsListView = (ListView) findViewById(R.id.finishedItems);
        myFinishedItemsListView.setAdapter(myFinishedItemsArrayAdapter);
        myFinishedItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view.findViewById(R.id.itemText);
                String text = item.getText().toString();
                myFinishedItems.remove(text);
                myItems.add(text);
                myFinishedItemsArrayAdapter.notifyDataSetChanged();
                myItemsArrayAdapter.notifyDataSetChanged();
            }
        });

        myItems = new ArrayList<String>();
        myItemsArrayAdapter = new ItemsArrayAdapter(this, myItems, false);
        myItemsListView = (DynamicListView) findViewById(R.id.itemsListView);
        myItemsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myItemsListView.setAdapter(myItemsArrayAdapter);
        myItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = (TextView) view.findViewById(R.id.itemText);
                String text = item.getText().toString();
                myItems.remove(text);
                myFinishedItems.add(text);
                myFinishedItemsArrayAdapter.notifyDataSetChanged();
                myItemsArrayAdapter.notifyDataSetChanged();
            }
        });

        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        friendUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(FriendContentProvider.CONTENT_ITEM_TYPE);

        myActionBar = getSupportActionBar();
        View view = getLayoutInflater().inflate(R.layout.friend_actionbar, null);

        myActionBar.setDisplayShowTitleEnabled(false);
        myActionBar.setCustomView(view);
        //ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        myActionBar.setDisplayShowCustomEnabled(true);
        myFriendTitle = (EditText) myActionBar.getCustomView().findViewById(R.id.friendName);

        // Or passed from the other activity
        if (extras != null) {
            if(extras.getParcelable(FriendContentProvider.CONTENT_ITEM_TYPE) != null) {
                friendUri = extras
                        .getParcelable(FriendContentProvider.CONTENT_ITEM_TYPE);
            }
            fillData(friendUri);

        }

        myItemsListView.setCheeseList(myItems);
    }

    private void fillData(Uri uri) {

        String[] projection = {FriendTable.COLUMN_ITEMS, FriendTable.COLUMN_SLASHED, FriendTable.COLUMN_FRIEND_TITLE};
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(uri, projection, null, null,
                    null);
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException caught: ", e);
        }
        if (cursor != null) {
            cursor.moveToFirst();

            String sItems = cursor.getString(cursor
                    .getColumnIndexOrThrow(FriendTable.COLUMN_ITEMS));

            String sSlashes = cursor.getString(cursor.getColumnIndexOrThrow(FriendTable.COLUMN_SLASHED));

            String title = cursor.getString(cursor.getColumnIndexOrThrow(FriendTable.COLUMN_FRIEND_TITLE));

            try {
                JSONArray jsonArray = new JSONArray(sItems);

                myFriendTitle.setText(title);

                JSONArray slashesJsonArray = new JSONArray(sSlashes);
                for (int i = 0; i < slashesJsonArray.length(); i++) {
                    slashes.add("" + slashesJsonArray.get(i));
                    if(slashesJsonArray.get(i).equals(FriendActivity.UNSLASHED)){
                        myItems.add((String) jsonArray.get(i));
                    }
                    else{
                        myFinishedItems.add((String) jsonArray.get(i));
                    }
                }
                myFinishedItemsArrayAdapter.notifyDataSetChanged();
                myItemsArrayAdapter.notifyDataSetChanged();
            } catch (JSONException ignored) {
            }

            // always close the cursor
            cursor.close();
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(FriendContentProvider.CONTENT_ITEM_TYPE, friendUri);
    }

    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState()
    {
        myItems.addAll(myFinishedItems);
        String friend = new JSONArray(myItems).toString();
        ArrayList<Integer> slashes = new ArrayList<>();

        for (int i = 0; i < myItemsListView.getChildCount(); i++) {
            slashes.add(FriendActivity.UNSLASHED);
        }
        for (int i = 0; i < myFinishedItemsListView.getChildCount(); i++) {
            slashes.add(FriendActivity.SLASHED);
        }

        String sSlashes = new JSONArray(slashes).toString();

        if (myItems.isEmpty()) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(FriendTable.COLUMN_ITEMS, friend);
        values.put(FriendTable.COLUMN_SLASHED, sSlashes);

        String friendTitle = myFriendTitle.getText().toString();
        if(friendTitle.isEmpty()){
            friendTitle = "Untitled";
        }

        values.put(FriendTable.COLUMN_FRIEND_TITLE, friendTitle);
        if (friendUri == null) {
            friendUri = getContentResolver().insert(FriendContentProvider.CONTENT_URI, values);
            String firstPart = FriendContentProvider.CONTENT_URI.toString();
            Long id = ContentUris.parseId(friendUri);
            String correctURIs = firstPart + "/" + id;
            Uri correctedUri = Uri.parse(correctURIs);
            try{
                getContentResolver().update(friendUri, values, null, null);
            }
            catch (Exception e){
                friendUri = correctedUri;
            }

        } else {
            getContentResolver().update(friendUri, values, null, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds myItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.addItem) {
//            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    addItem(null);
//                    return false;
//                }
//            });
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(View v) {

        if (myItems.size() < 100) {
            AlertDialog alertToShow = getDialog().create();
            alertToShow.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            alertToShow.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Max Items")
                    .setMessage("You have reached the maximum " +
                            "number of items (100) one note can hold.")
                    .setPositiveButton("OK", null);
            builder.show();
        }

    }

    public AlertDialog.Builder getDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        LinearLayout newFriendBaseLayout = (LinearLayout) li.inflate(R.layout.new_item_dialog, null);

        myNewItemText = (EditText) newFriendBaseLayout.getChildAt(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeKeyboard();
                String text = myNewItemText.getText().toString();
                if (!myItems.contains(text) && !text.isEmpty()) {
                    myItems.add(text);
                    myItemsArrayAdapter.update();
                    myItemsArrayAdapter.notifyDataSetChanged();
                }
                else if(myItems.contains(text)){
                    Toast.makeText(FriendActivity.this, text + " is already added.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeKeyboard();
            }
        })
                .setTitle("New Item");

        builder.setView(newFriendBaseLayout);
        return builder;
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myNewItemText.getWindowToken(), 0);
    }

    public void deleteItem(int position) {
        myItems.remove(position);
        myItemsArrayAdapter.update();
        myItemsArrayAdapter.notifyDataSetChanged();
    }

    public void editItem(final int position) {
        AlertDialog.Builder builder = getDialog();
        myNewItemText.setText(myItems.get(position));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeKeyboard();
                myItems.set(position, myNewItemText.getText().toString());
                myItemsArrayAdapter.update();
                myItemsArrayAdapter.notifyDataSetChanged();
            }
        });
        AlertDialog alertToShow = builder.create();
        alertToShow.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertToShow.show();
        myNewItemText.setSelection(myNewItemText.getText().length());
    }

    public void uncheckAll(View view) {
        myItems.addAll(myFinishedItems);
        myFinishedItems.clear();
        myItemsArrayAdapter.notifyDataSetChanged();
        myFinishedItemsArrayAdapter.notifyDataSetChanged();
    }

    public void deleteFinishedItem(int position) {
        myFinishedItems.remove(position);
        myFinishedItemsArrayAdapter.notifyDataSetChanged();
    }

    public void editFinishedItem(final int position) {
        AlertDialog.Builder builder = getDialog();
        myNewItemText.setText(myFinishedItems.get(position));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myFinishedItems.set(position, myNewItemText.getText().toString());
                myFinishedItemsArrayAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

}
