package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ScreenStringFieldsData mStringData;
    private Calculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator(this);
        mStringData = new ScreenStringFieldsData();

        mStringData.setInput(findViewById(R.id.inputField));
        mStringData.setResult(findViewById(R.id.resultField));
        View.OnClickListener buttonListener = v -> buttonPressedHandler(v.getId());
        setOnClickListenerToAll(buttonListener);
    }

    private void setOnClickListenerToAll(View.OnClickListener listener) {
        findViewById(R.id.button_0).setOnClickListener(listener);
        findViewById(R.id.button_1).setOnClickListener(listener);
        findViewById(R.id.button_2).setOnClickListener(listener);
        findViewById(R.id.button_3).setOnClickListener(listener);
        findViewById(R.id.button_4).setOnClickListener(listener);
        findViewById(R.id.button_5).setOnClickListener(listener);
        findViewById(R.id.button_6).setOnClickListener(listener);
        findViewById(R.id.button_7).setOnClickListener(listener);
        findViewById(R.id.button_8).setOnClickListener(listener);
        findViewById(R.id.button_9).setOnClickListener(listener);
        findViewById(R.id.button_point).setOnClickListener(listener);
        findViewById(R.id.button_plus).setOnClickListener(listener);
        findViewById(R.id.button_percent).setOnClickListener(listener);
        findViewById(R.id.button_negative).setOnClickListener(listener);
        findViewById(R.id.button_mul).setOnClickListener(listener);
        findViewById(R.id.button_minus).setOnClickListener(listener);
        findViewById(R.id.button_equal).setOnClickListener(listener);
        findViewById(R.id.button_div).setOnClickListener(listener);
        findViewById(R.id.button_clear).setOnClickListener(listener);
        findViewById(R.id.button_bracket).setOnClickListener(listener);
    }

    private void buttonPressedHandler(int id) {
        Button button = findViewById(id);

        switch (id) {
            case R.id.button_0: {
                if (!operandStartsWithZero())
                    inputAppend(button.getText());

                break;
            }

            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9: {
                if (operandStartsWithZero())
                    mStringData.getInput().setText(null);

                inputAppend(button.getText());
                break;
            }

            case R.id.button_clear: {
                mStringData.getInput().setText(null);
                mStringData.getResult().setText(null);
                mCalculator.clearStacks();
                break;
            }

            case R.id.button_plus:
            case R.id.button_minus:
            case R.id.button_mul:
            case R.id.button_div:
            case R.id.button_negative:
            case R.id.button_percent:
            case R.id.button_point:
            case R.id.button_bracket: {
                inputAppend(button.getText().toString());
                if (getLastOperand() != null) {
                    mCalculator.pushNumber(Double.parseDouble(getLastOperand().toString()));
                    mCalculator.pushOperator(new Operator(button.getText().toString()));
                }

                break;
            }

            case R.id.button_equal: {
                if (getLastOperand() != null) {
                    mCalculator.pushNumber(Double.parseDouble(getLastOperand().toString()));
                }

                setResult(mCalculator.compute());
            }
        }
    }

    private void inputAppend(CharSequence symbol) {
        String input = mStringData.getInput().getText().toString();
        input += symbol;
        mStringData.getInput().setText(input);
    }

    private CharSequence getLastOperand() {
        String[] res = mStringData.getInput().getText().toString().split("\\D");
        return (res.length != 0) ? res[res.length - 1] : null;
    }

    private boolean operandStartsWithZero() {
        CharSequence lastOperand = getLastOperand();

        if (lastOperand == null)
            return false;

        return lastOperand.toString().startsWith("0");
    }

    private void setResult(double result) {
        if (result % 1 == 0)
            mStringData.getResult().setText(String.valueOf((long) result));
        else
            mStringData.getResult().setText(String.valueOf(result));
    }

    public void showAlert(String alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(alert)
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable("StringFields", mStringData);
        instanceState.putParcelable("Calculator", mCalculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        mStringData = instanceState.getParcelable("StringFields");
        mCalculator = instanceState.getParcelable("Calculator");
    }
}