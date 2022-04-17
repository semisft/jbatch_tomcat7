package com.semiz.batch2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.ItemWriter;

import com.semiz.entity.SummaryRecord;
import com.semiz.utils.Utils;

public class BatchItemWriter implements ItemWriter {
	
	private final static Logger logger = Logger.getLogger(BatchItemWriter.class.getName());

	Connection conn;
	Integer count = 0;
	
	@Override
	public void open(Serializable checkpoint) throws Exception {
		if (conn == null) {
			conn = Utils.getConnection("destinationdb");
			Utils.createDestinationTable("destinationdb");
			logger.info("Got new connection");
		}
	}

	@Override
	public void close() throws Exception {
		conn.close();
		logger.info("Connection closed");
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		try(PreparedStatement ps = conn.prepareStatement("INSERT INTO summary(id,total) values (?,?)")) {
			items.forEach(obj -> {
				SummaryRecord item = (SummaryRecord) obj;
				logger.info(item.toString());
				try {
					ps.setInt(1, item.getId());
					ps.setLong(2, item.getTotal());
					ps.addBatch();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
				
			});
			ps.executeBatch();	
		}
		count += items.size();
		logger.info(count + " items processed");
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return count;
	}

}
