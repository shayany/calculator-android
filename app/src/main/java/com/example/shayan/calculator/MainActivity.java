package com.example.shayan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private BigDecimal firstNumber, endResult;
    private Character operator;
    private boolean decimalPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.resultTextView);
        decimalPoint = false;

    }

    public void enterDigit(View view) {
        Button button = (Button) view;
        if (textView.getText().toString().equals("Malformed expression") || textView.getText().toString().equals("Division by zero is undefined")) {
            reset();
        }
        textView.append(button.getText().toString());
    }

    public void onClickDecimalPoint(View view) {
        Button button = (Button) view;
        if (!decimalPoint) {
            textView.append(button.getText().toString());
            decimalPoint = true;
        }
    }

    public void assignmentOnClick(View view) {
        try {
            textView.setText(calculate(firstNumber, new BigDecimal(textView.getText().toString())).toString());
        } catch (ArithmeticException e) {
            textView.setText("Division by zero is undefined");
            reset();
        } catch (Exception e) {
            reset();
        }
    }

    public void operatorOnClick(View view) {
        try {
            Button button = (Button) view;
            operator = button.getText().charAt(0);
            firstNumber = new BigDecimal(textView.getText().toString());
            textView.setText("");
            decimalPoint = false;
        } catch (Exception e) {
            textView.setText("Malformed expression");
        }
    }

    public void resetOnClick(View view) {
        reset();
    }

    private void reset() {
        firstNumber = endResult = null;
        operator = null;
        textView.setText("");
        decimalPoint = false;
    }

    public BigDecimal calculate(BigDecimal number1, BigDecimal number2) {
        switch (operator) {
            case '/':
                if (number2.equals(0)) {
                    throw new ArithmeticException();
                }
                endResult = number1.divide(number2, MathContext.DECIMAL128);
                break;
            case '*':
                endResult = number1.multiply(number2);
                break;
            case '+':
                endResult = number1.add(number2);
                break;
            case '-':
                endResult = number1.subtract(number2);
                break;
            case '%':
                endResult = number1.remainder(number2);
                break;
        }
        if (endResult.doubleValue() == 0) {
            endResult = BigDecimal.ZERO;
        }
        return endResult;
    }
}
