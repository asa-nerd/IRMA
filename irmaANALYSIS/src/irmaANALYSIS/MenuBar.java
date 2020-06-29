package irmaANALYSIS;

import javax.swing.*;
import java.awt.event.*;  
import java.awt.Dimension;

public class MenuBar implements ActionListener {
	JMenu fileMenu, analyzeMenu, controlMenu;  
    JMenuItem f1, f2, c1, c2, c3, a1, a2, a3, a4;  
     
	MenuBar(JFrame f){
	 JMenuBar mb=new JMenuBar();  
     
	 fileMenu=new JMenu("File");
     controlMenu = new JMenu("Control");
     analyzeMenu = new JMenu("Analyze");
     
     f1=new JMenuItem("Load Sample");  
     f2=new JMenuItem("Quit");  
     c1=new JMenuItem("Play");  
     c2=new JMenuItem("Pause");  
     c3=new JMenuItem("Stop");  
     a1=new JMenuItem("Sample – Average Focus");  
     a2=new JMenuItem("Sample – Activity ");  
     a3=new JMenuItem("Subjects – Focus");  
     a4=new JMenuItem("Subjects – Activity");
     
     f1.addActionListener(this); f2.addActionListener(this); 
     c1.addActionListener(this); c2.addActionListener(this); c3.addActionListener(this); 
     a1.addActionListener(this); a2.addActionListener(this); a3.addActionListener(this); a4.addActionListener(this);
     
     fileMenu.setPreferredSize(new Dimension(400, 30));		// currently not working with OSX menu bar
     f1.setPreferredSize(new Dimension(400, 30));
     
     fileMenu.add(f1); fileMenu.add(f2); 
     controlMenu.add(c1); controlMenu.add(c2);  controlMenu.add(c3);  
     analyzeMenu.add(a1);  analyzeMenu.add(a2); analyzeMenu.add(a3);  analyzeMenu.add(a4);     
     
     mb.add(fileMenu);  mb.add(controlMenu); mb.add(analyzeMenu); 
     f.setJMenuBar(mb);  
     //f.setLayout(null);  
 
	}
	
	public void actionPerformed(ActionEvent e) {    
		if(e.getSource()==f1)   
			System.out.println("Load Sample");
		if(e.getSource()==f2)   
			System.out.println("Quit");
		if(e.getSource()==c1)   
			System.out.println("Play");
		if(e.getSource()==c2)   
			System.out.println("Pause");
		if(e.getSource()==c3)   
			System.out.println("Stop");
		if(e.getSource()==a1)   
			System.out.println("Sample – AFA");
		if(e.getSource()==a2)   
			System.out.println("Sample – Activity");
		if(e.getSource()==a3)   
			System.out.println("Subjects – Focus");
		if(e.getSource()==a4)   
			System.out.println("Subjects – Activity");
	}

}
