package com.example.reube.droby.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Family on 19/7/2017.
 */

public class ClothesAdapterTest extends ArrayAdapter<Clothes> {

    public ArrayList<Clothes> clothes_cart = new ArrayList<Clothes>();
    private ArrayList<Clothes> clothes;
    private ArrayList<Clothes> filteredClothes;
    public static ArrayList<String> clothes_basket_cart = new ArrayList<String>();

    public ClothesAdapterTest(Activity context, ArrayList<Clothes> clothes) {

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
            //viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.add_to_cart);
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
                        clothes_basket_cart.add(Integer.toString(item.getId()));
                        clothes_cart.add(item);
                        Toast.makeText(getContext(), getItem(position).getDescription() + " selected", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    clothes_basket_cart.remove(Integer.toString(item.getId()));
                    clothes_cart.remove(item);
                }
            }

        });


        viewHolder.button.setTag(position); // This line is important.

        viewHolder.title.setText(clothes.get(position).getDescription());
        viewHolder.button.setChecked(clothes.get(position).isSelected());



        Clothes currentClothe = getItem(position);

        TextView clothes_description = (TextView) convertView.findViewById(R.id.description);

        clothes_description.setText(currentClothe.getDescription());

        ImageView clothes_image = (ImageView) convertView.findViewById(R.id.clothes_image);

        clothes_image.setImageBitmap(convertToBitmap(currentClothe.getImage()));

        return convertView;
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        CheckBox button;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }




}