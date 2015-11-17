package de.beuth_hochschule.s790642.twoactivitiesexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InsertScreen extends AppCompatActivity {
    private EditText editName;
    private EditText editAge;
    private EditText editGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_screen);

        editName = (EditText) findViewById(R.id.insert_screen_edit_name);
        editAge = (EditText) findViewById(R.id.insert_screen_edit_age);
        editGender = (EditText) findViewById(R.id.insert_screen_edit_gender);

        Intent intent = getIntent();

        editName.setText(intent.getStringExtra(getString(R.string.main_intent_extra_nameView)));
        editAge.setText(intent.getStringExtra(getString(R.string.main_intent_extra_ageView)));
        editGender.setText(intent.getStringExtra(getString(R.string.main_intent_extra_genderView)));

        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    editName.getText().clear();
                }
            }
        });
        editAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editAge.getText().clear();
                }
            }
        });
        editGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editGender.getText().clear();
                }
            }
        });
    }


    public void onSaveBtnClick(View view){
        Intent intent = getIntent();
        intent.putExtra(getString(R.string.insertScreen_bundle_name), editName.getText().toString());
        intent.putExtra(getString(R.string.insertScreen_bundle_age), editAge.getText().toString());
        intent.putExtra(getString(R.string.insertScreen_bundle_gender), editGender.getText().toString());
        setResult(RESULT_OK, intent);
    }
}
