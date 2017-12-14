import java.util.LinkedList;

public class KnowledgeBase
{
    private Graphs rules;
    private LinkedList<Facts> facts;
    private LinkedList<Query> query;

    public KnowledgeBase(Graphs rules, LinkedList<Facts> facts, LinkedList<Query> query)
    {
        this.rules = rules;
        this.facts = facts;
        this.query = query;
    }

    public Graphs getRules()
    {
        return rules;
    }

    public LinkedList<Facts> getFacts()
    {
        return facts;
    }

    public void setFacts(LinkedList<Facts> facts)
    {
        this.facts = facts;
    }

    public LinkedList<Query> getQuery()
    {
        return query;
    }
}
