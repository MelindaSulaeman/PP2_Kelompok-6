
package controller;

import database.config;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LayarMitraController {

    public DefaultTableModel getPenjemputanData(String statusFilter) {
        String[] columnNames = {"ID Penjemputan", "No", "Nama Masyarakat", "Alamat", "Kota", "Kategori Sampah", "Nama Sampah", "Berat (kg)", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        String query = """
            SELECT p.idPenjemputan, m.nama, l.alamatLengkap, l.kota, k.namaKategori, p.namaSampah, p.beratSampah, p.statusPenjemputan
            FROM penjemputan p
            JOIN masyarakat m ON p.idMasyarakat = m.idMasyarakat
            JOIN lokasi l ON m.idMasyarakat = l.idMasyarakat
            JOIN kategori k ON p.idKategori = k.idKategori
            WHERE p.statusPenjemputan = ?
        """;

        try (Connection conn = config.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, statusFilter);
            ResultSet rs = stmt.executeQuery();

            int no = 1;
            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("idPenjemputan"),
                        no++,
                        rs.getString("nama"),
                        rs.getString("alamatLengkap"),
                        rs.getString("kota"),
                        rs.getString("namaKategori"),
                        rs.getString("namaSampah"),
                        rs.getFloat("beratSampah"),
                        rs.getString("statusPenjemputan")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tableModel;
    }

    public void updatePenjemputanStatus(int idPenjemputan, String newStatus) {
        String query = "UPDATE penjemputan SET statusPenjemputan = ? WHERE idPenjemputan = ?";
        
        try (Connection conn = config.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newStatus);
                stmt.setInt(2, idPenjemputan);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Status penjemputan berhasil diperbarui.");
                } else {
                    System.out.println("Gagal memperbarui status penjemputan. Baris dengan idPenjemputan " + idPenjemputan + " tidak ditemukan.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
