package com.example.reube.droby.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Family on 10/7/2017.
 */

public class FinalOutfitActivity extends AppCompatActivity {

    private ArrayList<Clothes> outfitClothes;
    private DatabaseHandler mDbHelper;
    private ArrayList<String> clothesListId = new ArrayList<String>();
    String topID;
    Clothes selectedTop = new Clothes();
    Clothes selectedBottom = new Clothes();
    Clothes selectedOuter = new Clothes();
    ProgressDialog pd;
    ArrayList<String> ID = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Outfit of the Day");
        setContentView(R.layout.activity_final_outfit);

        ClothesBasketAdapter.finalOutfitList.clear(); //clear lists from clothes basket
        ClothesBasketAdapter.singleTop.clear();
        ClothesBasketAdapter.singleBottom.clear();
        ClothesBasketAdapter.singleOuter.clear();
        ClothesBasketAdapter.singleOnepiece.clear();

        FrameLayout layout_main = (FrameLayout) findViewById(R.id.finalOutfitFrame);
        layout_main.getForeground().setAlpha(0); // remove foreground colour

        final ImageView clothingTop = (ImageView) findViewById(R.id.top);
        final ImageView clothingBottom = (ImageView) findViewById(R.id.bottomClothing);
        final ImageView clothingOuterwear = (ImageView) findViewById(R.id.outerwear);
        final ImageView clothingOnepiece = (ImageView) findViewById(R.id.onepiece);
        final TextView msg_top = (TextView) findViewById(R.id.msgTop);
        final TextView msg_bottom = (TextView) findViewById(R.id.msgBottom);
        final TextView msg_outer = (TextView) findViewById(R.id.msgOuter);
        final TextView recommend_top = (TextView) findViewById(R.id.recommendTop);
        final TextView recommend_bottom = (TextView) findViewById(R.id.recommendBottom);
        final TextView recommend_outer = (TextView) findViewById(R.id.recommendOuter);


        //setting clothing based on clothes basket selection
        clothesListId = (ArrayList<String>)getIntent().getExtras().getSerializable("OutfitList");
        mDbHelper = new DatabaseHandler(this);
        outfitClothes = mDbHelper.getSelectedClothesIdTest(clothesListId);
        for(int i=0; i<outfitClothes.size();i++){
            if (outfitClothes.get(i).getCategory_id().equals("Top")){
                clothingTop.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
                msg_top.setVisibility(View.GONE);
                recommend_top.setVisibility(View.GONE);
                selectedTop = outfitClothes.get(i);
            }
            else if(outfitClothes.get(i).getCategory_id().equals("Bottom")){
                clothingBottom.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
                msg_bottom.setVisibility(View.GONE);
                recommend_bottom.setVisibility(View.GONE);
                selectedBottom = outfitClothes.get(i);
            }
            else if(outfitClothes.get(i).getCategory_id().equals("Outerwear")){
                clothingOuterwear.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
                msg_outer.setVisibility(View.GONE);
                recommend_outer.setVisibility(View.GONE);
                selectedOuter = outfitClothes.get(i);
            }
            else if(outfitClothes.get(i).getCategory_id().equals("Onepiece")){
                clothingOnepiece.setImageBitmap(convertToBitmap(outfitClothes.get(i).getImage()));
                clothingOnepiece.setVisibility(View.VISIBLE);
                clothingTop.setVisibility(View.GONE);
                clothingBottom.setVisibility(View.GONE);
                msg_top.setVisibility(View.GONE);
                recommend_top.setVisibility(View.GONE);
                msg_bottom.setVisibility(View.GONE);
                recommend_bottom.setVisibility(View.GONE);
            }
        }

        //Onclick behaviour for recommend buttons
        recommend_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    try{
                        ID = new SyncColour().execute(selectedBottom.getId()).get();
                        Toast.makeText(getApplicationContext(), ID.get(0),Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                    }
                }
                ArrayList<Clothes> tops = mDbHelper.getAllClothes(MainActivity.user, ID);
                clothingTop.setImageBitmap(convertToBitmap(tops.get(0).getImage()));
                msg_top.setVisibility(View.GONE);
                recommend_top.setVisibility(View.GONE);
                clothesListId.add(Integer.toString(tops.get(0).getId()));
            }
        });

        recommend_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    try{
                        ID = new SyncColour().execute(selectedTop.getId()).get();
                        Toast.makeText(getApplicationContext(), ID.get(0),Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                    }
                }
                ArrayList<Clothes> bottoms = mDbHelper.getAllClothes(MainActivity.user, ID);
                clothingBottom.setImageBitmap(convertToBitmap(bottoms.get(1).getImage()));
                msg_bottom.setVisibility(View.GONE);
                recommend_bottom.setVisibility(View.GONE);
                clothesListId.add(Integer.toString(bottoms.get(1).getId()));
            }
        });

        recommend_outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Clothes> outers = mDbHelper.getAllClothes(MainActivity.user, "Outerwear");
                if(outers.size()==0){
                    msg_outer.setVisibility(View.GONE);
                    recommend_outer.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No outerwear available",Toast.LENGTH_SHORT).show();
                }
                else{
                    clothingOuterwear.setImageBitmap(convertToBitmap(outers.get(0).getImage()));
                    msg_outer.setVisibility(View.GONE);
                    recommend_outer.setVisibility(View.GONE);
                    clothesListId.add(Integer.toString(outers.get(0).getId()));
                }
            }
        });

        //choose button
        TextView textChoose = (TextView) findViewById(R.id.text_choose);
        textChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clothingTop.getDrawable() instanceof BitmapDrawable && clothingBottom.getDrawable() instanceof BitmapDrawable){
                    ClothesFragment.adapter.clothes_basket_cart.clear();
                    ClothesBasket.basketAdapter.finalOutfitList.clear();
                    Intent stylesFragIntent = new Intent(FinalOutfitActivity.this, MainActivity.class);
                    stylesFragIntent.putExtra("test", "Style");
                    stylesFragIntent.putExtra("outfitClothes", clothesListId);
                    startActivity(stylesFragIntent);
                }
                else if(clothingOnepiece.getDrawable() instanceof BitmapDrawable){
                    ClothesFragment.adapter.clothes_basket_cart.clear();
                    ClothesBasket.basketAdapter.finalOutfitList.clear();
                    Intent stylesFragIntent = new Intent(FinalOutfitActivity.this, MainActivity.class);
                    stylesFragIntent.putExtra("test", "Style");
                    stylesFragIntent.putExtra("outfitClothes", clothesListId);
                    startActivity(stylesFragIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Top and Bottom must be selected!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //calendar popup
        ImageView calenderImage = (ImageView) findViewById(R.id.calenderIcon);
        calenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow(v);
            }
        });

        //switch on-of behaviours
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

        //cancel button
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

    private class SyncColour extends AsyncTask<Integer, String, ArrayList<String>> {
//        FinalOutfitActivity Activity;
//        public SyncColour(FinalOutfitActivity Activity){
//
//        }
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            mDbHelper = new DatabaseHandler(getApplicationContext());
            ArrayList<String> a = (mDbHelper.syncColour(params[0]));
            mDbHelper.close();
            return a;
        }


        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
                topID = result.get(0);
                Toast.makeText(getApplicationContext(),topID,Toast.LENGTH_SHORT).show();
            }

        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(FinalOutfitActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
