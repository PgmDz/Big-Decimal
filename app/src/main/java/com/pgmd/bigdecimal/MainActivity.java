package com.pgmd.bigdecimal;

import android.app.Activity;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;
import android.icu.text.DecimalFormat;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormatSymbols;

public class MainActivity extends Activity {

	private TextView money;
	private Button add, remove;
	private Prefs prefs;

	private double coins = 0.000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		prefs = Prefs.with(this);
		money = findViewById(R.id.money);
		if(prefs.contains("MONEY")) {
			money.setText(String.valueOf(prefs.read("MONEY")));
		}
		else {
			money.setText("R$ 0,000");
		}

		add = findViewById(R.id.add);
		remove = findViewById(R.id.remove);

		add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1) {
					adicionarPontos(0.005);
				}
			});

		remove.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View p1) {
					adicionarPontos(-0.005);
				}
			});
    }

	private void adicionarPontos(double coins) {
		if (prefs.contains("COINS")) {
			double _coins = prefs.readDouble("COINS");
			_coins += coins;
			this.coins = _coins;
		}
		else {
			this.coins = coins;
		}

		if(this.coins >= 0) {
			prefs.writeDouble("COINS", this.coins);
			prefs.write("MONEY", currencyFormat(prefs.readDouble("COINS")));
			money.setText(prefs.read("MONEY"));
		}
		else {
			this.coins = 0.00;
			prefs.writeDouble("COINS", this.coins);
			prefs.write("MONEY", currencyFormat(prefs.readDouble("COINS")));
			money.setText(prefs.read("MONEY"));
		}
	}

	public static String currencyFormat(double n) {
		return String.valueOf("R$ ").concat(new DecimalFormat("#,###0.000").format(new BigDecimal(n).doubleValue()));
	}
}
