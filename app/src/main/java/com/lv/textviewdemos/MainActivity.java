package com.lv.textviewdemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daasuu.cat.CountAnimationTextView;
import com.hanks.htextview.HTextView;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.fabiomsr.moneytextview.MoneyTextView;

public class MainActivity extends AppCompatActivity {

    private HTextView mTextHTextView;
    private MoneyTextView mTextMoneyTextView;
    private CountAnimationTextView mCountAnimationTextView;
    private TickerView mTickerView;
    private CustomTextView mTestCustomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mTextHTextView.animateText("HTextView new simple string");
        mTickerView.setText("$789");
        mTextHTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextMoneyTextView.setAmount(156);
                mCountAnimationTextView.countAnimation(0, 99999);

                mTickerView.setText("$123456");
            }
        });
    }

    private void initView() {
        mTextHTextView = (HTextView) findViewById(R.id.text_HTextView);
        mTextMoneyTextView = (MoneyTextView) findViewById(R.id.text_MoneyTextView);
        mCountAnimationTextView = (CountAnimationTextView) findViewById(R.id.count_animation_textView);
        mTickerView = (TickerView) findViewById(R.id.tickerView);

        mTickerView.setCharacterList(TickerUtils.getDefaultListForUSCurrency());
        mTickerView.setAnimationDuration(500);


        mTestCustomTextView = (CustomTextView) findViewById(R.id.test_CustomTextView);
        mTestCustomTextView.setSelected(true);
    }
}
