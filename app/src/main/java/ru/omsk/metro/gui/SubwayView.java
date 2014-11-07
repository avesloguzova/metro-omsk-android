package ru.omsk.metro.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
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
import ru.omsk.metro.paths_search.GraphPath;

/**
 * Created by adkozlov on 06.11.14.
 */
public class SubwayView extends View {

    private static final int SECONDS_IN_MINUTE = 60;
    private static final String NO_SELECTED_MESSAGE = "Выберите стартовую станцию";
    private static final String START_SELECTED_MESSAGE = "Стартовая станция: \"%s\"\nВыберите конечную станцию";
    private static final String PATH_MESSAGE = "Стартовая станция: \"%s\"\nКонечная станция: \"%s\"\nОбщее время в пути: %d:%d";

    private static final int SELECTED_VERTEX_ALPHA = 0xFF;
    private static final int COLOR_MASK = 0x48000000;

    private final int pxWidth;
    private final int pxHeight;
    private final float radius;

    @NotNull
    private final Bitmap bitmap;

    @NotNull
    private final Paint mapPaint;
    @NotNull
    private final TextPaint textPaint;
    @NotNull
    private final Paint bitmapPaint;

    @NotNull
    private SubwayMap map;
    @NotNull
    private Graph graph;
    @Nullable
    private GraphPath path;

    private int fromId = -1;
    private int toId = -1;

    @NotNull
    private final Set<Vertex> vertices = new HashSet<Vertex>();

    public SubwayView(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        pxWidth = dpToPx(dpWidth);
        pxHeight = dpToPx(dpHeight);

        bitmap = Bitmap.createBitmap(pxHeight, pxWidth, Bitmap.Config.ARGB_8888);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        radius = 10;

        mapPaint = new Paint();
        mapPaint.setAntiAlias(true);
        mapPaint.setDither(true);
        mapPaint.setStrokeWidth(5f);
        mapPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mapPaint.setStrokeJoin(Paint.Join.ROUND);
        mapPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setShadowLayer(10f, 10f, 10f, Color.BLACK);

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
                    Vertex coordinate = vertexIsTouched(x, y);

                    if (coordinate != null) {
                        if (fromId == -1 && toId == -1) {
                            fromId = coordinate.getId();
                        } else if (toId == -1) {
                            toId = coordinate.getId();

                            path = graph.getpath(fromId, toId);
                        } else {
                            fromId = coordinate.getId();
                            toId = -1;

                            path = null;
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
    private Vertex vertexIsTouched(float x, float y) {
        for (Vertex vertex : vertices) {
            if (isInCircle(x, vertex.getX()) && isInCircle(y, vertex.getY())) {
                return vertex;
            }
        }

        return null;
    }

    @NotNull
    private String findVertexNameById(int id) {
        for (Vertex vertex : vertices) {
            if (vertex.getId() == id) {
                return vertex.getName();
            }
        }

        throw new RuntimeException("no such station found");
    }

    private boolean isInCircle(float x, float center) {
        return center - radius <= x && x <= center + radius;
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
            mapPaint.setColor(line.getColor() | COLOR_MASK);

            Iterator<Station> iterator = line.getStations().iterator();

            Vertex current = nextCoordinate(iterator);
            drawVertex(canvas, current);

            while (iterator.hasNext()) {
                Vertex previous = current;

                current = nextCoordinate(iterator);
                drawVertex(canvas, current);

                canvas.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY(), mapPaint);
            }
        }
    }

    private void drawTitle(@NotNull Canvas canvas) {
        String title = null;
        if (fromId == -1 && toId == -1) {
            title = NO_SELECTED_MESSAGE;
        } else if (toId == -1) {
            title = String.format(START_SELECTED_MESSAGE, findVertexNameById(fromId));
        } else {
            title = String.format(PATH_MESSAGE,
                    findVertexNameById(fromId), findVertexNameById(toId),
                    path.getTime() / SECONDS_IN_MINUTE, path.getTime() % SECONDS_IN_MINUTE);
        }

        int x = 0;
        int y = 50;
        for (String line : title.split("\n")) {
            canvas.drawText(line, x, y, textPaint);
            y += textPaint.ascent() + textPaint.descent();
        }
    }

    private void drawVertex(@NotNull Canvas canvas, @NotNull Vertex current) {
        int oldAlpha = mapPaint.getAlpha();
        if (current.getId() == fromId || current.getId() == toId) {
            mapPaint.setAlpha(SELECTED_VERTEX_ALPHA);
        }

        canvas.drawCircle(current.getX(), current.getY(), radius, mapPaint);
        mapPaint.setAlpha(oldAlpha);

        canvas.drawText(current.getName(), current.getX(), current.getY(), textPaint);
    }

    @NotNull
    private Vertex nextCoordinate(Iterator<Station> iterator) {
        Station station = iterator.next();

        Vertex result = Vertex.createFromStationCoordinate(station.getId(),
                station.getName(),
                station.getCoordinate(),
                pxWidth, pxHeight);
        vertices.add(result);

        return result;
    }

    @Override
    protected void onDraw(@NotNull Canvas canvas) {
        drawMap(canvas);
        drawTitle(canvas);
        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
