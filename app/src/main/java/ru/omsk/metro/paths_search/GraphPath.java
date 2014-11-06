package ru.omsk.metro.paths_search;

import java.util.List;

/**
 * Created by avesloguzova on 06.11.14.
 *
 */
public class GraphPath {
    private int time;
    private List<Integer> ids;

    public GraphPath( List<Integer> ids,int time) {
        this.time = time;
        this.ids = ids;
    }
}
