package ru.omsk.metro.gui;

import org.jetbrains.annotations.NotNull;

import ru.omsk.metro.model.AbstractSubwayObject;
import ru.omsk.metro.model.StationCoordinate;

/**
 * Created by adkozlov on 06.11.14.
 */
public class VertexCoordinate extends AbstractSubwayObject {

    private final int x;
    private final int y;

    public VertexCoordinate(int id, int x, int y) {
        super(id);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexCoordinate that = (VertexCoordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @NotNull
    public static VertexCoordinate createFromStationCoordinate(int id, @NotNull StationCoordinate coordinate,
                                                               int width, int height) {
        int x = (int) (coordinate.getX() * width * 0.8 + width * 0.1);
        int y = (int) ((height + width) / 2 - width * 0.1 - coordinate.getY() * width * 0.8);

        return new VertexCoordinate(id, x, y);
    }
}
