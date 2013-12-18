package com.sre.ninja;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class Ninja {
	
	private final Time time = new Time();

    public ArrayList<Result> run(ArrayList<? extends Work> workload) {
    	ArrayList<Result> result = new ArrayList<Result>();
    	time.start();
        try {
        	ActorSystem system = ActorSystem.create("ApplePie");
            ActorRef master = system.actorOf(Master.createMaster(), "master");
        	//Timeout timeout = new Timeout(Duration.create(500, SECONDS));
        	Future<Object> future = Patterns.ask(master, workload, new Timeout(Duration.create(500, SECONDS)));

			result = (ArrayList<Result>) Await.result(future, Duration.create(500, SECONDS));
			time.end();
		    System.out.println("Done: " + time.elapsedTimeSeconds());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }
    
    public static void main(String[] args){
    	System.out.println("here!");
    }
    
}
