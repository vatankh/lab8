package server;

import common.utils.*;

import java.net.SocketAddress;
import java.util.Queue;


public class  ReciverFromClient {
    private  final Queue<Pair<Request,SocketAddress>> queue;

    public ReciverFromClient(Queue<Pair<Request, SocketAddress>> queue) {
        this.queue = queue;
    }

    public void startReciving(ServerNetworkUdp2 serverNetworkUdp2) throws ClassNotFoundException {
        while (true){
            Object object = serverNetworkUdp2.receiveObj();
            if (object instanceof  Request){
                Request request = (Request) object;
                System.out.println("received request");
                try {
                    queue.add(new Pair<>(request,serverNetworkUdp2.client));
                } catch (Exception e){
                    System.out.println("ex in start recive");
                    return;
                }
             }
        }


 }
}
