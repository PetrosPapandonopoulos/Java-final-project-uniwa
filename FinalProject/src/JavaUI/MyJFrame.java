package JavaUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MyJFrame extends JFrame {

    private String filePath;
    private JFrame MyFrame;
    private JTextArea txtArea;
    private JTextField fieldPath;
    private JFileChooser chPath;
    private JPanel btnPanel, panelTxt;
    private JMenuBar mb;
    private JMenu m1, m2;
    private JMenuItem miNew, miOpen, miStats, miSave, miSaveAs, miClr, miClose;
    private JButton btnOpen, btnClr, btnSave, btnSaveAs, btnChangeMode;
    private Boolean fileExists=false;
    private FileReader fileIn;
    private FileWriter fileOut;
    private Box managerPanel;
    //Constructor
    public MyJFrame() {
        //Create my own frame
        MyFrame = new JFrame("Text editor");

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

        //add action listener
        miNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFile();
            }
        });
        miOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile();
            }
        });
        miSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveFile();

            }
        });
        miSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CopyFile();
            }
        });
        miStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        miClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitApp();
            }
        });
        miClr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClearAreaAndField();
            }
        });

        //add JmenuItems to Jmenu for edit
        m2.add(miClr);


        //add Jmenu to JmenuBar
        mb.add(m1);
        mb.add(m2);


        //Create Panel and Buttons for the panel
        btnPanel = new JPanel();
        btnOpen = new JButton("Open");
        btnClr = new JButton("Clear");
        btnSave = new JButton("Save");
        btnSaveAs = new JButton("copy");
        btnChangeMode = new JButton("Dark mode");

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile();
            }
        });

        btnClr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClearAreaAndField();

            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveFile();

            }
        });

        btnSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CopyFile();
            }
        });

        btnChangeMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtArea.getForeground() == Color.BLACK) {
                    DraculaMode();
                }
                else
                    DefaultMode();
            }
        });



        //add buttons to the panel
        btnPanel.add(btnOpen);
        btnPanel.add(btnClr);
        btnPanel.add(btnSave);
        btnPanel.add(btnSaveAs);
        btnPanel.add(btnChangeMode);


        //Creates panel manager, textfield for the fieldPath, text area and Scrollbar if needed
         panelTxt = new JPanel(new BorderLayout());
         fieldPath = new JTextField();
         txtArea = new JTextArea();
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
        MyFrame.add(mb, BorderLayout.NORTH);
        MyFrame.add(managerPanel);
        MyFrame.setSize(567, 512);
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame.setVisible(true);

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
        if(result ==JFileChooser.APPROVE_OPTION){
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

    private void SaveFile(){

        if(fileExists){
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
        else{
            chPath = new JFileChooser();
            chPath.setApproveButtonText("Save"); //it changes the button title from "Open" to save if this is for a new file
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt", "text");
            chPath.setFileFilter(filter);
            int result = chPath.showOpenDialog(null);
            if(result ==JFileChooser.APPROVE_OPTION){
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
    }

    private void CopyFile() {
        if (fileExists) {
            chPath = new JFileChooser();
            chPath.setApproveButtonText("Save"); //it changes the button title from "Open" to save if this is for a new file
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    private void ClearAreaAndField() {
        int exit = JOptionPane.showConfirmDialog(MyFrame, "Are you sure you want to clear the text area?","Clear confirmation", 1, 3);
        if (exit == JOptionPane.YES_OPTION){
            fieldPath.setText("");
            txtArea.setText("");
        }
       ;
    }

    private void ExitApp(){
        int exit = JOptionPane.showConfirmDialog(MyFrame, "Are you sure you want to exit?","Exit confirmation", 1, 3);
        if (exit == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

}
