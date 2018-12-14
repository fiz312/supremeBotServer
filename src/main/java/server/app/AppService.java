package server.app;

import java.nio.file.Paths;

import ratpack.func.Action;
import ratpack.groovy.Groovy;
import ratpack.handling.Chain;

public class AppService {

	public AppService() {

	}

	public Action<Chain> initializeApp() {
		return chain -> chain
				.path("xxx.html", ctx -> ctx.render(ctx.file("templates/xxx.html")))
				.path("auth.html", ctx -> ctx.render(ctx.file("templates/auth.html")))
				.path("home.html", ctx -> ctx.render(ctx.file("templates/home.html")))
				.path("order.html", ctx -> ctx.render(ctx.file("templates/order.html")))
				.path(ctx -> ctx.render(ctx.file("templates/index.html")))
				.files(f -> f.indexFiles("app.js", "home.js", "home.css", "auth.js", "auth.css", "order.js", "order.css"));	
	}
}
