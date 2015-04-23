/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package surrogate;

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
public class SurrogateReceiver extends Thread
{
    SurrogateFrame sf;
    int sid;
    int spt;
    SurrogateReceiver(SurrogateFrame se,int id)
    {
        sf=se;
        sid=id;
        spt=sid+8000;
    }
    
    public void run()
    {
        try
        {
            DatagramSocket ds=new DatagramSocket(spt);
            
            String sg="ServerDt#"+sid+"#"+sf.sRate+"#"+sf.aRate;
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
                
                if(req[0].equals("SetId"))
                {
                    String id1=req[1];
                    String tt=sf.jLabel7.getText().trim()+" "+id1;
                    sf.jLabel7.setText(tt);
                } //SetId
                
                if(req[0].equals("Request1"))
                {
                    DefaultTableModel dm1=(DefaultTableModel)sf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add(req[2]);
                    dm1.addRow(v);
                    
                    DefaultTableModel dm2=(DefaultTableModel)sf.jTable2.getModel();
                    Vector v2=new Vector();
                    v2.add(req[1]);
                    v2.add(sid);
                    dm2.addRow(v2);
                    JOptionPane.showMessageDialog(new JFrame(),"Request Received \n Process it on Connected Server");
                    
                    int pt=Integer.parseInt(req[1])+7000;
                    String ms="Response1#"+req[1]+"#"+sid;
                    byte be[]=ms.getBytes();
                    DatagramPacket dpt=new DatagramPacket(be,0,be.length,InetAddress.getByName("127.0.0.1"),pt);
                    ds.send(dpt);
                    
                } //Request1
                
                if(req[0].equals("Request2"))
                {
                    DefaultTableModel dm1=(DefaultTableModel)sf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add(req[2]);
                    dm1.addRow(v);
                    
                    DefaultTableModel dm2=(DefaultTableModel)sf.jTable2.getModel();
                    Vector v2=new Vector();
                    v2.add(req[1]);
                    v2.add(req[3]);
                    dm2.addRow(v2);
                    JOptionPane.showMessageDialog(new JFrame(),"Request Received \nResource not allocated to "+sid+" \nProcess it on Server "+req[3]);
                    
                    int pt=Integer.parseInt(req[1])+7000;
                    String ms="Response2#"+req[1]+"#"+sid+"#"+req[3];
                    byte be[]=ms.getBytes();
                    DatagramPacket dpt=new DatagramPacket(be,0,be.length,InetAddress.getByName("127.0.0.1"),pt);
                    ds.send(dpt);
                }
             }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
