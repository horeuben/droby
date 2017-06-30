package com.example.reube.droby.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.Clothes;
import com.example.reube.droby.R;

import java.util.ArrayList;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    public ArrayList<Clothes> clothes_cart = new ArrayList<Clothes>();
    private ArrayList<Clothes> clothes;


    public ClothesAdapter(Activity context, ArrayList<Clothes> clothes) {

        super(context,0,clothes);
        this.clothes = clothes;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.clothes_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.description);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.add_to_cart);
            viewHolder.button = (CheckBox) convertView.findViewById(R.id.btn1);
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.description, viewHolder.title);
            convertView.setTag(R.id.btn1, viewHolder.button);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                clothes.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.


                CheckBox checkbox = (CheckBox) buttonView;
                Clothes item = getItem(position);
                //clothes_cart.add(item);
                //Toast.makeText(getContext(), "position " + position , Toast.LENGTH_SHORT).show();
                if(checkbox.isChecked()){

                    if(clothes_cart.contains(item)){
                        Toast.makeText(getContext(),"Already Selected",Toast.LENGTH_SHORT);
                    }
                    else{
                        clothes_cart.add(item);
                        Toast.makeText(getContext(), getItem(position).getClothesDescription() + " selected", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    clothes_cart.remove(item);
                }
            }

        });


        viewHolder.button.setTag(position); // This line is important.

        viewHolder.title.setText(clothes.get(position).getClothesDescription());
        viewHolder.button.setChecked(clothes.get(position).isSelected());



        Clothes currentClothe = getItem(position);

        TextView clothes_description = (TextView) convertView.findViewById(R.id.description);

        clothes_description.setText(currentClothe.getClothesDescription());

        ImageView clothes_image = (ImageView) convertView.findViewById(R.id.clothes_image);

        clothes_image.setImageResource(currentClothe.getImageResourceId());

        return convertView;
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        CheckBox button;
    }



}