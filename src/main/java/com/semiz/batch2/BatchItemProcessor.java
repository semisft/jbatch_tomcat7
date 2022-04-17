package com.semiz.batch2;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

import com.semiz.entity.DetailRecord;
import com.semiz.entity.SummaryRecord;

/**
 * Aggregate sum using id as key
 * @author semimac
 *
 */
public class BatchItemProcessor implements ItemProcessor {
	@Inject
	JobContext jobContext;

	@Override
	public Object processItem(Object obj) throws Exception {
		DetailRecord item = (DetailRecord) obj;
		
		SummaryRecord record = (SummaryRecord) jobContext.getTransientUserData();
		record.setId(item.getId());
		record.setTotal(record.getTotal()+item.getValue());
		if (item.getNext() == null || ! (item.getNext().getId() == item.getId())) {
			jobContext.setTransientUserData(new SummaryRecord());
			return record;
		}
		else {
			//skip for write
			return null;
		}
	}

	
}
