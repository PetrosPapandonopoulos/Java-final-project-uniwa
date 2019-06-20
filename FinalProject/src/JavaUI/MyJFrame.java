package JavaUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MyJFrame extends JFrame {
    //Variables that are used in at least two different methods inside the MyJFrame class
    private String filePath;
    private JFrame myFrame;
    private JTextArea txtArea;
    private JTextField fieldPath;
    private JFileChooser chPath;
    private JButton btnChangeMode;
    private Boolean fileExists = false;
    private FileWriter fileOut;

    //Constructor
    public MyJFrame() {
        //Variables that are only used inside of the constructor
        JMenuItem miNew, miOpen, miStats, miSave, miSaveAs, miClr, miClose;
        JMenu m1, m2;
        JMenuBar mb;
        JPanel btnPanel, panelTxt;
        JButton btnNew, btnOpen, btnStats, btnClr, btnSave, btnSaveAs, btnClose;
        Box managerPanel;

        //Create my own frame
        myFrame = new JFrame("Text editor");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


        } catch (Exception e) {
            //Not handled
            e.printStackTrace();
        }



        // Create a menubar
        mb = new JMenuBar();

        // Create the JMenu File and Edit
        m1 = new JMenu("File");
        m2 = new JMenu("Edit");

        // Create  menu items for the File menu
        miNew = new JMenuItem("New");
        miOpen = new JMenuItem("Open");
        miStats = new JMenuItem("Statistics");
        miSave = new JMenuItem("Save");
        miSaveAs = new JMenuItem("Save as");
        miClr = new JMenuItem("Clear");
        miClose = new JMenuItem("Exit");

        //add JMenuItems to file JMenu
        m1.add(miNew);
        m1.add(miOpen);
        m1.add(miSave);
        m1.add(miSaveAs);
        m1.add(miStats);
        m1.add(miClose);

        //add JMenuItems to edit JMenu
        m2.add(miClr);

        //add actionListener to JMenu items using lambda expression

        miNew.addActionListener(e -> NewFile());
        miOpen.addActionListener(e -> OpenFile());
        miSave.addActionListener(e -> SaveFile());
        miSaveAs.addActionListener(e -> CopyFile());
        miStats.addActionListener(e -> ShowStats());
        miClose.addActionListener(e -> ExitApp());
        miClr.addActionListener(e -> ClearAreaAndField());

        //add JMenu to JMenuBar
        mb.add(m1);
        mb.add(m2);


        //Initialized the  Panel for the Buttons and the Buttons.
        btnPanel = new JPanel();
        btnNew = new JButton("New");
        btnOpen = new JButton("Open");
        btnClr = new JButton("Clear");
        btnSave = new JButton("Save");
        btnSaveAs = new JButton("copy");
        btnStats = new JButton("Statistics");
        btnChangeMode = new JButton("Dark mode");
        btnClose = new JButton("Exit");

        //addActionListener to buttons using lambda expressions
        btnNew.addActionListener(e -> NewFile());
        btnOpen.addActionListener(e -> OpenFile());
        btnClr.addActionListener(e -> ClearAreaAndField());
        btnSave.addActionListener( e -> SaveFile());
        btnSaveAs.addActionListener(e -> CopyFile());
        btnChangeMode.addActionListener(e -> {
            if (txtArea.getForeground() == Color.BLACK) {
                DraculaMode();
            }
            else {
                DefaultMode();
            }
        });
        btnStats.addActionListener(e -> ShowStats());
        btnClose.addActionListener(e -> ExitApp());

        //add buttons to the panel
        btnPanel.add(btnChangeMode);
        btnPanel.add(btnNew);
        btnPanel.add(btnOpen);
        btnPanel.add(btnClr);
        btnPanel.add(btnSave);
        btnPanel.add(btnSaveAs);
        btnPanel.add(btnStats);
        btnPanel.add(btnClose);


        //Creates panel for JTextField and JTextArea
        panelTxt = new JPanel(new BorderLayout());
        fieldPath = new JTextField();
        fieldPath.setFont(new Font("Consolas", Font.PLAIN, 15));  //set the font and size of the characters

        txtArea = new JTextArea();
        txtArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        //add text field and area to the panel manager
        panelTxt.add(fieldPath, BorderLayout.NORTH);

        DefaultMode(); //Here i have to initialize the theme to default. If i don't call this method here. When the user clicks on the Dark theme button the first time it will not change the color.

        //Add Scrollbar if need
        JScrollPane sp = new JScrollPane();
        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getViewport().add(txtArea);
        panelTxt.add(sp);


        //Create the full manager Layout with BoxLayout
        managerPanel = new Box(BoxLayout.Y_AXIS);
        managerPanel.add(btnPanel);
        managerPanel.add(panelTxt);
        myFrame.add(mb, BorderLayout.NORTH);
        myFrame.add(managerPanel);

        myFrame.addWindowListener(new WindowListener() { //add a windowListener so that i can setup the confirmation before windowClosing
            public void windowOpened(WindowEvent e) {} //not used
            public void windowClosing(WindowEvent e) {
                ExitApp();
            }
            public void windowClosed(WindowEvent e) {}//not used
            public void windowIconified(WindowEvent e) {}//not used
            public void windowDeiconified(WindowEvent e) {}//not used
            public void windowActivated(WindowEvent e) {}//not used
            public void windowDeactivated(WindowEvent e) {}//not used
        });
        myFrame.setSize(956, 816); //Setup Default size
        myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //if i don't setDefaultCloseOperation to "DO_NOTHING_ON_CLOSE" the frame will despawn but the program will not exit
        myFrame.setLocationRelativeTo(null); //this will start the frame in the center of the screen
        myFrame.setVisible(true); //this will set the frame visible

    }


    private void NewFile(){//Initialize for a new file
        filePath = "";
        fieldPath.setText("");
        txtArea.setText("");
        fileExists = false;
    }

    private void DraculaMode(){ //change the theme to dark
        fieldPath.setBackground(Color.BLACK);
        txtArea.setBackground(Color.BLACK);
        fieldPath.setForeground(Color.WHITE);
        txtArea.setForeground(Color.WHITE);
        btnChangeMode.setText("White mode");


    }

    private void DefaultMode() { //change the theme to white
        fieldPath.setBackground(Color.WHITE);
        txtArea.setBackground(Color.WHITE);
        fieldPath.setForeground(Color.BLACK);
        txtArea.setForeground(Color.BLACK);
        btnChangeMode.setText("Dark mode");
    }

    private void OpenFile(){ //open a file
        //Create a file reader
        FileReader fileIn;
        chPath = new JFileChooser();
        BufferedReader objReader;
        String temp;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        chPath.setFileFilter(filter); //Set a new filter for text files
        int result = chPath.showOpenDialog(null); //open the JFileChooser window
        if(result == JFileChooser.APPROVE_OPTION) { //If approve_option (the user choose a file to open
            try {
                fileIn = new FileReader(chPath.getSelectedFile());
                filePath = chPath.getSelectedFile().getAbsolutePath();
                fieldPath.setText(filePath);
                fileExists=true;
                objReader = new BufferedReader(fileIn);
                txtArea.setText("");
                while ((temp = objReader.readLine()) != null) {
                    txtArea.append(temp + "\n"); //Write the file into the JTextArea
                }
                fileIn.close(); //close the file
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(myFrame, "File not found", "Error Message", JOptionPane.ERROR_MESSAGE); //If the user choose a name of a file that is not exists or the
                                                                                                                                    // file could not open at all, it will show a message to the user that the file is not found

            } catch (IOException e) {
                //not handled
                e.printStackTrace();
            }
        }
    }

    private void CopyFile() { //Copy the content of the JTextArea to a specific file
        chPath = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        chPath.setFileFilter(filter);
        int result = chPath.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String temp;
            BufferedWriter objWriter;
            try {
                fileOut = new FileWriter(chPath.getSelectedFile());
                objWriter = new BufferedWriter(fileOut);
                temp = txtArea.getText();
                PrintWriter pw = new PrintWriter(objWriter);
                pw.print(temp);
                pw.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SaveFile() {

        if (fileExists) { //if a file is open then the content of the JTextArea
            try {
                String temp;
                BufferedWriter objWriter;
                fileOut = new FileWriter(filePath);
                objWriter = new BufferedWriter(fileOut);
                temp = txtArea.getText();
                PrintWriter pw = new PrintWriter(objWriter);
                pw.print(temp);
                pw.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {//if a file is not open, and in order to avoid duplicate code i just call the method CopyFile.
            CopyFile();
        }
    }


    private void ClearAreaAndField() {//It will delete any content written into the JTextArea and JTextField,a confirmation message is also included
        int exit = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to clear the text area?", "Clear confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (exit == JOptionPane.YES_OPTION){
            fieldPath.setText("");
            txtArea.setText("");
        }
    }

    private void ShowStats(){
        if(fileExists){ // boolean type that is true if a file is opened to the editor
            int countWords = 0, countChar = 0, countCharWithSpaces = 0, countPar = 0;
            FileReader file;//file is type string a it has the absolute path of the file that is opened to the text editor
            String line, prevLine="";
            BufferedReader objReader;
            try {
                file = new FileReader(filePath);
                objReader = new BufferedReader(file);
                while ((line = objReader.readLine()) != null) {

                    if( !(line.equals("")) && prevLine.equals("") ) {
                        countPar++; //if previous line was empty and the current line is not, then this is a new paragraph.
                        line = line.trim();
                        countCharWithSpaces +=  line.length();
                        for(int i=0;i<line.length();i++) {
                            if(!(line.charAt(i) == ' ')) {
                                countChar++;
                            }
                        }
                        String[] words = line.split("\\s+");
                        countWords += words.length;
                    }
                    prevLine = line;
                }

                if(countPar != 0){
                    countPar--;
                }
                File fileSize = new File(filePath); //in order to show the size of a file i have to call the length() method, but the lenght method cannot be used in a FileReader  object so i have to create a File class object to do so.
                String message = "The number of Words is " + countWords; //create the string that is displayed in showMessageDialog
                message += "\nThe number of paragraphs is " + countPar;
                message += "\nThe number of Characters with spaces  is " + countCharWithSpaces;
                message += "\nThe number of Characters without spaces is " + countChar;
                message += "\nThe size of file in bytes is " + fileSize.length();

                JOptionPane.showMessageDialog(myFrame, message, "Statistics of file ", JOptionPane.INFORMATION_MESSAGE);
            }catch (IOException e) {
                //not handled
                e.printStackTrace();
            }

        }
        else {
            JOptionPane.showMessageDialog(myFrame, "No opened file found", "Error Message",JOptionPane.ERROR_MESSAGE); //if a file is not open from the text editor the ShowStats method cannot be used
        }
    }

    private void ExitApp(){ //A method with a confirmation message before exiting the app
        int exit = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to exit?","Exit confirmation", 1, JOptionPane.QUESTION_MESSAGE);
        if (exit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}