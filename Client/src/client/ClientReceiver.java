/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author seabirds
 */
public class ClientReceiver extends Thread
{
    ClientFrame cf;
    int cid;
    int cpt;
    ClientReceiver(ClientFrame ce,int id)
    {
        cf=ce;
        cid=id;
        cpt=cid+7000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(cpt);
            String cip=InetAddress.getLocalHost().getHostAddress();
            String sg="ClientDt#"+cid+"#"+cip+"#"+cpt;
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
                
                if(req[0].equals("ServerId"))
                {
                    for(int i=1;i<req.length;i++)
                    {
                        cf.jComboBox1.addItem(req[i]);
                    }
                } // ServerId
                
                if(req[0].equals("Response1"))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Response is received from Server "+req[2]);
                    DefaultTableModel dm=(DefaultTableModel)cf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add(req[2]);
                    v.add(req[2]);
                    dm.addRow(v);
                    
                } //Response1
                
                if(req[0].equals("Response2"))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Response is received from Server "+req[3]);
                    DefaultTableModel dm=(DefaultTableModel)cf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add(req[2]);
                    v.add(req[3]);
                    dm.addRow(v);
                    
                } //Response2
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
