package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import database.config;
import model.Penjemputan;
import model.Kategori;
import view.LayarTotalSampahPoin;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class LayarTotalSampahPointController {
    private LayarTotalSampahPoin layarTotalSampahPoin;
    private Connection connection;

    public LayarTotalSampahPointController(LayarTotalSampahPoin layarTotalSampahPoin) {
        this.layarTotalSampahPoin = layarTotalSampahPoin;
        initializeConnection();
        setupActions();
        tampilRiwayatPenjemputan();
    }

    private void initializeConnection() {
        try {
            this.connection = config.getConnection();
            if (this.connection == null) {
                throw new SQLException("Connection is null");
            }
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal terhubung ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupActions() {
        layarTotalSampahPoin.getTombolKembali().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("kembali");
                closeConnection();
            }
        });
    }

    public void tampilRiwayatPenjemputan() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeConnection();
            }

            List<Penjemputan> listPenjemputanSelesai = getAllPenjemputanSelesai();
            System.out.println("Retrieved " + listPenjemputanSelesai.size() + " records");

            double totalBerat = getTotalBeratSampahSelesai();
            int totalPoin = getTotalPoinDikumpulkanSelesai();

            layarTotalSampahPoin.labelTotalSampah.setText("Total Sampah: " + String.format("%.2f", totalBerat) + " Kg");
            layarTotalSampahPoin.labelTotalPoin.setText("Total Poin: " + totalPoin + " Poin");

            DefaultTableModel model = (DefaultTableModel) layarTotalSampahPoin.tableRiwayat.getModel();
            model.setRowCount(0);

            int nomor = 1;
            for (Penjemputan penjemputan : listPenjemputanSelesai) {
                Object[] rowData = {
                        nomor,
                        penjemputan.getNamaSampah(),
                        String.format("%.2f", penjemputan.getBeratSampah()),
                        penjemputan.getPoinDikumpulkan(),
                        penjemputan.getTglPenjemputan()
                };
                model.addRow(rowData);
                nomor++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
            e.printStackTrace();
            closeConnection();
        }
    }

    private List<Penjemputan> getAllPenjemputanSelesai() throws SQLException {
        List<Penjemputan> penjemputanList = new ArrayList<>();
        String query = "SELECT p.*, k.namaKategori, k.icon " +
                "FROM penjemputan p " +
                "INNER JOIN kategori k ON p.idKategori = k.idKategori " +
                "WHERE LOWER(p.statusPenjemputan) = 'selesai' " +
                "ORDER BY p.tglPenjemputan DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            System.out.println("Executing query: " + query);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Kategori kategori = new Kategori(
                            resultSet.getInt("idKategori"),
                            resultSet.getString("namaKategori"),
                            resultSet.getString("icon")
                    );

                    Penjemputan penjemputan = new Penjemputan(
                            resultSet.getInt("idPenjemputan"),
                            resultSet.getInt("idMasyarakat"),
                            resultSet.getString("namaSampah"),
                            kategori.getIdKategori(),
                            resultSet.getDouble("beratSampah"),
                            resultSet.getString("deskripsi"),
                            resultSet.getString("statusPenjemputan"),
                            resultSet.getInt("poinDikumpulkan"),
                            resultSet.getDate("tglPenjemputan")
                    );

                    penjemputanList.add(penjemputan);
                    System.out.println("Found record: " + penjemputan.getNamaSampah());
                }
            }
        }

        System.out.println("Total records found: " + penjemputanList.size());
        return penjemputanList;
    }

    private double getTotalBeratSampahSelesai() throws SQLException {
        String query = "SELECT SUM(beratSampah) AS total_berat FROM penjemputan WHERE LOWER(statusPenjemputan) = 'selesai'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getDouble("total_berat");
            }
        }
        return 0;
    }

    private int getTotalPoinDikumpulkanSelesai() throws SQLException {
        String query = "SELECT SUM(poinDikumpulkan) AS total_poin FROM penjemputan WHERE LOWER(statusPenjemputan) = 'selesai'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("total_poin");
            }
        }
        return 0;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menutup koneksi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}