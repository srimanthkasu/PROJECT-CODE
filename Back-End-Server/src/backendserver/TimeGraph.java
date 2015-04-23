/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author seabirds
 */
public class TimeGraph 
{
     Details dt=new Details();
    public void displayGraph1()
    {
        try
        {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
  
            
            for(int i=0;i<dt.sind;i++)
            {
                double sr=Double.parseDouble(dt.server[i][1]);
                double ar=Double.parseDouble(dt.server[i][2]);
                
                double ro=ar/sr;
                double qu=ro/(sr-ar);
                //ro/(beta-alpha)
                
                dataset.setValue(qu, "Time", "Server"+dt.server[i][0]);
            }
           // dataset.setValue(dt.ubind, "Initial", "BS");
            //dataset.setValue(dt.ubsList.size(), "Convergence", "BS");
            
            
            
  
           
            JFreeChart chart = ChartFactory.createBarChart
                ("Waiting Time ","No of Server", "Value", dataset, 
   PlotOrientation.VERTICAL, true,true, false);
  //chart.setBackgroundPaint(Color.yellow);
  chart.getTitle().setPaint(Color.blue); 
  
  CategoryPlot p = chart.getCategoryPlot(); 
  
  //p.setRangeGridlinePaint(Color.red); 
  System.out.println("Range : "+p.getRangeAxisCount() );
  
  
  CategoryItemRenderer renderer = p.getRenderer();

      //renderer.setSeriesPaint(0, Color.red);

	  renderer.setSeriesPaint(0, Color.CYAN);
	  renderer.setSeriesPaint(1, Color.ORANGE);
	  
     // renderer.setSeriesPaint(1, Color.green);
	 // renderer.setSeriesPaint(2, Color.blue);
  
  //p.getRangeAxis() 
  ChartFrame frame1=new ChartFrame("Queue",chart);
  
  frame1.setSize(400,300);
  frame1.setLocation(100, 100);
  //frame1.setUndecorated(true);  
  frame1.setVisible(true);
  final BufferedImage image = new BufferedImage(frame1.getWidth(), frame1.getHeight(), BufferedImage.TYPE_INT_ARGB);
Graphics gr = image.getGraphics();
frame1.printAll(gr);
gr.dispose();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
