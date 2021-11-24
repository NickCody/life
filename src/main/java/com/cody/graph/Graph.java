package com.cody.graph;

import java.util.ArrayList;

public class Graph {

    static class AdjacencyListGraph {
        int numVertices;
        ArrayList[] adj;


        // constructor - where we create an empty array of ArrayList<Integer>
        //
        AdjacencyListGraph(int numVertices) {
            this.numVertices = numVertices;
            adj = new ArrayList[numVertices];
            for (int i = 0; i < numVertices; i++) {
                adj[i] = new ArrayList<Integer>();
            }
        }



        // undirected, add two edges
        void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }

        // Prints dot language representation, one row per edge relationship:
        //
        // Should print:
        //  graph {
        //   0 -- 1
        //   1 -- 2
        //   1 -- 3
        //   3 -- 0
        //   3 -- 4
        // }
        //
        // NOTE: This is not the physical representation of the graph, but one xdot needs
        //
        void printDot() {
            System.out.println("strict graph {");
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < adj[i].size(); j++) {
                    System.out.print(i + " -- " + adj[i].get(j));
                    System.out.println();
            }
            }
            System.out.println("}");
        }

        // Prints physical structure of the grid
        //
        // Should print:
        //
        // adjacency-list:
        //  Node 0: -- 1 -- 3
        //  Node 1: -- 0 -- 2 -- 3
        //  Node 2: -- 1
        //  Node 3: -- 1 -- 0 -- 4
        //  Node 4: -- 3
        // }
        void printStructure() {
            System.out.println("Adjacency list:");
            for (int i = 0; i < numVertices; i++) {
                System.out.print("Node " + i + ": ");
                for (Object x : adj[i]) {
                    System.out.print("-- " + x + " ");
                }
                System.out.println();
            }
            System.out.println("}");
        }

    }
    public static void main(String[] args) {
        AdjacencyListGraph graph = new AdjacencyListGraph(5);

        graph.addEdge(0,1);
        graph.addEdge(1, 2);
        graph.addEdge(1,3);
        graph.addEdge(3,0);
        graph.addEdge(3,4);

        //graph.printStructure();
        graph.printDot();
    }
}
