package com.example.test.customintentchooser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private static final String  FACEBOOK ="com.facebook.katana";
    private static final String  GOOGLE_PLUS ="com.google.android.apps.plus";
    private static final String  GMAIL ="com.google.android.gm";
    private static final String  MAIL ="com.android.email";
    private static final String  MESSAGE ="mms";
    private static final String TWIITER ="com.twitter.android";

    ArrayList<Appdata> name ;
    private GridView listView;
    private Button btnShare;
    private View viewTransparent;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupDefault();
        setupEvent();
    }

    private void init() {
        btnShare = (Button) findViewById(R.id.share);
        name = new ArrayList<>();
        for(int i= 0;i<5;i++){
            name.add(new Appdata(null,null));
        }
        viewTransparent = findViewById(R.id.transparent);
    }

    private void setupDefault() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appList = getPackageManager().queryIntentActivities( mainIntent, 0);

        boolean emailPackagename = false;
        for(int i= 0;i<appList.size();i++) {
            ActivityInfo appSingle = appList.get(i).activityInfo;

            if (appSingle.packageName.equalsIgnoreCase("com.android.email")) {
                emailPackagename = true;
            } else if (appSingle.packageName.equalsIgnoreCase("com.google.android.gm")) {
                emailPackagename = false;
            }

            /*if (emailPackagename) {
                name.add(new Appdata(appSingle.loadLabel(getPackageManager()).toString(), appSingle.loadIcon(getPackageManager())));
                emailPackagename = false;
            } else {*/
                if (appSingle.packageName.equalsIgnoreCase("com.facebook.katana")) {
                    name.add(new Appdata(appSingle.loadLabel(getPackageManager()).toString(), appSingle.loadIcon(getPackageManager())));
                }else if(appSingle.packageName.equalsIgnoreCase("com.twitter.android")){
                    name.add(new Appdata(appSingle.loadLabel(getPackageManager()).toString(), appSingle.loadIcon(getPackageManager())));
                }else if(appSingle.packageName.contains("mms")){
                    name.add(new Appdata(appSingle.loadLabel(getPackageManager()).toString(), appSingle.loadIcon(getPackageManager())));
                }else if((appSingle.packageName.equalsIgnoreCase("com.google.android.apps.plus")) && appSingle.loadLabel(getPackageManager()).equals("Google+")){
                    name.add(new Appdata(appSingle.loadLabel(getPackageManager()).toString(), appSingle.loadIcon(getPackageManager())));
                }
//            }
        }
    }

    private void setupEvent() {

        viewTransparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ss__ button clicke ","ss");
                viewTransparent.setVisibility(View.VISIBLE);
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popup, null);
                popupWindow = new PopupWindow(
                        popupView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                View viewLine = popupView.findViewById(R.id.view);
                LinearLayout llPrint = (LinearLayout) popupView.findViewById(R.id.print);

                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    viewLine.setVisibility(View.VISIBLE);
                    llPrint.setVisibility(View.VISIBLE);
                }

                listView = (GridView)popupView.findViewById(R.id.choose);
                listView.setNumColumns(3);
                NewAdapter customIntentAdapter = new NewAdapter(MainActivity.this, name);
                listView.setAdapter(customIntentAdapter);
                popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(btnShare, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        viewTransparent.setVisibility(View.INVISIBLE);
                    }
                });



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Appdata appdataName = (Appdata) parent.getItemAtPosition(position);
                        Log.e("ss___ ","appdataName " +appdataName);
                       /* switch(name.get(position).appName){
                            case "Facebook":
                                Toast.makeText(MainActivity.this,"Facebook clicked ",Toast.LENGTH_SHORT).show();
                                break;
                            case "Twitter":
                                Toast.makeText(MainActivity.this,"Twitter clicked ",Toast.LENGTH_SHORT).show();
                                break;
                            case "Email":
                                Toast.makeText(MainActivity.this,"Email clicked ",Toast.LENGTH_SHORT).show();
                                break;
                            case "Messaging":
                                Toast.makeText(MainActivity.this," Messaging clicked",Toast.LENGTH_SHORT).show();
                                break;
                            case "Google+":
                                Toast.makeText(MainActivity.this,"Google plus clicked ",Toast.LENGTH_SHORT).show();
                                break;
                        }*/
                        popupWindow.dismiss();
                    }
                });

                llPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
}
