/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redirect;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
/**
 *
 * @author seabirds
 */
public class Receiver extends Thread
{
    RedirectFrame sf;
    
    
    Receiver(RedirectFrame se)
    {
        sf=se;
        
        
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(6000);
            
            String sg="RedirectDt#";
            byte bt[]=sg.getBytes();
            DatagramPacket dp1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),9000);
            ds.send(dp1);
            
            
            while(true)
            {
                byte data[]=new byte[60000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                
                String str=new String(dp.getData()).trim();
                String req[]=str.split("#");
                
                
                if(req[0].equals("Redirect"))
                {
                    
                    DefaultTableModel dm2=(DefaultTableModel)sf.jTable2.getModel();
                    Vector v2=new Vector();
                    v2.add(req[1]);
                    v2.add(req[2]);
                    v2.add(req[3]);
                    dm2.addRow(v2);
                    JOptionPane.showMessageDialog(new JFrame(),"Request Redirected to "+req[3]);
                    
                   
                    
                } //Redirect
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
