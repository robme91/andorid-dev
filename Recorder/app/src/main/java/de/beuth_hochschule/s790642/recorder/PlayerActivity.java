package de.beuth_hochschule.s790642.recorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private String audioFileName;
    private ListView fileList;
    private Intent audioFileIntent;
    Player player;
    Button playBtn;
    Button pauseBtn;
    Button stopBtn;
    TextView selectedFileNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        player = new Player(getApplicationContext());

        //init Layout Elements
        TextView listLabel = (TextView) findViewById(R.id.player_textView_listLabel);
        listLabel.setText(R.string.player_textView_listLabel_text);
        selectedFileNameView = (TextView) findViewById(R.id.player_textView_selectedFile);
        playBtn = (Button) findViewById(R.id.player_btn_playFile);
        pauseBtn = (Button) findViewById(R.id.player_btn_pauseFile);
        stopBtn = (Button) findViewById(R.id.player_btn_stopFile);
        fileList = (ListView) findViewById(R.id.player_listView_fileList);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        fileList.setOnItemClickListener(this);

        playBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        stopBtn.setEnabled(false);
    }


    @Override
    protected void onResume(){
        super.onResume();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, player.getAudioFiles());
        fileList.setAdapter(listAdapter);

        //getIntent from recorder if there is one
        audioFileIntent = getIntent();
        final String intentExtra = audioFileIntent.getStringExtra(getString(R.string.intent_recordListener_filePath));
        if(audioFileIntent != null && intentExtra != null && !intentExtra.isEmpty()){
            this.audioFileName = audioFileIntent.getStringExtra(getString(R.string.intent_recordListener_filePath));
            selectedFileNameView.setText(this.audioFileName);
            playBtn.setEnabled(true);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        player.resetPlayer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.player_btn_playFile):
                stopBtn.setEnabled(true);
                pauseBtn.setEnabled(true);
                playBtn.setEnabled(false);
                player.playFile(this.audioFileName);
                break;
            case(R.id.player_btn_pauseFile):
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                player.pauseFile();
                break;
            case(R.id.player_btn_stopFile):
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(false);
                player.stopFile();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(player.isPlaying()){
            player.stopFile();
            playBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
            stopBtn.setEnabled(false);
        }
        final String fileName = (String) parent.getItemAtPosition(position);
        this.audioFileName = fileName;
        selectedFileNameView.setText(fileName);
        playBtn.setEnabled(true);
    }
}
