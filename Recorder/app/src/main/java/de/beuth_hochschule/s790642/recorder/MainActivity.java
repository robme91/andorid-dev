package de.beuth_hochschule.s790642.recorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    Button recordBtn;
    Button stopRecordBtn;
    Button pauseRecordBtn;
    Button openPlayerBtn;
    EditText fileNameInput;
    MyRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Recorder
        recorder = new MyRecorder(getApplicationContext());

        //init EditText for filename
        fileNameInput = (EditText) findViewById(R.id.main_editText_fileName);
        fileNameInput.setHint(R.string.main_editText_fileName); //set placceholder
        fileNameInput.setOnEditorActionListener(this);

        //Init Btns and set Captions
        recordBtn = (Button) findViewById(R.id.main_btn_record);
        recordBtn.setText(R.string.main_btn_record_text);
        stopRecordBtn = (Button) findViewById(R.id.main_btn_stopRecord);
        stopRecordBtn.setText(R.string.main_btn_stopRecord_text);
        pauseRecordBtn = (Button) findViewById(R.id.main_btn_pauseRecord);
        pauseRecordBtn.setText(R.string.main_btn_pauseRecord_text);
        openPlayerBtn = (Button) findViewById(R.id.main_btn_openPlayer);
        openPlayerBtn.setText(R.string.main_btn_openPlayer);


        //set Listenrs
        recordBtn.setOnClickListener(this);
        stopRecordBtn.setOnClickListener(this);
        pauseRecordBtn.setOnClickListener(this);
        openPlayerBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //disable all recorder btns to protect for overriding files
        recordBtn.setEnabled(false);
        stopRecordBtn.setEnabled(false);
        pauseRecordBtn.setEnabled(false);

        fileNameInput.setText("");
        fileNameInput.setEnabled(true);
        openPlayerBtn.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_record:
                final String fileName = this.fileNameInput.getText().toString().replaceAll("\\s","");
                if(!fileName.isEmpty()){
                    stopRecordBtn.setEnabled(true);
                    pauseRecordBtn.setEnabled(true);
                    recordBtn.setEnabled(false);
                    openPlayerBtn.setEnabled(false);
                    fileNameInput.setEnabled(false);
                    recorder.startRecord(fileName);
                }else{
                    Toast.makeText(this, "FileName must not be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.main_btn_stopRecord:
                recorder.stopRecord();
                //open Player --> give FileName to play recorded file directly
                Intent intentPlayer = new Intent(this, PlayerActivity.class);
                intentPlayer.putExtra(this.getString(R.string.intent_recordListener_filePath), recorder.getAudioFileName());
                startActivity(intentPlayer);
                break;
            case R.id.main_btn_pauseRecord:
                //TODO implement pause
                recordBtn.setEnabled(true);
                pauseRecordBtn.setEnabled(false);
                break;
            case R.id.main_btn_openPlayer:
                Intent intentOpenPlayer = new Intent(getApplicationContext(), PlayerActivity.class);
                startActivity(intentOpenPlayer);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { //check if FileName exists and change focus if it's not
        if(actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            if(recorder.isFileNameAlreadyExistent(v.getText().toString().replaceAll("\\s",""))){
                Toast.makeText(getApplicationContext(), "FileName already chosen", Toast.LENGTH_LONG).show();
            }else{
                // hide virtual Keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                recordBtn.requestFocus();
                recordBtn.setEnabled(true);
            }
            return true;
        }
        return false;
    }

}
