package ru.omsk.metro.gui;

import org.jetbrains.annotations.NotNull;

import ru.omsk.metro.model.NamedSubwayObject;
import ru.omsk.metro.model.StationCoordinate;

/**
 * Created by adkozlov on 06.11.14.
 */
public class Vertex extends NamedSubwayObject {

    private final int x;
    private final int y;

    public Vertex(int id, @NotNull String name, int x, int y) {
        super(id, name);
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
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex that = (Vertex) o;

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
    public static Vertex createFromStationCoordinate(int id, @NotNull String name,
                                                     @NotNull StationCoordinate coordinate,
                                                     int width, int height) {
        int x = (int) (coordinate.getX() * width * 0.8 + width * 0.1);
        int y = (int) ((height + width) / 2 - width * 0.1 - coordinate.getY() * width * 0.8);

        return new Vertex(id, name, x, y);
    }
}
