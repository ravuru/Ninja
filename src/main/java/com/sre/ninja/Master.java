package com.sre.ninja;

import java.util.ArrayList;

import scala.collection.mutable.ArraySeq;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class Master extends UntypedActor {

	private long messages = -1;
    private ActorRef workerRouter;
    private ActorRef sender;
    
    private ArrayList<Result> list = new ArrayList<Result>();

    public Master() {
        workerRouter = this.getContext().actorOf(Worker.createWorker().withRouter(new RoundRobinRouter(20)), "workerRouter");
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof ArrayList) {
        	sender = getSender();
        	messages = ((ArrayList<Work>) message).size();
            processWorkload((ArrayList<Work>) message);
        } else if (message instanceof Result) {
            list.add((Result) message);
        } else {
            unhandled(message);
        }
        if (list.size() == messages){
        	sender.tell(list, getSelf());
            end();
        }
    }

    private void processWorkload(ArrayList<Work> workload) {
        for (Work work : workload) {
            workerRouter.tell(work, getSelf());
        }
    }

    private void end() { 
        getContext().system().shutdown();
    }

    public static Props createMaster() {
        return Props.create(Master.class, new ArraySeq<Object>(0));
    }
}
