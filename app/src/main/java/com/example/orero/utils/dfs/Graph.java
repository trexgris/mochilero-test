package com.example.orero.utils.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    // No. of vertices in graph
    private int v;
    private String[] cities;

    // adjacency list
    //private ArrayList<String>[] adjList;
    private Map<String, List<String>> adjList;
    //Constructor
    public Graph(String[] cr_cities) {

        //initialise vertex count
        this.cities = cr_cities;
        this.v = cities.length;

        // initialise adjacency list
        initAdjList();
    }

    // utility method to initialise
    // adjacency list
    @SuppressWarnings("unchecked")
    private void initAdjList() {
        adjList = new HashMap<String, List<String>>();

        for (int i = 0; i < cities.length; ++i) {
            adjList.put(cities[i], new ArrayList<String>());
        }
    }

    // add edge from u to v
    public void addEdge(String u, String v) {
        // Add v to u's >list.
        List<String> tmp = adjList.get(u);
        tmp.add(v);
        adjList.put(u, tmp);
    }

    // Prints all paths from
    // 's' to 'd'
    public void printAllPaths(int s, int d) {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(s);

        //Call recursive utility
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores actual
    // vertices in the current path
    private void printAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                   List<Integer> localPathList) {

        // Mark the current node
        isVisited[u] = true;

        if (u.equals(d)) {
            System.out.println(localPathList);
            // if match found then no need to traverse more till depth
            isVisited[u] = false;
            return;
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Integer i : adjList[u]) {
            if (!isVisited[i]) {
                // store current node
                // in path[]
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }
}