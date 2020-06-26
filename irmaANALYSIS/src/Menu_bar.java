import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;
import processing.core.*;
import processing.data.*;
import processing.awt.*;
import processing.awt.PSurfaceAWT.SmoothCanvas;

public class Menu_bar {
  JFrame frame;

  public Menu_bar(PApplet app, String name, int width, int height) {
   // System.setProperty("apple.laf.useScreenMenuBar", "true");
    //frame = (JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas)app.getSurface().getNative()).getFrame();
    frame = (JFrame) app.getSurface().getNative()).getFrame();
    //frame = (JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas)app.getSurface().getNative()).getFrame();
  //**  java.awt.Frame frame = ( (processing.awt.PSurfaceAWT.SmoothCanvas)app.getSurface().getNative()).getFrame();
   // frame = (JFrame) ( ((PSurfaceAWT)surface).getNative()).getFrame();
    
   /* Field f = ((PSurfaceAWT) surface).getClass().getDeclaredField("frame");
    f.setAccessible(true);
    frame  = (Frame) (f.get(((PSurfaceAWT) surface)));
    frame.setTitle(name);*/

    // Creates a menubar for a JFrame
    JMenuBar menu_bar = new JMenuBar();
/*
    frame.setJMenuBar(menu_bar);

    JMenu import_menu = new JMenu("import");
    JMenu text_menu = new JMenu("text");
    JMenu shape_menu = new JMenu("shape");
    JMenu image_menu = new JMenu("image");
    JMenu video_menu = new JMenu("video");

    menu_bar.add(import_menu);
    menu_bar.add(text_menu);
    menu_bar.add(shape_menu);
    menu_bar.add(image_menu);
    menu_bar.add(video_menu);

    // Create and add simple menu item to one of the drop down menu
    JMenuItem new_file = new JMenuItem("Import file");
    JMenuItem new_folder = new JMenuItem("Import folder");
    JMenuItem action_exit = new JMenuItem("Exit");

    import_menu.add(new_file);
    import_menu.add(new_folder);
    import_menu.addSeparator();
    import_menu.add(action_exit);*/

    // Add a listener to the New menu item. actionPerformed() method will
    // invoked, if user triggred this menu item
    /*new_file.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        System.out.println("You have clicked on the new action");
      }
    }
    );
    frame.setVisible(true);*/
  }
}

/*
import javax.swing.*; // JFrame, JMenuBar, JMenu, JMenuItem
import java.awt.event.*; // ActionListener, ActionEvent

public class MenuBar extends JFrame {
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	JFrame f; 
	
	MenuBar(){
		f = new JFrame("Menu demo"); 
		menuBar = new JMenuBar();
		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		
		f.setJMenuBar(menuBar);
		f.setSize(500, 500); 
		f.setVisible(true);
	}

}
*/
