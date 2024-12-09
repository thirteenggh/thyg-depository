package org.sonatype.nexus.quartz.orient;

import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class SimpleJob
    implements Job
{
  private static final Logger log = LoggerFactory.getLogger(SimpleJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("..... Simple Job .....");
    for (Entry<String, Object> entry : context.getJobDetail().getJobDataMap().entrySet()) {
      log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
    }
  }
}
