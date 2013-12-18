Ninja
=====

Ninja is an akka based simple parallel task execution framework that could be embedded as part of any application. Ninja offers a better parallel task execution mechanism than java multithreading.


1.	Create a Java project
2.	Add the ninja jar file to the application class path
3.	Create a work object that implements Ninja Work interface. This is the work that you want ninja to perform
Example: This is a work that calculates the 1000 factorial
`````java
public class FactorialWork implements Work{
		/*
	 	* What needs to be done before doing the actual work, like setting up the
	 	* environment etc...
	 	* 
	 	* @see com.sre.applepie.Work#before()
	 	*/
		public void before(){
		
		}
	
		/*
	 	* The actual work that you want Apple Pie to perform. Returns a Result
	 	* object
		 * 
	 	* @see com.sre.applepie.Work#perform()
	 	*/
		public Result perform(){
			BigInteger result = BigInteger.valueOf(1);
        	for (int i = 1; i <= 1000; i++)
        		result = result.multiply(BigInteger.valueOf(i));
        	return new Result(result);
		}
	
		/*
		 * Post actions that you want Apple Pie to do like sending emails, updatinng db etc...
	 	* @see com.sre.applepie.Work#after()
	 	*/
		public void after(Result result){
		
		}
}
`````

4.	Create a class that submits the workload (List of work objects)
Example: 
`````java
ArrayList<FactorialWork> workload = new ArrayList<FactorialWork>();
    	for(int i=0; i<750000 ; i++){
    		workload.add(new FactorialWork());
    	}

	Ninja ninja = new Ninja();
ArrayList<Result> results = ninja.run(workload);
`````
5.	The ArrayList<Result> is the result of execution of the workload.

