package net.thetechstack.batchprocessor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import net.thetechstack.batchprocessor.book.BatchConfiguration;

@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BatchConfiguration.class)
class BatchProcessorApplicationTests {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    
    /*@Test
    public void testJob() throws Exception {
        
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();


        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }*/

}
