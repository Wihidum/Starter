package node_manger;

public class Node {

    private  String hostname;
    private  String port;

    public Node(String hostName, String port){
        this.hostname =hostName;
        this.port =port;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort(){
      return port;
    }
}
