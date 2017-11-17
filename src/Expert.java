import java.util.*;

public class Expert
{
    //KnowledgeBase knowledgeBase;

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
                s_array = ((String) (element)).split("=>");
                Rule temp = new Rule(s_array[1], ((String) (element)));
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
        /*goes through the graph and adds all of the conclusion to the Solvable list*/
        for(int v = 0; v < graph.getV(); v++)
        {
            for(Rule rule: graph.getRulesList(v))// this be made to iterate through the conclusion
            {
                crlsit.add(rule.getConclusion());//over here i can make so that takes the conclusion and then takes its operands(will help a lot)
                break;
            }
        }//if it doesnt get added in here that means there is no rule to prove
        /**************************************************************************************************/
        // adds all of the initial facts to the Solvable list
        LinkedList<Facts> facts = knowledgeBase.getFacts();
        ListIterator<Facts> factsListIterator = facts.listIterator();
        while(factsListIterator.hasNext())
        {
            Facts fact = factsListIterator.next();
            crlsit.add(fact.getOperand()+"");

        }// if it doesnt get added in crlist here then it is not in initial facts
        /*************************************************************************************************/
        //two loops below allow me traverse the graph sequentially
        for(int v = 0; v < graph.getV(); v++)
        {
            for (Rule rule : graph.getRulesList(v))
            {
                //here I split the rule up into a string array with just the operands in the array
                String[] operands = (rule.getRule()).split("=>|\\+|\\(|\\)|\\||\\^");//"=>|\\+|\\(|\\)|!|\\||\\^"
                int i = 0;
                //the loop allows me to iterate through all of the operands of the current rule
                while (i < operands.length - 1)
                {

                    if (operands[i].contains("!"))
                    {
                        operands[i] = operands[i].replaceAll("!", "");
      //                  System.out.println(operands[i]);
                    }
           //         if (operands[i].contains(""))
             //       {
               //         i++;
                 //   }
                    /***********************************************************************************/
                    //this section allows me to determine whether or not the current operand is in the Solvable list
                    ListIterator<String> listIterator = crlsit.listIterator();
                    boolean inlist = false;
                    while (listIterator.hasNext())
                    {
                        if (operands[i].equals(listIterator.next()))
                        {
                            inlist = true;
                        }
                    }
                    /***********************************************************************************/
                    // if the operand is not in the solvable lists
                    if (inlist == false)
                    {
                        LinkedList<Facts> facts1 = knowledgeBase.getFacts();
                        ListIterator<Facts> factsListIterator1 = facts1.listIterator();
                        boolean inFacts = false;
                        //will iterate through the list of Facts and check if the current operand is in the list of Facts
                        while(factsListIterator1.hasNext())
                        {
                            Facts fact1 = factsListIterator1.next();
                            if (operands[i].contains(fact1.getOperand()+""))
                            {
                                inFacts = true;
                                break;
                            }
                        }
                        //if current operand is not in the list of facts update lists of Facts with false fact
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //this is exactly where the problem is.
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
                                    /*if (operands[i].charAt(0) == checkRule.getConclusion().charAt(0))//over here i can make so that takes the conclusion and then takes its operands(will help a lot)
                                    {
                                        hasRule = true;
                                    }*/
                                    ;//over here i can make so that takes the conclusion and then takes its operands(will help a lot)
                                    break;
                                }
                            }
//check the above adding


                            /*LinkedList<Query> queries = knowledgeBase.getQuery();
                            ListIterator<Query> queryListIterator = queries.listIterator();
                            boolean inQuery = false;
                            while (queryListIterator.hasNext())
                            {
                                Query query = queryListIterator.next();
                                if (operands[i].charAt(0) == query.getQuery())
                                {
                                    inQuery = true;
                                }
                            }*/
                         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            if (hasRule == false)
                            {
                                Facts fact = new Facts(operands[i].charAt(0), false);
                                facts.add(fact);
                            }
                        }
                    }
                    /**********************************************************************************/
                    i++;
                }
            }
        }
        //create an if statement that check whether the fact is in crlist
        return facts;
    }

  /*  public static void main (String args[])
    {
        //the expert while the file
        ReadFile file = new ReadFile("input.txt");
        LinkedList fileList = file.readFile();
        Expert expert = new Expert();
        KnowledgeBase knowledgeBase =  expert.separator(fileList);
       // knowledgeBase.getRules().printGraph(knowledgeBase.getRules());

        /*LinkedList<Facts> facts = knowledgeBase.getFacts();
        ListIterator<Facts> factsListIterator = facts.listIterator();
        while (factsListIterator.hasNext())
        {
            Facts fact = factsListIterator.next();
            System.out.println(fact.getOperand() + " "+ fact.getState());
        }

        LinkedList<Query> queries = knowledgeBase.getQuery();
        ListIterator<Query> queryListIterator = queries.listIterator();
        while (queryListIterator.hasNext())
        {
            Query query = queryListIterator.next();
            System.out.println(query.getQuery());
        }*/
        ///////////////////////////////////////////////////////////////////////////////////
//        System.out.println("\n\n");
    /*    LinkedList<Facts> crlist = expert.confirmList(knowledgeBase);
        ListIterator<Facts> listIterator = crlist.listIterator();
        while (listIterator.hasNext())
        {
            Facts fact = listIterator.next();
            System.out.println(fact.getOperand() +" "+ fact.getState());
        }

   /*     KnowledgeBase knowledgeBase1 =  expert.confirmFacts(knowledgeBase);
        knowledgeBase1.getRules().printGraph(knowledgeBase1.getRules());

        LinkedList<Facts> facts1 = knowledgeBase1.getFacts();
        ListIterator<Facts> factsListIterator1 = facts1.listIterator();
        while (factsListIterator1.hasNext())
        {
            Facts fact = factsListIterator1.next();
            System.out.println(fact.getOperand() + " "+ fact.getState());
        }

        LinkedList<Query> queries1 = knowledgeBase1.getQuery();
        ListIterator<Query> queryListIterator1 = queries1.listIterator();
        System.out.println("\n");
        while (queryListIterator1.hasNext())
        {
            Query query = queryListIterator1.next();
            System.out.println(query.getQuery());
        }
*/

    //}
}
