package com.semiz.batch1;

import java.io.Serializable;
import java.util.Properties;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

import com.semiz.entity.DetailRecord;
import com.semiz.utils.Utils;

/**
 * Generate Random data 
 * @author semimac
 *
 */
public class BatchItemReader implements ItemReader {
	
	@Inject
	JobContext jobContext;

	Integer count = 0;
	int maxCount = 0;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		Utils.createSourceTable("sourcedb");
		Properties parameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
		maxCount = Integer.parseInt(parameters.getProperty("max").toString());
	}

	@Override
	public void close() throws Exception {
		
		
	}

	@Override
	public Object readItem() throws Exception {
		if (count<maxCount) {
			DetailRecord item = new DetailRecord();
			count++;
			return item;
		}
		else {
			//finished
			return null;	
		}
		
	}
	
	@Override
	public Serializable checkpointInfo() throws Exception {
		return count;
	}


}
