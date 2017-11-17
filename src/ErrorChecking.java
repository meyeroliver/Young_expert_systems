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

        if ((lineState = emptyRules()))
        {
            System.err.println("There are no rules in this file");
            return (lineState);
        }
        if ((lineState = emptyIniFacts()))
        {
            System.err.println("There are no initial facts in this file");
            return (lineState);
        }
        if ((lineState = emptyQuery()))
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

        if (isEmpty() == true)
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
                    if (!this.isUpper(line))
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters");
                        return (false);
                    }
                    if (!this.validExpression(line))
                    {
                        System.err.println(line + "\t\t <- This expression is invalid.");
                        return (false);
                    }
                }
                if (line.contains("="))
                {
                    if (!this.isUpper(line))
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters");
                        return (false);
                    }
                }
                if (line.contains("?"))
                {
                    if (!this.isUpper(line))
                    {
                        System.err.println(line + "\t\t <- This line conatins lowercase letters");
                        return (false);
                    }
                }
            }
        }
        return (true);
    }

    private boolean isUpper (String line)
    {
        int i;

        i = 0;
        while (i < line.length())
        {
            if (Character.isLetter(line.charAt(i)) && !Character.isUpperCase(line.charAt(i)))
                return false;
            i++;
        }
        return true;
    }

    private boolean emptyRules()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        String line;

        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if (line.contains("=>"))
                return false;
        }
        return true;
    }

    private boolean emptyIniFacts()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        String line;

        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if ((line.startsWith("=")) && (line.length() > 1))
                return false;
        }
        return true;
    }

    private boolean emptyQuery()
    {
        ListIterator litr = this.fileList.listIterator();
        Object element;
        String line;

        while (litr.hasNext())
        {
            element = litr.next();
            line = (String)(element);
            if ((line.startsWith("?")) && (line.length() > 1))
                return false;
        }
        return true;
    }

    private boolean consecutiveOperands(String line)
    {
        if (line.contains("++") || line.contains("+|") || line.contains("+^") || line.contains("+)") || line.contains("+=>"))
            return true;
        if (line.contains("||") || line.contains("|+") || line.contains("|^") || line.contains("|)") || line.contains("|=>"))
            return true;
        if (line.contains("^^") || line.contains("^|") || line.contains("^+") || line.contains("^)") || line.contains("^=>"))
            return true;
        if (line.contains(")("))
            return true;
        if (line.contains("=>=>") || line.contains("=>|") || line.contains("=>^") || line.contains("=>)") || line.contains("=>+"))
            return true;
        return false;
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
                    expStack.push(c_array[j]);
                if (c_array[j] == ')' && expStack.isEmpty())
                    return (false);
                if (c_array[j] == ')' && !expStack.isEmpty())
                    expStack.pop();
                j++;
            }
            if (!expStack.isEmpty())
                return (false);
            i++;
        }
        return (true);
    }

    private boolean validExpression (String line)
    {
        char[]      c_array;
        int         i;
        String[]    s_array;

        i = 0;
        line = line.replaceAll("\\s+","");
        c_array = line.toCharArray();
        s_array = line.split("=>");
        if(s_array.length != 2)
            return (false);
        if (!validBrackets(s_array))
            return (false);
        while(i < c_array.length - 1)
        {
            if (Character.isLetter(c_array[i]) && Character.isLetter(c_array[i + 1]))
                return (false);
            if (consecutiveOperands(line))
                return (false);
            i++;
        }
        return(true);
    }
}