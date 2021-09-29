# Generic Batch Processor

Reads any tokenized flat file(csv, pipe) and store them in the database.

![Batch processor flow](https://github.com/kpradeep12/batch-processor/blob/main/ref/batch-processor-flow.jpg?raw=true)

## Available commands

In the project directory, you can run:

### `.\mvnw clean package`

Builds the app for production.

### `.\mvnw spring-boot:run`

Runs the application

## Built with

- Java
- Spring Boot
- Spring Batch
- MySql

## Future updates

- Option in Job parameter's to auto create table in MySql before Job starts
- Option in Job parameter's to delete existing data before inserting new
- Initiate jobs from REST API
- Create UI with ReactJS to show Job status and initiate new Job

Give a ⭐️ if you like this project!