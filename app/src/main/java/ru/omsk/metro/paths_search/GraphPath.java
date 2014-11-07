package ru.omsk.metro.paths_search;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by avesloguzova on 06.11.14.
 *
 */
public class GraphPath {

    private int time;
    @NotNull
    private List<Integer> ids;

    public GraphPath(@NotNull List<Integer> ids, int time) {
        this.time = time;
        this.ids = ids;
    }

    public int getTime() {
        return time;
    }

    @NotNull
    public List<Integer> getIds() {
        return ids;
    }
}
