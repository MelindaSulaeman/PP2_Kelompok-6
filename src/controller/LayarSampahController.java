package controller;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import model.Masyarakat;
import database.config;

public class LayarSampahController {
    private JComboBox<String> comboJenisSampah;
    private JTextField fieldBerat;
    private JSpinner dateSpinner;
    private JTextArea fieldDeskripsi;
    private JButton tombolNext2, tombolBack1;
    private JTextField idPenjemputan;
    
    public LayarSampahController(JComboBox<String> comboJenisSampah, JTextField fieldBerat, 
                                JSpinner dateSpinner, JTextArea fieldDeskripsi, 
                                JButton tombolNext2, JButton tombolBack1, JTextField idPenjemputan) {
        this.comboJenisSampah = comboJenisSampah;
        this.fieldBerat = fieldBerat;
        this.dateSpinner = dateSpinner;
        this.fieldDeskripsi = fieldDeskripsi;
        this.tombolNext2 = tombolNext2;
        this.tombolBack1 = tombolBack1;
        this.idPenjemputan = idPenjemputan;
        
        setupActions();
    }

    private void setupActions() {
        tombolNext2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWasteDetails();
            }
        });

        tombolBack1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    private void saveWasteDetails() {
        String selectedWasteType = (String) comboJenisSampah.getSelectedItem();
        if (selectedWasteType == null || selectedWasteType.equals("Pilih Jenis Sampah")) {
            JOptionPane.showMessageDialog(null, "Jenis sampah harus dipilih!");
            return;
        }
        
        String beratSampah = fieldBerat.getText();
        String deskripsi = fieldDeskripsi.getText();
        java.util.Date pickUpDate = (java.util.Date) dateSpinner.getValue();
        
        if (beratSampah.isEmpty() || deskripsi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Berat dan deskripsi sampah harus diisi!");
            return;
        }

        float berat = Float.parseFloat(beratSampah);

        int idKategori = getCategoryIdByName(selectedWasteType);
        
        if (idKategori == -1) {
            JOptionPane.showMessageDialog(null, "Kategori sampah tidak valid!");
            return;
        }

        int userId = getUserId();

        try (Connection conn = config.getConnection()) {
            // Insert data into Penjemputan
            String query = "INSERT INTO Penjemputan (idMasyarakat, idSampah, jumlahSampah, beratSampah, deskripsi, statusPenjemputan, tglPenjemputan) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, idKategori);
                stmt.setInt(3, 1);
                stmt.setFloat(4, berat);
                stmt.setString(5, deskripsi);
                stmt.setString(6, "Pending");
                stmt.setTimestamp(7, new java.sql.Timestamp(pickUpDate.getTime()));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        Integer generatedId = rs.getInt(1);
                        this.idPenjemputan.setText(generatedId.toString());
                        JOptionPane.showMessageDialog(null, "Data telah disimpan. ID Penjemputan: " + generatedId);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menyimpan data penjemputan!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + ex.getMessage());
        }
    }

    private int getCategoryIdByName(String categoryName) {
        int categoryId = -1;
        try (Connection conn = config.getConnection()) {
            String query = "SELECT idKategori FROM Kategori WHERE namaKategori = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, categoryName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    categoryId = rs.getInt("idKategori");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categoryId;
    }

    private int getUserId() {
        return 1;
    }
}