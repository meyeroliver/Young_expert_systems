import java.util.*;

public class Expert
{
    public KnowledgeBase separator(LinkedList fileList)
    {
        LinkedList<Query> queries = new LinkedList<>();
        LinkedList<Rule> rules = new LinkedList<>();
        LinkedList<Facts> facts = new LinkedList<>();
        String[] s_array;
        ListIterator<String> litr = fileList.listIterator();

        while (litr.hasNext())
        {
            String element = litr.next();
            element = element.replaceAll("\\s+","");
            if(element.contains("=>"))
            {
                s_array = ((element)).split("=>");
                Rule temp = new Rule(s_array[1], ((element)));
                rules.add(temp);
            }
            else if (element.startsWith("="))
            {
                int i = 0;
                while (i < element.length() )
                {
                    if(element.charAt(i) != '=')
                    {
                        Facts fact = new Facts(element.charAt(i), true);
                        facts.add(fact);
                    }
                    i++;
                }
            }
            else if (element.startsWith("?"))
            {
                int i = 0;
                while (i < element.length())
                {
                    if(element.charAt(i) != '?')
                    {
                        Query query = new Query(element.charAt(i));
                        queries.add(query);
                    }
                    i++;
                }
            }
        }
        IndexRules indexRules = new IndexRules();
        ListIterator<Rule> check = (indexRules.graphRules(rules)).listIterator();
        Graphs graphedRules = new Graphs(indexRules.getNumOfRules());
        while (check.hasNext())
        {
            Rule element = check.next();
            graphedRules.addEdge(graphedRules, element.getIndex(), element);
        }
        KnowledgeBase knowledgeBase = new KnowledgeBase(graphedRules, facts, queries);
        return knowledgeBase;
    }

    public  LinkedList<Facts> confirmList(KnowledgeBase knowledgeBase)
    {
        Graphs graph = knowledgeBase.getRules();
        LinkedList<String> crlsit = new LinkedList<>();

        for (int v = 0; v < graph.getV(); v++)
        {
            for (Rule rule: graph.getRulesList(v))
            {
                crlsit.add(rule.getConclusion());
                break;
            }
        }

        LinkedList<Facts> facts = knowledgeBase.getFacts();
        ListIterator<Facts> factsListIterator = facts.listIterator();
        while(factsListIterator.hasNext())
        {
            Facts fact = factsListIterator.next();
            crlsit.add(fact.getOperand()+"");

        }

        for(int v = 0; v < graph.getV(); v++)
        {
            for (Rule rule : graph.getRulesList(v))
            {

                String[] operands = (rule.getRule()).split("=>|\\+|\\(|\\)|\\||\\^");
                int i = 0;

                while (i < operands.length - 1)
                {

                    if (operands[i].contains("!"))
                    {
                        operands[i] = operands[i].replaceAll("!", "");
                    }

                    ListIterator<String> listIterator = crlsit.listIterator();
                    boolean inlist = false;
                    while (listIterator.hasNext())
                    {
                        if (operands[i].equals(listIterator.next()))
                        {
                            inlist = true;
                        }
                    }

                    if (inlist == false)
                    {
                        LinkedList<Facts> facts1 = knowledgeBase.getFacts();
                        ListIterator<Facts> factsListIterator1 = facts1.listIterator();
                        boolean inFacts = false;

                        while(factsListIterator1.hasNext())
                        {
                            Facts fact1 = factsListIterator1.next();
                            if (operands[i].contains(fact1.getOperand()+""))
                            {
                                inFacts = true;
                                break;
                            }
                        }

                        if (inFacts == false)
                        {
                            Graphs checkGraph = knowledgeBase.getRules();
                            boolean hasRule = false;
                            for(int cv = 0; cv < checkGraph.getV(); cv++)
                            {
                                for(Rule checkRule: checkGraph.getRulesList(cv))
                                {
                                    if (checkRule.getConclusion().contains(operands[i]))
                                    {
                                        hasRule = true;
                                        crlsit.add(checkRule.getConclusion());
                                    }
                                    break;
                                }
                            }

                            if (hasRule == false)
                            {
                                Facts fact = new Facts(operands[i].charAt(0), false);
                                facts.add(fact);
                            }
                        }
                    }

                    i++;
                }
            }
        }
        return facts;
    }

}
