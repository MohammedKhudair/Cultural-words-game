package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ShareActivity extends AppCompatActivity {
    EditText editTextTitle;
    ImageView imageViewQuestion;
    int questionImage;

    BitmapDrawable bitmapDrawable;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imageViewQuestion = findViewById(R.id.share_image_view_question);
        editTextTitle = findViewById(R.id.edit_text_share_title);

        questionImage = getIntent().getIntExtra(Constants.IMAGE_VIEW_QUESTION, 0);
        imageViewQuestion.setImageResource(questionImage);

        // حفظ بيانات عنوان المشاركة
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SAVE_ShareTitle, MODE_PRIVATE);
        if (!sharedPreferences.equals("")) {
            editTextTitle.setText(sharedPreferences.getString(Constants.ShareTitle_KEY, ""));
        }
    }

    //  تقوم هذه الداله بمشاركة الصورة عبر التطبيقات الاخرى
    public void shareImageQuestion(View view) {
        String title = editTextTitle.getText().toString();
        // حفض عنوان المشاركة
        saveShareTitle(editTextTitle.getText().toString());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageViewQuestion.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap) {
        File imageFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imageFolder.mkdirs();
            File file = new File(imageFolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.barmej.culturalwords.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    // حفض عنوان النشاركة
    private void saveShareTitle(String title) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SAVE_ShareTitle, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.ShareTitle_KEY, title);
        editor.apply();
    }
}