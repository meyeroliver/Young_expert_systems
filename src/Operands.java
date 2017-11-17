import java.util.LinkedList;

public class Operands
{
    LinkedList operandList;

    public Operands()
    {
        this.operandList = new LinkedList();
    }

    private void setOperands()
    {
        this.operandList.add("(");
        this.operandList.add(")");
        this.operandList.add("!");  //NEGATIVE
        this.operandList.add("+");  //AND
        this.operandList.add("|");  //OR
        this.operandList.add("^");  //XOR
        this.operandList.add("=>"); //IMPLIES
    }
}
