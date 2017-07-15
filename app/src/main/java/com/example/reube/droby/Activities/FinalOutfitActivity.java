package com.example.reube.droby.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.R;

/**
 * Created by Family on 10/7/2017.
 */

public class FinalOutfitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Outfit of the Day");
        setContentView(R.layout.activity_final_outfit);

        FrameLayout layout_main = (FrameLayout) findViewById(R.id.finalOutfitFrame);
        layout_main.getForeground().setAlpha(0); // remove foreground colour

        TextView textChoose = (TextView) findViewById(R.id.text_choose);
        textChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stylesFragIntent = new Intent(FinalOutfitActivity.this, MainActivity.class);
                stylesFragIntent.putExtra("test", "Style");
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



    }

    public void popUpWindow(View v){
        try{
            LayoutInflater inflater = (LayoutInflater) FinalOutfitActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.calendar,
                    (ViewGroup) findViewById(R.id.calendar));

            final PopupWindow popWindow = new PopupWindow(layout, 1000, 1500, true);
            popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            final FrameLayout layout_main = (FrameLayout) findViewById(R.id.finalOutfitFrame);
            layout_main.getForeground().setAlpha(220); // set foreground colour

            final CalendarView calendar = (CalendarView) layout.findViewById(R.id.calendar);

            // sets the first day of week according to Calendar.
            // here we set Monday as the first day of the Calendar
            calendar.setFirstDayOfWeek(2);



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
                        dateText.setText("Save this for today");
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
