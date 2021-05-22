package com.barmej.culturalwords;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewQuestion;
    int[] mCulturePictures = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13};

    private String[] mAnswers;
    private String[] mAnswerDescription;

    private int mCurrentPicture;
    private String mCurrentAnswer;
    private String mCurrentAnswerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // يتم تغيير الغة المحفوضه عند بدء الدالة
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SAVE_language, MODE_PRIVATE);
        String language = sharedPreferences.getString(Constants.LANGUAGE_KEY, "");
        if (!language.equals("")) {
            LocaleHelper.setLocale(MainActivity.this, language);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnswers = getResources().getStringArray(R.array.answers);
        mAnswerDescription = getResources().getStringArray(R.array.answer_description);

        imageViewQuestion = findViewById(R.id.image_view_question);
      // عرض صوره عشوائية
        showRandomImage();
    }
   //   Change picture in a random way
    public void changeImageButton(View view) {
        showRandomImage();
    }
   // عرض الاجابه الصحيحه
    public void openAnswerButton(View view) {
        Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
        intent.putExtra(Constants.ANSWER_DETAILS, mCurrentAnswer + ": " + mCurrentAnswerDescription);
        startActivity(intent);
    }
 //  مشاركة الصوره
    public void shareImageButton(View view) {
        // كود مشاركة الصورة هنا
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        intent.putExtra(Constants.IMAGE_VIEW_QUESTION, mCurrentPicture);
        startActivity(intent);
    }
    //  تغيير اللغة
    public void changeLanguageButton(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.chooseLanguage)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        // دالة حفظ الغة عند اختيار احد الغات
                        saveLanguage(language);

                        // هنا يتم تغيير الغة
                        LocaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        dialog.show();
    }
    // دالة حفظ الغة عند اختيار احد الغات
    private void saveLanguage(String language) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SAVE_language, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LANGUAGE_KEY, language);
        editor.apply();
    }
    // عرض صوره عشوائية
    public void showRandomImage() {
        Random random = new Random();
        int randomPictureIndex = random.nextInt(mCulturePictures.length);
        mCurrentPicture = mCulturePictures[randomPictureIndex];
        mCurrentAnswer = mAnswers[randomPictureIndex];
        mCurrentAnswerDescription = mAnswerDescription[randomPictureIndex];
        showImageOnScreen(randomPictureIndex);
    }
  // عرض الصوره على الشاشة
    private void showImageOnScreen(int image) {
        Drawable drawable = ContextCompat.getDrawable(this, mCulturePictures[image]);
        imageViewQuestion.setImageDrawable(drawable);
    }



}