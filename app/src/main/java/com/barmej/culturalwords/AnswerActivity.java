package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    TextView textViewAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        textViewAnswer=findViewById(R.id.text_view_answer);

        String answerDetails = getIntent().getStringExtra("ANSWER_DETAILS");
        if (!answerDetails.equals("")){
            textViewAnswer.setText(answerDetails);
        }

    }

    public void backButton(View view){
        finish();
    }

}