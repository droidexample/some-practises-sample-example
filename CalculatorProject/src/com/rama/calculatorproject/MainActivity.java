package com.rama.calculatorproject;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView mCalculatorDisplay;
	private Boolean userIsInTheMiddleOfTypingANumber = false;
	private Calculation mCalculation;
	private static final String DIGITS = "0123456789.";
	Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven,
			btnEight, btnNine, btnZero, btnAdd, btnSub, btnMul, btnDiv,
			btnToggle, btnDecimal, btnEqual, btnClear, btnClearMemory,
			btnAddToMemory, btnSubFromMemory, btnRecallMemory, btnSing, btnTan,
			btnCos, btnSqured, btnSqureRoot, btnInvert;
	DecimalFormat df = new DecimalFormat("@###########");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide the status bar and other OS-level chrome
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCalculation = new Calculation();
		mCalculatorDisplay = (TextView) findViewById(R.id.textView1);

		df.setMaximumFractionDigits(0);
		df.setMinimumIntegerDigits(1);
		df.setMaximumIntegerDigits(8);

		btnZero = (Button) findViewById(R.id.button0);
		btnOne = (Button) findViewById(R.id.button1);
		btnTwo = (Button) findViewById(R.id.button2);
		btnThree = (Button) findViewById(R.id.button3);
		btnFour = (Button) findViewById(R.id.button4);
		btnFive = (Button) findViewById(R.id.button5);
		btnSix = (Button) findViewById(R.id.button6);
		btnSeven = (Button) findViewById(R.id.button7);
		btnEight = (Button) findViewById(R.id.button8);
		btnNine = (Button) findViewById(R.id.button9);

		btnAdd = (Button) findViewById(R.id.buttonAdd);
		btnSub = (Button) findViewById(R.id.buttonSubtract);
		btnDiv = (Button) findViewById(R.id.buttonDivide);
		btnMul = (Button) findViewById(R.id.buttonMultiply);

		btnToggle = (Button) findViewById(R.id.buttonToggleSign);
		btnDecimal = (Button) findViewById(R.id.buttonDecimalPoint);
		btnEqual = (Button) findViewById(R.id.buttonEquals);
		btnClear = (Button) findViewById(R.id.buttonClear);
		btnClearMemory = (Button) findViewById(R.id.buttonClearMemory);
		btnAddToMemory = (Button) findViewById(R.id.buttonAddToMemory);
		btnSubFromMemory = (Button) findViewById(R.id.buttonSubtractFromMemory);
		btnRecallMemory = (Button) findViewById(R.id.buttonRecallMemory);

		// set large screen

		btnSqureRoot = (Button) findViewById(R.id.buttonSquareRoot);
		btnSqured = (Button) findViewById(R.id.buttonSquared);
		btnInvert = (Button) findViewById(R.id.buttonInvert);
		btnSing = (Button) findViewById(R.id.buttonSine);
		btnTan = (Button) findViewById(R.id.buttonTangent);
		btnCos = (Button) findViewById(R.id.buttonCosine);

		btnZero.setOnClickListener(this);
		btnOne.setOnClickListener(this);
		btnTwo.setOnClickListener(this);
		btnThree.setOnClickListener(this);
		btnFour.setOnClickListener(this);
		btnFive.setOnClickListener(this);
		btnSix.setOnClickListener(this);
		btnSeven.setOnClickListener(this);
		btnEight.setOnClickListener(this);
		btnNine.setOnClickListener(this);

		btnAdd.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		btnMul.setOnClickListener(this);
		btnDiv.setOnClickListener(this);

		btnToggle.setOnClickListener(this);
		btnDecimal.setOnClickListener(this);
		btnEqual.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnClearMemory.setOnClickListener(this);
		btnAddToMemory.setOnClickListener(this);
		btnSubFromMemory.setOnClickListener(this);
		btnRecallMemory.setOnClickListener(this);

		if (btnSqureRoot != null) {
			btnSqureRoot.setOnClickListener(this);
		}
		if (btnSqured != null) {
			btnSqured.setOnClickListener(this);
		}
		if (btnInvert != null) {
			btnInvert.setOnClickListener(this);
		}
		if (btnSing != null) {
			btnSing.setOnClickListener(this);
		}
		if (btnCos != null) {
			btnCos.setOnClickListener(this);
		}
		if (btnTan != null) {
			btnTan.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		String buttonPressed = ((Button) v).getText().toString();

		if (DIGITS.contains(buttonPressed)) {

			// digit was pressed
			if (userIsInTheMiddleOfTypingANumber) {

				if (buttonPressed.equals(".")
						&& mCalculatorDisplay.getText().toString()
								.contains(".")) {
					// ERROR PREVENTION
					// Eliminate entering multiple decimals
				} else {
					mCalculatorDisplay.append(buttonPressed);
				}

			} else {

				if (buttonPressed.equals(".")) {
					// ERROR PREVENTION
					// This will avoid error if only the decimal is hit before
					// an operator, by placing a leading zero
					// before the decimal
					mCalculatorDisplay.setText(0 + buttonPressed);
				} else {
					mCalculatorDisplay.setText(buttonPressed);
				}

				userIsInTheMiddleOfTypingANumber = true;
			}

		} else {
			// operation was pressed
			if (userIsInTheMiddleOfTypingANumber) {

				mCalculation.setOperand(Double.parseDouble(mCalculatorDisplay
						.getText().toString()));
				userIsInTheMiddleOfTypingANumber = false;
			}

			mCalculation.performOperation(buttonPressed);
			mCalculatorDisplay.setText(df.format(mCalculation.getResult()));

		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save variables on screen orientation change
		outState.putDouble("OPERAND", mCalculation.getResult());
		outState.putDouble("MEMORY", mCalculation.getMemory());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore variables on screen orientation change
		mCalculation.setOperand(savedInstanceState.getDouble("OPERAND"));
		mCalculation.setMemory(savedInstanceState.getDouble("MEMORY"));
		mCalculatorDisplay.setText(df.format(mCalculation.getResult()));
	}

}
