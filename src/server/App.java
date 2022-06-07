package server;

import common.utils.*;
import control.Storage;
import database.UserHandler;


import java.io.BufferedReader;
import java.net.SocketAddress;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class App {
    private final Logger logger;
    private final ExecutorService threadPool;
    private final ForkJoinPool forkJoinPool;
    private final ServerNetworkUdp2 networkUdp;
    private final BufferedReader br;
    private final  Queue<Pair<Request,SocketAddress>> queue;
    private  final  ReciverFromClient reciverFromClient;
    private  final  RequestReciver requestReciver;
    Storage storage;
    UserHandler userHandler;
    Invoker invoker;

    public App(Logger logger, ExecutorService threadPool, ForkJoinPool forkJoinPool,  ServerNetworkUdp2 networkUdp,BufferedReader br,Storage storage,UserHandler userHandler,Invoker invoker) {
        this.logger = logger;
        this.threadPool = threadPool;
        this.forkJoinPool = forkJoinPool;
        this.networkUdp = networkUdp;
        this.br=br;
        this.storage=storage;
        this.userHandler=userHandler;
        this.invoker=invoker;
        queue=new LinkedBlockingQueue<>();
        reciverFromClient=new ReciverFromClient(queue);
        requestReciver =new RequestReciver(storage,networkUdp,userHandler,invoker,forkJoinPool,logger,queue);
    }
    public void start() throws Exception {
        threadPool.submit(()->{
            try {
                reciverFromClient.startReciving(networkUdp);
            } catch (ClassNotFoundException e) {
                System.out.println("errr");
                e.printStackTrace();
            }

        });
        requestReciver.handle(br,threadPool);
    }
}
