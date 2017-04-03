package gifty.jack.example.com.gifty;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gifty.jack.example.com.gifty.Adapters.CardViewAdapter;
import gifty.jack.example.com.gifty.contentprovider.FriendContentProvider;
import gifty.jack.example.com.gifty.database.FriendTable;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int MAX_NOTES = 200;
    RecyclerView recList;
    public int priorSelectedFriendPosition = -13;
    public int prevCol = -2;
    public int currCol = -9;
    public int selectedFriendPosition = -17;
    public int selectedId = -1;
    public Drawable cardviewBackground;
    public CardViewAdapter myCardViewAdapter;
    public ActionBar myActionBar;
    public boolean noneSelected = true;
    public List<List<String>> myFriendList;
    public List<List<String>> myTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recList = (RecyclerView) findViewById(R.id.cardList_left);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addNewFriend);
        fab.attachToRecyclerView(recList);
        getLoaderManager().initLoader(0, null, this);
    }

    public void deleteIcon(View v){
        deleteNote(selectedId);
    }

    public void backIcon(View v){
        clearFocus();
        actionBarOff(null);
        noneSelected = true;
    }

    public void deleteNote(int id) {
        Uri uri = Uri.parse(FriendContentProvider.CONTENT_URI + "/"
                + id);
        getContentResolver().delete(uri, null, null);
        getLoaderManager().initLoader(0, null, this);
        actionBarOff(null);
        noneSelected = true;
    }

    public void friendFocused(int col, View v){
        currCol = col;
        final CardView card = (CardView) v;
        if(selectedFriendPosition == priorSelectedFriendPosition && col == prevCol && !noneSelected){
            clearFocus();
            card.setEnabled(false);
            card.postDelayed(new Runnable() {
                @Override
                public void run() {
                    card.setEnabled(true);
                }
            }, 300);
            actionBarOff(null);
            noneSelected = true;
        }
        else {
            clearFocus();
            card.setBackgroundDrawable(
                    getResources().getDrawable(R.drawable.highlight_cardview));
            card.setEnabled(false);
            card.postDelayed(new Runnable() {
                @Override
                public void run() {
                    card.setEnabled(true);
                }
            }, 300);
            actionBarOn();
            noneSelected = false;
        }

        priorSelectedFriendPosition = selectedFriendPosition;
        prevCol = col;
    }

    public void clearFocus(){
        for(int i = 0; i < recList.getChildCount(); i++){
            LinearLayout linearLayout = (LinearLayout) recList.getChildAt(i);
            CardView one = (CardView) linearLayout.findViewById(R.id.card_view);
            CardView two = (CardView) linearLayout.findViewById(R.id.card_view2);
            if(cardviewBackground == null){
                cardviewBackground = one.getBackground();
            }
            one.setBackgroundDrawable(cardviewBackground);
            if(two != null) {
                two.setBackgroundDrawable(cardviewBackground);
            }
        }
    }

    public void actionBarOn(){
        if(myActionBar == null) {
            myActionBar = getSupportActionBar();
        }
        View view = getLayoutInflater().inflate(R.layout.highlight_actionbar, null);
        if(myActionBar.getCustomView() == null) {
            myActionBar.setCustomView(view);
        }
        myActionBar.setDisplayShowCustomEnabled(true);
    }

    public void actionBarOff(View v){
        if(myActionBar == null) {
            myActionBar = getSupportActionBar();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myActionBar.setDisplayShowCustomEnabled(false);
            }
        }, 150);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds myItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    public void toNoteActivity(View view) {
        if(((recList.getChildCount() * 2) - 1 > MAX_NOTES) && view != null ) {
            Toast.makeText(this, "You cannot have more than " + MAX_NOTES + " to do lists", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, FriendActivity.class);
            intent.putExtra("source", "newNote");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                startActivity(intent,
                        makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(intent);
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {FriendTable.COLUMN_ID, FriendTable.COLUMN_ITEMS, FriendTable.COLUMN_SLASHED, FriendTable.COLUMN_FRIEND_TITLE};
        return new CursorLoader(this,
                FriendContentProvider.CONTENT_URI, projection, null, null, null);
    }

    public void shareButton(View v){
        shareIntent(noteToString(selectedFriendPosition, currCol));
    }

    public String noteToString(int pos, int col){
        int index = (col==1) ? 0 : 1;
        String title = myTitleList.get(pos).get(index);
        String text = title + ":\n\n";
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(myFriendList.get(pos).get(index));
            for(int i = 0; i < jsonArray.length(); i++){
                text += jsonArray.get(i) + "\n";
            }
        } catch (JSONException ignored) {}
        return text;
    }


    public void shareIntent(String textToShare){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        startActivity(Intent.createChooser(shareIntent, "How do you want to share?"));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myFriendList = new ArrayList<>();
        List<List<Integer>> mainColId = new ArrayList<>();
        List<List<String>> mainSlashes = new ArrayList<>();
        myTitleList = new ArrayList<>();

        List<String> oneTitles = new ArrayList<>();
        List<String> oneList = new ArrayList<>();
        List<Integer> oneListId = new ArrayList<>();
        List<String> oneListSlashes = new ArrayList<>();

        if (data != null) {
            data.moveToFirst();
            for (int c = 0; c < data.getCount(); c++) {
                try {
                    String sItems = data.getString(
                            data.getColumnIndexOrThrow(FriendTable.COLUMN_ITEMS));

                    String sColId = data.getString(
                            data.getColumnIndexOrThrow(FriendTable.COLUMN_ID));

                    String sSlashes = data.getString(
                            data.getColumnIndexOrThrow(FriendTable.COLUMN_SLASHED));

                    String sTitles = data.getString(
                            data.getColumnIndexOrThrow(FriendTable.COLUMN_FRIEND_TITLE));

                    oneTitles.add(sTitles);
                    oneListSlashes.add(sSlashes);
                    oneListId.add(Integer.parseInt(sColId));
                    oneList.add(sItems);
                    data.moveToNext();
                }
                catch(Exception e) {
                    Log.v(TAG, "Exception caught:", e);
                }
            }
        }

        int rows;

        if (oneList.size() % 2 == 0) {
            rows = oneList.size() / 2;
        } else {
            rows = (oneList.size() / 2) + 1;
        }

        for (int i = 0; i < rows; i++) {
            myTitleList.add(new ArrayList<String>());
            myFriendList.add(new ArrayList<String>());
            mainColId.add(new ArrayList<Integer>());
            mainSlashes.add(new ArrayList<String>());
        }

        for (int i = 0, b = 0; i < oneList.size(); i += 2, b++) {
            myFriendList.get(b).add(oneList.get(i));
            if (oneList.size() > i + 1) {
                myFriendList.get(b).add(oneList.get(i + 1));
            }
            mainColId.get(b).add(oneListId.get(i));
            if (oneListId.size() > i + 1) {
                mainColId.get(b).add(oneListId.get(i + 1));
            }
            mainSlashes.get(b).add(oneListSlashes.get(i));
            if (oneListSlashes.size() > i + 1) {
                mainSlashes.get(b).add(oneListSlashes.get(i + 1));
            }
            myTitleList.get(b).add(oneTitles.get(i));
            if (oneTitles.size() > i + 1) {
                myTitleList.get(b).add(oneTitles.get(i + 1));
            }
        }

        myCardViewAdapter = new CardViewAdapter(myFriendList, this, mainColId, mainSlashes, myTitleList);
        recList.setAdapter(myCardViewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(TAG, "onLoaderReset method called");
    }
}