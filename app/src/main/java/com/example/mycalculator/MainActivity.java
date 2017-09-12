package com.example.mycalculator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

    private double valueOne = Double.NaN;
    private double valueTwo;
    private Spinner mySpinner;

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';

    private char CURRENT_ACTION = '0';
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##########", new DecimalFormatSymbols(Locale.US));

    EditText editText;

    String coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);


        final Button button0 = (Button) findViewById(R.id.buttonZero);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "0");
            }
        });

        final Button button1 = (Button) findViewById(R.id.buttonOne);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "1");
            }
        });

        final Button button2 = (Button) findViewById(R.id.buttonTwo);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "2");
            }
        });

        final Button button3 = (Button) findViewById(R.id.buttonThree);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "3");
            }
        });

        final Button button4 = (Button) findViewById(R.id.buttonFour);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "4");
            }
        });

        final Button button5 = (Button) findViewById(R.id.buttonFive);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "5");
            }
        });

        final Button button6 = (Button) findViewById(R.id.buttonSix);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "6");
            }
        });

        final Button button7 = (Button) findViewById(R.id.buttonSeven);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "7");
            }
        });

        final Button button8 = (Button) findViewById(R.id.buttonEight);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "8");
            }
        });

        final Button button9 = (Button) findViewById(R.id.buttonNine);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + "9");
            }
        });


        final Button buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText() + ".");
            }
        });

        final Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                CURRENT_ACTION = ADDITION;
                editText.setText(decimalFormat.format(valueOne) + "+");
            }
        });

        final Button buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                CURRENT_ACTION = SUBTRACTION;
                editText.setText(decimalFormat.format(valueOne) + "-");
            }
        });

        final Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                CURRENT_ACTION = MULTIPLICATION;
                editText.setText(decimalFormat.format(valueOne) + "*");
            }
        });

        final Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                CURRENT_ACTION = DIVISION;
                editText.setText(decimalFormat.format(valueOne) + "/");
            }
        });

        final Button buttonPercent = (Button) findViewById(R.id.buttonPercent);
        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                valueOne = valueOne/100;
                editText.setText(decimalFormat.format(valueOne));
            }
        });

        final Button buttonEqual = (Button) findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySpinner = (Spinner) findViewById(R.id.spinner1);
                coin = mySpinner.getSelectedItem().toString();

                if(!coin.equals("$") && CURRENT_ACTION == '0'){
                    new MyTask().execute(coin);
                }

                compute();
                if(Double.isNaN(valueOne))
                    valueOne = 0;
                editText.setText(decimalFormat.format(valueOne));
                CURRENT_ACTION = '0';
                valueTwo = Double.NaN;
                valueOne = Double.NaN;
            }
        });

        final Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_ACTION = '0';
                valueOne = Double.NaN;
                valueTwo = Double.NaN;
                editText.setText("");
            }
        });

//
//        final Button buttonPower = (Button) findViewById(R.id.buttonPower);
//        buttonPower.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                compute();
//                valueOne = java.lang.Math.pow(valueOne,2);
//                editText.setText(decimalFormat.format(valueOne));
//            }
//        });

        final Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //compute();
                if(!Double.isNaN(valueOne)) {



                    if(editText.getText().toString().length()<=1) {
                        valueOne = Double.NaN;
                        editText.setText("");
                    }//if

                    //in case of only valueOne
                    if(editText.getText().toString().length() == decimalFormat.format(valueOne).length()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() -1));
                        valueOne =  Double.parseDouble(editText.getText().toString());
                        CURRENT_ACTION = '0';
                    }//if

                    //in case of only valueOne and operator
                    if(editText.getText().toString().length() == decimalFormat.format(valueOne).length() +1) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() -1).replace(",", "."));
                        valueOne =  Double.parseDouble(editText.getText().toString());
                        CURRENT_ACTION = '0';
                    }//if

                    //in case of 2nd value
                    if(editText.getText().toString().length() > decimalFormat.format(valueOne).length() +1){
                        String s = editText.getText().toString().substring(0, editText.getText().toString().length() -1).substring(0, editText.getText().toString().length() -1);

                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() -1));
                    }//if
                }
                else { //if nan
                    try {
                        if(editText.getText().toString().length()>1) {
                            //valueOne = Double.parseDouble(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                            editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        }else{
                            editText.setText("");
                        }
                    }
                    catch (Exception e){}
                }//if
            }

        });


    }//onCreate


    public void compute() {
        if(!Double.isNaN(valueOne)) {
            if(editText.getText().toString().length() > decimalFormat.format(valueOne).length() +1)
                valueTwo = Double.parseDouble(editText.getText().toString().substring(editText.getText().toString().lastIndexOf(CURRENT_ACTION) + 1));

            if(CURRENT_ACTION == ADDITION)
                valueOne = this.valueOne + valueTwo;
            else if(CURRENT_ACTION == SUBTRACTION)
                valueOne = this.valueOne - valueTwo;
            else if(CURRENT_ACTION == MULTIPLICATION)
                valueOne = this.valueOne * valueTwo;
            else if(CURRENT_ACTION == DIVISION)
                valueOne = this.valueOne / valueTwo;

        }
        else {
            try {
                valueOne = Double.parseDouble(editText.getText().toString());
            }
            catch (Exception e){}
        }//if

    }//compute


    public class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String myString = params[0];

            StringBuilder urlString = new StringBuilder();
            urlString.append("http://api.fixer.io/latest?symbols="+myString);


            HttpURLConnection urlConnection = null;
            BufferedReader bReader = null;
            InputStream inStream = null;
            URL url = null;
            JSONObject object = null;

            try
            {
                url = new URL(urlString.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();
                inStream = urlConnection.getInputStream();
                bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null)
                    response += temp;
                if(null != bReader){  bReader.close();}
                if(null != inStream){  inStream.close();}
                if(null != urlConnection){  urlConnection.disconnect();}
                object = (JSONObject) new JSONTokener(response).nextValue();

            }
            catch (Exception e)
            {
                return e.toString();
            }

            return (object.toString());
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONObject json = null;
            String coin_value = "1";
            try {
                json = new JSONObject(result);
                JSONObject rates = json.getJSONObject("rates");
                coin_value=rates.getString(coin);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            valueOne = valueOne*Double.parseDouble(coin_value);
            editText.setText(decimalFormat.format(valueOne));
        }
    }//myTask


}//class

