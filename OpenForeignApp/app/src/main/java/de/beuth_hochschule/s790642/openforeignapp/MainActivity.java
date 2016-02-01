package de.beuth_hochschule.s790642.openforeignapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText urlInput;
    RadioButton radioBtnUrl;
    RadioButton radioBtnCamera;
    RadioButton radioBtnNote;
    private int checkedRadioBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Init UI Components
        RadioGroup radioGrp = (RadioGroup) findViewById(R.id.main_radioGroup);
        checkedRadioBtnId = R.id.main_radioBtn_url;
        radioBtnUrl =(RadioButton) findViewById(R.id.main_radioBtn_url);
        radioBtnCamera =(RadioButton) findViewById(R.id.main_radioBtn_camera);
        radioBtnNote =(RadioButton) findViewById(R.id.main_radioBtn_note);
        radioBtnUrl.setText(R.string.main_radioBtn_url_caption);
        radioBtnCamera.setText(R.string.main_radioBtn_cam_caption);
        radioBtnNote.setText(R.string.main_radioBtn_note_caption);

        TextView ownName = (TextView) findViewById(R.id.main_textArea_name);
        ownName.setText(R.string.main_textArea_name_text);
        urlInput = (EditText) findViewById(R.id.main_editText_URL);
        urlInput.setText(R.string.main_textView_url_initText);

        final Button btn_openUrl = (Button) findViewById(R.id.main_button_openUrl);
        btn_openUrl.setText(R.string.main_radioBtn_url_caption);

        //Build Listeners
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_radioBtn_url:
                        btn_openUrl.setText(R.string.main_radioBtn_url_caption);
                        urlInput.setVisibility(View.VISIBLE);
                        checkedRadioBtnId = R.id.main_radioBtn_url;
                        break;
                    case R.id.main_radioBtn_camera:
                        btn_openUrl.setText(R.string.main_radioBtn_cam_caption);
                        urlInput.setVisibility(View.INVISIBLE);
                        checkedRadioBtnId = R.id.main_radioBtn_camera;
                        break;
                    case R.id.main_radioBtn_note:
                        btn_openUrl.setText(R.string.main_radioBtn_note_caption);
                        urlInput.setVisibility(View.INVISIBLE);
                        checkedRadioBtnId = R.id.main_radioBtn_note;
                        break;
                    default:
                        break;
                }
            }
        });

        btn_openUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (checkedRadioBtnId){
                    case R.id.main_radioBtn_url:
                        Uri url = Uri.parse(String.valueOf(urlInput.getText()));
                        Intent intent = new Intent(Intent.ACTION_VIEW, url);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.errorMessage_badUrl) + urlInput.getText(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.main_radioBtn_camera:

                        break;
                    case R.id.main_radioBtn_note:
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), getString(R.string.errorMessage_noChoice), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
