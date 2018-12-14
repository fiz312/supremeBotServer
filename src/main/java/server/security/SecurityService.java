package server.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.jwt.profile.JwtProfile;

import com.google.common.collect.Maps;

import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.pac4j.RatpackPac4j;
import ratpack.session.Session;
import server.security.api.User;
import server.security.authenticators.ServerDirectHttpAuthenticator;
import server.security.repository.UserInMemoryRepository;

public class SecurityService {

	private final UserInMemoryRepository userInMemoryRepository;

	private final String JWT_SALT = "12345678901234567890123456789012";
	private final SignatureConfiguration signatureConfiguration = new SecretSignatureConfiguration(JWT_SALT);
	private final EncryptionConfiguration encryptionConfiguration = new SecretEncryptionConfiguration(JWT_SALT);

	public SecurityService(UserInMemoryRepository userInMemoryRepository) {
		this.userInMemoryRepository = userInMemoryRepository;
	}

	public Action<Chain> auth() {
		return chain -> {

			final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(
					new ServerDirectHttpAuthenticator(userInMemoryRepository));
			chain.all(RatpackPac4j.authenticator("callback", directBasicAuthClient))
					.all(RatpackPac4j.requireAuth(DirectBasicAuthClient.class)).get(ctx -> {
						CommonProfile user = (CommonProfile) ctx.get(UserProfile.class);
						final JwtGenerator generator = new JwtGenerator(signatureConfiguration,
								encryptionConfiguration);
						final String token = generator.generate(user);
						RatpackPac4j.logout(ctx);
						ctx.render(Jackson.json(token));
					});
		};
	}

	public Action<Chain> secure(Action<Chain> handlers) {
		return chain -> {
			final JwtAuthenticator jwtAuthenticator = new JwtAuthenticator(Arrays.asList(signatureConfiguration),
					Arrays.asList(encryptionConfiguration));
			final HeaderClient parameterClient = new HeaderClient("Auth", "Bearer", jwtAuthenticator);

			chain.all(RatpackPac4j.authenticator("callback", parameterClient))
					.all(RatpackPac4j.requireAuth(HeaderClient.class)).insert(handlers);
		};
	}
}
