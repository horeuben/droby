package com.example.reube.droby.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Family on 20/7/2017.
 */

public class NewClothesActivity extends AppCompatActivity {

    ArrayList<String> newItem = new ArrayList<String>();
    DatabaseHandler dbHelper = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clothes);

        ImageView imageView = (ImageView) findViewById(R.id.clothesImage);
        final TextView textView = (TextView) findViewById(R.id.clothesDescription);

        final int singleClothesId = getIntent().getIntExtra("clothesID",0);
        newItem.add(Integer.toString(singleClothesId));
        ArrayList<Clothes> clothesItem = dbHelper.getAllClothes(MainActivity.user, newItem);
        imageView.setImageBitmap(convertToBitmap(clothesItem.get(0).getImage()));
        textView.setText(clothesItem.get(0).getDescription());

        EditText editDescription = (EditText) findViewById(R.id.clothesDescription);
        EditText addTag = (EditText) findViewById(R.id.input_tag);
        ImageView addTagButton = (ImageView) findViewById(R.id.addButton);
        TextView saveChanges = (TextView) findViewById(R.id.save_changes);

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });


        final LinearLayout layout = (LinearLayout) this.findViewById(R.id.layout_current_tags);
        List<String> l = Arrays.asList("Dress","Bright Coloured","Comfortable","item4","item5","item6","item7","item8","item9","item10");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView[] views = new TextView[l.size()];
        for (int i= 0; i<l.size(); i++ ){
            views[i] = new TextView(this);
            views[i].setText(l.get(i));
            views[i].setTextSize(15);
            views[i].setLayoutParams(lp);
            views[i].setBackgroundResource(R.drawable.rounded_corner);
            views[i].setPadding(40,20,40,20);
        }
        populateText(layout, views, this );
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
