package ru.omsk.metro.paths_search;

import android.util.SparseIntArray;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.omsk.metro.model.Line;
import ru.omsk.metro.model.Station;
import ru.omsk.metro.model.SubwayMap;
import ru.omsk.metro.model.TimeInfinity;
import ru.omsk.metro.model.WayStation;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class Graph {
    private Vertex[] vertexes;
    private SparseIntArray vertexesMap;// station id to index of vertex

    public Graph(@NotNull SubwayMap subwayMap) {
        int count = 0;
        for (Line line : subwayMap.getLines()) {
            count += line.getStations().size();
        }
        vertexes = new Vertex[count];
        vertexesMap = new SparseIntArray(count);
        int last_id = 0;
        for (Line line : subwayMap.getLines())
            for (Station station : line.getStations()) {
                Vertex v = new Vertex(station.getId());
                vertexes[last_id] = v;
                if (!station.getTimeToNext().equals(TimeInfinity.getInstance()))
                    v.edges.add(new Edge(last_id + 1, station.getTimeToNextInSeconds()));
                if (!station.getTimeToPrevious().equals(TimeInfinity.getInstance()))
                    v.edges.add(new Edge(last_id - 1, station.getTimeToPreviousInSecondes()));
                vertexesMap.put(station.getId(), last_id);
                last_id++;
            }
        for (WayStation transition : subwayMap.getWayStations()) {
            int from = vertexesMap.get(transition.getIdFrom());
            int to = vertexesMap.get(transition.getIdTo());
            vertexes[from].edges.add(new Edge(to, transition.getWayTimeInSeconds()));
        }
    }

    @NotNull
    public GraphPath getpath(int fromId, int toId) {
        int[] d = new int[vertexes.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        int[] p = new int[vertexes.length];
        boolean[] used = new boolean[vertexes.length];
        int start = vertexesMap.get(fromId);
        int end = vertexesMap.get(toId);
        d[start] = 0;
        for (int i = 0; i < vertexes.length; ++i) {
            int v = -1;
            for (int j = 0; j < vertexes.length; ++j)
                if (!used[j] && (v == -1 || d[j] < d[v]))
                    v = j;
            if (d[v] == Integer.MAX_VALUE)
                break;
            used[v] = true;

            for (Edge e : vertexes[v].edges) {
                int to = e.idTo;
                int len = e.weight;
                if (d[v] + len < d[to]) {
                    d[to] = d[v] + len;
                    p[to] = v;
                }
            }
        }
        List<Integer> path = new LinkedList<Integer>();
        if (d[end] != Integer.MAX_VALUE) {
            for (int v = end; v != start; v = p[v]) {
                path.add(vertexes[v].external_id);
            }
            path.add(vertexes[start].external_id);
            Collections.reverse(path);
        }
        return new GraphPath(path, d[end]);

    }

    private static class Vertex {
        int external_id;
        List<Edge> edges;

        public Vertex(int external_id) {
            this.external_id = external_id;
            this.edges = new LinkedList<Edge>();
        }
    }

    private static class Edge {
        int idTo;
        int weight;

        public Edge(int idTo, int weight) {
            this.idTo = idTo;
            this.weight = weight;
        }
    }
}
