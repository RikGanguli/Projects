//Name - Rik Ganguli Biwas, CWID - A20513718
package com.rikganguli.tipsplit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //declaring variables
    private EditText field1;

    private EditText field2;

    private TextView calc1;

    private TextView calc2;

    private TextView calc3;

    private RadioGroup g1;

    private double totalWithTip;

    private double tipAmount;

    private double totalPerPerson;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing all the variables declared above
        field1 = findViewById(R.id.originalBillField);
        field2 = findViewById(R.id.peopleField);
        calc1 = findViewById(R.id.tipField);
        calc2 = findViewById(R.id.totalField);
        calc3 = findViewById(R.id.perPersonField);
        g1 = findViewById(R.id.rg1);
    }

    //Calculates the total bill amount with tip
    public void calculateTotalWithTip(View v){

        //save the total bill amount without tip
        String f1 = field1.getText().toString();

        //if no amount is entered the radio button is unchecked
        if (f1.isEmpty()) {
            g1.clearCheck();
            return;
        }

        double bill = Double.parseDouble(f1);

        //actions for when a certain radio button is clicked
        switch(v.getId()){

            case(R.id.tipOne):
                tipAmount = bill * 0.12;
                break;

            case(R.id.tipTwo):
                tipAmount = bill * 0.15;
                break;

            case(R.id.tipThree):
                tipAmount = bill * 0.18;
                break;

            case(R.id.tipFour):
                tipAmount = bill * 0.2;
                break;

            default:
                tipAmount = 0;
        }

        //display the tip amount
        calc1.setText(String.format("$%.2f",tipAmount));

        totalWithTip = bill + tipAmount;

        //display the total amount with tip
        calc2.setText(String.format("$%.2f",totalWithTip));
    }

    //Calculates the total amount per person
    public void calculateTotalPerPerson(View v){

        //save the number of people
        String f2 = field2.getText().toString();

        //do nothing if no value is input
        if(f2.isEmpty())
            return;

        int numOfPeople = Integer.parseInt(f2);

        //Show a message if a value of 0 is entered as an input to the number of people field
        if (numOfPeople == 0){
            Toast.makeText(this, "Please enter a positive number!", Toast.LENGTH_SHORT).show();
            return;
        }

        //calculate the total amount per person
        totalPerPerson = totalWithTip / numOfPeople;

        //round all values up to the nearest cent so that the amount of all people combines is always greater than the total amount
        df.setRoundingMode(RoundingMode.UP);
        String a = df.format(totalPerPerson);

        totalPerPerson = Double.parseDouble(a);

        //Additional message shown
        Toast.makeText(this, "Total per person is: $" + totalPerPerson, Toast.LENGTH_LONG).show();

        //display the total amount per person
        calc3.setText(String.format("$%.2f", totalPerPerson));
    }

    //Clear all the fields and uncheck radio buttons
    public void clearScreen(View v){
        field1.getText().clear();
        g1.clearCheck();
        calc1.setText("");
        calc2.setText("");
        field2.getText().clear();
        calc3.setText("");
    }

    //Save the data when screen orientation is changed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String calcOne = calc1.getText().toString();
        String calcTwo = calc2.getText().toString();
        String calcThree = calc3.getText().toString();
        String blank1 = field1.getText().toString();
        String blank2 = field2.getText().toString();

        outState.putString("val1", calcOne);
        outState.putString("val2", calcTwo);
        outState.putString("val3", calcThree);
        outState.putString("blank1", blank1);
        outState.putString("blank2", blank2);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        calc1.setText(savedInstanceState.getString("val1"));
        calc2.setText(savedInstanceState.getString("val2"));
        calc3.setText(savedInstanceState.getString("val3"));
        field1.setText(savedInstanceState.getString("blank1"));
        field2.setText(savedInstanceState.getString("blank2"));
    }
}
