package com.guide.zelda.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guide.zelda.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleView extends RelativeLayout {

    @BindView(R.id.title_root)
    RelativeLayout layoutRoot;
    @BindView(R.id.tv_center_title)
    TextView tvCenterTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        int backgroundColor = typedArray.getResourceId(R.styleable.TitleView_title_background_color, R.color.colorWhite);
        int centerColor = typedArray.getColor(R.styleable.TitleView_title_color_center, getResources().getColor(R.color.colorBlack));
        int leftColor = typedArray.getResourceId(R.styleable.TitleView_title_color_left, getResources().getColor(R.color.colorBlack));
        int rightColor = typedArray.getResourceId(R.styleable.TitleView_title_color_right, getResources().getColor(R.color.colorBlack));
        String centerText = typedArray.getString(R.styleable.TitleView_title_text_center);
        String leftText = typedArray.getString(R.styleable.TitleView_title_text_left);
        String rightText = typedArray.getString(R.styleable.TitleView_title_text_right);
        int leftImgRes = typedArray.getResourceId(R.styleable.TitleView_title_img_left, 0);
        int rightImgRes = typedArray.getResourceId(R.styleable.TitleView_title_img_right, 0);

        typedArray.recycle();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_view_titlebar, this, true);
        ButterKnife.bind(this, view);

        layoutRoot.setBackgroundResource(backgroundColor);
        tvLeft.setTextColor(leftColor);
        tvRight.setTextColor(rightColor);
        tvCenterTitle.setTextColor(centerColor);

        if (!TextUtils.isEmpty(centerText)) centerTitle(centerText);
        if (!TextUtils.isEmpty(leftText)) leftTitle(leftText);
        if (!TextUtils.isEmpty(rightText)) rightTitle(rightText);
        if (leftImgRes != 0) leftImage(leftImgRes);
        if (rightImgRes != 0) rightImage(rightImgRes);
    }

    public void centerTitle(@StringRes int titleRes) {
        tvCenterTitle.setText(titleRes);
    }

    public void centerTitle(String title) {
        tvCenterTitle.setText(title);
    }

    public void centerColor(@ColorRes int colorRes) {
        tvCenterTitle.setTextColor(getResources().getColor(colorRes));
    }

    public void rightTitle(@StringRes int titleRes) {
        ivRight.setVisibility(GONE);
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(titleRes);
    }

    public void rightTitle(String leftText) {
        ivRight.setVisibility(GONE);
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(leftText);
    }

    public void rightImage(@DrawableRes int imageRes) {
        tvRight.setVisibility(GONE);
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(imageRes);
    }

    public void rightColor(@ColorRes int colorRes) {
        ivRight.setVisibility(GONE);
        tvRight.setVisibility(VISIBLE);
        tvRight.setTextColor(getResources().getColor(colorRes));
    }

    public void leftTitle(@StringRes int titleRes) {
        ivLeft.setVisibility(GONE);
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(titleRes);
    }

    public void leftTitle(String rightText) {
        ivLeft.setVisibility(GONE);
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(rightText);
    }

    public void leftImage(@DrawableRes int imageRes) {
        tvLeft.setVisibility(GONE);
        ivLeft.setVisibility(VISIBLE);
        ivLeft.setImageResource(imageRes);
    }

    public void clickLeft(OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
        ivLeft.setOnClickListener(listener);
    }

    public void clickRight(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
        ivRight.setOnClickListener(listener);
    }

    public void clickCenter(OnClickListener listener) {
        tvCenterTitle.setOnClickListener(listener);
    }

    public void leftClickable(boolean clickable) {
        tvLeft.setClickable(clickable);
        ivLeft.setClickable(clickable);
    }

    public void rightClickable(boolean clickable) {
        tvRight.setClickable(clickable);
        ivRight.setClickable(clickable);
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public void leftImageInvisible() {
        ivLeft.setVisibility(GONE);
    }

}