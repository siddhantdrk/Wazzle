package com.app.wazzle;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    int TWITTER = 0;
    int YOUTUBE = 1;
    int SPOTIFY = 2;
    int INSTAGRAM = 3;
    int SOUNCLOUD = 4;
    int CAMER_PERMISSION_REQUEST_CODE = 101;
    int GALLERY_PERMISSION_REQUEST_CODE = 102;
    Spinner spin;
    String photoType;
    ImageView iv_profile_cover;
    CircleImageView iv_profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        iv_profile_cover = findViewById(R.id.iv_profile_cover);
        iv_profile_pic = findViewById(R.id.iv_profile_pic);

        findViewById(R.id.iv_edit_cover).setOnClickListener(view -> {
            photoType = "COVER";
            selectImage();
        });

        findViewById(R.id.frame_profile_pic).setOnClickListener(view -> {
            photoType = "PROFILE";
            selectImage();
        });

        findViewById(R.id.mcv_twitter).setOnClickListener(view -> {
            showSocialLinkInputDialog(TWITTER);
        });

        findViewById(R.id.mcv_youtube).setOnClickListener(view -> {
            showSocialLinkInputDialog(YOUTUBE);
        });

        findViewById(R.id.mcv_spotify).setOnClickListener(view -> {
            showSocialLinkInputDialog(SPOTIFY);
        });

        findViewById(R.id.mcv_instagram).setOnClickListener(view -> {
            showSocialLinkInputDialog(INSTAGRAM);
        });

        findViewById(R.id.mcv_soundcloud).setOnClickListener(view -> {
            showSocialLinkInputDialog(SOUNCLOUD);
        });

        spin = findViewById(R.id.color_spinner);
        spin.setOnItemSelectedListener(this);

        ArrayList<String> colorsName = new ArrayList<>();
        colorsName.add("White");
        colorsName.add("Black");
        colorsName.add("Grey");
        colorsName.add("Yellow");
        colorsName.add("Red");
        colorsName.add("Blue");
        colorsName.add("Green");
        colorsName.add("Brown");
        colorsName.add("Pink");
        colorsName.add("Orange");
        colorsName.add("Pruple");

        ArrayList<String> colorsCode = new ArrayList<>();
        colorsCode.add("#FFFFFFFF");
        colorsCode.add("#000000");
        colorsCode.add("#808080");
        colorsCode.add("#FFFF00");
        colorsCode.add("#ff0000");
        colorsCode.add("#00008B");
        colorsCode.add("#00ff00");
        colorsCode.add("#964B00");
        colorsCode.add("#FFC0CB");
        colorsCode.add("#FFA500");
        colorsCode.add("#800080");
        CustomAdapter aa = new CustomAdapter(this, android.R.layout.simple_spinner_item, colorsName, colorsCode);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        findViewById(R.id.add_more_fav_book).setOnClickListener(view -> {
            if (findViewById(R.id.txt_top_book2).getVisibility() == View.GONE) {
                findViewById(R.id.txt_top_book2).setVisibility(View.VISIBLE);
            } else if (findViewById(R.id.txt_top_book3).getVisibility() == View.GONE) {
                findViewById(R.id.txt_top_book3).setVisibility(View.VISIBLE);
                findViewById(R.id.add_more_fav_book).setVisibility(View.GONE);
            } else if (findViewById(R.id.txt_top_book2).getVisibility() == View.VISIBLE
                    && findViewById(R.id.txt_top_book3).getVisibility() == View.VISIBLE) {
                Toast.makeText(EditProfile.this,
                        "You can only add maximum 3 books", Toast.LENGTH_LONG).show();
            }
        });
    }

    void showSocialLinkInputDialog(int socialType) {
        Dialog dialog = new Dialog(EditProfile.this);
        dialog.setContentView(R.layout.social_input_dialog);
        dialog.findViewById(R.id.cancel_dialog).setOnClickListener(view -> dialog.dismiss());

        TextInputEditText tv_social_input = dialog.findViewById(R.id.tv_social_input);
        switch (socialType) {
            case 0:
                tv_social_input.setCompoundDrawables(null, null,
                        ContextCompat.getDrawable(EditProfile.this, R.drawable.ic_twitter), null);
                break;
            case 1:
                tv_social_input.setCompoundDrawables(null, null,
                        ContextCompat.getDrawable(EditProfile.this, R.drawable.ic_youtube), null);
                break;
            case 2:
                tv_social_input.setCompoundDrawables(null, null,
                        ContextCompat.getDrawable(EditProfile.this, R.drawable.ic_spotify), null);
                break;
            case 3:
                tv_social_input.setCompoundDrawables(null, null,
                        ContextCompat.getDrawable(EditProfile.this, R.drawable.ic_instagram), null);
                break;
            case 4:
                tv_social_input.setCompoundDrawables(null, null,
                        ContextCompat.getDrawable(EditProfile.this, R.drawable.ic_soundcloud), null);
                break;
        }
        dialog.findViewById(R.id.cancel_dialog).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.save_link).setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    checkCamerPermission();
                } else if (options[item].equals("Choose from Gallery")) {
                    checkGalleryPermission();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void checkCamerPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            openCamera();
        }

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                CAMER_PERMISSION_REQUEST_CODE);
    }

    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestGalleryPermission();
        } else {
            openGallery();
        }

    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                GALLERY_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMER_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getApplicationContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getApplicationContext(), "Gallery Permission Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Gallery Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        if (photoType.equals("COVER")) {
                            iv_profile_cover.setImageBitmap(photo);
                        } else {
                            iv_profile_pic.setImageBitmap(photo);
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        Uri selectedImage = result.getData().getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                        if (photoType.equals("COVER")) {
                            iv_profile_cover.setImageBitmap(thumbnail);
                        } else {
                            iv_profile_pic.setImageBitmap(thumbnail);
                        }
                    }
                }
            });

    void openGallery() {
        Intent pictureActionIntent = null;

        pictureActionIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(
                pictureActionIntent);
    }

    void openCamera() {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        cameraActivityResultLauncher.launch
                (intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}