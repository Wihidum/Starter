package event_divider;


import event_publisher.EventPublisher;
import node_manger.Node;
import node_manger.NodeProvider;
import org.wso2.carbon.databridge.commons.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDivider implements Divider,Runnable{

      private static final List<Node> nodelist  = NodeProvider.getNodeList();
      private static int eventCount=0;
      private int nodeCount=0;
      private static List<Event> eventList = new ArrayList<Event>();


    @Override
    public synchronized void  divide(Event event) {
          eventCount++;
          eventList.add(event);
        if(eventCount>0 && eventCount >=10000){
            EventPublisher.publishEvents(nodelist.get(nodeCount).getHostname(),nodelist.get(nodeCount).getPort(),eventList);
            nodeCount++;
            eventList.clear();
            eventCount=0;
        }
        if(nodeCount== nodelist.size()){
            nodeCount=0;

        }
        }

    @Override
    public void run() {
      synchronized (eventList){
        while(true){
//        if(eventCount>0 && eventCount %1000==0){
//            EventPublisher.publishEvents(nodelist.get(nodeCount).getHostname(),nodelist.get(nodeCount).getPort(),eventList);
//            nodeCount++;
//            eventList.clear();
//        }
//        if(nodeCount== nodelist.size()){
//            nodeCount=1;
//        }


        }
      }

    }
}
