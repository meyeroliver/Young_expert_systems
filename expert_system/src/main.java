import java.util.LinkedList;
import java.util.*;
public class main
{
    public static void main (String[] args)
    {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter in the name of the input file : \t");
        String[] arguments = userInput.nextLine().split(" ");

        if (arguments.length == 1)
        {
            if (arguments[0].length() == 0)
            {
                System.err.println("\nError! There are no arguments detected");
            }
            else
            {
                ReadFile file = new ReadFile(arguments[0]);

                if (file.openFile() == true)
                {
                    LinkedList<String> fileList = file.readFile();
                    file.closeFile();
                    ErrorChecking fileChecker = new ErrorChecking(fileList);
                    if (fileChecker.checker() == true)
                    {
                        Expert expert = new Expert();
                        KnowledgeBase knowledgeBase = expert.separator(fileList);
                        knowledgeBase.setFacts(expert.confirmList(knowledgeBase));
                        InferenceEngine inferenceEngine = new InferenceEngine(knowledgeBase);
                        inferenceEngine.initialQuery(knowledgeBase.getQuery());
                    }
                }
            }
        }
        else
        {
            System.err.println("\nError! There are too many arguments detected");
        }
    }
}
