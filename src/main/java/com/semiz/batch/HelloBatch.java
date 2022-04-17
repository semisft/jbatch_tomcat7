package com.semiz.batch;

import java.util.Properties;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

public class HelloBatch extends AbstractBatchlet {
	
	private boolean stopRequested = false;

	@Inject
	JobContext jobContext;

	@Override
	public String process() throws Exception {
		
		Properties parameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
		System.out.println(parameters);
		
		System.out.println("hello world");
		for (int i=0;i<5;i++) {
			System.out.println("Step "+i+" is being processed.");
			Thread.sleep(5000);
			if (stopRequested) {
				System.out.println("stopped");
				return BatchStatus.STOPPED.toString();
			}
		}
		return BatchStatus.COMPLETED.toString();
	}
	
	@Override
	public void stop() throws Exception {
		stopRequested = true;
		System.out.println("stop requested");
	}

}