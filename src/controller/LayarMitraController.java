
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
                Object[] row = {
                    rs.getInt("idPenjemputan"),   // ID Penjemputan
                    no++,                        // No
                    rs.getString("nama"),        // Nama Masyarakat
                    rs.getString("alamatLengkap"), // Alamat
                    rs.getString("kota"),        // Kota
                    rs.getString("namaKategori"), // Kategori Sampah
                    rs.getString("namaSampah"),  // Nama Sampah
                    rs.getFloat("beratSampah"),  // Berat (kg)
                    rs.getString("statusPenjemputan") // Status
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
