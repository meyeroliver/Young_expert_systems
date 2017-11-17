import java.util.*;

public class EvaluateExpression
{
    char[] tokens;
    // Stack for numbers: 'values'
    Stack<Facts> values;

    // Stack for Operators: 'ops'
    Stack<Character> ops;

    public EvaluateExpression(String expression)
    {
        this.tokens = expression.toCharArray();
        this.values = new Stack<>();
        this.ops = new Stack<>();
    }

    private Facts getFact (LinkedList<Facts> facts, char token)
    {
        Facts foundFact = new Facts('?', false);//temp, because a fact will always be found
        for (Facts fact: facts)
        {
            if (token == fact.getOperand())
            {
                foundFact = fact;
                break;
            }
        }
        return foundFact;
    }

    public boolean evaluate(LinkedList<Facts> facts)
        {
            //char[] tokens = expression.toCharArray();
            for (int i = 0; i < this.tokens.length; i++)
            {
                // Current token is a whitespace, skip it
                if (this.tokens[i] == ' ')
                    continue;
                // Current token is a operand, push it to stack for operands
                if (this.tokens[i] >= 'A' && this.tokens[i] <= 'Z') // remember to add x as a condition for the result case
                    this.values.push(this.getFact(facts, this.tokens[i]));
                // Current token is an opening brace, push it to 'ops'
                else if (this.tokens[i] == '(')
                    this.ops.push(this.tokens[i]);
                // Closing brace encountered, solve entire brace
                else if (this.tokens[i] == ')')
                {
                    while (this.ops.peek() != '(')
                        this.values.push(applyOp(this.ops.pop(), this.values.pop(), this.values.pop()));
                    this.ops.pop();
                }
                // Current token is an operator.

                else if (this.tokens[i] == '!' || this.tokens[i] == '+' || this.tokens[i] == '|' || this.tokens[i] == '^')
                {
                    // While top of 'ops' has same or greater precedence to current
                    // token, which is an operator. Apply operator on top of 'ops'
                    // to top two elements in values stack
                    if (this.tokens[i] == '!')
                    {
                        int stay = i;
                        int cnt_ex = 0;
                        while (this.tokens[i] == '!')
                        {
                            this.ops.push(this.tokens[i++]);
                            cnt_ex++;
                        }
                        this.values.push(this.getFact(facts, this.tokens[i]));
                        //this.ops.push(this.tokens[i]);
                        int check_ex = 0;
                        while (!this.ops.empty() && hasPrecedence(this.tokens[stay], this.ops.peek()))
                        {
                            this.values.push(applyOp(this.values.pop()));
                            this.ops.pop();
                            check_ex++;
                            if (check_ex == cnt_ex)
                            {
                                break;
                            }
                        }
                        i++;
                    }
                    else
                    {
                        while (!this.ops.empty() && hasPrecedence(this.tokens[i], this.ops.peek()))
                            this.values.push(applyOp(this.ops.pop(), this.values.pop(), this.values.pop()));
                        // Push current token to 'ops'.
                        this.ops.push(this.tokens[i]);
                    }
                }

            }
            while (!this.ops.empty())
                this.values.push(applyOp(this.ops.pop(), this.values.pop(), this.values.pop()));
            // Top of 'values' contains result, return it
            return this.values.pop().getState();
        }

        // Returns true if 'op2' has higher or same precedence as 'op1',
        // otherwise returns false.
        private static boolean hasPrecedence(char op1, char op2)
        {
            if (op2 == '(' || op2 == ')')
                return false;
            if ((op1 == '!' || op1 == '+') && (op2 == '|' || op2 == '^'))
                return false;
            else
                return true;
        }

        private Facts applyOp(Facts a)
        {
            a.setState(this.not(a));
            return (a);
        }

        private Facts applyOp(char op, Facts a, Facts b)
        {
            Facts result = new Facts('x', false);
            switch (op)
            {
                case '+':
                    result.setState(this.and(a, b));
                    break;
                case '|':
                    result.setState(this.or(a, b));
                    break;
                case '^':
                    result.setState(this.xor(a, b));
            }
            return result;
        }

        private  boolean or(Facts a, Facts b)
        {
            if (a.getState() == true && b.getState() == true)
                return  true;
            else if ((a.getState() == true && b.getState() == false) || (a.getState() == false && b.getState() == true))
                return  true;
            else
               return false;
        }

        private boolean and(Facts a, Facts b)
        {
            if (a.getState() == true && b.getState() == true)
                return  true;
            else if ((a.getState() == true && b.getState() == false) || (a.getState() == false && b.getState() == true))
                return false;
            else
                return false;
        }

        private boolean not(Facts a)
        {
            if (a.getState() == true)
                return false;
            else
                return true;
        }

        private  boolean xor(Facts a, Facts b)
        {
            if (a.getState() == false && b.getState() == false)
                return  false;
            else if ((a.getState() == true && b.getState() == false) || (a.getState() == false && b.getState() == true))
                return true;
            else
                return false;
        }
}
