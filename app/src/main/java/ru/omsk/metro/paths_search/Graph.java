package ru.omsk.metro.paths_search;

import android.util.SparseIntArray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.omsk.metro.objects.Line;
import ru.omsk.metro.objects.Station;
import ru.omsk.metro.objects.TimeInfinity;
import ru.omsk.metro.objects.WayStation;

/**
 * Created by avesloguzova on 05.11.14.
 *
 */
public class Graph {

    public static Graph fromModel(List<Line>lines,List<WayStation> transitions){
        int count=0;
        for (Line line:lines){
            count+=line.getStations().size();
        }
        Graph graph = new Graph(count);
        int last_id = 0;
        for(Line line:lines)
            for (Station station:line.getStations()){
               Vertex v = new Vertex(last_id);
                graph.vertexes[last_id] = v;
                if(!station.getTimeToNext().equals(TimeInfinity.getInstance()))
                    v.edges.add(new Edge(last_id+1,station.getTimeToNextInSeconds()));
                    //graph.weights[last_id][last_id+1] = station.getTimeToNextInSeconds();
                if(!station.getTimeToPrevious().equals(TimeInfinity.getInstance()))
                    v.edges.add(new Edge(last_id-1,station.getTimeToNextInSeconds()));
                graph.vertexesMap.put(station.getId(), last_id);
                last_id++;
            }
        for(WayStation transition:transitions){
            int from = graph.vertexesMap.get(transition.getIdFrom());
            int to = graph.vertexesMap.get(transition.getIdTo());
            graph.vertexes[from].edges.add(new Edge(to, transition.getWayTimeInSeconds()));
        }
       return graph;
    }
    public GraphPath getpath(int fromId,int toId){

        int[] d = new int[vertexes.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        int[] p = new int[vertexes.length];
        boolean[] used = new boolean[vertexes.length];
        int start = vertexesMap.get(fromId);
        int end = vertexesMap.get(toId);
        d[start]=0;
        for (int i=0; i<vertexes.length; ++i) {
            int v = -1;
            for (int j=0; j<vertexes.length; ++j)
                if (!used[j] && (v == -1 || d[j] < d[v]))
                    v = j;
            if (d[v] == Integer.MAX_VALUE)
                break;
            used[v] = true;

            for (Edge e:vertexes[v].edges) {
                int to = e.idTo;
                 int len = e.weight;
                if (d[v] + len < d[to]) {
                    d[to] = d[v] + len;
                    p[to] = v;
                }
            }
        }
        List<Integer> path = new LinkedList<Integer>();

        for (int v=end; v!=start; v=p[v]){
            path.add (v);
        }
        path.add(start);
        Collections.reverse(path);
        return new GraphPath(path,d[end]);

    }
    private Graph(int count){
        vertexes = new Vertex[count];
        vertexesMap = new SparseIntArray(count);
    }
    private Vertex[] vertexes;
    private SparseIntArray vertexesMap;


    private static class Vertex {
        int id;
        List<Edge> edges;

        public Vertex(int id) {
            this.id=id;
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
