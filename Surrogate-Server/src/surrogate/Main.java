/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package surrogate;
import javax.swing.JFrame;
import javax.swing.JDialog;
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
        
        int id=Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "Enter Server Id"));
        String sRate=JOptionPane.showInputDialog(new JFrame(), "Enter Service Rate");
        String aRate=JOptionPane.showInputDialog(new JFrame(), "Enter Arrival Rate");
        
        SurrogateFrame sf=new SurrogateFrame(id,sRate,aRate);
        sf.setVisible(true);
        sf.setResizable(false);
        sf.setTitle("Server "+id);
        
        SurrogateReceiver sr=new SurrogateReceiver(sf,id);
        sr.start();
        
        
        
    }
}
