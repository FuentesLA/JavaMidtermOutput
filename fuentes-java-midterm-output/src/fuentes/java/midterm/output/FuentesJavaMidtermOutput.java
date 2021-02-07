package fuentes.java.midterm.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class FuentesJavaMidtermOutput 
{
    public static void main(String[] args) 
    {
        PersonNames pn = new PersonNames();
        FileP fp = new FileP("data.csv", pn);
        MenuList ml = new MenuList(pn,fp);

        ml.mainMenu();
    }
    // private static class PersonNames extends PersonNames {
    }   
//}
class EntryNames
    {
    String name;
    int age;
    
    public EntryNames(String name, int age) 
    {
        this.name = name;
        this.age = age;
    }
}
class PersonNames
    {
    public ArrayList<EntryNames> EntryNamesArrayList = new ArrayList<>();
    public void addEntry(String name, int age)            
    {
        EntryNames newEntryNames = new EntryNames(name, age);
        EntryNamesArrayList.add(newEntryNames);
    }    
    public void deleteEntry(int index)      
    {
        EntryNamesArrayList.remove(index);
    }    
    public void updateEntry(String name, int age, int index)    
    {
        EntryNamesArrayList.get(index).name = name;
        EntryNamesArrayList.get(index).age = age;
    }
}
final class FileP
    {
    String filePath = "data.txt";
    PersonNames pn;
    
    public FileP (String filePath, PersonNames pn) 
    { 
        this.pn = pn;
        this.filePath = filePath;
        CreateFile();    
        ReadFile();
    }
    
    public void CreateFile()
    {
        try 
        {
            File myFile = new File(filePath);
            if (myFile.createNewFile()) 
            {
                System.out.println("File created: " + myFile.getName());
            } else 
            {
                System.out.println("File exists.");
            }
        } catch (IOException e) 
        {
            System.out.println("ERROR OCCURED.");
        }
    }

    public void ReadFile () 
        { 
        try 
        {
            File myFile = new File(filePath);
            try (Scanner myReader = new Scanner(myFile) 
            ) {
                while (myReader.hasNextLine())
                {
                    String line = myReader.nextLine();
                    String[] lineArray = line.split(",");
                    EntryNames newEntryNames = new EntryNames(lineArray[0], Integer.parseInt(lineArray[1]));
                    pn.EntryNamesArrayList.add(newEntryNames);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR OCCURED.");
        }
    }

    public void WriteToFile() 
    {
        try 
        {
            try (FileWriter myWriter = new FileWriter(filePath)) {
                for (EntryNames en : pn.EntryNamesArrayList)
                {
                    myWriter.write(en.name+","+en.age+"\n");
                }
            }
            System.out.println("SUCCEEDED" + "\n" + "FILE UPDATED.\n");
        } catch (IOException e) 
        {
            System.out.println("ERROR OCCURED.");
        }
    }
}
class MenuList  
    {
    PersonNames pn;
    FileP fp;
    public MenuList(PersonNames pn, FileP fp) 
    {
        this.pn = pn;
        this.fp = fp;
    }

    public void mainMenu() {
        String input = JOptionPane.showInputDialog("Select option \n" +
                 "1. Add Entry \n" +
                " 2. Delete Entry \n" +
                " 3. View all entries \n" +
                " 4. Update An Entry\n" +
                " 0. Exit");
        do {
            int option = Integer.parseInt(input);
            switch (option) {
                case 1:
                    if (option == 1) {
                        JTextField name = new JTextField();
                        JTextField age = new JTextField();
                        Object[] addField =
                                {
                                        "               ADD ENTRY " +
                                                "                      \n\n",
                                        "                    Name: ", name,
                                        "                      Age: ", age};

                        int confirm = JOptionPane.showConfirmDialog(null, addField, "Add Entry", JOptionPane.OK_CANCEL_OPTION);
                        if (confirm == JOptionPane.OK_OPTION) {
                            try {
                                int ageInt = Integer.parseInt(age.getText());
                                pn.addEntry(name.getText(), ageInt);
                                fp.WriteToFile();
                                String message = "You have Successfully Added " + name.getText();
                                showSuccess(message);
                            } catch (NumberFormatException e) {

                            }
                        } else {
                            mainMenu();
                        }

                    }
                    break;
                case 2:
                    if (option == 2) {
                        JTextField deleteIndex = new JTextField();
                        String entriesField = "";
                        for (int i = 0; i < pn.EntryNamesArrayList.size(); i++) {
                            entriesField += i + 1 + ". " + pn.EntryNamesArrayList.get(i).name + " is " + pn.EntryNamesArrayList.get(i).age + " years old." + "\n";
                        }
                        entriesField += "\n";

                        Object[] deleteField =
                                {
                                        entriesField,
                                        "              DELETE ENTRY " +
                                                "                      \n",
                                        "Please Select Number to Delete: ",
                                        deleteIndex
                                };

                        int confirm = JOptionPane.showConfirmDialog(null, deleteField, "Delete Entry", JOptionPane.OK_CANCEL_OPTION);
                        if (confirm == JOptionPane.OK_OPTION) {
                            try {
                                int deleteIndexInt = Integer.parseInt(deleteIndex.getText()) - 1;
                                if (deleteIndexInt >= pn.EntryNamesArrayList.size()) {
                                    throw new IndexOutOfBoundsException("Index " + deleteIndexInt + " is out of bounds!");
                                }
                                String message = "You have Successfully Deleted " + pn.EntryNamesArrayList.get(deleteIndexInt).name + ". ";
                                pn.deleteEntry(deleteIndexInt);
                                fp.WriteToFile();

                                showSuccess(message);
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {

                            }
                        } else {
                            mainMenu();
                        }
                    }


                    break;
                case 3:
                    if (option == 3) {

                        String[] entriesButton = {"Back To Menu"};
                        String entriesField = "";

                        for (int i = 0; i < pn.EntryNamesArrayList.size(); i++) {
                            entriesField += i + 1 + ". " + pn.EntryNamesArrayList.get(i).name + " is " + pn.EntryNamesArrayList.get(i).age + " years old.\n";
                        }

                        entriesField += "\n";

                        JOptionPane.showOptionDialog(null, entriesField, "View All Entries", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, entriesButton, entriesButton[0]);
                        mainMenu();
                    }


                    break;
                case 4:
                    if (option == 4) {
                        JTextField updateIndex = new JTextField();
                        String entriesField = "";
                        for (int i = 0; i < pn.EntryNamesArrayList.size(); i++) {
                            entriesField += i + 1 + ". " + pn.EntryNamesArrayList.get(i).name + " is " + pn.EntryNamesArrayList.get(i).age + " years old." + "\n";
                        }
                        entriesField += "\n";

                        Object[] updateField =
                                {
                                        entriesField,
                                        "               UPDATE ENTRY " +
                                                "                      \n",
                                        "Select a Number to Update: ",
                                        updateIndex
                                };
                        int confirm = JOptionPane.showConfirmDialog(null, updateField, "Update An Entry", JOptionPane.OK_CANCEL_OPTION);
                        if (confirm == JOptionPane.OK_OPTION) {
                            try {
                                int updateIndexInt = Integer.parseInt(updateIndex.getText()) - 1;
                                if (updateIndexInt >= pn.EntryNamesArrayList.size()) {
                                    throw new IndexOutOfBoundsException("Index " + updateIndexInt + " is out of bounds!");
                                }
                                showUpdatePrompt(updateIndexInt);
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {

                            }
                        } else {
                            mainMenu();
                        }
                    }

            break;
            case 0:
                
                    System.exit(0);
                break;

        }

    } while(true);


    }

public void showUpdatePrompt(int updateIndexInt)
    {
        JTextField newName = new JTextField();
        JTextField newAge = new JTextField();
        Object[] updateField =
                {
                        "Current Name: " + pn.EntryNamesArrayList.get(updateIndexInt).name,
                        "Current Age: " + pn.EntryNamesArrayList.get(updateIndexInt).age + "\n\n",
                        "                     New Name: ", newName,
                        "                     New Age: ", newAge,
                        "\n"
                };
        int confirm = JOptionPane.showConfirmDialog(null, updateField, "Updating " + pn.EntryNamesArrayList.get(updateIndexInt).name, JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION)
        {
            try
            {
                int newAgeInt = Integer.parseInt(newAge.getText());
                String message = pn.EntryNamesArrayList.get(updateIndexInt).name +
                        " is Successfully Updated to\n" + newName.getText() + " with the Age of " + newAge.getText() + ". " ;
                pn.updateEntry(newName.getText(), newAgeInt, updateIndexInt);
                fp.WriteToFile();
                showSuccess(message);
            }
            catch (NumberFormatException e)
            {
                showUpdatePrompt(updateIndexInt);
            }
        }

    }




    public void showSuccess(String message) {
        {
            String[] successField = {"Back To Menu"};
            JOptionPane.showOptionDialog(null, message, "Success!!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, successField, successField[0]);
            mainMenu();
        }
    }

}