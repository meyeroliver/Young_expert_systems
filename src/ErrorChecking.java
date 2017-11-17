import java.util.*;
import java.lang.*;

public class ErrorChecking
{
    private LinkedList fileList;

    public ErrorChecking (LinkedList fileList)
    {
        this.fileList = fileList;
    }

    private boolean isEmpty()
    {
        boolean lineState;

        if ((lineState = emptyRules()) == true) //goes in when there are no rules
        {
            System.err.println("There are no rules in this file");
            return (lineState);
        }
        if ((lineState = emptyIniFacts()) == true) //goes in when there are no initial facts
        {
            System.err.println("There are no initial facts in this file");
            return (lineState);
        }
        if ((lineState = emptyQuery()) == true) //goes in when there are no queries
        {
            System.err.println("There are no query in this file");
            return (lineState);
        }
        return (lineState);
    }

    public boolean checker ()
    {
        Object element;
        String line;
        //boolean lineState;

        if (isEmpty() == true)//this checks whether there is an empty aspect in the file
        {
            return (false);
        }
        else
        {
            ListIterator litr = this.fileList.listIterator();
            while (litr.hasNext())
            {
                element = litr.next();
                line = (String) (element);
                if (line.contains("=>"))
                {
                    if (this.isUpper(line) == false)
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters"); //format the print out later
                        return (false);
                    }
                    if (this.validExpression(line) == false)
                    {
                        System.err.println(line + "\t\t <- This expression is invalid.");
                        return (false);
                    }
                }
                if (line.contains("="))
                {
                    if (this.isUpper(line) == false)
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters"); //format the print out later
                        return (false);
                    }
                }
                if (line.contains("?"))
                {
                    if (this.isUpper(line) == false)
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters"); //format the print out later
                        return (false);
                    }
                }
            }
        }
        return (true);
    }

    private boolean isUpper (String line)
    {
        boolean lineState;
        int i;

        i = 0;
        lineState = true;
        while (i < line.length())
        {
            if (Character.isLetter(line.charAt(i)) && !Character.isUpperCase(line.charAt(i)))
            {
                lineState = false;
                break;
            }
            i++;
        }
        return (lineState);
    }

    private boolean emptyRules()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        boolean emptyRules;
        String line;

        emptyRules = true;
        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if (line.contains("=>"))
            {
                emptyRules = false;
                break;
            }
        }
        return (emptyRules);
    }

    private boolean emptyIniFacts()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        boolean emptyRules;
        String line;

        emptyRules = true;
        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if ((line.startsWith("=")) && (line.length() > 1))
            {
                emptyRules = false;
                break;
            }
        }
        return (emptyRules);
    }

    private boolean emptyQuery()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        boolean emptyRules;
        String line;

        emptyRules = true;
        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if ((line.startsWith("?")) && (line.length() > 1))
            {
                emptyRules = false;
                break;
            }
        }
        return (emptyRules);
    }

    private boolean consecutiveOperands(String line)//try to make this more efficient
    {
        boolean     lineState;
        lineState = false;

        if (line.contains("++") || line.contains("+|") || line.contains("+^") || line.contains("+)") || line.contains("+=>"))
        {
            lineState = true;
            return (lineState);
        }
        if (line.contains("||") || line.contains("|+") || line.contains("|^") || line.contains("|)") || line.contains("|=>"))
        {
            lineState = true;
            return (lineState);
        }
        if (line.contains("^^") || line.contains("^|") || line.contains("^+") || line.contains("^)") || line.contains("^=>"))
        {
            lineState = true;
            return (lineState);
        }
        if (line.contains(")("))
        {
            return (true);
        }
        if (line.contains("=>=>") || line.contains("=>|") || line.contains("=>^") || line.contains("=>)") || line.contains("=>+"))
        {
            lineState = true;
            return (lineState);
        }

        return (lineState);
    }

    private boolean validBrackets(String[] s_array)
    {
        int i;
        int j;
        char[]      c_array;
        Stack expStack =  new Stack();

        i = 0;
        while (i < s_array.length)
        {
            c_array = s_array[i].toCharArray();
            j = 0;
            while (j < c_array.length)
            {
                if (c_array[j] == '(')
                {
                    expStack.push(c_array[j]);
                }
                if (c_array[j] == ')' && expStack.isEmpty())
                {
                    return (false);
                }
                if (c_array[j] == ')' && !expStack.isEmpty())
                {
                    expStack.pop();
                }
                j++;
            }
            if (!expStack.isEmpty())
            {
                return (false);
            }
            i++;
        }
        return (true);
    }

    private boolean validExpression (String line)
    {
        boolean     lineState;
        char[]      c_array;
        int         i;
        String[]    s_array;

        i = 0;
        line = line.replaceAll("\\s+","");
        c_array = line.toCharArray();
        lineState = true;
        s_array = line.split("=>");

        //checking for incomplete expression
        if(s_array.length != 2)
        {
            return (false);
        }
        //check for correct brackets
        if (validBrackets(s_array) == false)
        {
            return (false);
        }
        //check for consecutive facts
        while(i < c_array.length - 1)
        {
            if (Character.isLetter(c_array[i]) && Character.isLetter(c_array[i + 1]))
            {
                return (false);
            }
            if (consecutiveOperands(line) == true)
            {
                return (false);
            }
            i++;
        }
        return(lineState);
    }
}