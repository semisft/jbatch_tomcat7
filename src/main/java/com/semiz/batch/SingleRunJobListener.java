package com.semiz.batch;

import java.util.List;
import java.util.stream.Collectors;

import javax.batch.api.listener.JobListener;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

public class SingleRunJobListener implements JobListener {

	@Inject
	JobContext jobContext;
	
	@Override
	public void beforeJob() throws Exception {
		List<Long> running = BatchRuntime.getJobOperator().getRunningExecutions(jobContext.getJobName());
		if (running.size()>1) {
			throw new Exception("Job is already running with id:"+running.stream().map(Object::toString)
                    .collect(Collectors.joining(", ")));
		}
	}

	@Override
	public void afterJob() throws Exception {
		
	}

}
