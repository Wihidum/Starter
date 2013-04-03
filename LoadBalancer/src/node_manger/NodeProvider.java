package node_manger;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NodeProvider {

     private static List<Node> nodeList = new ArrayList<Node>();





     public static List<Node> getNodeList(){

         try{

             FileInputStream fstream = new FileInputStream("/home/isuru/Documents/project/LoadBalancer/src/utils/OperatorNodes.txt");
             // Get the object of DataInputStream
             DataInputStream in = new DataInputStream(fstream);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
             String strLine;
             //Read File Line By Line
             while ((strLine = br.readLine()) != null)   {
                 String[] array =strLine.split("\\|");
                 Node node = new Node(array[0],array[1]);
                 nodeList.add(node);
             }

             in.close();
         }catch (Exception e){//Catch exception if any
             System.err.println("Error: " + e.getMessage());
         }












    return  nodeList;


}
}
