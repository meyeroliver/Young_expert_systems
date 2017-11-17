import java.io.*;
import java.util.*;
import java.lang.*;

public class ReadFile
{
    private Scanner scan;
    private String filename;

    public ReadFile(String filename)
    {
        this.filename = filename;
    }

    public boolean openFile()
    {
        try
        {
            this.scan = new Scanner(new File("/goinfre/omeyer/Desktop/Expert_System/src/" + this.filename));
            return true;
        }
        catch (Exception e)
        {
            System.err.println("\n \""+filename+"\" does not exist. File cannot be opened");
            return false;
        }
    }

    public LinkedList readFile()
    {
        String line;
        LinkedList fileList =  new LinkedList();

        this.openFile();
        try
        {
            FileReader freader = new FileReader("/goinfre/omeyer/Desktop/Expert_System/src/" + this.filename);
            BufferedReader br = new BufferedReader(freader);
            while ((line = br.readLine()) != null)
            {
                fileList.add(line);
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not read file");
        }
        this.closeFile();
        return (fileList);
    }

    public void display(LinkedList fileList)
    {
        ListIterator litr = fileList.listIterator();
        while(litr.hasNext())
        {
            Object element = litr.next();
            System.out.println(element);
        }
    }

    public void closeFile()
    {
        scan.close();
    }
}

