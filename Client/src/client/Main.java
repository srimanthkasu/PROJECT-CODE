/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author seabirds
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try
        {
            UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
            
        }
        catch (Exception ex)
        {
             System.out.println("Failed loading L&F: ");
                   //System.out.println(ex);
        }
        
        int id=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "Enter Client Id"));
        ClientFrame cf=new ClientFrame(id);
        cf.setVisible(true);
        cf.setResizable(false);
        cf.setTitle("Client - "+id);
        
        ClientReceiver cr=new ClientReceiver(cf,id);
        cr.start();
    }
}
