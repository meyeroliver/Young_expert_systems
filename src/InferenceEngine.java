import  java.util.*;
public class InferenceEngine {
    KnowledgeBase knowledgeBase;

    public InferenceEngine(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    private int getRulesIndex(Query query) {
        Graphs rules = this.knowledgeBase.getRules();
        int index = -1;
        for (int v = 0; v < rules.getV(); v++)
            for (Rule rule : rules.getRulesList(v)) {
                if (query.getQuery() == rule.getConclusion().charAt(0)) // incase of AND in conculsion create different system
                    index = v;
                break;
            }
        return index;
    }

    private LinkedList<Character> getOperands(Rule rule)// wont account for not rule, need to look at other functions
    {
        LinkedList<Character> operands = new LinkedList<>();
        String antecedant;
        antecedant = rule.getRule().split("=>")[0];

        for (int i = 0; i < antecedant.length(); i++) {
            if (antecedant.charAt(i) >= 'A' && antecedant.charAt(i) <= 'Z') {
                operands.add(antecedant.charAt(i));
            }
        }
        return operands;
    }

    private Facts isInFacts(char currentOperand)
    {
        for (Facts fact : this.knowledgeBase.getFacts())
            if (currentOperand == fact.getOperand())
                return fact;
        return null;
    }

    private void displayInitialQueries(LinkedList<Query> queryLinkedList)
    {
        System.out.println("______________________________________________________________________________________________");
        System.out.print("\nThis is the list of initial queries to solve.\n\n\t* -> ");
        for (Query checkQuery: queryLinkedList)
        {
            System.out.print(checkQuery.getQuery() + " | ");
        }
        System.out.print("\n______________________________________________________________________________________________\n\n");
    }

    private void displayFacts()
    {
        System.out.println("______________________________________________________________________________________________");
        System.out.print("\nThis is the list of Confirmed facts.\n\n\t* -> ");
        for (Facts fact: this.knowledgeBase.getFacts())
        {
            System.out.print(fact.getOperand() + " : " + fact.getState() +" | ");
        }
        System.out.println();
    }

    public void initialQuery(LinkedList<Query> queryLinkedList)
    {
        this.displayFacts();
        this.displayInitialQueries(queryLinkedList);
        for (Query currentQuery: queryLinkedList)
        {
            if (this.displayCheckedFacts(currentQuery.getQuery()) == true)
            {
                continue;
            }
            else
            {
                System.out.println("\t\t * -> " + currentQuery.getQuery() + " : unknown\n");
                Facts newFact = engine(currentQuery);
                System.out.println("\t\t -----------------");
                System.out.println("\t\t * -> " + newFact.getOperand() + " : " + newFact.getState());
                System.out.println("\t\t -----------------");
            }
        }
    }

    private Facts engine(Query currentQuery)
    {
        Facts newFact;
        String antecedent = "";
        String conclusion = "";
        int index = this.displayRules(currentQuery);
        int satisfied = 0;
        LinkedList<Rule> rulesList = this.knowledgeBase.getRules().getRulesList(index);
        int ruleCnt = 1;
        for (Rule currentRule: rulesList)// make this more efficient once this version works properly
        {
            String[] s_rule = currentRule.getRule().split("=>");
            antecedent = s_rule[0];
            conclusion = s_rule[1];
            LinkedList<Character> operandList = this.getOperands(currentRule);
            for (char currentOperand: this.getOperands(currentRule))
            {
                if (checkedFacts(currentOperand) == true)//need to format this more sexilly, need to make a checkfact and displaycheckedfacts
                {
                    satisfied++;
                    continue;
                }
                else
                {
                    System.out.println("\t\t * -> " + currentOperand + " : unknown\n");
                    Query newQuery = new Query(currentOperand);
                    Facts subNewFact = engine(newQuery);
                    System.out.println("\t\t -----------------");
                    System.out.println("\t\t * -> " + subNewFact.getOperand() + " : " + subNewFact.getState());
                    System.out.println("\t\t -----------------");
                }
            }
            if (satisfied == operandList.size())
            {
                EvaluateExpression solver = new EvaluateExpression(antecedent);
                boolean state = solver.evaluate(this.knowledgeBase.getFacts());
                LinkedList<Character> characterLinkedList = getConclusionOperands(conclusion);

                //get rid of duplicate code
                if (state == true)
                {int work = 0;
                    for (char conclusionOperand :characterLinkedList)
                    {
                        newFact = new Facts(conclusionOperand, state);
                        this.knowledgeBase.getFacts().add(newFact);
                        if (work == characterLinkedList.size() - 1)
                        {
                            return newFact;
                        }
                        work++;
                    }
                }
                if (state == false && ruleCnt == rulesList.size())
                {int work = 0;
                    for (char conclusionOperand :characterLinkedList)
                    {
                        newFact = new Facts(conclusionOperand, state);
                        this.knowledgeBase.getFacts().add(newFact);
                        if (work == characterLinkedList.size() - 1)
                        {
                            return newFact;
                        }
                        work++;
                    }
                }
            }
            if (this.isInFacts(currentRule.getConclusion().charAt(0)) != null)
            {
                break;
            }
            ruleCnt++;
        }
        EvaluateExpression solver = new EvaluateExpression(antecedent);
        boolean state = solver.evaluate(this.knowledgeBase.getFacts());
        newFact = new Facts(conclusion.charAt(0), state);
        this.knowledgeBase.getFacts().add(newFact);
        return (newFact);//need to make this case
    }

    private LinkedList<Character> getConclusionOperands(String conclusion)// wont account for not rule, need to look at other functions
    {
        LinkedList<Character> operands = new LinkedList<>();

        for (int i = 0; i < conclusion.length(); i++) {
            if (conclusion.charAt(i) >= 'A' && conclusion.charAt(i) <= 'Z') {
                operands.add(conclusion.charAt(i));
            }
        }
        return operands;
    }

    private boolean checkedFacts(char currentQuery)
    {
        Facts checkFact = this.isInFacts(currentQuery);
        if (checkFact != null)
            return true;
        return false;
    }

    private boolean displayCheckedFacts(char currentQuery)
    {
        Facts checkFact = this.isInFacts(currentQuery);
        if (checkFact != null)
        {
            System.out.println("\t\t -----------------");
            System.out.println("\t\t * -> " + checkFact.getOperand() + " : " + checkFact.getState());
            System.out.println("\t\t -----------------");
            return true;
        }
        return false;
    }

    private int displayRules(Query currentQuery)
    {
        int index = this.getRulesIndex(currentQuery);// gets index of  rules to solve ?
        LinkedList<Rule> rules = this.knowledgeBase.getRules().getRulesList(index);
        for (Rule checkRule: rules)
        {
            System.out.print("\t\t\t * -> " + checkRule.getRule()+ " | ");
        }
        System.out.print("\n\n");
        return index;
    }

   /* public static void main (String args[])
    {
        ReadFile file = new ReadFile("input.txt");
        LinkedList fileList = file.readFile();
        Expert expert = new Expert();
        KnowledgeBase knowledgeBase = expert.separator(fileList);
        knowledgeBase.setFacts(expert.confirmList(knowledgeBase));
        InferenceEngine inferenceEngine = new InferenceEngine(knowledgeBase);
        inferenceEngine.initialQuery(knowledgeBase.getQuery());
    }*/
}
