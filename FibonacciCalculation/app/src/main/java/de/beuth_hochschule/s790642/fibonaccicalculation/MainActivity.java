package de.beuth_hochschule.s790642.fibonaccicalculation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startCalcBtn;
    Button cancelButton;
    EditText fibNumberInput;
    TextView fibOutput;
    AsyncTask<Integer, Integer, String> calcFibTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fibNumberInput = (EditText) findViewById(R.id.main_editText_fibInput);
        fibOutput = (TextView) findViewById(R.id.main_textView_fibOutput);

        startCalcBtn = (Button) findViewById(R.id.main_btn_calc);
        startCalcBtn.setText(R.string.main_calcBtn_text);
        startCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberInput = fibNumberInput.getText().toString();
                if (!numberInput.isEmpty()) {
                    calcFibTask = new AsyncFibonacciCalc();
                    calcFibTask.execute(Integer.valueOf(numberInput));
                    startCalcBtn.setEnabled(false);
                    cancelButton.setEnabled(true);
                } else {
                    fibOutput.setText("Häh?");
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setEnabled(false);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcFibTask.cancel(true);
            }
        });
    }

    public long calcFib(final int number){
        if(number <= 2) {
            return (number>0) ? 1:0;
        }
        else
            return calcFib(number - 1) + calcFib(number - 2);
    }

    private class AsyncFibonacciCalc extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            String ergebnis = "";
            for(int i = 0; i <= params[0]; i++){
                long result = calcFib(i);
                ergebnis = ergebnis + "\n" + result;
                if (isCancelled()) break;
            }
            return ergebnis;
        }

        @Override
        protected void onPostExecute(String output){
            TextView outputView = (TextView) findViewById(R.id.main_textView_fibOutput);
            outputView.setText(output);
            startCalcBtn.setEnabled(true);
            cancelButton.setEnabled(false);
        }

        @Override
        protected void onCancelled (String result){
            Toast.makeText(getApplicationContext(), "Cancelled Calculation", Toast.LENGTH_SHORT).show();
            startCalcBtn.setEnabled(true);
            cancelButton.setEnabled(false);
        }

    }


}
