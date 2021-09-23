package net.thetechstack.batchprocessor;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.batch.core.Job;

@SpringBootApplication
public class BatchProcessorApplication implements CommandLineRunner{

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Pass the required Job Parameters from here to read it anywhere within
		// Spring Batch infrastructure
		JobParameters jobParameters = new JobParametersBuilder().addString("input-file", "C:/Users/PRADEEP/OneDrive/code/books_test.csv").toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);
		System.out.println("STATUS :: " + execution.getStatus());
	}

}
