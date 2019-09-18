package com.example.tipcalculator;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable; // for adding text
import android.text.TextWatcher;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // to change the tip percantage
import android.widget.SeekBar.OnSeekBarChangeListener;  // add listner
import android.widget.TextView; // to display text

import org.w3c.dom.Text;

import java.text.NumberFormat; // for the currency format

public class MainActivity extends AppCompatActivity {
    // currency and percent format object
private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
private static  final NumberFormat percentFormat = NumberFormat.getPercentInstance();

private double billAmount = 0.0; // bill amount entered by users
private double percent = 0.15; // tip amount by default
private TextView amountTextView; // show formatted bill amount
private TextView tipTextView; // shows calculated tip amount
private TextView percentTextView; // shows tip percentage
private TextView totalTextView; // shows calculated bill amount
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //using R class to getting each text view  by its id  and cast it
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);


        tipTextView.setText(currencyFormat.format(0)); //set text to 0
        totalTextView.setText(currencyFormat.format(0));

        // set amount edit text by casting editText
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWather);

        ///set percentage seekBar
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    //this method to display and calculate the total amount

    private void calculate(){
        // format percent and display ain the percentTextView
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip and the total
        double tip = billAmount * percent;
        double total = billAmount + tip;

        // display the total and the tip formatted as currency
        totalTextView.setText(currencyFormat.format(total));
        tipTextView.setText(currencyFormat.format(tip));

    }
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        //update percent then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0; // set percent based on progress on the seekBar
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };
    private final TextWatcher amountEditTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                // get bill amount and display formatted value
                billAmount = Double.parseDouble(s.toString()) /100.0;
                amountTextView.setText(currencyFormat.format(billAmount));


            }
            catch(NumberFormatException e){
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate(); // update the tip and the total view
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

