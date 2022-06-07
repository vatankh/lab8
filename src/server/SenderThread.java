package server;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.net.SocketAddress;

@AllArgsConstructor
public class SenderThread extends Thread {
    SocketAddress client;
    ServerNetworkUdp2 networkUdp;
    Object message;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("now will send to "+client);
        try {
            networkUdp.sendObj(message,client);
            System.out.println("sent successfully");
        }catch (Exception e){
            System.out.println("erorr");
            System.out.println(e.getMessage());
        }
    }
}
