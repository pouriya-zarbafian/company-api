# Company API

Sample project using Spring MVC and PostgreSQL to provide an API for managing a list of companies and their beneficial owners.

## Pre-requisite

You need to have at least Java 8 installed [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

You also need a PostgreSQL database with at least version 9.4 [PostgreSQL](https://www.postgresql.org/download/). Once installed, login as root and then as postgre and create a company user:

`$ createuser --interactive`

Then create a company database:

`$ createdb -O company company`

Set the password for the company user:

`$ psql`

`postgres=# ALTER USER company WITH PASSWORD 'YourPassword';`

You will have to use the same password for the datasource in `application.properties`.

## Building a JAR with all dependencies

To build the project locally run `mvn clean package` from the project directory. The resulting JAR will be located in the `target/` folder.

## Running the project

To launch the server execute `java -jar target/company-api-1.0.jar` from the project directory. The server is accessible at URL `http://localhost:8080/`.

## Querying the API

To list all companies:

`curl http://localhost:8080/api/companies`

To search for a specific company with id 1:

`curl http://localhost:8080/api/companies/1`

To list all the available owners:

`curl http://localhost:8080/api/owners`

To create a new company:

`curl -X POST -H 'Content-Type: application/json' -d '{"name":"Acme Company","address":"3 Place de Nancy","city":"Metz","country":"France","email":"info@acme.com","phone":"+33 1234 5678","owners":[{"id":4}]}' http://localhost:8080/api/companies`

To update an existing company with id 1:

`curl -X PUT -H 'Content-Type: application/json' -d '{"name":"Foo and Bar Company","address":"3 Place de Nancy","city":"Metz","country":"France","email":"info@acme.com","phone":"+33 1234 5678","owners":[{"id":4, "name": "Sophie"}]}' http://localhost:8080/api/companies/1`

To delete a company with id 2:

`curl -X DELETE http://localhost:8080/api/companies/2`

To add a beneficial owner with id 4 to the company with id 2:

`curl -X POST -H 'Content-Type: application/json' -d '{"id":4}' http://localhost:8080/api/companies/2/owners`

# Further improvements

## Security

The API should be secured using authentication to prevent unauthorized access. On successful login, a session ID would be generated by the server and set as a cookie. The cookie should be HTTP only so that it cannot be accessed by the web client, thus preventing client side scripting.

This can be done by adding Spring authentication and configuring the appropriate authentication provider (database, LDAP,...).

## High availability

To achieve high-availability, both the database and the web server should be reduntant. Database redundancy can be achieved with a cluster of replicated and in-sync databases (e.g. HA-JDBC or Cassandra). Web server redundancy can be achieved by replicating the web server nodes. This would require a front load balancer to dispatch requests to the live nodes. To use this configuration with caching the server nodes must be configured to use a distributed cache such as Ehcache or Hazelcast.

A single of point of failure remains in the load balancer. A floating IP such as those provided by DigitalOcean could then by assigned dynamically to one of the load balancers. This would improve further the availability of the system.
