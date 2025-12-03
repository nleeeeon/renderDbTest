// src/main/java/dao/TestNoteDao.java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Db;

public class TestNoteDao {

    public void insert(String body) throws SQLException {
        String sql = "INSERT INTO test_note(body) VALUES (?)";
        try (Connection con = Db.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, body);
            ps.executeUpdate();
        }
    }

    public List<String> findAll() throws SQLException {
        String sql = "SELECT body FROM test_note ORDER BY id DESC";
        List<String> list = new ArrayList<>();
        try (Connection con = Db.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("body"));
            }
        }
        return list;
    }
}
