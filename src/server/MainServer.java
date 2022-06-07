package server;

import java.net.*;
import java.nio.channels.DatagramChannel;


public class MainServer {
    public static void main(String[] args) throws Exception {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        try {
            InetSocketAddress socket=new InetSocketAddress(1507);
            datagramChannel.socket().bind(socket);
            System.out.println("finished successfully");
        }catch (SocketException e){
            System.out.println("error in bind:"+e.getClass().getName());
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
        }
        Server server = new Server(datagramChannel);
        server.start();
    }

}
