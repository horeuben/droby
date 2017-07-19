package com.example.reube.droby.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Family on 9/6/2017.
 */

public class ClothesDescription extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<String> singleItem = new ArrayList<String>();
    boolean editOn = false;
    private static ArrayList<String> l = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Details");

        setContentView(R.layout.activity_clothes_description);

        ImageView imageView = (ImageView) findViewById(R.id.clothes_image);

        final TextView textView = (TextView) findViewById(R.id.clothes_description);

        final int clothes_id = getIntent().getIntExtra("clothesID",0);
        singleItem.add(Integer.toString(clothes_id));
        ArrayList<Clothes> item = db.getAllClothes(MainActivity.user, singleItem);
        imageView.setImageBitmap(convertToBitmap(item.get(0).getImage()));
        textView.setText(item.get(0).getDescription());

        final LinearLayout layout = (LinearLayout) this.findViewById(R.id.tagslayout);
        List<String> l = Arrays.asList("Dress","Bright Coloured","Comfortable","item4","item5","item6","item7","item8","item9","item10");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView[] views = new TextView[l.size()];
        for (int list= 0; list<l.size(); list++ ){
            views[list] = new TextView(this);
            views[list].setText(l.get(list));
            views[list].setTextSize(15);
            views[list].setLayoutParams(lp);
            views[list].setBackgroundResource(R.drawable.rounded_corner);
            views[list].setPadding(40,20,40,20);
        }
        populateText(layout, views, this );

        final ImageView editClothesItem = (ImageView) findViewById(R.id.editIcon);
        final TextView editDescription = (TextView) findViewById(R.id.clothes_description);
        final TextView saveChanges = (TextView) findViewById(R.id.saveChanges);
        final TextView clickTagsMsg = (TextView) findViewById(R.id.clickTagsMsg);


//        editDescription.setOnClickListener(new View.OnClickListener() { //set blinking cursor to ON only when keyboard is displayed
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == editDescription.getId())
//                {
//                    editDescription.setCursorVisible(true);
//                }
//            }
//        });

        editClothesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription.setEnabled(true);
                saveChanges.setVisibility(View.VISIBLE);
                editClothesItem.setVisibility(View.GONE);
                clickTagsMsg.setVisibility(View.VISIBLE);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription.setEnabled(false);
                textView.setText(editDescription.getText());
                ClothesFragment.clothes.get(clothes_id-1).setDescription(editDescription.getText().toString());
                saveChanges.setVisibility(View.GONE);
                editClothesItem.setVisibility(View.VISIBLE);
                clickTagsMsg.setVisibility(View.GONE);
                db.updateClothes(ClothesFragment.clothes.get(clothes_id-1));
                ClothesFragment.clothes = db.getAllClothes(MainActivity.user);
                ClothesFragment.adapter.notifyDataSetChanged();
            }
        });

        for(int i =0; i<views.length; i++){
            final int finalI = i;
            views[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //l.remove(finalI);
                    Toast.makeText(getApplicationContext(), "PRESSED",Toast.LENGTH_SHORT).show();
                    //populateText(layout, views, getApplicationContext());
                }
            });
        }







    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    private void populateText(LinearLayout ll, View[] views , Context mContext) {
        Display display = getWindowManager().getDefaultDisplay();
        ll.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(mContext);
        newLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 140; //including margins

        for (int i = 0 ; i < views.length ; i++ ){
            LinearLayout LL = new LinearLayout(mContext);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            views[i].measure(0,0);
            params = new LinearLayout.LayoutParams(views[i].getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 20, 0, 0);

            LL.addView(views[i], params);
            LL.measure(0, 0);
            widthSoFar += views[i].getMeasuredWidth();// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
                ll.addView(newLL);

                newLL = new LinearLayout(mContext);
                newLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setGravity(Gravity.LEFT);
                params = new LinearLayout.LayoutParams(LL
                        .getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
                newLL.addView(LL);
            }
        }
        ll.addView(newLL);
    }

}
