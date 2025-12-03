// src/main/java/util/Db.java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    // Render 用に環境変数から読む（なければ今までのローカル設定）
    private static final String URL =
        System.getenv().getOrDefault("DB_URL",
            "jdbc:postgresql://dpg-d4o3gbq4d50c73a9dp00-a:5432/testdb_y175");

    private static final String USER =
        System.getenv().getOrDefault("DB_USER", "testdb_y175_user");

    private static final String PASS =
        System.getenv().getOrDefault("DB_PASSWORD", "FZRWfVksSWe7rRos87cv9RgPSX3jUUhz");

    static {
        try {
            // 今は MariaDB を使う
            Class.forName("org.mariadb.jdbc.Driver");

            // 将来 Postgres に変えるときはここをコメント切り替え
            // Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("DB driver not found", e);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
