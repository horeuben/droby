package com.example.reube.droby.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.reube.droby.Activities.AddImageTestActivity;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    public ArrayList<Clothes> clothes_cart = new ArrayList<Clothes>();
    public ArrayList<String> clothes_basket_cart = new ArrayList<String>();
    private ArrayList<Clothes> clothes;
    private ArrayList<Clothes> filteredClothes;


    public ClothesAdapter(Activity context, ArrayList<Clothes> clothes) {

        super(context,0,clothes);
        this.clothes = clothes;
        filteredClothes = new ArrayList<Clothes>();
        filteredClothes.addAll(clothes);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.clothes_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.description);
            //viewHolder.image = (ImageView) convertView.findViewById(R.id.add_to_cart);
            viewHolder.button = (CheckBox) convertView.findViewById(R.id.btn1);
            viewHolder.image_clothes = (ImageView) convertView.findViewById(R.id.clothes_image);
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
                        clothes_basket_cart.add(Integer.toString(item.getId()));
                        clothes_cart.add(item);
                        Toast.makeText(getContext(), getItem(position).getDescription() + " selected", Toast.LENGTH_SHORT);
                    }

                }
                else{
                    clothes_basket_cart.remove(Integer.toString(item.getId()));
                    clothes_cart.remove(item);
                }
            }

        });


        viewHolder.button.setTag(position); // This line is important.
        viewHolder.button.setChecked(clothes.get(position).isSelected());

        viewHolder.title.setText(clothes.get(position).getDescription());
        viewHolder.image_clothes.setImageBitmap(convertToBitmap(clothes.get(position).getImage()));


//        Clothes currentClothe = getItem(position);
//
//        TextView clothes_description = (TextView) convertView.findViewById(R.id.description);
//        clothes_description.setText(currentClothe.getDescription());
//
//        ImageView clothes_image = (ImageView) convertView.findViewById(R.id.clothes_image);
//
//        clothes_image.setImageBitmap(convertToBitmap(currentClothe.getImage()));

        return convertView;
    }

    private class ViewHolder {

        ImageView image;
        TextView title;
        CheckBox button;
        ImageView image_clothes;
    }

    //recursive blind checks removal for everything inside a View
    public void removeAllChecks(ViewGroup vg) {
        View v = null;
        for(int i = 0; i < vg.getChildCount(); i++){
            try {
                v = vg.getChildAt(i);
                ((CheckBox)v).setChecked(false);
            }
            catch(Exception e1){ //if not checkBox, null View, etc
                try {
                    removeAllChecks((ViewGroup)v);
                }
                catch(Exception e2){ //v is not a view group
                    continue;
                }
            }
        }

    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        clothes.clear();
        if (charText.length() == 0) {
            clothes.addAll(filteredClothes);

        } else {
            for (Clothes clothesDetail : filteredClothes) {
                if (charText.length() != 0 && clothesDetail.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clothes.add(clothesDetail);
                }

                else if (charText.length() != 0 && clothesDetail.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clothes.add(clothesDetail);
                }
            }
        }
        notifyDataSetChanged();
    }


}