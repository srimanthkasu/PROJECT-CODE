/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;
import javax.swing.JFrame;
import javax.swing.JDialog;
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
        
        MainServerFrame sf=new MainServerFrame();
        sf.setVisible(true);
        sf.setTitle("Back End Server");
        sf.setResizable(false);
        
        ServerReceiver sr=new ServerReceiver(sf);
        sr.start();
    }
}
