package JavaUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MyJFrame extends JFrame {

    private String filePath;
    private JFrame myFrame;
    private JTextArea txtArea;
    private JTextField fieldPath;
    private JFileChooser chPath;
    private JPanel btnPanel, panelTxt;
    private JMenuBar mb;
    private JMenu m1, m2;
    private JMenuItem miNew, miOpen, miStats, miSave, miSaveAs, miClr, miClose;
    private JButton btnNew, btnOpen,btnStats, btnClr, btnSave, btnSaveAs, btnChangeMode,btnClose;
    private Boolean fileExists=false;
    private FileReader fileIn;
    private FileWriter fileOut;
    private Box managerPanel;
    //Constructor
    public MyJFrame() {
        //Create my own frame
        myFrame = new JFrame("Text editor");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


        } catch (Exception e) {

        }



        // Create a menubar
        mb = new JMenuBar();

        // Create a menu for file menu
        m1 = new JMenu("File");
        m2 = new JMenu("Edit");

        // Create File menu items
        miNew = new JMenuItem("New");
        miOpen = new JMenuItem("Open");
        miStats = new JMenuItem("Statistics");
        miSave = new JMenuItem("Save");
        miSaveAs = new JMenuItem("Save as");
        miClr = new JMenuItem("Clear");
        miClose = new JMenuItem("Exit");

        //add JmenuItems to Jmenu
        m1.add(miNew);
        m1.add(miOpen);
        m1.add(miSave);
        m1.add(miSaveAs);
        m1.add(miStats);
        m1.add(miClose);

        //add action listener with lambda expression

        miNew.addActionListener(e -> NewFile());
        miOpen.addActionListener(e -> OpenFile());
        miSave.addActionListener(e -> SaveFile());
        miSaveAs.addActionListener(e -> CopyFile());
        miStats.addActionListener(e -> ShowStats());
        miClose.addActionListener(e -> ExitApp());
        miClr.addActionListener(e -> ClearAreaAndField());

        //add JmenuItems to Jmenu for edit
        m2.add(miClr);


        //add Jmenu to JmenuBar
        mb.add(m1);
        mb.add(m2);


        //Create Panel and Buttons for the panel
        btnPanel = new JPanel();
        btnNew = new JButton("New");
        btnOpen = new JButton("Open");
        btnClr = new JButton("Clear");
        btnSave = new JButton("Save");
        btnSaveAs = new JButton("copy");
        btnStats = new JButton("Statistics");
        btnChangeMode = new JButton("Dark mode");
        btnClose = new JButton("Exit");

        //addActionListener with lambda expressions
        btnNew.addActionListener(e -> NewFile());
        btnOpen.addActionListener(e -> OpenFile());
        btnClr.addActionListener(e -> ClearAreaAndField());
        btnSave.addActionListener( e -> SaveFile());
        btnSaveAs.addActionListener(e -> CopyFile());
        btnChangeMode.addActionListener(e -> {
            if (txtArea.getForeground() == Color.BLACK) {
                DraculaMode();
            } else
                DefaultMode();
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


        //Creates panel manager, textfield for the fieldPath, text area and Scrollbar if needed
        panelTxt = new JPanel(new BorderLayout());
        fieldPath = new JTextField();
        fieldPath.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtArea = new JTextArea();
        txtArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        JScrollPane sp = new JScrollPane();
        DefaultMode();

        //add text field and area to the panel manager
        panelTxt.add(fieldPath, BorderLayout.NORTH);
        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getViewport().add(txtArea);
        panelTxt.add(sp);


        //Create the  full manager Layout
        managerPanel = new Box(BoxLayout.Y_AXIS);
        managerPanel.add(btnPanel, BorderLayout.WEST);
        managerPanel.add(panelTxt);
        myFrame.add(mb, BorderLayout.NORTH);
        myFrame.add(managerPanel);
        myFrame.addWindowListener(new WindowListener() {
            @Override
            //not used
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                ExitApp();
            }
            @Override
            //not used
            public void windowClosed(WindowEvent e) {}

            @Override
            //not used
            public void windowIconified(WindowEvent e) {}

            @Override
            //not used
            public void windowDeiconified(WindowEvent e) {}

            @Override
            //not used
            public void windowActivated(WindowEvent e) {}

            @Override
            //not used
            public void windowDeactivated(WindowEvent e) {}
        });
        myFrame.setSize(956, 816);
        myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        myFrame.setVisible(true);

    }


    private void NewFile(){
        filePath = "";
        fieldPath.setText("");
        txtArea.setText("");
        fileExists = false;
    }

    private void DraculaMode(){
        fieldPath.setBackground(Color.BLACK);
        txtArea.setBackground(Color.BLACK);
        fieldPath.setForeground(Color.WHITE);
        txtArea.setForeground(Color.WHITE);
        btnChangeMode.setText("White mode");


    }

    private void DefaultMode() {
        fieldPath.setBackground(Color.WHITE);
        txtArea.setBackground(Color.WHITE);
        fieldPath.setForeground(Color.BLACK);
        txtArea.setForeground(Color.BLACK);
        btnChangeMode.setText("Dark mode");
    }

    private void OpenFile(){
        chPath = new JFileChooser();
        BufferedReader objReader;
        String temp;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        chPath.setFileFilter(filter);
        int result = chPath.showOpenDialog(null);
        if(result ==JFileChooser.APPROVE_OPTION) {
            filePath = chPath.getSelectedFile().getAbsolutePath();
            fieldPath.setText(filePath);
            fileExists=true;
            try {
                fileIn = new FileReader(chPath.getSelectedFile());
                objReader = new BufferedReader(fileIn);
                txtArea.setText("");
                while ((temp = objReader.readLine()) != null) {
                    txtArea.append(temp+"\n");
                }
                fileIn.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void CopyFile() {

        chPath = new JFileChooser();
        chPath.setApproveButtonText("Save"); //it changes the button title from "Open" to save if this is for a new file
        chPath.setDialogTitle("Choose file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
        chPath.setFileFilter(filter);
        int result = chPath.showOpenDialog(null);
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

        if (fileExists) {
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
        else {
            CopyFile();
        }
    }


    private void ClearAreaAndField() {
        int exit = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to clear the text area?","Clear confirmation", 1, 3);
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
                objReader = new BufferedReader(file);//it reads all the file into the input string
                while ((line = objReader.readLine()) != null) {

                    if(!(line.equals("")) && prevLine.equals("") ) {
                        countPar++; //if previous line was empty and the currect line is not it meant a paragraph was between them.
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
                File fileSize = new File(filePath);
                StringBuilder message = new StringBuilder();
                message.append("The number of Words is " + countWords + "\n");
                message.append("The number of paragraphs is " + countPar + "\n");
                message.append("The number of Characters with spaces  is " + countCharWithSpaces + "\n");
                message.append("The number of Characters without spaces is " + countChar + "\n");
                message.append("The size of file in bytes is " + fileSize.length());

                JOptionPane.showMessageDialog(myFrame, message.toString().trim(), "Statistics of file ", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            JOptionPane.showMessageDialog(myFrame, "No opened file found", "Error Message",0);
        }
    }

    private void ExitApp(){
        int exit = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to exit?","Exit confirmation", 1, 3);
        if (exit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}