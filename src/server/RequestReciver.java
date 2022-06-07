package server;

import common.utils.*;
import control.Storage;
import common.commands.*;
import database.UserHandler;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class RequestReciver {
    String message;
    Invoker invoker;
    ForkJoinPool forkJoinPool;
    RequestHandler requestHandler;
    Logger logger;
    private   final Queue<Pair<Request,SocketAddress>> queue;

    public RequestReciver( Storage storage, ServerNetworkUdp2 networkudp, UserHandler userHandler, Invoker invoker, ForkJoinPool forkJoinPool, Logger logger,  Queue<Pair<Request,SocketAddress>> queue) {
        this.message = "";
        this.storage = storage;
        this.networkudp = networkudp;
        this.userHandler = userHandler;
        this.invoker=invoker;
        this.forkJoinPool=forkJoinPool;
        this.requestHandler=new RequestHandler(invoker,forkJoinPool);
        this.logger=logger;
        this.queue=queue;
    }

    Storage storage;
    ServerNetworkUdp2 networkudp;
    UserHandler userHandler;
    public void handle(BufferedReader br,ExecutorService threadPool) throws Exception {
        Runnable checkForCommands =new Runnable() {
            @Override
            public  void run() {
                    while (true) {
                        try {
                            if (isSave(br)) {
                                Exit save = new Exit();
                                save.loadStorage(storage);
                                message = save.work();
                            } else if (!queue.isEmpty()){
                                    requestHandler.process(queue.poll(), networkudp);
                            }
                        }catch (Exception exception){
                            System.out.println("erorr in handle ");
                            exception.printStackTrace();
                        }
                    }
            }
        };
        threadPool.submit(checkForCommands);
        }



    boolean isSave(BufferedReader br) throws IOException {
        return  br.ready() && br.readLine().equals("save");
    }






}
