package cn.waitfor.mbga.integration.persistence;

import java.nio.file.Paths;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MySQLContainer;

@JooqTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScans({@ComponentScan("jooq.tables.daos"), @ComponentScan("cn.waitfor.mbga.repository")})
public class AbstractDataAccessLayerTest {

  public static MySQLContainer<?> mysql =
      new MySQLContainer<>("mysql:8.4.2")
          .withDatabaseName("mjga")
          .withUsername("root")
          .withFileSystemBind(
              Paths.get("db.d/init/01-init.sql").toAbsolutePath().toString(),
              "/docker-entrypoint-initdb.d/01-init.sql",
              BindMode.READ_ONLY);

  @DynamicPropertySource
  static void mysqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysql::getJdbcUrl);
    registry.add("spring.datasource.username", mysql::getUsername);
    registry.add("spring.datasource.password", mysql::getPassword);
    registry.add("spring.docker.compose.skip.in-tests", () -> "true");
  }

  static {
    mysql.start();
  }
}
