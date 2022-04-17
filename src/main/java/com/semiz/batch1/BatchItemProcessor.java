package com.semiz.batch1;

import javax.batch.api.chunk.ItemProcessor;

import com.semiz.entity.DetailRecord;

public class BatchItemProcessor implements ItemProcessor {

	@Override
	public Object processItem(Object obj) throws Exception {
		DetailRecord item = (DetailRecord) obj;
		item.setId((int) (1+Math.random()*3));
		item.setValue((int) (1+Math.random()*10));
		return item;
	}

	
}
