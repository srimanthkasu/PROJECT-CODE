/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author seabirds
 */
public class ServerReceiver extends Thread
{
    MainServerFrame sf;
    Details dt=new Details();
    ServerReceiver(MainServerFrame se)
    {
        sf=se;
    }
    public void run()
    {
        try
        {
             DatagramSocket ds=new DatagramSocket(9000);
            while(true)
            {
                byte data[]=new byte[60000];
                DatagramPacket dp=new DatagramPacket(data,0,data.length);
                ds.receive(dp);
                
                String str=new String(dp.getData()).trim();
                String req[]=str.split("#");
                
                 if(req[0].equals("ServerDt"))
                {
                    dt.server[dt.sind][0]=req[1]; // id
                    dt.server[dt.sind][1]=req[2]; // s rate
                    dt.server[dt.sind][2]=req[3]; // a rate
                    dt.sind++;
                    
                    DefaultTableModel dm=(DefaultTableModel)sf.jTable1.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add(req[2]);
                    v.add(req[3]);
                    v.add("Surrogate");
                    dm.addRow(v);
                    
                } //ServerDt
                
                 if(req[0].equals("RedirectDt"))
                 {
                     dt.redirect="Yes";
                 } // 
                if(req[0].equals("ClientDt"))
                {
                    dt.user[dt.uind][0]=req[1]; // id
                    dt.user[dt.uind][1]=req[2]; // ip
                    dt.user[dt.uind][2]=req[3]; //pt
                    dt.user[dt.uind][3]="-"; // sid
                    dt.uind++;
                    
                    DefaultTableModel dm=(DefaultTableModel)sf.jTable2.getModel();
                    Vector v=new Vector();
                    v.add(req[1]);
                    v.add("-");
                    dm.addRow(v);
                    
                    String ms="ServerId";
                    for(int i=0;i<dt.sind;i++)
                    {
                        ms=ms+"#"+dt.server[i][0];
                    }
                    byte bt[]=ms.getBytes();
                    DatagramPacket dp1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName(req[2]),Integer.parseInt(req[3]));
                    ds.send(dp1);
                    
                    
                } // ClientDt
                if(req[0].equals("SetServerId"))
                {
                    String id1=req[1];
                    String id2=req[2];
                    DefaultTableModel dm=(DefaultTableModel)sf.jTable2.getModel();
                    int row=dm.getRowCount();
                    for(int i=0;i<row;i++)
                    {
                        String g1=dm.getValueAt(i, 0).toString();
                        if(id1.equals(g1))
                        {
                            dm.setValueAt(id2, i, 1);
                            dt.user[i][3]=id2;
                        }
                    }
                    
                    String ms="SetId#"+id1;
                    byte bt[]=ms.getBytes();
                    int pt=Integer.parseInt(id2)+8000;
                    DatagramPacket dpt1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),pt);
                    ds.send(dpt1);
                    
                } // SetServerId
                
                if(req[0].equals("CtRequest"))
                {
                    Random rn=new Random();
                    boolean b1=rn.nextBoolean();
                    System.out.println(b1);
                    if(b1) // send to own id
                    {
                        DefaultTableModel dm=(DefaultTableModel)sf.jTable3.getModel();
                        Vector v=new Vector();
                        v.add(req[1]);
                        v.add(req[2]);
                        v.add("-");
                        dm.addRow(v);
                        
                        String ms="Request1#"+req[1]+"#"+req[3];
                        byte bt[]=ms.getBytes();
                        int pt=Integer.parseInt(req[2])+8000;
                        DatagramPacket dpt1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),pt);
                        ds.send(dpt1);
                    }
                    else  // select other
                    {
                         if(dt.redirect.equals("Yes"))
                         {
                            ArrayList at=new ArrayList();
                            at.add(req[2]);
                            boolean b2=true;
                            String sid="";
                            while(b2)
                            {
                                Random rm=new Random();
                                int k=rm.nextInt(dt.sind);
                                String g1=dt.server[k][0];
                                if(!(at.contains(g1)))
                                {
                                    sid=g1;
                                    b2=false;
                                }
                            }
                            System.out.println("server id "+sid);
                         
                            DefaultTableModel dm=(DefaultTableModel)sf.jTable3.getModel();
                            Vector v=new Vector();
                            v.add(req[1]);
                            v.add(req[2]);
                            v.add(sid);
                            dm.addRow(v);
                         
                             String ms="Request2#"+req[1]+"#"+req[3]+"#"+sid;
                             byte bt[]=ms.getBytes();
                             int pt=Integer.parseInt(req[2])+8000;
                             DatagramPacket dpt1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),pt);
                             ds.send(dpt1);
                             
                             
                             String ms1="Redirect#"+req[1]+"#"+req[2]+"#"+sid;
                             byte bt1[]=ms1.getBytes();                             
                             DatagramPacket dpt2=new DatagramPacket(bt1,0,bt1.length,InetAddress.getByName("127.0.0.1"),6000);
                             ds.send(dpt2);
                             
                         }
                         else
                         {
                              DefaultTableModel dm=(DefaultTableModel)sf.jTable3.getModel();
                              Vector v=new Vector();
                              v.add(req[1]);
                              v.add(req[2]);
                              v.add("-");
                              dm.addRow(v);
                        
                            String ms="Request1#"+req[1]+"#"+req[3];
                            byte bt[]=ms.getBytes();
                            int pt=Integer.parseInt(req[2])+8000;
                            DatagramPacket dpt1=new DatagramPacket(bt,0,bt.length,InetAddress.getByName("127.0.0.1"),pt);
                            ds.send(dpt1);
                         }
                    }
                } //CtRequest
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
