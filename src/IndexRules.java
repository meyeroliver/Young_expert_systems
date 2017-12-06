import java.util.*;

public class IndexRules
{
    private  int numOfRules;

    public LinkedList<Rule> graphRules(LinkedList<Rule> graphIndexLinkedList)
    {
        ListIterator<Rule> outerIter = graphIndexLinkedList.listIterator();
        int i = 0;
        int prev_i = 0;
        while (outerIter.hasNext())
        {
            Rule checkOut = outerIter.next();
            ListIterator<Rule> innerIter = graphIndexLinkedList.listIterator();
            while (innerIter.hasNext())
            {
                Rule checkIn = innerIter.next();
                if (checkIn.getIndex() == -1)
                {
                    if(checkOut.getConclusion().equals(checkIn.getConclusion()))
                    {
                        prev_i = i;
                        checkIn.setIndex(prev_i);
                    }
                }
            }
            i = prev_i;
            i++;
        }
        this.numOfRules = prev_i + 1;
        return (graphIndexLinkedList);
    }

    public int getNumOfRules()
    {
        return numOfRules;
    }
}
