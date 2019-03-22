package com.easysoft.shortUrl.db;

import com.easysoft.shortUrl.ShortUrlGenerator;
import com.easysoft.shortUrl.ShortUrlProperties;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlShortUrlGenerator extends ShortUrlGenerator {
    private DataSource dataSource;
    private ShortUrlProperties shortUrlProperties;

    public MysqlShortUrlGenerator(DataSource dataSource, ShortUrlProperties shortUrlProperties) {
        this.dataSource = dataSource;
        this.shortUrlProperties = shortUrlProperties;
    }

    @Override
    public void init() {
        createTable();
    }

    @Override
    protected Long generatorId(String longUrl) {
        Long id = getId(longUrl);
        if (id == null) {
            id = insertRecord(longUrl);
        }
        return id;
    }

    @Override
    public String getLongUrl(Long id) {
        String sql = new StringBuilder("SELECT LONG_URL FROM ").append(shortUrlProperties.getTableName())
                .append(" WHERE ID = ?")
                .toString();
        String longUrl = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try (Connection connection = dataSource.getConnection()) {
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                longUrl = rs.getString("LONG_URL");
                break;
            }
        } catch (Exception e) {
            throw new ShortUrlInitException("query fail", e);
        } finally {
            close(rs);
            close(ps);
        }
        return longUrl;
    }

    private Long getId(String longUrl) {
        String sql = new StringBuilder("SELECT ID FROM ").append(shortUrlProperties.getTableName())
                .append(" WHERE LONG_URL = ?")
                .toString();
        Long id = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try (Connection connection = dataSource.getConnection()) {
            ps = connection.prepareStatement(sql);
            ps.setString(1, longUrl);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getLong("ID");
                break;
            }
        } catch (Exception e) {
            throw new ShortUrlInitException("query fail", e);
        } finally {
            close(rs);
            close(ps);
        }
        return id;
    }

    private Long insertRecord(String longUrl) {
        String sql = new StringBuilder("INSERT INTO ").append(shortUrlProperties.getTableName())
                .append("(LONG_URL) VALUES (?)")
                .toString();
        Long generateKey = null;
        PreparedStatement ps = null;
        try (Connection connection = dataSource.getConnection()) {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, longUrl);
            ps.execute();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                generateKey = generatedKeys.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            generateKey = getId(longUrl);
        } finally {
            close(ps);
        }
        return generateKey;
    }

    private void createTable() {
        String createTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(shortUrlProperties.getTableName())
                .append(" (ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, CREATE_TIME  DATETIME DEFAULT NULL, UPDATE_TIME DATETIME DEFAULT NULL, STATUS INT DEFAULT NULL, LONG_URL  VARCHAR(255)   NOT NULL UNIQUE)")
                .toString();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try (Connection connection = dataSource.getConnection()) {
            ps = connection.prepareStatement(createTable);
            ps.execute();
        } catch (Exception e) {
            throw new ShortUrlInitException("createTable fail", e);
        } finally {
            close(rs);
            close(ps);
        }
    }
}
