package com.nrf.eyemovies.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nrf.eyemovies.R;
import com.nrf.eyemovies.utils.DensityUtils;
import com.nrf.eyemovies.utils.ScreenUtils;


/**
 * Created by Administrator on 2015/10/24.
 */
public class TopTitleBar extends android.support.v7.widget.Toolbar {
    private LinearLayout bacContainer;//返回按键区域的容器
    private View container;//title的容器
    private ImageView bacArrow;//返回箭头

    public LinearLayout getRightContainer() {
        return rightContainer;
    }

    public void setRightContainer(LinearLayout rightContainer) {
        this.rightContainer = rightContainer;
    }

    private LinearLayout rightContainer;//右侧控制区域的容器
    private TextView topBarTitle;
    private String title;
    private int titleColor = -1;
    private boolean isShowBac;
    private OnClickListener bacOnclickListener;
    private Context context;

    public TextView getRightTv() {
        return rightTv;
    }

    public void setRightTv(TextView rightTv) {
        this.rightTv = rightTv;
    }

    private TextView rightTv;
    private RelativeLayout mCenterContariner;

    /**
     * 头部标题
     *
     * @param context   上下文
     * @param title     设置标题
     * @param isShowBac 设置是否显示返回按钮
     */
    public TopTitleBar(Context context, String title, boolean isShowBac) {
        super(context);
        this.context = context;
        this.title = title;
        this.isShowBac = isShowBac;
        init();
    }

    public TopTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopTitleBar);
        isShowBac = typedArray.getBoolean(R.styleable.TopTitleBar_isShowBack, false);
        title = typedArray.getString(R.styleable.TopTitleBar_topTitleBarTitle);
        setIsShowBac(isShowBac);
        setTitle(TextUtils.isEmpty(title) ? "" : title);
        final String rightText = typedArray.getString(R.styleable.TopTitleBar_rightTitle);
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }
        typedArray.recycle();
    }

    public TopTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        this.setContentInsetsAbsolute(0, 0);
        setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = LayoutInflater.from(context).inflate(R.layout.view_title_details_bar, this, false);
        bacContainer = (LinearLayout) container.findViewById(R.id.titlebar_back_container_ll);
        bacArrow = (ImageView) container.findViewById(R.id.titlebar_back_arrow_iv);
        mCenterContariner = (RelativeLayout) container.findViewById(R.id.titlebar_center_container_rl);
        rightContainer = (LinearLayout) container.findViewById(R.id.titlebar_right_container_ll);
        topBarTitle = (TextView) container.findViewById(R.id.titlevar_title_tv);
        topBarTitle.setText(title);
        rightTv = (TextView) rightContainer.findViewById(R.id.tilebar_right_tv);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.setLayoutParams(layoutParams);
        this.addView(container);
        setBackViewShow();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setElevation(DensityUtils.dp2px(context,1));
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int measureSpec;
        int statusHeight = ScreenUtils.getStatusHeight(context);
        int height = (int) (68-  DensityUtils.px2dp(context, statusHeight));
        if (mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST) {
            measureSpec = MeasureSpec.makeMeasureSpec(DensityUtils.dp2px(context,height), mode);
        } else {
            measureSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, measureSpec);
    }

    /**
     * 清除右侧所有子类
     */
    public void clearRightChild() {
        rightContainer.removeAllViews();
        rightContainer.addView(rightTv);
    }

    /**
     *
     */
    private void setBackViewShow() {
        if (isShowBac) {
            bacContainer.setVisibility(VISIBLE);
        } else {
            bacContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题的背景颜色
     * 默认是主标题的蓝色
     *
     * @param color
     */
    public void setTopBarBackground(int color) {
        container.setBackgroundColor(color);
    }


    private void bacListener() {
        bacContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof AppCompatActivity) {
                    ((AppCompatActivity) context).finish();
                } else if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    //绑定监听事件


    /**
     * 设置右侧内容是否显示
     *
     * @param isshow
     */
    public void setRightShow(boolean isshow) {
        if (isshow) {
            rightContainer.setVisibility(View.VISIBLE);
        } else {
            rightContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右侧显示的内容
     *
     * @param view
     */
    public void setRightView(View view) {
        rightTv.setVisibility(GONE);
        if (rightContainer != null) {
            rightContainer.addView(view);
            rightContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setRightShow(true);
        }
    }

    /**
     * 自定义右侧显示内容
     *
     * @param view         自定义视图
     * @param layoutParams 布局
     */
    public void setRightView(View view, LinearLayout.LayoutParams layoutParams) {
        rightTv.setVisibility(GONE);
        if (rightContainer != null) {
            rightContainer.addView(view, layoutParams);
            rightContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setRightShow(true);
        }
    }

    /**
     * 设置右侧显示的内容
     *
     * @param view
     */
    public void setCenterView(View view) {
        topBarTitle.setVisibility(GONE);
        if (mCenterContariner != null) {
            mCenterContariner.setVisibility(VISIBLE);
            mCenterContariner.addView(view);
            mCenterContariner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setRightShow(true);
        }
    }

    /**
     * 自定义右侧显示内容
     *
     * @param view         自定义视图
     * @param layoutParams 布局
     */
    public void setCenterView(View view, RelativeLayout.LayoutParams layoutParams) {
        topBarTitle.setVisibility(GONE);
        if (mCenterContariner != null) {
            mCenterContariner.setVisibility(VISIBLE);
            mCenterContariner.addView(view, layoutParams);
            mCenterContariner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setRightShow(true);
        }
    }


    /**
     * caused by fly
     *
     * @param rightImage
     */
    public void setRightImage(@DrawableRes int rightImage) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(rightImage);
        setRightView(imageView);
    }

    /**
     * 设置显示在右侧的内容
     *
     * @param str
     */
    public void setRightText(String str) {
        if (rightTv != null) {
            rightContainer.setVisibility(VISIBLE);
            rightTv.setText(str);
        }
    }

    /**
     * 设置显示在右侧的内容
     *
     * @param str
     */
    public void setRightText(String str, int textColdr) {
        if (rightTv != null) {
            rightContainer.setVisibility(VISIBLE);
            rightTv.setText(str);
            rightTv.setTextColor(textColdr);
        }
    }

    /**
     * 根据需要设置返回按键的样式(这将会覆盖掉返回箭头)
     *
     * @param view 样式的视图
     */
    public void setBackView(View view) {
        if (view != null) {
            bacArrow.setVisibility(View.GONE);
            bacContainer.addView(view);
            bacContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setIsShowBac(true);
        }
    }

    /**
     * 设置右侧的监听事件
     *
     * @param rightClickListener 监听器
     */
    public void setRightClickListener(OnClickListener rightClickListener) {
        if (rightClickListener != null) {
            rightContainer.setOnClickListener(rightClickListener);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
        topBarTitle.setText(this.title);
    }

    /*
    * 设置标题
    *
    * */
    @Override
    public void setTitle(@StringRes int stringId) {
        this.title = context.getString(stringId);
        topBarTitle.setText(title);
    }

    /**
     * 设置标题字体颜色
     *
     * @param color 标题颜色
     */
    public void setTitleColor(int color) {
        topBarTitle.setTextColor(color);
    }




    /**
     * 设置是否显示返回按钮
     *
     * @param isShowBac true显示 false不显示
     */
    public void setIsShowBac(boolean isShowBac) {
        this.isShowBac = isShowBac;
        setBackViewShow();
        bacListener();
    }

    /**
     * 设置返回的监听（如果不设置会执行默认的监听）
     *
     * @param bacOnclickListener 单击事件监听器
     */
    public void setBacOnclickListener(OnClickListener bacOnclickListener) {
        this.bacOnclickListener = bacOnclickListener;
        bacContainer.setOnClickListener(bacOnclickListener);
    }

    /**
     * 设置自定义的视图的背景
     *
     * @param color
     */
    public void setCustomBg(int color) {
        container.setBackgroundColor(color);
    }

}
