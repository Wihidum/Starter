package event_publisher;
import org.apache.log4j.Logger;

import org.wso2.carbon.databridge.agent.thrift.Agent;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.conf.AgentConfiguration;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.exception.*;
import utils.KeyStoreUtil;

import java.net.MalformedURLException;
import java.util.List;



public class EventPublisher {
    private static Logger logger = Logger.getLogger(EventPublisher.class);
    private static  String STREAM_NAME="org.wso2.phone.retail.store" ;
    private static  String VERSION= "1.2.0";







    public static void publishEvents(String host,String port,List<Event> eventList)
    {
        KeyStoreUtil.setTrustStoreParams();
       // System.out.println("Starting Sending Events to host ="+ host +"port =" +port);
        AgentConfiguration agentConfiguration = new AgentConfiguration();
        Agent    agent = new Agent(agentConfiguration);
        DataPublisher dataPublisher = null;
        try {
            dataPublisher = new DataPublisher("tcp://"+host+":"+port, "admin", "admin", agent);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            dataPublisher.stop();
        } catch (AgentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            dataPublisher.stop();
        } catch (org.wso2.carbon.databridge.commons.exception.AuthenticationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            dataPublisher.stop();
        } catch (TransportException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            dataPublisher.stop();
        }
        String streamId= null;
        try {
            streamId = dataPublisher.findStream(STREAM_NAME,VERSION);
            //System.out.println("Stream already defined");

        } catch (NoStreamDefinitionExistException e) {
            try {
                streamId = dataPublisher.defineStream("{" +
                        "  'name':'" + STREAM_NAME + "'," +
                        "  'version':'" + VERSION + "'," +
                        "  'nickName': 'Phone_Retail_Shop'," +
                        "  'description': 'Phone Sales'," +
                        "  'metaData':[" +
                        "          {'name':'clientType','type':'STRING'}" +
                        "  ]," +
                        "  'payloadData':[" +
                        "          {'name':'brand','type':'STRING'}," +
                        "          {'name':'quantity','type':'INT'}," +
                        "          {'name':'total','type':'INT'}," +
                        "          {'name':'buyer','type':'STRING'}" +
                        "  ]" +
                        "}");
            } catch (AgentException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                dataPublisher.stop();
            } catch (MalformedStreamDefinitionException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                dataPublisher.stop();
            } catch (StreamDefinitionException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                dataPublisher.stop();
            } catch (DifferentStreamDefinitionAlreadyDefinedException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                dataPublisher.stop();
            }
//            //Define event stream
        } catch (StreamDefinitionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            dataPublisher.stop();
        } catch (AgentException e) {
            e.printStackTrace();
            dataPublisher.stop();//To change body of catch statement use File | Settings | File Templates.
        }
        for(Event event:eventList)  {
            try {
                dataPublisher.publish(event);
               // logger.info("publishEvent");
            } catch (AgentException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                dataPublisher.stop();
            }
        }
        dataPublisher.stop();
    }



 public void setSTREAM_ID(String id){

    STREAM_NAME = id;
 }
  public String getSTREAM_ID(){

      return STREAM_NAME;
  }

  public void setVERSION(String version){
        VERSION = version;
  }
  public String getVERSION(){
     return VERSION;
  }

}
