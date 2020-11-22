package com.example.automatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static androidx.core.os.LocaleListCompat.create;

public class DashboardActivity extends AppCompatActivity {

    boolean installed;
    final String fbLink = "https://www.facebook.com/Automatt.pk/", instaLink = "https://www.instagram.com/automatt.pk/";
    Button bttnContact, bttnOnlineShopping, bttnAdmin, bttnAbout;
    ImageView bttnFb, bttnInsta;
    ViewFlipper viewFlipper;
    private  static final int REQUEST_CALL = 1;
    final String phoneNumber="+923312593190";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        bttnAbout = (findViewById(R.id.bttnAbout));
        bttnAdmin =(findViewById(R.id.bttnAdmin));
        bttnContact = (findViewById(R.id.bttnContact));
//        bttnFb = (findViewById(R.id.bttnFb));
//        bttnInsta = (findViewById(R.id.instabttn));
        bttnOnlineShopping =(findViewById(R.id.bttnshoponline));


//        bttnFb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbLink));
//                    startActivity(intent);
//                } catch(Exception e) {
//                    Toast.makeText(DashboardActivity.this,"Error Laoding page",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        bttnInsta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Uri uri = Uri.parse(instaLink);
//                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
//
//                likeIng.setPackage("com.instagram.android");
//
//                try {
//                    startActivity(likeIng);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(instaLink)));
//                }
//            }
//        });
        bttnOnlineShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("value","onlineshopping");
              //  Toast.makeText(DashboardActivity.this,"Loading...",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        bttnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("value","admin");
                startActivity(intent);
            }
        });
        bttnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);

                builder.setMessage("Please select any one option");
                builder.setTitle("Contact");
                builder.setCancelable(true);
                builder.setIcon(R.drawable.contactlogo_icon);

                builder.setNeutralButton("Open Messenger", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/messages/Automatt.pk"));
                        startActivity(i);
                        //builder.finish();
                    }
                });
                builder.setNegativeButton("Open Whatsapp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installed = isAppInstalled("com.whatsapp");

                        if (installed){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phoneNumber));
                            startActivity(intent);
                        }else{
                            Toast.makeText(DashboardActivity.this,"Whatsapp is not installed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setPositiveButton("Phone Call", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        phoneCall();
                        dialog.cancel();
                    }

                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        bttnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("value","about");
                startActivity(intent);

            }
        });

        int images[] = {R.drawable.b,R.drawable.a,R.drawable.c,R.drawable.d,R.drawable.e};
        viewFlipper =(ViewFlipper)findViewById(R.id.id_flipper);

        for(int image:images){
            flipperImages(image);
        }

    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }


    public void  flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2500); //2 sec
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
    private void phoneCall(){
        if (ContextCompat.checkSelfPermission(DashboardActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dial = "tel:"+ phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exist")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                phoneCall();
            }else{
                Toast.makeText(this,"PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

