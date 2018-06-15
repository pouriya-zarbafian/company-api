# Company API

Sample project using Spring MVC and PostgreSQL to provide an API for managing a list of companies and their beneficial owners.

## Building a JAR with all dependencies

To build the project locally run `mvn clean package` from the project directory. The resulting JAR will be located in the `target/` folder.

## Running the project

To launch the server execute `java -jar target/company-api-1.0.jar` from the project directory. The server is accessible at URL `http://localhost:8080/`.

## Querying the API

To list all companies:

`curl http://localhost:8080/api/companies`

To search for a specific company with id 1:

`curl http://localhost:8080/api/companies/1`

To create a new company:

`curl -X POST -H 'Content-Type: application/json' -d '{"name":"Acme Company","address":"3 Place de Nancy","city":"Metz","country":"France","email":"info@acme.com","phone":"+33 1234 5678","owners":[{"id":4}]}' http://localhost:8080/api/companies`

To update an existing company with id 1:

`curl -X PUT -H 'Content-Type: application/json' -d '{"name":"Foo and Bar Company","address":"3 Place de Nancy","city":"Metz","country":"France","email":"info@acme.com","phone":"+33 1234 5678","owners":[{"id":4, "name": "Sophie"}]}' http://localhost:8080/api/companies/1`

To delete a company with id 2:

`curl -X DELETE http://localhost:8080/api/companies/2`

To add a beneficial owner with id 4 to the company with id 2:

`curl -X POST -H 'Content-Type: application/json' -d '{"id":4}' http://localhost:8080/api/companies/2/owners`

