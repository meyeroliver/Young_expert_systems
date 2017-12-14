public class Rule
{
    private int index;
    private String conclusion;
    private String rule;

    public Rule(String conclusion, String rule) {
        this.conclusion = conclusion;
        this.rule = rule;
        this.index = -1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }


}