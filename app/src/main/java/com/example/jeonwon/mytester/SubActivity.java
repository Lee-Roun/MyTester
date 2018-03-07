package com.example.jeonwon.mytester;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JeonWon on 2018-03-07.
 */

public class SubActivity extends AppCompatActivity{

    private static String TAG = "phptest_MainActivity";

    private EditText mEditTextName;
    private EditText mEditTextId;
    private EditText mEditTextPw;
    private EditText mEditTextPwChk;
    private EditText mEditTextAge;
    private EditText mEditTextPhone;
    private EditText mEditTextEmail;
    private EditText mEditTextGender;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Button btnCancel = (Button)findViewById(R.id.buttonCancel);
        Button btnSend = (Button)findViewById(R.id.buttonSend);

        mEditTextName = (EditText)findViewById(R.id.editTextName);
        mEditTextId = (EditText)findViewById(R.id.editTextId);
        mEditTextPw = (EditText)findViewById(R.id.editTextPw);
        mEditTextPwChk = (EditText)findViewById(R.id.editTextPWChk);
        mEditTextAge = (EditText)findViewById(R.id.editTextAge);
        mEditTextPhone = (EditText)findViewById(R.id.editTextPhone);
        mEditTextEmail = (EditText)findViewById(R.id.editTextEmail);
        mEditTextGender = (EditText)findViewById(R.id.editTextGender);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(view.getId()){
                    case R.id.buttonCancel:

                        finish();
                        break;
                    case R.id.buttonSend:
                        String name = mEditTextName.getText().toString();
                        String id = mEditTextId.getText().toString();
                        String pw = mEditTextPw.getText().toString();
                        String pwcheck = mEditTextPwChk.getText().toString();
                        String age = mEditTextAge.getText().toString();
                        String phone = mEditTextPhone.getText().toString();
                        String email = mEditTextEmail.getText().toString();
                        String gender = mEditTextGender.getText().toString();

                        if(pw.equals(pwcheck)){
                            InsertData task = new InsertData();
                            task.execute(id, pw, name, age, phone, email, gender);

                            mEditTextName.setText("");
                            mEditTextId.setText("");
                            mEditTextPw.setText("");
                            mEditTextPwChk.setText("");
                            mEditTextAge.setText("");
                            mEditTextPhone.setText("");
                            mEditTextEmail.setText("");
                            mEditTextGender.setText("");
                        }
                        else{
                            mEditTextPw.setText("");
                            mEditTextPwChk.setText("");
                        }

                        break;

                }

            }
        };

        btnCancel.setOnClickListener(listener);
        btnSend.setOnClickListener(listener);

    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SubActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[0];
            String pw = (String)params[1];
            String name = (String)params[2];
            String age = (String)params[3];
            String phone = (String)params[4];
            String email = (String)params[5];
            String gender = (String)params[6];

            String serverURL = "http://192.168.0.128/insertGaza.v1.0.php";
            String postParameters = "id=" + id + "&pw=" + pw + "&name=" + name + "&age=" + age + "&phone=" + phone + "&email=" + email + "&gender=" + gender;


            try {
                //서버 연결
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                //OutputStream은 안드로이드->서버
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);


                //InputStream은 서버->안드로이드
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }
}
