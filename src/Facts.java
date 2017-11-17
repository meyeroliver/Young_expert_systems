public class Facts
{
    private char operand;
    private boolean state;



    public Facts(char operand, boolean state)
    {
        this.operand = operand;
        this.state = state;
    }

    public char getOperand()
    {
        return operand;
    }

    public boolean getState()
    {
        return state;
    }

    public void setState(boolean state)
    {
        this.state = state;
    }
}
