package com.example.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int l = (int) System.currentTimeMillis();
        showNotify("标题 ", "内容", 0, l);

        //to json
        JsonArray();
        JsonObject();
        //format json
        formatObjectJson();
        formatArrayJson();
        Json2Bean();

        Type type = new TypeToken<DemoBean>() {}.getType();
        DemoBean o = new Gson().fromJson(JsonObject(), type);
        Log.e("FromJson2Bean=o=",o.toString());

        FromJson2Bean<DemoBean> bean = new FromJson2Bean<>();
        DemoBean demoBean = bean.fromJson(JsonObject());
        Log.e("FromJson2Bean==",demoBean.toString());
    }

    private String JsonArray(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            jsonObject.put("id",1);
            jsonObject.put("name","demo1");
            object.put("id",2);
            object.put("name","demo2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        jsonArray.put(object);
        Log.e("jsonArray==",jsonArray.toString());
        return jsonArray.toString();
    }

    private String JsonObject(){
        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            jsonObject.put("id",1);
            jsonObject.put("name","demo");
            object.put("cid","2");
            object.put("cname","cdemo");
            jsonObject.put("data",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("jsonObject==",jsonObject.toString());
        return jsonObject.toString();
    }

    private void formatArrayJson(){
        String s = JsonArray();
        try {
            JSONArray jsonArray = new JSONArray(s);
            Log.e("formatArrayJson==",jsonArray.toString() + "  "+jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.e("formatArrayJson==",jsonObject.toString() + "  ");
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                Log.e("formatArrayJson==",i + " == " + id + "  " + name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void formatObjectJson(){
        String s = JsonObject();
        try {
            JSONObject object = new JSONObject(s);
            String id = object.getString("id");
            String name = object.getString("name");
            Log.e("formatObjectJson==",id + "  " + name);
            JSONObject data = object.getJSONObject("data");
            String cid = data.getString("cid");
            String cname = data.getString("cname");
            Log.e("formatObjectJson==",cid + "  " + cname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Json2Bean(){
        String s = JsonObject();
        DemoBean bean = null;
        try {
            bean = new Gson().fromJson(s, DemoBean.class);
            Log.e("Json2Bean",bean.toString());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        Log.e("Json2Bean",bean+"");
    }

    private void showNotify(String title, String text, int num, int id) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("有新消息");
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setNumber(num);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(getDefaultIntent(title, text, id));
//        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);

        Notification notification = builder.build();
        NotificationManager manger = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manger.notify(id, notification);
    }

    private PendingIntent getDefaultIntent(String title, String content, int id) {

        Intent perIntent = new Intent(this, MainActivity.class);
        perIntent.putExtra("title", title);
        perIntent.putExtra("content", content);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this, id, perIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }
}
