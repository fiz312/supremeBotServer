package server;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ratpack.error.ServerErrorHandler;
import ratpack.func.Action;
import ratpack.func.Function;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;
import ratpack.server.RatpackServerSpec;
import ratpack.session.SessionModule;
import server.app.AppService;
import server.order.OrderService;
import server.security.SecurityService;

class Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	private RatpackServer ratpackServer;

	private final SecurityService securityService;
	private final OrderService orderService;
	private final AppService appService;

	
	public Server(SecurityService securityService, OrderService orderService, AppService appService) throws Exception {
		this.securityService = securityService;
		this.orderService = orderService;
		this.appService = appService;
		ratpackServer = createServer(makeApi(orderService), makeApp(appService));
	}

	private RatpackServer createServer(Action<Chain> handlers, Action<Chain> appHandlers) throws Exception {
		return RatpackServer.of(server -> configure(server)
				.handlers(chain -> {
					chain
						.prefix("auth", securityService.auth())
						.prefix("api", securityService.secure(handlers))
						.prefix("app", appHandlers);
		}));
	}

	public void start() throws Exception {
		ratpackServer.start();
	}

	private Action<Chain> makeApi(OrderService orderService) {
		return chain -> chain
				.insert(orderService.orderApi());
	}
	
	private Action<Chain> makeApp(AppService appService) {
		return chain -> chain
				.insert(appService.initializeApp());
	}

	private static RatpackServerSpec configure(RatpackServerSpec server) throws Exception {
			return server.serverConfig(scb -> scb.baseDir(Paths.get("C:\\Users\\Raberr\\Desktop\\dev\\eclipseWorkspace\\supremeServer\\build\\resources\\main")).port(8080).threads(1))
					.registry(Guice.registry(b -> b.bindInstance(ServerErrorHandler.class, (ctx, error) -> {
							LOGGER.error("Unexpected error", error);
							ctx.render(Jackson.json("error 500"));
						})
						.module(SessionModule.class)
						.module(TextTemplateModule.class)
					));
	}
}
