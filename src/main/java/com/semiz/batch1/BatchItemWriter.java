package com.semiz.batch1;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.ItemWriter;

import com.semiz.entity.DetailRecord;
import com.semiz.utils.Utils;

public class BatchItemWriter implements ItemWriter {
	
	private final static Logger logger = Logger.getLogger(BatchItemWriter.class.getName());

	Connection conn;
	Integer count = 0;
	
	@Override
	public void open(Serializable checkpoint) throws Exception {
		if (conn == null) {
			conn = Utils.getConnection("sourcedb");
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
		try(PreparedStatement ps = conn.prepareStatement("INSERT INTO detail(id,value) values (?,?)")) {
			items.forEach(obj -> {
				DetailRecord item = (DetailRecord) obj;
				try {
					ps.setInt(1, item.getId());
					ps.setInt(2, item.getValue());
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
