package com.example.tugas1ke2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class extravaganza extends AppCompatActivity {

    private ImageView wheel;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extravaganza);

        wheel = findViewById(R.id.wheel);
        random = new Random();

        ImageView wheel = findViewById(R.id.wheel);
        wheel.setOnClickListener(v -> spinWheel());
    }

    private void spinWheel() {
        // Sudut awal acak antara 0 hingga 360 derajat
        float startAngle = random.nextFloat() * 360;
        // Sudut akhir acak untuk memberikan hasil yang berbeda setiap kali
        float endAngle = startAngle + 360f * 5 + random.nextInt(360);

        // Definisikan animasi rotasi
        RotateAnimation rotate = new RotateAnimation(0, endAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000); // durasi dalam milidetik
        rotate.setInterpolator(new LinearInterpolator()); // menjaga kecepatan tetap konstan
        rotate.setFillAfter(true); // Setelah animasi selesai, roda akan tetap pada posisi terakhir

        // Mulai animasi
        wheel.startAnimation(rotate);
    }

    public void back(View view) {
        Intent back = new Intent(extravaganza.this, congrats.class);
        startActivity(back);
    }


    public void scanktp(View view) {
        Intent scanktp = new Intent(extravaganza.this, scan_ktp_page.class);
        startActivity(scanktp);
    }
}