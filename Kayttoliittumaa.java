import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kayttoliittuma extends JFrame{
    Kayttoliittuma(){
        JFrame frame = new JFrame("Rannekkeet"); 

        JButton btn1 = new JButton("Normaali"); 
        JButton btn2 = new JButton("Lasten"); 
        JButton btn3 = new JButton("Alennus");  //napit ikkunan yläosassa
 
        JButton btn4 = new JButton("Tulosta"); 
        JButton btn5 = new JButton("Tulosta"); 
        JButton btn6 = new JButton("Tulosta");     //tulosta napit pohjalla

        JTextArea txt1 = new JTextArea();
        JTextArea txt2 = new JTextArea();               //tekstikentät

        String s1[] = { "Opiskelija", "Eläkeläinen", "Varusmies"};
 
        JComboBox checkbox1 = new JComboBox(s1); //dropdown menu

        JLabel lbl1 = new JLabel("Hinta:22€(sis alv)");
        JLabel lbl2 = new JLabel("Hinta:20€(sis alv)");
        JLabel lbl3 = new JLabel("Hinta:20€(sis alv)");//tekstialueita 


        JLabel lbl11 = new JLabel("Huoltajan Puhelinnumero");
        JLabel lbl5 = new JLabel("Lapsen etunimi");
        JLabel lbl6 = new JLabel("Valitse alennusryhmä");



        //tyhjät osat täyttämään grid layooutin
        JLabel lbl12 = new JLabel(" ");
        JLabel lbl13 = new JLabel(" ");
        JLabel lbl7 = new JLabel(" ");
        JLabel lbl10 = new JLabel(" ");
        JLabel lbl4 = new JLabel(" ");
        JLabel lbl15 = new JLabel(" ");
        JLabel lbl16 = new JLabel(" ");
        JLabel lbl17 = new JLabel(" ");
        JLabel lbl18 = new JLabel(" ");






        JPanel panel = new JPanel(new GridLayout(8, 3, 10, 10)); 

        panel.add(btn1); 
        panel.add(btn2); 
        panel.add(btn3); 

        panel.add(lbl1);
        panel.add(lbl2);
        panel.add(lbl3);
        
        panel.add(lbl4);
        panel.add(lbl5);
        panel.add(lbl6);
       
        panel.add(lbl7);
        panel.add(txt1);
        panel.add(checkbox1);

        
        panel.add(lbl10);
        panel.add(lbl11);
        panel.add(lbl12);
       
        panel.add(lbl13);   
        panel.add(txt2);
        panel.add(lbl15);

        
        panel.add(btn4); 
        panel.add(btn5);
        panel.add(btn6);
        
        
        panel.add(lbl16); 
        panel.add(lbl17);
        panel.add(lbl18);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        frame.setSize(1280, 720); 

        frame.getContentPane().add(panel); 

        frame.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    
}
