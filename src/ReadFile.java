import java.io.*;
import java.util.*;
import java.lang.*;

public class ReadFile
{
    private Scanner scan;
    private String filename;
    private String pathName;

    public ReadFile(String filename)
    {
        this.filename = filename;
    }

    public boolean openFile()
    {
        try
        {
            File currentDir = new File(this.filename);
            this.pathName = currentDir.getAbsolutePath();
            this.scan = new Scanner(this.pathName);
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

        try
        {
            String segments[] = this.pathName.split("/");
            String fresh = "";
            int i = 0;
            while (i < segments.length - 1)
            {
                fresh = fresh + segments[i] + "/";
                i++;
            }
            fresh = fresh + "src/" + this.filename;
            FileReader freader = new FileReader(fresh);
            BufferedReader br = new BufferedReader(freader);
            while ((line = br.readLine()) != null)
            {
               if (line.contains("#"))
               {
                   fileList.add(line.substring(0, line.indexOf('#')));
               }
               else
               {
                   fileList.add(line);
               }
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not read file");
        }
        return (fileList);
    }

    public void closeFile()
    {
        scan.close();
    }
}

