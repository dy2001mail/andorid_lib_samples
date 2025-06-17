
package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.calculatorlib.Calculator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // UI组件
    private TextView tvExpression, tvResult;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnPlus, btnMinus, btnMultiply, btnDivide;
    private Button btnEquals, btnClear, btnBackspace, btnDecimal;

    // 计算相关变量
    private Calculator calculator;
    private String currentNumber = "";
    private String firstNumber = "";
    private String operator = "";
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 初始化计算器库
        calculator = new Calculator();
        
        // 初始化UI组件
        initViews();
        
        // 设置点击事件监听器
        setClickListeners();
    }

    private void initViews() {
        // 文本显示区域
        tvExpression = findViewById(R.id.tv_expression);
        tvResult = findViewById(R.id.tv_result);
        
        // 数字按钮
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        
        // 运算符按钮
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDivide = findViewById(R.id.btn_divide);
        
        // 功能按钮
        btnEquals = findViewById(R.id.btn_equals);
        btnClear = findViewById(R.id.btn_clear);
        btnBackspace = findViewById(R.id.btn_backspace);
        btnDecimal = findViewById(R.id.btn_decimal);
    }

    private void setClickListeners() {
        // 数字按钮
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        
        // 运算符按钮
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        
        // 功能按钮
        btnEquals.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        
        // 数字按钮处理
        if (id == R.id.btn_0) appendNumber("0");
        else if (id == R.id.btn_1) appendNumber("1");
        else if (id == R.id.btn_2) appendNumber("2");
        else if (id == R.id.btn_3) appendNumber("3");
        else if (id == R.id.btn_4) appendNumber("4");
        else if (id == R.id.btn_5) appendNumber("5");
        else if (id == R.id.btn_6) appendNumber("6");
        else if (id == R.id.btn_7) appendNumber("7");
        else if (id == R.id.btn_8) appendNumber("8");
        else if (id == R.id.btn_9) appendNumber("9");
        else if (id == R.id.btn_decimal) appendDecimal();
        
        // 运算符按钮处理
        else if (id == R.id.btn_plus) handleOperator("+");
        else if (id == R.id.btn_minus) handleOperator("-");
        else if (id == R.id.btn_multiply) handleOperator("×");
        else if (id == R.id.btn_divide) handleOperator("÷");
        
        // 功能按钮处理
        else if (id == R.id.btn_equals) calculateResult();
        else if (id == R.id.btn_clear) clearAll();
        else if (id == R.id.btn_backspace) handleBackspace();
    }

    private void appendNumber(String number) {
        if (isOperatorClicked) {
            currentNumber = number;
            isOperatorClicked = false;
        } else {
            // 避免多个0开头
            if (currentNumber.equals("0") && number.equals("0")) return;
            if (currentNumber.equals("0") && !number.equals("0")) {
                currentNumber = number;
            } else {
                currentNumber += number;
            }
        }
        updateDisplay();
    }

    private void appendDecimal() {
        if (isOperatorClicked) {
            currentNumber = "0.";
            isOperatorClicked = false;
        } else if (!currentNumber.contains(".")) {
            if (currentNumber.isEmpty()) {
                currentNumber = "0.";
            } else {
                currentNumber += ".";
            }
        }
        updateDisplay();
    }

    private void handleOperator(String op) {
        if (!currentNumber.isEmpty()) {
            if (!firstNumber.isEmpty() && !isOperatorClicked) {
                calculateResult();
            }
            firstNumber = currentNumber;
            operator = op;
            isOperatorClicked = true;
            updateDisplay();
        } else if (firstNumber.isEmpty() && op.equals("-")) {
            // 处理负数
            currentNumber = "-";
            updateDisplay();
        } else if (!firstNumber.isEmpty()) {
            // 更改运算符
            operator = op;
            updateDisplay();
        }
    }

    private void calculateResult() {
        if (!firstNumber.isEmpty() && !currentNumber.isEmpty() && !operator.isEmpty()) {
            double num1 = Double.parseDouble(firstNumber);
            double num2 = Double.parseDouble(currentNumber);
            double result = 0;
            
            switch (operator) {
                case "+":
                    result = calculator.add((int)num1, (int)num2);
                    break;
                case "-":
                    result = calculator.subtract((int)num1, (int)num2);
                    break;
                case "×":
                    result = calculator.multiply((int)num1, (int)num2);
                    break;
                case "÷":
                    try {
                        result = calculator.divide(num1, num2);
                    } catch (ArithmeticException e) {
                        tvResult.setText("错误");
                        return;
                    }
                    break;
            }
            
            // 处理整数结果
            String resultStr;
            if (result == (int) result) {
                resultStr = String.valueOf((int) result);
            } else {
                resultStr = String.valueOf(result);
            }
            
            // 更新显示
            tvExpression.setText(firstNumber + " " + operator + " " + currentNumber + " =");
            tvResult.setText(resultStr);
            
            // 重置计算状态
            firstNumber = resultStr;
            currentNumber = "";
            isOperatorClicked = true;
        }
    }

    private void clearAll() {
        currentNumber = "";
        firstNumber = "";
        operator = "";
        isOperatorClicked = false;
        tvExpression.setText("");
        tvResult.setText("0");
    }

    private void handleBackspace() {
        if (!currentNumber.isEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        // 更新表达式显示
        if (!firstNumber.isEmpty()) {
            tvExpression.setText(firstNumber + " " + operator + (isOperatorClicked ? "" : " " + currentNumber));
        } else {
            tvExpression.setText(currentNumber);
        }
        
        // 更新结果显示
        if (!currentNumber.isEmpty() && !isOperatorClicked) {
            tvResult.setText(currentNumber);
        } else if (isOperatorClicked && !firstNumber.isEmpty()) {
            tvResult.setText(firstNumber);
        } else if (currentNumber.isEmpty()) {
            tvResult.setText("0");
        }
    }
}
