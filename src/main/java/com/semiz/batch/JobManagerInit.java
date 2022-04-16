package com.semiz.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ibm.jbatch.spi.BatchSPIManager;
import com.ibm.jbatch.spi.BatchSPIManager.PlatformMode;
import com.ibm.jbatch.spi.ExecutorServiceProvider;

public class JobManagerInit {

	public static void configure() {
		BatchSPIManager.getInstance().registerPlatformMode(PlatformMode.SE);
		BatchSPIManager.getInstance().registerExecutorServiceProvider(new ExecutorServiceProvider() {

			public ExecutorService getExecutorService() {
				return Executors.newCachedThreadPool();
			}
		});
	}
}
