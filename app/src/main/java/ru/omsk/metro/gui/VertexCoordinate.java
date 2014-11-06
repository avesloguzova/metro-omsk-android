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
                                                               int width, int height) {
        int x = (int) (coordinate.getX() * width * 0.8 + width * 0.1);
        int y = (int) ((height + width) / 2 - width * 0.1 - coordinate.getY() * width * 0.8);

        return new VertexCoordinate(x, y);
    }
}
