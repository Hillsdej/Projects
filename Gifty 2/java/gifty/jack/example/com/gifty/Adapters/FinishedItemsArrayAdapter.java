package gifty.jack.example.com.gifty.Adapters;

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

import gifty.jack.example.com.gifty.FriendActivity;
import gifty.jack.example.com.gifty.R;


/**
 * Created by Jack on 03/04/2017.
 */

public class FinishedItemsArrayAdapter extends ArrayAdapter<String> {

    Context myContext;
    ArrayList<String> myArrayList;
    boolean wantsSlash;
    FriendActivity myFriendActivity;

    public FinishedItemsArrayAdapter
            (FriendActivity context, ArrayList<String> arrayList, boolean slashes){
        super(context, R.layout.item_row,arrayList);
        myContext = context;
        myArrayList = arrayList;
        myFriendActivity = context;
        wantsSlash = slashes;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(myContext).inflate(R.layout.item_row, parent, false);
            holder = new ViewHolder();

            holder.itemName = (TextView)convertView.findViewById(R.id.itemText);

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
                myFriendActivity.deleteFinishedItem(position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFriendActivity.editFinishedItem(position);
            }
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView itemName;
        ImageView edit;
        ImageView delete;
    }
}