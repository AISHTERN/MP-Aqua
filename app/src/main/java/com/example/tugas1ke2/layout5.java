package com.example.tugas1ke2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class layout5 extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 1;
    private TextView totalTutupBotolText;
    private int totalTutupBotol = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout5);

        sharedPreferences = getSharedPreferences("BarcodePrefs", Context.MODE_PRIVATE);
        totalTutupBotol = sharedPreferences.getInt("TotalTutupBotol", 0);

        totalTutupBotolText = findViewById(R.id.some_id);
        totalTutupBotolText.setText(String.valueOf(totalTutupBotol));

        Button group_869 = findViewById(R.id.group_869);
        group_869.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
    }

    private void openDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.main);
        NavigationView navigationView = findViewById(R.id.hal_view);
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public void scanbarcode(View view) {
        Intent scanIntent = new Intent(layout5.this, scantutupbotol.class);
        scanIntent.putExtra("TotalTutupBotol", totalTutupBotol);
        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    public void redeemvaganza(View view) {
        Intent redeemvaganza = new Intent(layout5.this, reedemvaganza.class);
        startActivity(redeemvaganza);
    }
    public void congrats(View view) {
        Intent congrats = new Intent(layout5.this, congrats.class);
        startActivity(congrats);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                totalTutupBotol = data.getIntExtra("TotalTutupBotol", totalTutupBotol);
                totalTutupBotolText.setText(String.valueOf(totalTutupBotol));
                saveTotalTutupBotol(totalTutupBotol);
            }
        }
    }

    private void saveTotalTutupBotol(int total) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("TotalTutupBotol", total);
        editor.apply();
    }
}
