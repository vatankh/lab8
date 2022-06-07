package server;


import common.commands.*;
import common.utils.*;
import control.Storage;
import database.UserHandler;

import java.net.SocketAddress;

public class Invoker {
    Answer answer;
    UserHandler userHandler;
    Storage storage;
    public Invoker(UserHandler userHandler, Storage storage){
        this.userHandler=userHandler;
        this.storage=storage;
    }


    public String execute(Pair<Request, SocketAddress> pair, ServerNetworkUdp2 networkUdp2) throws Exception {
        Request request = pair.getFirst();
        SocketAddress theClient= pair.getSecond();
        networkUdp2.client=theClient;
        AbsCommand command =  request.getCommand();
        command.loadStorage(storage);
        command.loadAuth(request.getAuth());
        answer = (Answer) command.work();
        new SenderThread(theClient,networkUdp2,answer).start();
        return answer.getMessage() ==null?"": answer.getMessage();
        }
    }
