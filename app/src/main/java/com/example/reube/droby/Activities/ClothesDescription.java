package com.example.reube.droby.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Family on 9/6/2017.
 */

public class ClothesDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Details");

        setContentView(R.layout.activity_clothes_description);

        ImageView imageView = (ImageView) findViewById(R.id.clothes_image);

        TextView textView = (TextView) findViewById(R.id.clothes_description);

        //int image_Id = getIntent().getIntExtra("imageId",0);
        byte[] image_Id = getIntent().getByteArrayExtra("imageId");
        String description = getIntent().getStringExtra("imageDescription");
        imageView.setImageBitmap(convertToBitmap(image_Id));
        textView.setText(description);



        LinearLayout layout = (LinearLayout) this.findViewById(R.id.tagslayout);
        List<String> l = Arrays.asList("Dress","Bright Coloured","Comfortable","item4","item5","item6","item7","item8");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView[] views = new TextView[l.size()];
        for (int list= 0; list<l.size(); list++ ){
            views[list] = new TextView(this);
            views[list].setText(l.get(list));
            views[list].setTextSize(15);
            views[list].setLayoutParams(lp);
            views[list].setBackgroundResource(R.drawable.rounded_corner);
            views[list].setPadding(40,30,40,30);

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

        int widthSoFar = 0;

        for (int i = 0 ; i < views.length ; i++ ){
            LinearLayout LL = new LinearLayout(mContext);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            views[i].measure(0,0);
            params = new LinearLayout.LayoutParams(views[i].getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 0, 0);

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
