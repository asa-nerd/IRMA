import java.awt.*;  

class MenuBar{
	
	MenuBar(){
		Frame f= new Frame("Menu and MenuItem Example");  
        MenuBar mb=new MenuBar();  
        Menu menu=new Menu("Menu");  
        Menu submenu=new Menu("Sub Menu");  
        MenuItem i1=new MenuItem("Item 1");  
        MenuItem i2=new MenuItem("Item 2");  
        MenuItem i3=new MenuItem("Item 3");  
        MenuItem i4=new MenuItem("Item 4");  
        MenuItem i5=new MenuItem("Item 5");  
        menu.add(i1);  
        menu.add(i2);  
        menu.add(i3);  
        submenu.add(i4);  
        submenu.add(i5);  
        menu.add(submenu);  
        mb.add(menu);  
        f.setMenuBar(mb);  
        f.setSize(400,400);  
       // f.setLayout(null);  
       // f.setVisible(true);  
		
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
