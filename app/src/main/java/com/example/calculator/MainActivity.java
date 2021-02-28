package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mInput;
    private TextView mResult;
    private Calculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();
        mInput = findViewById(R.id.inputField);
        mResult = findViewById(R.id.resultField);
        ArrayList<Button> buttons = getButtons();

        View.OnClickListener buttonListener = v -> buttonPressedHandler(v.getId());
        setOnClickListenerToAll(buttons, buttonListener);
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
                    mInput.setText(null);

                inputAppend(button.getText());
                break;
            }

            case R.id.button_clear: {
                mInput.setText(null);
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
                mCalculator.pushNumber(Double.parseDouble(getLastOperand().toString()));
                try {
                    mCalculator.pushOperator(new Operator(button.getText().toString()));
                } catch (ArithmeticException e) {
                    mResult.setText(e.getMessage()); // TODO: make an error view
                }
                break;
            }

            case R.id.button_equal: {
                mCalculator.pushNumber(Double.parseDouble(getLastOperand().toString()));
                mInput.setText(null);
                setResult(mCalculator.compute());
            }
        }
    }

    private void inputAppend(CharSequence symbol) {
        String input = mInput.getText().toString();
        input += symbol;
        mInput.setText(input);
    }

    private CharSequence getLastOperand() {
        String[] res = mInput.getText().toString().split("\\D");
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
            mResult.setText(String.valueOf((int) result));
        else
            mResult.setText(String.valueOf(result));
    }

    private ArrayList<Button> getButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(findViewById(R.id.button_0));
        buttons.add(findViewById(R.id.button_1));
        buttons.add(findViewById(R.id.button_2));
        buttons.add(findViewById(R.id.button_3));
        buttons.add(findViewById(R.id.button_4));
        buttons.add(findViewById(R.id.button_5));
        buttons.add(findViewById(R.id.button_6));
        buttons.add(findViewById(R.id.button_7));
        buttons.add(findViewById(R.id.button_8));
        buttons.add(findViewById(R.id.button_9));
        buttons.add(findViewById(R.id.button_point));
        buttons.add(findViewById(R.id.button_plus));
        buttons.add(findViewById(R.id.button_percent));
        buttons.add(findViewById(R.id.button_negative));
        buttons.add(findViewById(R.id.button_mul));
        buttons.add(findViewById(R.id.button_minus));
        buttons.add(findViewById(R.id.button_equal));
        buttons.add(findViewById(R.id.button_div));
        buttons.add(findViewById(R.id.button_clear));
        buttons.add(findViewById(R.id.button_bracket));

        return buttons;
    }

    private void setOnClickListenerToAll(ArrayList<Button> buttons, View.OnClickListener listener) {
        for (Button button : buttons) {
            button.setOnClickListener(listener);
        }
    }

    // TODO: Parcelable
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}