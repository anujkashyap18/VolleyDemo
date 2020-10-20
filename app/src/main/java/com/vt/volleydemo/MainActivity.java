package com.vt.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TextView token, email, id, firstName, userName;
    ImageView profileImg;

    RequestQueue requestQueue;
    String status;
    String userNames;

    String url = "http://lillyann.betaplanets.com/wp-json/mobileapi/v1/facebook_login";

    String tokens,profileImage,userEmail,firstNames;
    Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = findViewById(R.id.tv_token);
        email = findViewById(R.id.tv_email);
        id = findViewById(R.id.tv_id);
        firstName = findViewById(R.id.tv_first);
        userName = findViewById(R.id.tv_username);
        profileImg = findViewById(R.id.tv_img);


        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(getClass().getSimpleName(),"firstName"+response);
                Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tokens = jsonObject.getString("token");
                     userEmail = jsonObject.getString("user_email");
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    JSONObject data = jsonObject.getJSONObject("data");
                    int status = data.getInt("status");
                    user_id = jsonObject.getInt("user_id");
                    firstNames = jsonObject.getString("first_name");
                    String lastName = jsonObject.getString("last_name");
                    String phone = jsonObject.getString("phone");
                    String address = jsonObject.getString("address");
                    userNames = jsonObject.getString("username");
                    profileImage = jsonObject.getString("profileImg");
                    boolean isTrail = jsonObject.getBoolean("is_trail");
                    String subscriptionType = jsonObject.getString("subscription_type");
                    String subStartDate = jsonObject.getString("subs_start_date");
                    String subEndDate = jsonObject.getString("subs_end_date");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                token.setText(tokens);
                email.setText(userEmail);
                id.setText(""+user_id);
                firstName.setText(firstNames);
                Picasso.get().load(profileImage).into(profileImg);
                userName.setText(userNames);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hm = new HashMap<>();
                hm.put("fbname", "testFB");
                hm.put("username", "anuj");
                hm.put("email", "anuj@gmail.com");
                hm.put("facebook_id", "123");
                return hm;
            }
        };
        requestQueue.add(stringRequest);
    }
}