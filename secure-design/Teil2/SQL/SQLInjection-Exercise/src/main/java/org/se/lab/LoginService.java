package org.se.lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class LoginService {
    private final static Logger LOG = Logger.getLogger(LoginService.class);

    public boolean login(Connection connection, String username, String password) {
        final String SQL = "SELECT id FROM User WHERE username = ? AND password = ?"; // Prepared Statement mit Platzhaltern
        LOG.info("Executing SQL query with prepared statement.");

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            // Setzen der Parameter, um SQL-Injection zu verhindern
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // True, wenn mindestens ein Eintrag gefunden wurde
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception during login", e);
            return false;
        }
    }
}
