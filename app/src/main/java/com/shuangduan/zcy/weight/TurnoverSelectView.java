package com.shuangduan.zcy.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.shuangduan.zcy.R;

import retrofit2.http.PUT;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.weight$
 * @class TurnoverSelectView$
 * @class describe
 * @time 2019/11/3 10:03
 * @change
 * @class describe
 */
public class TurnoverSelectView extends ConstraintLayout {

    private boolean isShow;
    private String title;
    private String value;
    private View line;
    private TextView tvValue;


    public TurnoverSelectView(Context context) {
        super(context);
        initView(context);
    }

    public TurnoverSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeValue(context, attrs);
        initView(context);
    }

    public TurnoverSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeValue(context, attrs);
        initView(context);
    }

    public void initTypeValue(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TurnoverSelectView);
        isShow = a.getBoolean(R.styleable.TurnoverSelectView_line_show, false);
        title = a.getString(R.styleable.TurnoverSelectView_title_text);
        value = a.getString(R.styleable.TurnoverSelectView_value_text);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_turnover_group, this, true);

        TextView tvTitle = findViewById(R.id.tv_item_title);
        tvValue = findViewById(R.id.tv_item_value);
        line = findViewById(R.id.line);

        tvTitle.setText(title);
        tvValue.setText(value);
        line.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void showLine(boolean show) {
        line.setVisibility(show ? VISIBLE : GONE);
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }

    public void setValue(int valueRes) {
        tvValue.setText(valueRes);
    }
}
