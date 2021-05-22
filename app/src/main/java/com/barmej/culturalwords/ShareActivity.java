package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

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

        questionImage = getIntent().getIntExtra("IMAGE_VIEW__QUESTION", 0);
        imageViewQuestion.setImageResource(questionImage);

        // حفظ بيانات عنوان المشاركة
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_ShareTitle", MODE_PRIVATE);
        if (!sharedPreferences.equals("")) {
            editTextTitle.setText(sharedPreferences.getString("ShareTitle_KEY", ""));
        }
    }
    //  تقوم هذه الداله بمشاركة الصورة عبر التطبيقات الاخرى
    public void shareImageQuestion(View view) {
        String title = editTextTitle.getText().toString();
        // حفض عنوان النشاركة
        saveShareTitle(editTextTitle.getText().toString());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        bitmapDrawable = (BitmapDrawable) imageViewQuestion.getDrawable();
        bitmap = bitmapDrawable.getBitmap();
        File file = new File(getExternalCacheDir() + "/" + "Beautiful image" + ".png");
        Intent intent;
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_TEXT, title);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(intent, "Share image via: "));
    }
    // حفض عنوان النشاركة
    private void saveShareTitle(String title) {
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_ShareTitle", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ShareTitle_KEY", title);
        editor.apply();
    }
}