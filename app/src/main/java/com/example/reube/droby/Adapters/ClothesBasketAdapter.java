package com.example.reube.droby.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.reube.droby.Adapters.ClothesAdapter.clothes_basket_cart;
import static com.example.reube.droby.Fragments.ClothesFragment.adapter;
import static com.example.reube.droby.Fragments.ClothesFragment.clothes;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasketAdapter extends ArrayAdapter<Clothes>  {

    public static ArrayList<Clothes> adapterList;
    public static ArrayList<String> finalOutfitList = new ArrayList<String>();
    public static ArrayList<Clothes> singleTop = new ArrayList<Clothes>();
    public static ArrayList<Clothes> singleBottom = new ArrayList<Clothes>();
    public static ArrayList<Clothes> singleOuter = new ArrayList<Clothes>();
    public static ArrayList<Clothes> singleOnepiece = new ArrayList<Clothes>();

    public ClothesBasketAdapter(Activity context, ArrayList<Clothes> clothesBasket) {

        super(context,0,clothesBasket);
        this.adapterList = clothesBasket;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.clothes_basket_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.discard = (ImageView) listItemView.findViewById(R.id.trash);
            viewHolder.check = (CheckBox) listItemView.findViewById(R.id.boxx);
            viewHolder.clothes_image = (ImageView) listItemView.findViewById(R.id.selected_clothes);
            viewHolder.description_clothes = (TextView) listItemView.findViewById(R.id.selected_clothes_descr);
            listItemView.setTag(viewHolder);
            listItemView.setTag(R.id.trash, viewHolder.discard);
            listItemView.setTag(R.id.boxx, viewHolder.check);
            listItemView.setTag(R.id.selected_clothes_descr, viewHolder.description_clothes);

        }
        else{
            viewHolder = (ViewHolder) listItemView.getTag();
        }



        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), position + " Discarded", Toast.LENGTH_SHORT).show();
                finalViewHolder.check.setChecked(false);
                for (int i=0; i<ClothesFragment.clothes.size(); i++){
                    if (ClothesFragment.clothes.get(i).getId()== ClothesBasket.basketList.get(position).getId()){
                        ClothesFragment.clothes.get(i).setSelected(false);
                    }
                }

                ClothesBasket.basketList.remove(position);
                ClothesFragment.adapter.clothes_basket_cart.remove(position);

                notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });


        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                adapterList.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.

                CheckBox checkbox = (CheckBox) buttonView;
                Clothes item = getItem(position);

                if(checkbox.isChecked()){

                    if(finalOutfitList.contains(item.getId())){
                        Toast.makeText(getContext(),"Already Selected",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(item.getCategory_id().equals("Top")){
                            if(!singleTop.contains(item)){
                                singleTop.add(item);
                                finalOutfitList.add(Integer.toString(item.getId()));
                            }
                        }

                        else if(item.getCategory_id().equals("Bottom")){
                            if(!singleBottom.contains(item)){
                                singleBottom.add(item);
                                finalOutfitList.add(Integer.toString(item.getId()));
                            }
                        }

                        else if(item.getCategory_id().equals("Outerwear")){
                            if(!singleOuter.contains(item)){
                                singleOuter.add(item);
                                finalOutfitList.add(Integer.toString(item.getId()));
                            }
                        }
                        else if(item.getCategory_id().equals("Onepiece")){
                            if(!singleOnepiece.contains(item)){
                                singleOnepiece.add(item);
                                finalOutfitList.add(Integer.toString(item.getId()));
                            }
                        }
//                        finalOutfitList.add(Integer.toString(item.getId()));
//                        if(finalOutfitList.size()>=3){
//                            checkbox.setChecked(false);
//                            //Toast.makeText(getContext(),"3 pieces selected already!",Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(),finalOutfitList.get(0)+finalOutfitList.get(1)+finalOutfitList.get(2),Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            finalOutfitList.add(Integer.toString(item.getId()));
//                            //Toast.makeText(getContext(), Integer.toString(finalOutfitList.size()), Toast.LENGTH_SHORT).show();
//                        }
                    }

                }
                else{
                    if(item.getCategory_id().equals("Top")){
                        singleTop.remove(item);

                    }
                    else if(item.getCategory_id().equals("Bottom")){
                        singleBottom.remove(item);
                    }
                    else if(item.getCategory_id().equals("Outerwear")){
                        singleOuter.remove(item);
                    }
                    else if(item.getCategory_id().equals("Onepiece")){
                        singleOnepiece.remove(item);
                    }

                    finalOutfitList.remove(Integer.toString(item.getId()));


                }
                notifyDataSetChanged();

            }
        });



        viewHolder.check.setTag(position); // This line is important.
        viewHolder.check.setChecked(adapterList.get(position).isSelected());
        viewHolder.clothes_image.setImageBitmap(convertToBitmap(getItem(position).getImage()));
        viewHolder.description_clothes.setText(getItem(position).getDescription());

//        Clothes currentClothe = getItem(position);
//
//        TextView clothes_description = (TextView) listItemView.findViewById(R.id.selected_clothes_descr);
//
//        clothes_description.setText(currentClothe.getDescription());
//
//        ImageView clothes_image = (ImageView) listItemView.findViewById(R.id.selected_clothes);
//
//        clothes_image.setImageBitmap(convertToBitmap(currentClothe.getImage()));

        return listItemView;

    }

    private class ViewHolder {

        ImageView discard;
        ImageView clothes_image;
        TextView description_clothes;
        CheckBox check;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    //extra stuff to try to reduce bitmap size but doesnt seems like its working
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}