import java.util.*;

public class Graphs
{
    private  int V;
    private LinkedList<Rule> adjListArray[];

    // constructor
    Graphs(int V)
    {
        this.V = V;

        // define the size of array as
        // number of vertices
        adjListArray = new LinkedList[V];

        // Create a new list for each vertex
        // such that adjacent nodes can be stored
        for(int i = 0; i < V ; i++)
        {
            adjListArray[i] = new LinkedList<>();
        }
    }


     // Adds an edge to an undirected graph
     public void addEdge(Graphs graph, int src, Rule dest)
     {
        // Add an edge from src to dest.
        graph.adjListArray[src].addFirst(dest);
     }

     // A utility function to print the adjacency list
     // representation of graph
     public int numberOfNodes(Graphs graph)
     {
        int numberOfNodes = 0;
        for(int v = 0; v < graph.V; v++)
        {
            for(Rule pCrawl: graph.adjListArray[v])
            {
                numberOfNodes++;
            }
        }
        return numberOfNodes;
     }

    public LinkedList<Rule> getRulesList(int i)
    {
        return adjListArray[i];
    }

    public int getV()
    {
        return V;
    }

    public LinkedList<Rule>[] getAdjListArray() {
        return adjListArray;
    }
}
