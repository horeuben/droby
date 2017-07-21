package com.example.reube.droby.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Adapters.ClothesBasketAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Family on 10/7/2017.
 */

public class FinalOutfitActivity extends AppCompatActivity {

    private ArrayList<Clothes> outfitClothes;
    private DatabaseHandler mDbHelper;
    private ArrayList<String> clothesListId = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Outfit of the Day");
        setContentView(R.layout.activity_final_outfit);

        ClothesBasketAdapter.finalOutfitList.clear(); //clear lists from clothes basket
        ClothesBasketAdapter.singleTop.clear();
        ClothesBasketAdapter.singleBottom.clear();
        ClothesBasketAdapter.singleOuter.clear();

        FrameLayout layout_main = (FrameLayout) findViewById(R.id.finalOutfitFrame);
        layout_main.getForeground().setAlpha(0); // remove foreground colour

        ImageView clothingTop = (ImageView) findViewById(R.id.top);
        ImageView clothingBottom = (ImageView) findViewById(R.id.bottomClothing);
        ImageView clothingOuterwear = (ImageView) findViewById(R.id.outerwear);

        clothesListId = (ArrayList<String>)getIntent().getExtras().getSerializable("OutfitList");
        mDbHelper = new DatabaseHandler(this);
        outfitClothes = mDbHelper.getSelectedClothesIdTest(clothesListId);
        for(int i=0; i<outfitClothes.size();i++){
            if (outfitClothes.get(i).getCategory_id().equals("Top")){
                clothingTop.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
            }
            else if(outfitClothes.get(i).getCategory_id().equals("Bottom")){
                clothingBottom.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
            }
            else if(outfitClothes.get(i).getCategory_id().equals("Outerwear")){
                clothingOuterwear.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
            }
        }

        TextView textChoose = (TextView) findViewById(R.id.text_choose);
        textChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClothesFragment.adapter.clothes_basket_cart.clear();
                ClothesBasket.basketAdapter.finalOutfitList.clear();
                Intent stylesFragIntent = new Intent(FinalOutfitActivity.this, MainActivity.class);
                stylesFragIntent.putExtra("test", "Style");
                stylesFragIntent.putExtra("outfitClothes", clothesListId);
                startActivity(stylesFragIntent);
            }
        });

        ImageView calenderImage = (ImageView) findViewById(R.id.calenderIcon);
        calenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow(v);
            }
        });

        Switch viewSwitch = (Switch) findViewById(R.id.view_switch);
        viewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout r1 = (RelativeLayout)findViewById(R.id.relative1);
                RelativeLayout r2 = (RelativeLayout)findViewById(R.id.relative2);
                RelativeLayout r3 = (RelativeLayout)findViewById(R.id.relative3);
                if(isChecked){
                    r1.setVisibility(RelativeLayout.VISIBLE);
                    r2.setVisibility(RelativeLayout.VISIBLE);
                    r3.setVisibility(RelativeLayout.VISIBLE);
                }
                else{
                    r1.setVisibility(RelativeLayout.GONE);
                    r2.setVisibility(RelativeLayout.GONE);
                    r3.setVisibility(RelativeLayout.GONE);
                }

            }
        });

        TextView cancel_button = (TextView) findViewById(R.id.text_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClothesBasket.basketList!=null){
                    for (int i=0; i<ClothesBasket.basketList.size(); i++){
                        ClothesBasket.basketList.get(i).setSelected(true);
                        ClothesBasket.basketList.get(i).setSelected(false);
                    }

                    ClothesBasket.basketAdapter.notifyDataSetChanged();
                }
                finish();
            }
        });


    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        if (ClothesBasket.basketList!=null){
//            for (int i=0; i<ClothesBasket.basketList.size(); i++){
//                ClothesBasket.basketList.get(i).setSelected(true);
//                ClothesBasket.basketList.get(i).setSelected(false);
//            }
//
//            ClothesBasket.basketAdapter.notifyDataSetChanged();
//        }
    }

    //calendar pop-up
    private void popUpWindow(View v){
        try{
            LayoutInflater inflater = (LayoutInflater) FinalOutfitActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.calendar,
                    (ViewGroup) findViewById(R.id.calendar));

            final PopupWindow popWindow = new PopupWindow(layout, 1000, 1500, true);
            popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            popWindow.setElevation(5);
            final FrameLayout layout_main = (FrameLayout) findViewById(R.id.finalOutfitFrame);
            layout_main.getForeground().setAlpha(220); // set foreground colour

            final CalendarView calendar = (CalendarView) layout.findViewById(R.id.calendar);

            // sets the first day of week according to Calendar.
            // here we set Monday as the first day of the Calendar
            calendar.setFirstDayOfWeek(1);




            TextView cancel = (TextView) layout.findViewById(R.id.cancelText);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_main.getForeground().setAlpha(0); //remove foreground
                    popWindow.dismiss();
                }
            });

            TextView okButton = (TextView) layout.findViewById(R.id.okText);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextView dateText = (TextView) findViewById(R.id.calendarText);
                    if(dateText.getText().toString().equals(getResources().getString(R.string.SaveForAnotherDay))){
                        dateText.setText("Save this for: Today");
                    }
                    layout_main.getForeground().setAlpha(0); //remove foreground
                    popWindow.dismiss();
                }
            });

            final TextView dateText = (TextView) findViewById(R.id.calendarText);
            final String[] dateSelcted = new String[1];
            //sets the listener to be notified upon selected date change.
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    dateSelcted[0] = dayOfMonth + "/" + month + "/" + year;
                    dateText.setText("Save this for: " + dateSelcted[0]);
                }
            });
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
