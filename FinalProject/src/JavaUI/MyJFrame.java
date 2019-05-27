package JavaUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame implements ActionListener {


    private JTextArea txtArea;
    private JTextField path;

    public MyJFrame() {
        JFrame MyFrame = new JFrame("Text editor");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


        } catch (Exception e) {

        }


        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create a menu for file menu
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Edit");

        // Create File menu items
        JMenuItem miNew = new JMenuItem("New");
        JMenuItem miOpen = new JMenuItem("Open");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miSave = new JMenuItem("Save");
        JMenuItem miSaveAs = new JMenuItem("Save as");
        JMenuItem miClr = new JMenuItem("Clear");
        JMenuItem miClose = new JMenuItem("Exit");

        //add JmenuItems to Jmenu
        m1.add(miNew);
        m1.add(miOpen);
        m1.add(miSave);
        m1.add(miSaveAs);
        m1.add(miStats);
        m1.add(miClose);

        //add action listener
        miOpen.addActionListener(this);
        miSave.addActionListener(this);
        miSaveAs.addActionListener(this);
        miStats.addActionListener(this);
        miClose.addActionListener(this);
        miClr.addActionListener(this);

        //add JmenuItems to Jmenu for edit
        m2.add(miClr);


        //add Jmenu to JmenuBar
        mb.add(m1);
        mb.add(m2);


        //Create Panel and Buttons for the panel
        JPanel btnPanel = new JPanel();
        JButton btnOpen = new JButton("Open");
        JButton btnClr = new JButton("Clear");
        JButton btnSave = new JButton("Save");
        JButton btnSaveAs = new JButton("copy");
        JButton btnChangeMode = new JButton("Change mode");

        //add buttons to the panel
        btnPanel.add(btnOpen);
        btnPanel.add(btnClr);
        btnPanel.add(btnSave);
        btnPanel.add(btnSaveAs);
        btnPanel.add(btnChangeMode);


        //Creates panel manager, textfield for the path, text area and Scrollbar if needed
        JPanel panelTxt = new JPanel(new BorderLayout());
        JTextField path = new JTextField();
        JTextArea txtArea = new JTextArea();
        JScrollPane sp = new JScrollPane();

        //add text field and area to the panel manager
        panelTxt.add(path, BorderLayout.NORTH);
        sp.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getViewport().add(txtArea);
        panelTxt.add(sp);


        //Create the  full manager Layout
        Box managerPanel = new Box(BoxLayout.Y_AXIS);


        managerPanel.add(btnPanel, BorderLayout.WEST);
        managerPanel.add(panelTxt);

        MyFrame.add(mb, BorderLayout.NORTH);
        MyFrame.add(managerPanel);
        MyFrame.setSize(567, 512);
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
