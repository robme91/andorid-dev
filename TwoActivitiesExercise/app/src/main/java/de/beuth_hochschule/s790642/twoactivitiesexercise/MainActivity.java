package de.beuth_hochschule.s790642.twoactivitiesexercise;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView authorView;
    private TextView nameView;
    private TextView ageView;
    private TextView genderView;
    final int activity_insert_screen = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authorView = (TextView) findViewById(R.id.main_textView_author);
        authorView.setText(R.string.main_textView_author);

        nameView = (TextView) findViewById(R.id.Main_TextView_Name);
        ageView = (TextView) findViewById(R.id.Main_TextView_Alter);
        genderView = (TextView) findViewById(R.id.main_textView_gender);

        nameView.setText(R.string.main_textView_name);
        ageView.setText(R.string.main_textView_alter);
        genderView.setText(R.string.main_textView_gender);
    }

    public void onBtnClick(View view){
        //Aufruf der anderen Activity
        startActivityForResult(new Intent(this, InsertScreen.class), activity_insert_screen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == activity_insert_screen){
            Bundle result = data.getExtras();
            nameView.setText(result.getString(getString(R.string.insertScreen_bundle_name)));
            ageView.setText(result.getString(getString(R.string.insertScreen_bundle_age)));
            genderView.setText(result.getString(getString(R.string.insertScreen_bundle_gender)));
        }
    }
}
