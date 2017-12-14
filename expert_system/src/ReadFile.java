import java.io.*;
import java.util.*;
import java.lang.*;

public class ReadFile
{
    private Scanner scan;
    private String filename;
    private String pathname;

    public ReadFile(String filename)
    {
        this.filename = filename;
		this.pathname = "/nfs/zfs-student-7/users/omeyer/Desktop/expert_system/src/" + this.filename;
    }

    public boolean openFile()
    {
        try
        {
            File currentDir = new File(this.pathname);
             this.scan = new Scanner(this.pathname);
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
            FileReader freader = new FileReader(this.pathname);
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

