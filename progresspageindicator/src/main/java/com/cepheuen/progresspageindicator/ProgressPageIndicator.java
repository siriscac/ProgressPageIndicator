package com.cepheuen.progresspageindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Muthuramakrishnan on 21-05-2015.
 * www.cepheuen.com
 */

/**
 * Draws circles (one for each view). Views till the position is filled and
 * others are only stroked.
 */
public class ProgressPageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private float mRadius = 20;
    private float mStrokeSize = 2;
    private float mDotGap = 10;

    private int mCurrentPage, mMaxPage;
    private int mStrokeColor = Color.parseColor("#ffffff");
    private int mFillColor = Color.parseColor("#ffffff");

    private ShapeDrawable mCircleDrawable;
    private GradientDrawable mStrokeDrawable;

    private ViewPager mViewPager;

    public ProgressPageIndicator(Context context) {
        this(context, null);
    }

    public ProgressPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressPageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressPageIndicator, defStyle, 0);

        mRadius = typedArray.getDimension(R.styleable.ProgressPageIndicator_radius, 20);
        mDotGap = typedArray.getDimension(R.styleable.ProgressPageIndicator_dotGap, 10);
        mStrokeSize = typedArray.getDimension(R.styleable.ProgressPageIndicator_strokeRadius, 2);

        mFillColor = typedArray.getColor(R.styleable.ProgressPageIndicator_fillColor, Color.parseColor("#ffffff"));
        mStrokeColor = typedArray.getColor(R.styleable.ProgressPageIndicator_strokeColor, Color.parseColor("#ffffff"));

        typedArray.recycle();

        drawIndicators();
    }

    /**
     * Sets the radius of the dot indicator
     *
     * @param radius Radius of the dot indicator
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
        invalidate();
    }

    /**
     * Sets the radius of the stroke
     *
     * @param size
     */
    public void setStrokeRadius(int size) {
        this.mStrokeSize = size;
        invalidate();
    }

    /**
     * Sets the gap between the indicators
     *
     * @param dotGap Gap between the indicators
     */
    public void setDotGap(int dotGap) {
        this.mDotGap = dotGap;
        invalidate();
    }

    /**
     * Sets the fill color of the indicator
     *
     * @param fillColor Fill color of the indicator
     */
    public void setFillColor(int fillColor) {
        this.mFillColor = fillColor;
        invalidate();
    }

    /**
     * Sets the stroke color of the unfilled indicator
     *
     * @param strokeColor Stroke color of the unfilled indicator
     */
    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        invalidate();
    }

    /**
     * Sets the viewpager to the indicator
     *
     * @param viewPager Viewpager
     */
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(this);
        this.mMaxPage = mViewPager.getAdapter().getCount();
        initialize();
    }

    /**
     * Sets the viewpager to the indicator and points to the specified position
     *
     * @param viewPager       ViewPager
     * @param initialPosition Default position
     */
    public void setViewPager(ViewPager viewPager, int initialPosition) {
        setViewPager(viewPager);
        setCurrentPage(initialPosition);
        initialize();
    }

    /**
     * Draws the filled and the stroked indicators
     */
    private void drawIndicators() {
        mCircleDrawable = new ShapeDrawable(new OvalShape());
        mCircleDrawable.getPaint().setStyle(Paint.Style.FILL);
        mCircleDrawable.getPaint().setColor(mFillColor);
        mCircleDrawable.getPaint().setAntiAlias(true);
        mCircleDrawable.setIntrinsicHeight((int) mRadius);
        mCircleDrawable.setIntrinsicWidth((int) mRadius);

        mStrokeDrawable = new GradientDrawable();
        mStrokeDrawable.setColor(Color.TRANSPARENT);
        mStrokeDrawable.setShape(GradientDrawable.OVAL);
        mStrokeDrawable.setStroke((int) mStrokeSize, mStrokeColor);
        mStrokeDrawable.setSize((int) mRadius, (int) mRadius);
    }

    /**
     * Sets the current page
     *
     * @param page Current page
     */
    public void setCurrentPage(int page) {
        this.mCurrentPage = page;
    }

    /**
     * Used to notify the data set change to the indicator
     */
    public void notifyDataSetChanged() {
        this.mViewPager.getAdapter().notifyDataSetChanged();
        mMaxPage = this.mViewPager.getAdapter().getCount();
        initialize();
    }

    private void initialize() {
        prepareDots();
        fillDots();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        drawIndicators();
        initialize();
    }

    /**
     * Preparing the canvas with the indicators
     */
    private void prepareDots() {
        removeAllViews();
        for (int dotLoop = 0; dotLoop < mMaxPage; dotLoop++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams((int) mRadius, (int) mRadius);
            if (dotLoop != 0) {
                if (getOrientation() == HORIZONTAL)
                    params.setMargins((int) mDotGap, 0, 0, 0);
                else
                    params.setMargins(0, (int) mDotGap, 0, 0);
            }

            imageView.setLayoutParams(params);
            imageView.setImageDrawable(mStrokeDrawable);
            imageView.setId(dotLoop);
            addView(imageView);
        }
    }

    /**
     * Filling the canvas with the indicators
     */
    private void fillDots() {
        for (int fillLoop = 0; fillLoop <= mCurrentPage; fillLoop++) {
            ((ImageView) findViewById(fillLoop)).setImageDrawable(mCircleDrawable.getCurrent());
        }
        for (int unFillLoop = mCurrentPage + 1; unFillLoop < mMaxPage; unFillLoop++) {
            ((ImageView) findViewById(unFillLoop)).setImageDrawable(mStrokeDrawable.getCurrent());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        fillDots();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        initialize();
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }


            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }


        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }
    }
}
