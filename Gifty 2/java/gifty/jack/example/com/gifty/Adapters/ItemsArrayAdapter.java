package gifty.jack.example.com.gifty.Adapters;

/**
 * Created by Jack on 03/04/2017.
 */
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import gifty.jack.example.com.gifty.FriendActivity;
import gifty.jack.example.com.gifty.R;


public class ItemsArrayAdapter extends ArrayAdapter<String> {

    private Context myContext;
    private ArrayList<String> myArrayList;
    private boolean wantsSlash;
    private FriendActivity myFriendActivity;

    private HashMap<String, Integer> myIdMap = new HashMap<>();

    public ItemsArrayAdapter
            (FriendActivity context, ArrayList<String> arrayList, boolean slashes){
        super(context, R.layout.item_row,arrayList);
        myContext = context;
        myArrayList = arrayList;
        myFriendActivity = context;
        wantsSlash = slashes;
        update();
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(myContext).inflate(R.layout.item_row, Objects.requireNonNull(parent), false);
            holder = new ViewHolder();

            holder.itemName = (TextView)convertView.findViewById(R.id.itemText);

            for (int i = 0; i < myArrayList.size(); ++i) {
                myIdMap.put(myArrayList.get(i), i);
            }

            holder.delete = (ImageView)convertView.findViewById(R.id.delete_item);
            holder.edit = (ImageView)convertView.findViewById(R.id.edit_item);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(myArrayList.get(position));
        if(wantsSlash){
            holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFriendActivity.deleteItem(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFriendActivity.editItem(position);
            }
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView itemName;
        ImageView edit;
        ImageView delete;
    }

    @Override
    public long getItemId(int position) {
        int INVALID_ID = -1;
        Long idToReturn = (long) INVALID_ID;
        if (position >= 0 && position <= myIdMap.size()-1) {
            try {
                String item = getItem(position);
                idToReturn = (long) myIdMap.get(item);
            }
            catch(Exception ignored){}
        }

        return idToReturn;
    }

    public void update(){
        for (int i = 0; i < myArrayList.size(); ++i) {
            myIdMap.put(myArrayList.get(i), i);
        }
    }

    @Override
    public boolean hasStableIds() {
        return android.os.Build.VERSION.SDK_INT < 20;
    }

}