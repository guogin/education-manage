package mobi.dashuxia.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(value = "jdbc")
public class JdbcConnectionSettings extends AbstractSettings {

	private String driver;
	private String url;
	private String username;
	private String password;
	private int maxActive;
	private int minIdel;
	private int maxIdle;
	private int maxWait;
	private String validationQuery = "/* ping */ select 1";
	private Boolean testOnBorrow = false;
	private Boolean testOnReturn = false;
	private Boolean testWhileIdle = true;
	private long timeBetweenEvictionRunsMillis = 60000;
	private long minEvictableIdleTimeMillis = -1;
}
