package server;
import control.Storage;
import common.collection.*;
import database.*;
import database.UserHandler;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.DatagramChannel;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;


public class Server {
    private final static Logger lOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Storage storage;
    ServerNetworkUdp2 networkudp;
    DatagramChannel channel;
    private static final ExecutorService cachedThreadPool=Executors.newCachedThreadPool();
    private static final ForkJoinPool forkJoinPool=new ForkJoinPool();


    public Server(DatagramChannel datagramChannel) {
        this.channel=datagramChannel;
        this.networkudp = new ServerNetworkUdp2(this.channel);

    }

    public void start() throws Exception {
        System.out.println("started");

        Connection connection = Connector.connectToDp();

        UserHandler userHandler =new UserHandler(connection);


        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        Stack<Vehicle> stack=new Stack<Vehicle>();
        storage =new Storage(stack,userHandler);
        Invoker invoker =new Invoker(userHandler,storage);
        App app = new App(lOGGER,cachedThreadPool,forkJoinPool,networkudp,br,storage,userHandler,invoker);
        app.start();


    }

}



