package ru.omsk.metro.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import ru.omsk.metro.model.Line;
import ru.omsk.metro.model.Station;
import ru.omsk.metro.model.StationCoordinate;
import ru.omsk.metro.model.SubwayMap;

/**
 * Created by adkozlov on 06.11.14.
 */
public class SubwayView extends View {

    private final int pxWidth;
    private final int pxHeight;

    @NotNull
    private final Bitmap bitmap;
    @NotNull
    private final Canvas canvas;

    @NotNull
    private final Paint paint;
    @NotNull
    private final Paint bitmapPaint;

    @NotNull
    private SubwayMap map;

    public SubwayView(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        pxWidth = dpToPx(dpWidth);
        pxHeight = dpToPx(dpHeight);

        bitmap = Bitmap.createBitmap(pxHeight, pxWidth, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xffff0505);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

//        canvas.drawLine(0, 0, pxWidth, pxHeight, paint);
//        canvas.drawLine(pxWidth, 0, pxHeight, 0, paint);
    }

    @NotNull
    public SubwayMap getMap() {
        return map;
    }

    public void setMap(@NotNull SubwayMap map) {
        this.map = map;
    }

    public void drawMap() {
        for (Line line : map.getLines()) {
            paint.setColor(line.getColor());

            Iterator<Station> iterator = line.getStations().iterator();

            VertexCoordinate current = nextCoordinate(iterator);
            while (iterator.hasNext()) {
                VertexCoordinate previous = current;
                current = nextCoordinate(iterator);

                canvas.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY(), paint);
            }
        }
    }

    private VertexCoordinate nextCoordinate(Iterator<Station> iterator) {
        StationCoordinate[] boundingRectangle = map.getBoundingRectangle();
        double scaleX = boundingRectangle[1].getX() - boundingRectangle[0].getX();
        double scaleY = boundingRectangle[1].getY() - boundingRectangle[0].getY();

        double offsetX = boundingRectangle[0].getX();
        double offsetY = boundingRectangle[1].getY();

        return VertexCoordinate.createFromStationCoordinate(iterator.next().getCoordinate(),
                offsetX, offsetY,
                scaleX, scaleY,
                pxWidth, pxHeight);
    }

    @Override
    protected void onDraw(@NotNull Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
