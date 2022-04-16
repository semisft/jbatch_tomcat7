package com.semiz.batch;

import javax.batch.api.AbstractBatchlet;

public class HelloBatch extends AbstractBatchlet {

	@Override
	public String process() throws Exception {
		System.out.println("hello world");
		return null;
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println("stop requested");
	}

}