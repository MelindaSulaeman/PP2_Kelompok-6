package controller;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import model.Masyarakat;
import database.config;

public class LayarPermintaanController {
    private JTextField fieldNama, fieldEmail, fieldTelepon;
    private JButton tombolNext1, tombolKembali;
    
    public LayarPermintaanController(JTextField fieldNama, JTextField fieldEmail, JTextField fieldTelepon,
                                    JButton tombolNext1, JButton tombolKembali) {
        this.fieldNama = fieldNama;
        this.fieldEmail = fieldEmail;
        this.fieldTelepon = fieldTelepon;
        this.tombolNext1 = tombolNext1;
        this.tombolKembali = tombolKembali;

        setupActions();
    }

    private void setupActions() {
        tombolNext1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePersonalData();
            }
        });

        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigasi kembali ke halaman sebelumnya (jika perlu)
            }
        });
    }

    private void savePersonalData() {
        String nama = fieldNama.getText();
        String email = fieldEmail.getText();
        String telepon = fieldTelepon.getText();
        
        // Validasi data
        if (nama.isEmpty() || email.isEmpty() || telepon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!");
            return;
        }

        // Simpan data ke dalam database
        Masyarakat masyarakat = new Masyarakat(0, nama, "", telepon, 0, 0);  // Data default untuk sementara

        try (Connection conn = config.getConnection()) {
            String query = "INSERT INTO masyarakat (nama, noTelp, email, totalSampah, totalPoin) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, masyarakat.getNama());
                stmt.setString(2, masyarakat.getNoTelp());
                stmt.setString(3, masyarakat.getEmail());
                stmt.setFloat(4, masyarakat.getTotalSampah());
                stmt.setInt(5, masyarakat.getTotalPoin());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data telah disimpan.");
                // Lanjutkan ke halaman berikutnya
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + ex.getMessage());
        }
    }
}
