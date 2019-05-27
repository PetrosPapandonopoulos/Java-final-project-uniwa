package JavaUI;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame implements ActionListener {


    private JButton btnOpen, btnClr, btnSave, btnCp;
    private JTextArea txtArea;
    private JTextField path;

    public MyJFrame() {
        //call super constructor to add title to the app
        super("Text editor");
        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create a menu for file menu
        JMenu m1 = new JMenu("File");

        // Create File menu items
        JMenuItem miNew = new JMenuItem("New");
        JMenuItem miOpen = new JMenuItem("Open");
        JMenuItem miClr = new JMenuItem("Clear");
        JMenuItem miSave = new JMenuItem("Save");
        JMenuItem miSaveAs = new JMenuItem("Save as");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miClose = new JMenuItem("Exit");

        //add JmenuItems to Jmenu
        m1.add(miNew);
        m1.add(miOpen);
        m1.add(miClr);
        m1.add(miSave);
        m1.add(miSaveAs);
        m1.add(miStats);
        m1.add(miClose);

        //add action listener
        miClose.addActionListener(this);
        miOpen.addActionListener(this);
        miClr.addActionListener(this);
        miSave.addActionListener(this);
        miSaveAs.addActionListener(this);
        miStats.addActionListener(this);
        miClose.addActionListener(this);


        //add Jmenu to JmenuBar
        mb.add(m1);

        //Create a menu for edit menu

        //Create Panel for Buttons
        JPanel panelBtns = new JPanel();

        //Create Buttons for the panel
        JButton btnOpen = new JButton("Open");
        JButton btnClr = new JButton("Clear");
        JButton btnSave = new JButton("Save");
        JButton btnSaveAs = new JButton("Save as");

        //add buttons to panelBtns
        panelBtns.add(btnOpen);
        panelBtns.add(btnClr);
        panelBtns.add(btnSave);
        panelBtns.add(btnSaveAs);

        //setBoxlayout to panelBtns
        panelBtns.add(Box.createHorizontalGlue());



        JPanel panelTxt = new JPanel();
        JTextArea txtArea = new JTextArea(25,50);

        //GridbagLayout for  panelTxt
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridbag.setConstraints(txtArea, gridBagConstraints);
        panelTxt.add(txtArea);
        panelTxt.setBorder(BorderFactory.createRaisedBevelBorder());

        //create north panel to edit the layout
        JPanel northPanel = new JPanel();
        northPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        northPanel.add(panelBtns, new FlowLayout(FlowLayout.RIGHT));
        this.add(mb, BorderLayout.NORTH);
        this.add(northPanel, BorderLayout.CENTER);
        //this.add(new JLabel("Text Editor"));
        this.add(panelTxt, BorderLayout.SOUTH);

        //this.add(panelBtns, BorderLayout.NORTH);
        this.setSize(600, 520);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);







    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
