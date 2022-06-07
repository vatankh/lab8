package server;

import common.utils.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class ServerNetworkUdp2  {
    DatagramChannel channel;
     SocketAddress client;
    public  ServerNetworkUdp2(DatagramChannel channel){
        this.channel=channel;
    }
    public  Object receiveObj()  throws  ClassNotFoundException  {
         ByteBuffer buffer = ByteBuffer.allocate(4096);
         long startTime = System.currentTimeMillis();
                try {
                    while (System.currentTimeMillis() -startTime <50){
                        client = channel.receive(buffer);
                        if (buffer.position() != 0){
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            return SerializerDeserializer.deserilizObj(bytes);
                        }
                    }

                }catch (IOException io){
                    System.out.println("erorr in server network recive");
                    io.printStackTrace();
                }

        return null;

    }
    public void sendObj(Object obj,SocketAddress client) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(SerializerDeserializer.serilizeobj(obj));
        System.out.println("client="+client);
        channel.send(buffer, client);
        System.out.println("sent something to client ="+obj.toString());
    }

}
