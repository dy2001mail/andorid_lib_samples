
package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.calculatorlib.Calculator;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        Calculator calculator = new Calculator();
        int result = calculator.add(3, 4);
        textView.setText("3 + 4 = " + result);
        setContentView(textView);
    }
}
