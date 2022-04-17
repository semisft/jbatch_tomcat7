package com.semiz.batch2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

import com.semiz.entity.DetailRecord;
import com.semiz.entity.SummaryRecord;
import com.semiz.utils.Utils;

/**
 * Generate Random data 
 * @author semimac
 *
 */
public class BatchItemReader implements ItemReader {
	
	private final static Logger logger = Logger.getLogger(BatchItemReader.class.getName());

	@Inject
	JobContext jobContext;

	Integer count = 0;
	Connection conn;
	PreparedStatement ps = null;
	ResultSet rs = null;
	boolean hasRecords;
	DetailRecord prev;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		Properties parameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
		System.out.println(parameters);
		
		conn = Utils.getConnection("sourcedb");
		/*ps = conn.prepareStatement("SELECT id, value FROM detail WHERE id BETWEEN ? AND ? ORDER BY id");
		ps.setInt(1, Integer.parseInt(parameters.get("start").toString()));
		ps.setInt(2, Integer.parseInt(parameters.get("end").toString()));
		*/
		ps = conn.prepareStatement("SELECT id, value FROM detail ORDER BY id");
		rs = ps.executeQuery();
		hasRecords = rs.next();
		prev = new DetailRecord();
		prev.setNext(rsToDetailRec(rs));
		//Variable to use between steps
		jobContext.setTransientUserData(new SummaryRecord());
		
	}

	@Override
	public void close() throws Exception {
		rs.close();
		ps.close();
		conn.close();
	}

	@Override
	public Object readItem() throws Exception {
		if (hasRecords) {
			DetailRecord result = prev.getNext();
			if (rs.next()) {
				result.setNext(rsToDetailRec(rs));
				logger.info(result.toString());
			}
			else if (result != null){
				result.setNext(null);
				logger.info(result.toString());
			}
			count++;
			prev = result;
			return result;
		}
		else {
			return null;
		}
	}

	private DetailRecord rsToDetailRec(ResultSet rs) throws SQLException {
		DetailRecord result = new DetailRecord();
		result.setId(rs.getInt(1));
		result.setValue(rs.getInt(2));
		return result;
	}
	
	@Override
	public Serializable checkpointInfo() throws Exception {
		return count;
	}


}
