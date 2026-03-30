package comp3350.fittrack.presentation.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import comp3350.fittrack.R;

/** **************************************************
 CLASS NAME: CircleProgressBar.java

 CLASS FUNCTION: the class that handles the circle visual for the app

 ************************************************** */

public class CircleProgressBar extends View {
    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF arcRect;
    private float progress = 0; // 0-100%
    private float maxValue = 100; // Default max value
    private float startAngle = 0;  // Default start position
    private float sweepAngle = 360;  // Default arc span
    private int backgroundColor = 0xFFCCCCCC; // Default background colour
    private int progressColor = 0xFFde1b1b;   // Default bar colour
    private float strokeWidth = 50f; // Default Arc thickness
    private int padding = 50; // Default padding of the drawable area for the view

    public CircleProgressBar(Context context) {
        super(context);
        init(null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init( attrs);
    }


    //set variables to proper value on initialization
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
            startAngle = a.getFloat(R.styleable.CircleProgressBar_startAngle, startAngle);
            sweepAngle = a.getFloat(R.styleable.CircleProgressBar_sweepAngle, sweepAngle);
            backgroundColor = a.getColor(R.styleable.CircleProgressBar_backgroundArcColor, backgroundColor);
            progressColor = a.getColor(R.styleable.CircleProgressBar_progressArcColor, progressColor);
            strokeWidth = a.getFloat(R.styleable.CircleProgressBar_arcStrokeWidth, strokeWidth);
            maxValue = a.getFloat(R.styleable.CircleProgressBar_maxValue, maxValue);
            // Check if padding is set; if not, default to strokeWidth / 2
            if (a.hasValue(R.styleable.CircleProgressBar_padding)) {
                padding = a.getInt(R.styleable.CircleProgressBar_padding, (int) (strokeWidth / 2));
            } else {
                padding = (int) (strokeWidth / 2);
            }
            a.recycle();
        }

        backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (arcRect == null) {
            arcRect = new RectF(padding, padding, getWidth() - padding, getHeight() - padding);
        }

        // Draw background arc
        canvas.drawArc(arcRect, startAngle, sweepAngle, false, backgroundPaint);

        // Scale progress to fit within the arc sweep
        float scaledProgress = (progress / maxValue) * sweepAngle;
        // Draw foreground progress arc
        canvas.drawArc(arcRect, startAngle, scaledProgress, false, progressPaint);
    }

    // Set progress dynamically
    public void setProgress(float value) {
        progress = Math.max(0, Math.min(maxValue, value)); // Clamp to maxValue
        invalidate();
    }

    // Set max value dynamically
    public void setMaxValue(float max) {
        maxValue = max;
        invalidate();
    }


}
