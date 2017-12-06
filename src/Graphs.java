import java.util.*;

public class Graphs
{
    private  int V;
    private LinkedList<Rule> adjListArray[];

    Graphs(int V)
    {
        this.V = V;
        adjListArray = new LinkedList[V];

        for(int i = 0; i < V ; i++)
        {
            adjListArray[i] = new LinkedList<>();
        }
    }


     public void addEdge(Graphs graph, int src, Rule dest)
     {
        graph.adjListArray[src].addFirst(dest);
     }

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
