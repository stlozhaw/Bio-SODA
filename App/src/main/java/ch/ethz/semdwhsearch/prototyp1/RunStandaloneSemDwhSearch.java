package ch.ethz.semdwhsearch.prototyp1;

import java.util.Collections;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.semdwhsearch.prototyp1.servlets.SemDwhSearchServlet;
import ch.ethz.semdwhsearch.prototyp1.servlets.SparqlServlet;

/**
 * Run SemDwhSearch as a standalone application using Jetty as its HTTP server.
 * 
 * @author Lukas Blunschi, Ana Sima
 */
public class RunStandaloneSemDwhSearch {

	public static Logger logger = LoggerFactory.getLogger(RunStandaloneSemDwhSearch.class);

	public static void main(String[] args) throws Exception {
		int port = 8081;

		DefaultParser parser = new DefaultParser();
		Option portOption = Option.builder("p")
		.longOpt("port")
		.argName("Number")
		.desc("Port to serve on")
		.required(false)
		.type(Number.class)
		.hasArg()
		.build();

		Options options = new Options();
		options.addOption(portOption);

		try {
			CommandLine cli = parser.parse(options, args);
			if(cli.hasOption("p")) {
				port = ((Number)cli.getParsedOptionValue("p")).intValue();
			}
		} catch(ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(" ", options);
			return;
		}

		Server server = createServer(port);
		server.start();
		logger.info("Running at: http://localhost:{}", port);
		server.join();
	}

	public static Server createServer(final int port) throws Exception {
		Server server = new Server(port);

		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setResourceBase(RunStandaloneSemDwhSearch.class.getClassLoader().getResource("web").toExternalForm());

		servletHandler.addServlet(new ServletHolder(new DefaultServlet()), "/");
		servletHandler.addServlet(new ServletHolder(SemDwhSearchServlet.class), "");
		servletHandler.addServlet(new ServletHolder(SparqlServlet.class), "/sparql/*");

		server.setHandler(servletHandler);

		return server;
	}

}
