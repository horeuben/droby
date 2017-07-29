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
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesAdapter extends ArrayAdapter<Clothes> {

    public ArrayList<Clothes> clothes_cart = new ArrayList<Clothes>();
    public static ArrayList<String> clothes_basket_cart = new ArrayList<String>();
    private ArrayList<Clothes> clothes;
    private ArrayList<Clothes> clothesCopy;
    private ArrayList<Clothes> filteredClothes;
    DatabaseHandler dbHandler = new DatabaseHandler(getContext());

    public ClothesAdapter(Activity context, ArrayList<Clothes> clothes) {

        super(context, 0, clothes);
        this.clothes = clothes;
        clothesCopy = new ArrayList<Clothes>();
        clothesCopy.addAll(clothes);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.clothes_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.description);
            viewHolder.button = (CheckBox) convertView.findViewById(R.id.btn1);
            viewHolder.image_clothes = (ImageView) convertView.findViewById(R.id.clothes_image);
            viewHolder.image_filter = (ImageView) convertView.findViewById(R.id.blackFilter);
            //viewHolder.image_clothes.getForeground().setAlpha(0);
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.description, viewHolder.title);
            convertView.setTag(R.id.btn1, viewHolder.button);
            convertView.setTag(R.id.blackFilter, viewHolder.image_filter);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                clothes.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.


                CheckBox checkbox = (CheckBox) buttonView;
                Clothes item = getItem(position);

                if (checkbox.isChecked()) {
                    if (clothes_cart.contains(item)) {
                        Toast.makeText(getContext(), "Already Selected", Toast.LENGTH_SHORT);
                    } else {
                        clothes_basket_cart.add(Integer.toString(item.getId()));
                        clothes_cart.add(item);
//                        Toast.makeText(getContext(), Integer.toString(item.getLocation()),Toast.LENGTH_SHORT).show();
                    }

                } else {
                    clothes_basket_cart.remove(Integer.toString(item.getId()));
                    clothes_cart.remove(item);

                }
            }

        });

        viewHolder.button.setTag(position); // This line is important.
        viewHolder.button.setChecked(clothes.get(position).isSelected());
        viewHolder.button.setClickable(!clothes.get(position).getCheckboxClickable());

        viewHolder.title.setText(clothes.get(position).getDescription());
        viewHolder.image_clothes.setImageBitmap(convertToBitmap(clothes.get(position).getImage()));
        viewHolder.image_filter.setImageResource(clothes.get(position).getForegroundColour());



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
        TextView title;
        CheckBox button;
        ImageView image_clothes;
        ImageView image_filter;
    }

    //recursive blind checks removal for everything inside a View
    public void removeAllChecks(ViewGroup vg) {
        View v = null;
        for (int i = 0; i < vg.getChildCount(); i++) {
            try {
                v = vg.getChildAt(i);
                ((CheckBox) v).setChecked(false);
            } catch (Exception e1) { //if not checkBox, null View, etc
                try {
                    removeAllChecks((ViewGroup) v);
                } catch (Exception e2) { //v is not a view group
                    continue;
                }
            }
        }

    }

    private Bitmap convertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        clothes.clear();
        if (charText.length() == 0) {
            clothes.addAll(clothesCopy);

        } else {
            for (Clothes clothesDetail : clothesCopy) {
                if (charText.length() != 0 && clothesDetail.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clothes.add(clothesDetail);
                } else if (charText.length() != 0 && clothesDetail.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    clothes.add(clothesDetail);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterByCategory(int arrayPosition) {
        clothes.clear();
        filteredClothes = new ArrayList<Clothes>();
        if (arrayPosition == 0) {
            clothes.addAll(clothesCopy);
        } else if (arrayPosition == 1) {
            for (int i = 0; i < clothesCopy.size(); i++) {
                if (clothesCopy.get(i).getCategory_id().equals("Top")) {
                    clothes.add(clothesCopy.get(i));
                }
            }

        } else if (arrayPosition == 2) {
            for (int i = 0; i < clothesCopy.size(); i++) {
                if (clothesCopy.get(i).getCategory_id().equals("Bottom")) {
                    clothes.add(clothesCopy.get(i));
                }
            }
        } else if (arrayPosition == 3) {
            for (int i = 0; i < clothesCopy.size(); i++) {
                if (clothesCopy.get(i).getCategory_id().equals("Outerwear")) {
                    clothes.add(clothesCopy.get(i));
                }
                clothes.addAll(filteredClothes);
            }
        }
        else if (arrayPosition == 4) {
            for (int i = 0; i < clothesCopy.size(); i++) {
                if (clothesCopy.get(i).getCategory_id().equals("Onepiece")) {
                    clothes.add(clothesCopy.get(i));
                }
                clothes.addAll(filteredClothes);
            }
        }

        notifyDataSetChanged();
    }


}