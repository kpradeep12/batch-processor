package net.thetechstack.batchprocessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchProcessorApplication implements CommandLineRunner{

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job flatFileJob;

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder()
		.addString("input-file", "C:/Users/PRADEEP/OneDrive/code/books_test.csv").toJobParameters();

		jobLauncher.run(flatFileJob, jobParameters);

	}

}
