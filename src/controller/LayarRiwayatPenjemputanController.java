package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import database.config;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LayarRiwayatPenjemputanController {

    public List<Object[]> getPenjemputanData(String filter) {
        List<Object[]> listData = new ArrayList<>();
        String query = "SELECT p.idPenjemputan, p.idMasyarakat, p.namaSampah, p.idKategori, p.beratSampah, p.deskripsi, p.statusPenjemputan, p.poinDikumpulkan, p.tglPenjemputan " +
                "FROM penjemputan p ";

        switch (filter) {
            case "newest":
                query += "ORDER BY p.tglPenjemputan DESC";
                break;
            case "oldest":
                query += "ORDER BY p.tglPenjemputan ASC";
                break;
            case "pending_first":
                query += "ORDER BY CASE WHEN p.statusPenjemputan = 'On Progress' THEN 0 ELSE 1 END, p.tglPenjemputan DESC";
                break;
            default:
                query += "ORDER BY CASE WHEN p.statusPenjemputan = 'On Progress' THEN 0 ELSE 1 END, p.tglPenjemputan DESC";
                break;
        }

        try (Connection connection = config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idPenjemputan = resultSet.getInt("idPenjemputan");
                int idMasyarakat = resultSet.getInt("idMasyarakat");
                String namaSampah = resultSet.getString("namaSampah");
                int idKategori = resultSet.getInt("idKategori");
                double beratSampah = resultSet.getDouble("beratSampah");
                String deskripsi = resultSet.getString("deskripsi");
                String statusPenjemputan = resultSet.getString("statusPenjemputan");
                int poinDikumpulkan = resultSet.getInt("poinDikumpulkan");

                Date tglPenjemputan = resultSet.getTimestamp("tglPenjemputan");
                if (tglPenjemputan == null) {
                    tglPenjemputan = new Date();
                }

                String namaKategori = getNamaKategori(connection, idKategori);

                Object[] rowData = {
                        idPenjemputan,
                        namaKategori,
                        beratSampah,
                        poinDikumpulkan,
                        tglPenjemputan,
                        statusPenjemputan
                };
                listData.add(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return listData;
    }

    private String getNamaKategori(Connection connection, int idKategori) throws SQLException {
        String query = "SELECT namaKategori FROM kategori WHERE idKategori = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idKategori);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("namaKategori");
                }
            }
        }
        return "Unknown Category";
    }
}