package off.itgoes;

import org.eclipse.microprofile.auth.LoginConfig;

import jakarta.ws.rs.core.Application;

@LoginConfig(authMethod = "MP-JWT", realmName = "TCK-MP-JWT")
public class AppConfig extends Application {
	// analoga ad una classe SpringApplication usata per configurazione
}
