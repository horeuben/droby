package com.example.reube.droby.Activities;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.Tag;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.Chip;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Family on 9/6/2017.
 */

public class ClothesDescription extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<String> singleItem = new ArrayList<String>();
    private ChipsInput tagNoEditLayout,tagEditLayout;
    private ArrayList<Tag> allTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Details");
        setContentView(R.layout.activity_clothes_description);

        tagNoEditLayout = (ChipsInput)findViewById(R.id.tagsNoEditLayout);
        tagEditLayout = (ChipsInput) findViewById(R.id.tagsEditLayout) ;
        tagEditLayout.setVisibility(View.GONE);
        tagNoEditLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        tagEditLayout.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {
                // chip added
                // newSize is the size of the updated selected chip list
            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                // chip removed
                // newSize is the size of the updated selected chip list
            }

            @Override
            public void onTextChanged(CharSequence text) {
                // text changed
                if (text.toString().contains(" ") && text.toString().length()>1) {
                    String tag = text.toString().replaceAll(" ","");
                    tagEditLayout.addChip(tag,null);

                }
            }

        });
        ImageView imageView = (ImageView) findViewById(R.id.clothes_image);

        final TextView textView = (TextView) findViewById(R.id.clothes_description);

        final int clothes_id = getIntent().getIntExtra("clothesID",0);
        final int position_id = getIntent().getIntExtra("positionID",0);
        singleItem.add(Integer.toString(clothes_id));
        final ArrayList<Clothes> item = db.getAllClothes(MainActivity.user, singleItem);
        imageView.setImageBitmap(convertToBitmap(item.get(0).getImage()));
        textView.setText(item.get(0).getDescription());
        allTags = item.get(0).getTags();
        if (allTags.size()>0){
            for (int i=0;i<allTags.size();i++){
                tagNoEditLayout.addChip(allTags.get(i).getName(),null);
                tagEditLayout.addChip(allTags.get(i).getName(),null);
            }
        }
        final ImageView editClothesItem = (ImageView) findViewById(R.id.editIcon);
        final TextView editDescription = (TextView) findViewById(R.id.clothes_description);
        final TextView saveChanges = (TextView) findViewById(R.id.saveChanges);
        final TextView clickTagsMsg = (TextView) findViewById(R.id.clickTagsMsg);
        
        editClothesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription.setEnabled(true);
                saveChanges.setVisibility(View.VISIBLE);
                editClothesItem.setVisibility(View.GONE);
                clickTagsMsg.setVisibility(View.VISIBLE);
                tagNoEditLayout.setVisibility(View.GONE);
                tagEditLayout.setVisibility(View.VISIBLE);
                List<Chip> removedTags = (List<Chip>) tagNoEditLayout.getSelectedChipList();
                for (int i = removedTags.size()-1; i>=0;i--){
                    tagNoEditLayout.removeChipByLabel(removedTags.get(i).getLabel());
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription.setEnabled(false);
                textView.setText(editDescription.getText());
                ClothesFragment.clothes.get(position_id).setDescription(editDescription.getText().toString());

                saveChanges.setVisibility(View.GONE);
                editClothesItem.setVisibility(View.VISIBLE);
                clickTagsMsg.setVisibility(View.INVISIBLE);
                tagNoEditLayout.setVisibility(View.VISIBLE);
                tagEditLayout.setVisibility(View.GONE);
                List<Chip> tags = (List<Chip>) tagEditLayout.getSelectedChipList();
                allTags = new ArrayList<Tag>();
                int length = tags.size();
                for(int i =0; i<length;i++){
                    String name = tags.get(i).getLabel();
                    tagNoEditLayout.addChip(name,null);
                    Tag tag = new Tag();
                    tag.setName(name);
                    tag.setUser_id(MainActivity.user.getId());
                    tag.setClothes_id( ClothesFragment.clothes.get(position_id).getId());
                    allTags.add(tag);
                }
                ClothesFragment.clothes.get(position_id).setTags(allTags);
                db.updateClothes(ClothesFragment.clothes.get(position_id));
                ClothesFragment.adapter.notifyDataSetChanged();

            }
        });


    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }


    @Override
    public void onBackPressed() {
        ImageView editClothesItem = (ImageView) findViewById(R.id.editIcon);
        if(editClothesItem.getVisibility()==View.GONE){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your changes are not yet saved!\nAre you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ClothesDescription.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            super.onBackPressed();
        }

    }
}
