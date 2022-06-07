package client;

import common.commands.AbsCommand;
import common.utils.*;

import java.io.IOException;
import java.net.*;

public class ClientNetworkUdp {
    DatagramSocket socket;
    Auth auth;


    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public ClientNetworkUdp(DatagramSocket datagramSocket){
        this.socket=datagramSocket;
    }
    public void sendCommand(AbsCommand command) throws IOException {
        Request request = new Request(command,auth);
        byte[] serilzedObj= SerializerDeserializer.serilizeobj(request);
        InetAddress client = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(serilzedObj, serilzedObj.length, client, 1507);
        socket.send(packet);
        System.out.println("sent to server");
    }
    public  Object reciveObj() throws IOException {
        try {
            byte[] data = new byte[65535];
            DatagramPacket packet = new DatagramPacket(data, data.length );
            socket.receive(packet);
            System.out.println("recived from server");
            return SerializerDeserializer.deserilizObj(data);
        } catch(SocketTimeoutException e) {
            System.out.println("the server is unavilable right now try again later");
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("class not found");
        }
        return null;
    }

}
