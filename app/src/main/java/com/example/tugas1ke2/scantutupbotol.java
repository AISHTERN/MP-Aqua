package com.example.tugas1ke2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class scantutupbotol extends AppCompatActivity {
    private Button btn_scan, btn_submit;
    private ImageView alertMerah, alertIjo;
    private TextView totalTutupBotolText;
    private int totalTutupBotol;
    private SharedPreferences sharedPreferences;
    private boolean isBarcodeScanned = false; // Flag untuk mengidentifikasi apakah barcode telah discan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scantutupbotol);

        sharedPreferences = getSharedPreferences("BarcodePrefs", Context.MODE_PRIVATE);
        totalTutupBotol = sharedPreferences.getInt("TotalTutupBotol", 0);

        btn_scan = findViewById(R.id.btn_scan);
        btn_submit = findViewById(R.id.btn_submit);
        alertMerah = findViewById(R.id.alert_merah);
        alertIjo = findViewById(R.id.alert_ijo);
        totalTutupBotolText = findViewById(R.id.total_tutup_botol);
        totalTutupBotolText.setText(String.valueOf(totalTutupBotol));
        alertMerah.setVisibility(View.INVISIBLE);
        alertIjo.setVisibility(View.INVISIBLE);
        btn_scan.setOnClickListener(v -> scanCode());

        // Initialize totalTutupBotol from the intent if available
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TotalTutupBotol")) {
            totalTutupBotol = intent.getIntExtra("TotalTutupBotol", totalTutupBotol);
            totalTutupBotolText.setText(String.valueOf(totalTutupBotol));
        }
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        launcher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            handleBarcode(result.getContents());
        }
    });

    private void handleBarcode(String barcode) {
        if (isBarcodeClaimed(barcode)) {
            alertMerah.setVisibility(View.VISIBLE);
            alertIjo.setVisibility(View.INVISIBLE);
            btn_submit.setText("Submit");
            btn_submit.setEnabled(false); // Menonaktifkan tombol saat alert merah muncul
            isBarcodeScanned = false; // Barcode tidak valid untuk ditambahkan
        } else {
            claimBarcode(barcode);
            alertMerah.setVisibility(View.INVISIBLE);
            alertIjo.setVisibility(View.VISIBLE);
            btn_submit.setText("Save");
            btn_submit.setEnabled(true); // Mengaktifkan tombol saat alert hijau muncul
            totalTutupBotol++;
            totalTutupBotolText.setText(String.valueOf(totalTutupBotol));
            isBarcodeScanned = true; // Barcode valid untuk ditambahkan
        }
    }

    private boolean isBarcodeClaimed(String barcode) {
        return sharedPreferences.contains(barcode);
    }

    private void claimBarcode(String barcode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(barcode, true);
        editor.apply();
    }

    private void saveTotalTutupBotol(int total) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("TotalTutupBotol", total);
        editor.apply();
    }

    public void submit(View view) {
        // Mengirim nilai total tutup botol kembali ke layout5.java
        Intent submitIntent = new Intent();
        submitIntent.putExtra("TotalTutupBotol", totalTutupBotol);
        setResult(RESULT_OK, submitIntent);
        finish();
    }

    public void cancel(View view) {
        if (alertMerah.getVisibility() == View.VISIBLE || alertIjo.getVisibility() == View.VISIBLE) {
            // Jika alert merah atau hijau muncul, cukup sembunyikan alert
            alertMerah.setVisibility(View.INVISIBLE);
            alertIjo.setVisibility(View.INVISIBLE);
            // Mengurangi totalTutupBotol jika alert hijau muncul dan cancel diklik
            if (isBarcodeScanned) {
                totalTutupBotol--;
                totalTutupBotolText.setText(String.valueOf(totalTutupBotol));
                isBarcodeScanned = false; // Reset flag setelah pengurangan
            }
        } else {
            // Jika tidak ada alert yang muncul, kembali ke layout5
            Intent cancelIntent = new Intent();
            setResult(RESULT_CANCELED, cancelIntent);
            finish();
        }
    }

    public void panahkembalikelayer5(View view) {
        Intent panahkembalikelayer5 = new Intent(scantutupbotol.this, layout5.class);
        startActivity(panahkembalikelayer5);
    }
}
