package goodreads_admin;

import java.net.URI;

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

import org.jdbi.v3.core.Jdbi;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

/**
 * Party like it's 1999 PHP, but with Quarkus and Java.
 *
 * @author Michael J. Simons
 */
@Path("/")
public final class Application {

	private final Jdbi jdbi;

	private final Template index;

	@Inject
	public Application(DataSource dataSource, Template index) {

		this.jdbi = Jdbi.create(dataSource);
		this.index = index;
	}

	void onStart(@Observes StartupEvent ev) {
		this.jdbi.useHandle(handle -> {
			handle.execute("INSTALL httpfs");
			handle.execute("""
				CREATE TABLE books 
				AS SELECT * 
				FROM read_csv('https://raw.githubusercontent.com/michael-simons/goodreads/master/all.csv', header=true, auto_detect=true)
				"""
			);
		});
	}


	void onStop(@Observes ShutdownEvent ev) {
		this.jdbi.useHandle(handle -> {
			handle.execute("""
				COPY (SELECT * FROM books ORDER BY author collate de asc, title collate de ASC) 
				TO '../all.csv' 
				WITH (header true, delimiter ';');
				"""
			);
		});
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance index() {

		var result = this.jdbi.withHandle(handle -> handle.createQuery("""
				WITH src AS (SELECT unnest(split(author,'&')) author FROM books)
				SELECT DISTINCT trim(author) FROM src""")
			.map(r -> r.getColumn(1, String.class))
			.list()
		);
		return index.data("authors", result);
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
}
