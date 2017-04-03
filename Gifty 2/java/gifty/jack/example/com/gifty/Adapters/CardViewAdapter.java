package gifty.jack.example.com.gifty.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import gifty.jack.example.com.gifty.MainActivity;
import gifty.jack.example.com.gifty.FriendActivity;
import gifty.jack.example.com.gifty.R;
import gifty.jack.example.com.gifty.contentprovider.FriendContentProvider;


/**
 * Created by Jack on 03/04/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ContactViewHolder> {

    private static final String TAG = CardViewAdapter.class.getSimpleName();

    private List<List<String>> friendList;
    private List<List<Integer>> columnId;
    private List<List<String>> slashes;
    private List<List<String>> titles;
    private Context myContext;
    private MainActivity myMainActivity;

    public CardViewAdapter(List<List<String>> friendList, MainActivity context,
                           List<List<Integer>> columnId, List<List<String>> slashes, List<List<String>> titles) {
        this.friendList = friendList;
        myContext = context;
        this.columnId = columnId;
        this.slashes = slashes;
        myMainActivity = context;
        this.titles = titles;
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    @SuppressLint("NewApi")
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        try {
            JSONArray jsonArray = new JSONArray(friendList.get(i).get(0));
            JSONArray jsonSlashes = new JSONArray(slashes.get(i).get(0));
            try{
                contactViewHolder.myTitle.setText(titles.get(i).get(0));
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(0).toString()) == FriendActivity.SLASHED) {
                    contactViewHolder.myItem1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.myItem1.setText(jsonArray.get(0).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(1).toString()) == FriendActivity.SLASHED) {
                    contactViewHolder.myItem2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.myItem2.setText(jsonArray.get(1).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(2).toString()) == FriendActivity.SLASHED) {
                    contactViewHolder.myItem3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.myItem3.setText(jsonArray.get(2).toString());
            } catch (Exception ignored){}
            try{
                if (Integer.parseInt(jsonSlashes.get(3).toString()) == FriendActivity.SLASHED) {
                    contactViewHolder.myItem4.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                contactViewHolder.myItem4.setText(jsonArray.get(3).toString());
                //set left drawable
            } catch (Exception ignored){}
        } catch (JSONException ignore) {
        }

        contactViewHolder.mCardView.setTag(i);

        contactViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myContext, FriendActivity.class);
                Uri friendUri = Uri.parse(FriendContentProvider.CONTENT_URI + "/" + columnId.get((int) v.getTag()).get(0));
                i.putExtra(FriendContentProvider.CONTENT_ITEM_TYPE, friendUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    myContext.startActivity(i, ActivityOptions
//                            .makeSceneTransitionAnimation(myMainActivity).toBundle());
                    myContext.startActivity(i);
                    //make animation
                } else {
                    myContext.startActivity(i);
                }
            }
        });

        contactViewHolder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.v(TAG, "onLongClick");
                myMainActivity.selectedId = columnId.get((int) v.getTag()).get(0);
                myMainActivity.selectedFriendPosition = (int) v.getTag();
                myMainActivity.friendFocused(1, v);
                //myMainActivity.deleteNote(columnId.get((int) v.getTag()).get(0));
                return false;
            }
        });

        if (friendList.get(i).size() == 2) {
            contactViewHolder.myCardView2.setVisibility(View.VISIBLE);
            try {
                JSONArray jsonArray = new JSONArray(friendList.get(i).get(1));
                JSONArray jsonSlashes = new JSONArray(slashes.get(i).get(1));
                try{
                    contactViewHolder.myTitle2.setText(titles.get(i).get(1));
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(0).toString()) == FriendActivity.SLASHED) {
                        contactViewHolder.myItem12.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.myItem12.setText(jsonArray.get(0).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(1).toString()) == FriendActivity.SLASHED) {
                        contactViewHolder.myItem22.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.myItem22.setText(jsonArray.get(1).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(2).toString()) == FriendActivity.SLASHED) {
                        contactViewHolder.myItem32.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.myItem32.setText(jsonArray.get(2).toString());
                } catch (Exception ignored){}
                try{
                    if (Integer.parseInt(jsonSlashes.get(3).toString()) == FriendActivity.SLASHED) {
                        contactViewHolder.myItem42.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    contactViewHolder.myItem42.setText(jsonArray.get(3).toString());
                    //set left drawable
                } catch (Exception ignored){}
            } catch (JSONException ignore) {
                Log.e(TAG, "Exception caught=", ignore);
            }

            contactViewHolder.myCardView2.setTag(i);

            contactViewHolder.myCardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(myContext, FriendActivity.class);
                    Uri friendUri = Uri.parse(FriendContentProvider.CONTENT_URI + "/" + columnId.get((int) v.getTag()).get(1));
                    i.putExtra(FriendContentProvider.CONTENT_ITEM_TYPE, friendUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    myContext.startActivity(i, ActivityOptions
//                            .makeSceneTransitionAnimation(myMainActivity).toBundle());
                        myContext.startActivity(i);
                        //make animation
                    } else {
                        myContext.startActivity(i);
                    }
                }
            });
            contactViewHolder.myCardView2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myMainActivity.selectedId = columnId.get((int) v.getTag()).get(1);
                    myMainActivity.selectedFriendPosition = (int) v.getTag();
                    myMainActivity.friendFocused(2, v);
                    return false;
                }
            });
        }


    }

    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView myTitle;
        TextView myItem1;
        TextView myItem2;
        TextView myItem3;
        TextView myItem4;
        CardView myCardView2;
        TextView myTitle2;
        TextView myItem12;
        TextView myItem22;
        TextView myItem32;
        TextView myItem42;

        ContactViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            myTitle = (TextView) v.findViewById(R.id.cardview_note_title);
            myItem1 = (TextView) v.findViewById(R.id.friend_item1);
            myItem2 = (TextView) v.findViewById(R.id.friend_item2);
            myItem3 = (TextView) v.findViewById(R.id.friend_item3);
            myItem4 = (TextView) v.findViewById(R.id.friend_item4);
            myCardView2 = (CardView) v.findViewById(R.id.card_view2);
            myTitle2 = (TextView) v.findViewById(R.id.cardview_friend_title2);
            myItem12 = (TextView) v.findViewById(R.id.friend_item12);
            myItem22 = (TextView) v.findViewById(R.id.friend_item22);
            myItem32 = (TextView) v.findViewById(R.id.friend_item32);
            myItem42 = (TextView) v.findViewById(R.id.friend_item42);

        }
    }

}
