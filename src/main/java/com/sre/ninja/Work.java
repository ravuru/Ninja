package com.sre.ninja;


public interface Work {
	public void before();
	
	public Result perform();
	
	public void after(Result result);
}
