package de.beuth_hochschule.s790642.twoactivitiesexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InsertScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_screen);
    }

    public void onBackBtnClick(View view){
        Intent intent = getIntent();
        intent.putExtra("ergebnis", "Das Ergebnis");
        setResult(RESULT_OK, intent);
    }
}
