package com.example.reube.droby.Activities;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Adapters.ClothesAdapter;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.Database.Tag;
import com.example.reube.droby.R;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.Chip;
import com.pchmn.materialchips.model.ChipInterface;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.button;

/**
 * Created by Family on 2/7/2017.
 */
public class AddImageTestActivity extends AppCompatActivity {

    private EditText description;
    private ImageView pic;
    private DatabaseHandler mDbHelper;
    private String des;
    private String clothesColour;
    private ClothesAdapter data;
    private Clothes dataModel;
    private Bitmap bp;
    private byte[] photo;
    private ArrayList<Clothes> clothesList;
    private Spinner spinner;
    private ChipsInput chipsInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_test);
//        chiptextview = (RecipientEditTextView)findViewById(R.id.testview);
//        chiptextview.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        chiptextview.setAdapter(new BaseRecipientAdapter(this));
        mDbHelper = new DatabaseHandler(this);

        pic = (ImageView) findViewById(R.id.pic);
        description = (EditText) findViewById(R.id.txt1);
        chipsInput = (ChipsInput)findViewById(R.id.chips_input);

        chipsInput.addChipsListener(new ChipsInput.ChipsListener() {
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
                if (text.toString().contains(" ")) {
                    String tag = text.toString().replaceAll(" ","");
                    chipsInput.addChip(tag,null);

                }
            }

        });

        TextView text = (TextView) findViewById(R.id.displayinfo);
        text.setText(mDbHelper.getClothes());
        spinner = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Top");
        list.add("Bottom");
        list.add("Outerwear");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Name edit text is empty, Enter name", Toast.LENGTH_LONG).show();
                } else {
                    AddClothes();
                    TextView text = (TextView) findViewById(R.id.displayinfo);
                    text.setText(mDbHelper.getClothes());

                }

            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.pic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

//    public void buttonClicked(View v) {
//        int id = v.getId();
//
//        switch (id) {
//
//            case R.id.save:
//
//                if (description.getText().toString().trim().equals("") || colour.getText().toString().trim().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Name edit text is empty, Enter name", Toast.LENGTH_LONG).show();
//                } else {
//                    AddClothes();
//                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//            case R.id.display:
//
//                //displayDatabaseInfo();
//                break;
//            case R.id.pic:
//                selectImage();
//                break;
//        }
//    }

    public void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri choosenImage = data.getData();

                    if (choosenImage != null) {

                        bp = decodeUri(choosenImage, 200);
                        pic.setImageBitmap(bp);
                    }
                }
        }
    }


    //Convert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

    private void getValues() {
        des = description.getText().toString();
        photo = profileImage(bp);
    }

    private void AddClothes() {

        getValues();
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id",MainActivity.user.getId());
        values.put(DatabaseHandler.KEY_DESCRIPTION, des);
        values.put(DatabaseHandler.KEY_IMAGE_BLOB, profileImage(bp));
        values.put("category_id", spinner.getSelectedItem().toString());
        long newRowId = db.insert(DatabaseHandler.TABLE_CLOTHES, null, values);

        List<Chip> tags = (List<Chip>) chipsInput.getSelectedChipList();
        for(int i =0; i <tags.size();i++){
            Tag tag = new Tag();
            tag.setUser_id(MainActivity.user.getId());
            tag.setName(tags.get(i).getLabel());
            tag.setClothes_id((int)newRowId);
            mDbHelper.createTag(tag);
        }

        //Toast.makeText(getApplicationContext(),"Added data, user_id is:"+MainActivity.user.getId() , Toast.LENGTH_SHORT).show();
    }
}
