import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.JTabbedPane;


public class Menu_bar extends JFrame implements ActionListener {
  JFrame frame;
  JMenuItem new_file;

  JPanel panel;

  public Menu_bar(PApplet app, String name) {
    frame = (JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas)app.getSurface().getNative()).getFrame();

    frame.setTitle(name);

    panel =  new JPanel(); 
    panel.setOpaque(false);

    // ADDING PANEL WITH BUTTON

    JButton exit = new JButton("Exit");
    exit.setBounds(300, 300, 300, 300);  
    exit.addActionListener(this);
    panel.add(exit);

    // Creates a menubar for a JFrame
    JMenuBar menu_bar = new JMenuBar();
    // Add the menubar to the frame
    frame.setJMenuBar(menu_bar);
    // Define and add two drop down menu to the menubar
    JMenu import_menu = new JMenu("import");
    JMenu text_menu = new JMenu("text");
    JMenu shape_menu = new JMenu("shape");
    JMenu image_menu = new JMenu("image");
    JMenu video_menu = new JMenu("video");

    JMenu submenu = new JMenu("Submenu");

    JMenuItem test = new JMenuItem("Testando");

    menu_bar.add(import_menu);
    menu_bar.add(text_menu);
    menu_bar.add(shape_menu);
    menu_bar.add(image_menu);
    menu_bar.add(video_menu);

    test.addActionListener(this);
    submenu.add(test);

    import_menu.add(submenu);

    // Create and add simple menu item to one of the drop down menu
    new_file = new JMenuItem("Import file");
    JMenuItem new_folder = new JMenuItem("Import folder");
    JMenuItem action_exit = new JMenuItem("Exit");

    new_file.addActionListener(this);
    new_folder.addActionListener(this);
    action_exit.addActionListener(this);

    import_menu.add(new_file);
    import_menu.add(new_folder);

    import_menu.addSeparator();

    import_menu.add(action_exit);
    frame.setContentPane(panel);
    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) { 
    String str = e.getActionCommand(); 

    Object source = e.getSource();
    if (source == new_file) {
      println("Selected new file");
    }
  }
}
