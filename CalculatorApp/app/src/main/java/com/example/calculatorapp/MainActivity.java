package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calculatorlib.Calculator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        int result1 = Calculator.add(5, 3);
        int result2 = Calculator.subtract(10, 4);
        textView.setText("5 + 3 = " + result1 + "\n10 - 4 = " + result2);
        setContentView(textView);
    }
}