package ru.omsk.metro.gui;

import org.jetbrains.annotations.NotNull;

import ru.omsk.metro.model.StationCoordinate;

/**
 * Created by adkozlov on 06.11.14.
 */
public class VertexCoordinate {

    private final int x;
    private final int y;

    private VertexCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "VertexCoordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @NotNull
    public static VertexCoordinate createFromStationCoordinate(@NotNull StationCoordinate coordinate,
                                                               double offsetX, double offsetY,
                                                               double scaleX, double scaleY,
                                                               int width, int height) {
        int x = (int) ((coordinate.getX() - offsetX) / scaleX * width * 0.8);
        int y = (int) ((coordinate.getY() - offsetY) / scaleY * height * 0.8);

        return new VertexCoordinate((int) (0.1 * width + x), (int) (0.1 * height + height - y));
    }
}
