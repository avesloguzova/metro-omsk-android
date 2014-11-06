package ru.omsk.metro.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ru.omsk.metro.model.Line;
import ru.omsk.metro.model.Station;
import ru.omsk.metro.model.SubwayMap;
import ru.omsk.metro.paths_search.Graph;

/**
 * Created by adkozlov on 06.11.14.
 */
public class SubwayView extends View {

    private static final int SELECTED_VERTEX_ALPHA = 0x48;
    private static final int COLOR_MASK = 0xFF000000;

    private final int pxWidth;
    private final int pxHeight;
    private final float radius = 10;

    @NotNull
    private final Bitmap bitmap;

    @NotNull
    private final Paint paint;
    @NotNull
    private final Paint bitmapPaint;

    @NotNull
    private SubwayMap map;
    @NotNull
    private Graph graph;

    private int fromId = -1;
    private int toId = -1;

    @NotNull
    private final Set<VertexCoordinate> vertices = new HashSet<VertexCoordinate>();

    public SubwayView(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        pxWidth = dpToPx(dpWidth);
        pxHeight = dpToPx(dpHeight);

        bitmap = Bitmap.createBitmap(pxHeight, pxWidth, Bitmap.Config.ARGB_8888);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        setWillNotDraw(false);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (graph == null) {
                    return false;
                }

                float x = motionEvent.getX();
                float y = motionEvent.getY();

                switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    VertexCoordinate coordinate = vertexIsTouched(x, y);

                    if (coordinate != null) {
                        if (fromId == -1) {
                            fromId = coordinate.getId();
                        } else if (toId == -1) {
                            toId = coordinate.getId();
                        } else {
                            fromId = coordinate.getId();
                            toId = -1;
                        }

                        invalidate();
                    }

                    break;
                }

                return true;
            }
        });
    }

    @Nullable
    private VertexCoordinate vertexIsTouched(float x, float y) {
        for (VertexCoordinate coordinate : vertices) {
            if  (isInCircle(x, coordinate.getX()) && isInCircle(y, coordinate.getY())) {
                return coordinate;
            }
        }

        return null;
    }

    private boolean isInCircle(float x, float center) {
        return center - radius <= x && x <= center + radius;
    }

    @NotNull
    public SubwayMap getMap() {
        return map;
    }

    public void setMap(@NotNull SubwayMap map) {
        this.map = map;

        graph = new Graph(map);
        invalidate();
    }

    public void drawMap(@NotNull Canvas canvas) {
        if (map == null) {
            return;
        }

        for (Line line : map.getLines()) {
            paint.setColor(line.getColor() | COLOR_MASK);

            Iterator<Station> iterator = line.getStations().iterator();

            VertexCoordinate current = nextCoordinate(iterator);
            drawCircle(canvas, current);

            while (iterator.hasNext()) {
                VertexCoordinate previous = current;

                current = nextCoordinate(iterator);
                drawCircle(canvas, current);

                canvas.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY(), paint);
            }
        }
    }

    private void drawCircle(@NotNull Canvas canvas, @NotNull VertexCoordinate current) {
        int oldAlpha = paint.getAlpha();
        if (current.getId() == fromId || current.getId() == toId) {
            paint.setAlpha(SELECTED_VERTEX_ALPHA);
        }

        canvas.drawCircle(current.getX(), current.getY(), radius, paint);
        paint.setAlpha(oldAlpha);
    }

    @NotNull
    private VertexCoordinate nextCoordinate(Iterator<Station> iterator) {
        Station station = iterator.next();

        VertexCoordinate result =  VertexCoordinate.createFromStationCoordinate(station.getId(), station.getCoordinate(),
                pxWidth, pxHeight);
        vertices.add(result);

        return result;
    }

    @Override
    protected void onDraw(@NotNull Canvas canvas) {
        drawMap(canvas);
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
