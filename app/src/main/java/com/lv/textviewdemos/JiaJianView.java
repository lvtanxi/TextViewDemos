package com.lv.textviewdemos;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author 吕勇
 * @Title: JiaJianView.java
 * @Description: 自定义加减控件
 * @date 2016-1-28 下午6:58:20
 */
public class JiaJianView extends LinearLayout implements OnClickListener, TextWatcher, OnFocusChangeListener {

    private int minNum, defNum, maxNum, valNum;

    private ImageView btn_jian, btn_jia;
    private EditText numEdit;


    public JiaJianView(Context context) {
        this(context, null);
    }

    public JiaJianView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JiaJianView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.jianjia_view, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.JiaJianView);
        defNum = array.getInt(R.styleable.JiaJianView_defNum, 0);
        minNum = array.getInt(R.styleable.JiaJianView_minNum, 0);
        maxNum = array.getInt(R.styleable.JiaJianView_maxNum, 100);
        array.recycle();
        btn_jian = (ImageView) findViewById(R.id.btn_jian);
        btn_jia = (ImageView) findViewById(R.id.btn_jia);
        numEdit = (EditText) findViewById(R.id.edit_num);

        numEdit.setText(String.valueOf(defNum));

        btn_jian.setOnClickListener(this);
        btn_jia.setOnClickListener(this);
        numEdit.addTextChangedListener(this);
        numEdit.setOnFocusChangeListener(this);
        if (defNum == minNum) {
            btn_jian.setAlpha(0.5f);
        }
        if (defNum == maxNum) {
            btn_jia.setAlpha(0.5f);
        }
    }

    @Override
    public void onClick(View v) {
        hiddenKeyBoard(v);
        numEdit.clearFocus();
        if (v.getId() == R.id.btn_jian) {
            doJian();
        } else {
            doJia();
        }
    }

    private void doJia() {
        String val = numEdit.getText().toString();
        if (!TextUtils.equals("", val)) {
            valNum = Integer.valueOf(val);
            if (valNum < maxNum) {
                startAnim(1);
            }
        }
    }

    private void doJian() {
        String val = numEdit.getText().toString();
        if (!TextUtils.equals("", val)) {
            valNum = Integer.valueOf(val);
            if (valNum > minNum) {
                startAnim(0);
            }
        }
    }

    /**
     * 开启动画
     *
     * @param type 1为加0为减
     */
    private void startAnim(final int type) {
        int outHeight = getMeasuredHeight();
        if (type == 1)
            outHeight = -outHeight;
        ObjectAnimator outAnimi = ObjectAnimator.ofFloat(numEdit, "translationY", 0, outHeight);
        ObjectAnimator inAnimi = ObjectAnimator.ofFloat(numEdit, "translationY", -outHeight, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .setDuration(150)
                .play(inAnimi)
                .after(outAnimi);
        animatorSet.start();
        outAnimi.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (type == 1) {
                    numEdit.setText(String.valueOf(++valNum));
                    return;
                }
                numEdit.setText(String.valueOf(--valNum));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            numEdit.setText(String.valueOf(defNum));
        } else if (s.toString().startsWith("0") && s.length() > 1) {
            numEdit.setText(s.subSequence(1, s.length()));
        }
        String val = numEdit.getText().toString();
        if (!TextUtils.equals("", val)) {
            int valNum = Integer.valueOf(val);
            if (valNum == minNum) {
                btn_jian.setAlpha(0.5f);
            } else if (valNum == maxNum) {
                btn_jia.setAlpha(0.5f);
            } else {
                btn_jia.setAlpha(1f);
                btn_jian.setAlpha(1f);
            }
        }
        numEdit.setSelection(val.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            numEdit.setSelection(numEdit.getText().length());
        }
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getDefNum() {
        return defNum;
    }

    public void setDefNum(int defNum) {
        this.defNum = defNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public ImageView getBtn_jian() {
        return btn_jian;
    }

    public void setBtn_jian(ImageView btn_jian) {
        this.btn_jian = btn_jian;
    }

    public ImageView getBtn_jia() {
        return btn_jia;
    }

    public void setBtn_jia(ImageView btn_jia) {
        this.btn_jia = btn_jia;
    }

    public EditText getNumEdit() {
        return numEdit;
    }

    public void setNumEdit(EditText numEdit) {
        this.numEdit = numEdit;
    }

    public int getJiaJianVal() {
        String val = numEdit.getText().toString();
        if (TextUtils.equals("", val)) {
            return defNum;
        }
        return Integer.valueOf(val);
    }

    private void hiddenKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
