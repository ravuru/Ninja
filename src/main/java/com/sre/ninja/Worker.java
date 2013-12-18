package com.sre.ninja;

import scala.collection.mutable.ArraySeq;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof Work) {
        	Work work = (Work) message;
        	work.before();
            Result result = work.perform();
            work.after(result);
            getSender().tell(result, getSelf());
        } else{
        	unhandled(message);
        }
    }

    public static Props createWorker() {
        return Props.create(Worker.class, new ArraySeq<Object>(0));
    }
}
