//usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17
//JAVAC_OPTIONS -parameters
//JAVA_OPTIONS -Djava.util.logging.manager=org.jboss.logmanager.LogManager
//DEPS io.quarkus.platform:quarkus-bom:2.16.5.Final@pom
//DEPS org.jdbi:jdbi3-bom:3.37.1@pom
//DEPS io.quarkus:quarkus-resteasy-qute
//DEPS io.quarkus:quarkus-agroal
//DEPS org.duckdb:duckdb_jdbc:0.7.1
//DEPS org.jdbi:jdbi3-core
//DEPS org.apache.commons:commons-text:1.10.0
//Q:CONFIG quarkus.datasource.db-kind=other
//Q:CONFIG quarkus.datasource.jdbc.driver=org.duckdb.DuckDBDriver
//Q:CONFIG quarkus.datasource.jdbc.url=jdbc:duckdb:
import java.net.URI;
import java.util.stream.Collectors;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.text.StringEscapeUtils;
import org.jdbi.v3.core.Jdbi;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

/**
 * Party like it's 1999 PHP, but with Quarkus and Java.
 *
 * @author Michael J. Simons
 */
@Path("/")
public final class admin {

	private static final String INDEX_HTML = """
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="UTF-8">
		    <title>Add new book</title>
		</head>
		<body>
		<h1>Add a new book</h1>
		<form method="post">
		    <label for="author">Author</label>&#160;<input type="text" list="authors" id="author" name="author"><br>
		    <datalist id="authors">
		        ${authors}
		    </datalist>
		    <label for="title">Title</label>&#160;<input type="text" id="title" name="title"><br>
		    <label for="type">Type</label>&#160;
		    <select name="type" id="type">
		        <option value="R">Roman / Prose</option>
		        <option value="S">Sachbuch / Non-fiction</option>
		    </select><br>
		    <label for="state">State</label>&#160;
		    <select name="state" id="state">
		        <option value="R">Read</option>
		        <option value="U">Unread</option>
		    </select><br>
		    <input type="submit">
		</form>
		</body>
		</html>
		""";

	private final Jdbi jdbi;

	public static void main(String[] args) {

		Quarkus.run(args);
	}

	@Inject
	public admin(DataSource dataSource) {

		this.jdbi = Jdbi.create(dataSource);
	}

	void onStart(@Observes StartupEvent ev) {

		this.jdbi.useHandle(handle -> handle.execute("""
			CREATE TABLE books 
			AS SELECT * 
			FROM read_csv('all.csv', header=true, auto_detect=true)
			"""
		));
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String index() {

		var authors = this.jdbi.withHandle(handle -> handle.createQuery("""
				WITH src AS (SELECT unnest(split(author,'&')) author FROM books)
				SELECT DISTINCT trim(author) FROM src
				""")
			.map(r -> r.getColumn(1, String.class))
			.map(StringEscapeUtils::escapeHtml4)
			.map("<option value=\"%s\">"::formatted)
			.stream().collect(Collectors.joining())
		);
		return INDEX_HTML.replace("${authors}", authors);
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response save(@Context UriInfo uriInfo, MultivaluedMap<String, String> formdata) {

		this.jdbi.useHandle(handle -> handle.createUpdate("""
				INSERT INTO books (author, title, type, state) 
				VALUES (:author, :title, :type, :state)
				""")
			.bind("author", formdata.get("author").get(0))
			.bind("title", formdata.get("title").get(0))
			.bind("type", formdata.get("type").get(0))
			.bind("state", formdata.get("state").get(0))
			.execute());

		return Response.seeOther(uriInfo.resolve(URI.create("/"))).build();
	}

	void onStop(@Observes ShutdownEvent ev) {

		this.jdbi.useHandle(handle -> handle.execute("""
			COPY (SELECT * FROM books ORDER BY author collate de asc, title collate de ASC) 
			TO 'all.csv' 
			WITH (header true, delimiter ';');
			"""
		));
	}
}