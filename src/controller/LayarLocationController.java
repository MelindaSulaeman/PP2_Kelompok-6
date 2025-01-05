package controller;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import PP2_Kelompok_6.src.model.Lokasi;
import PP2_Kelompok_6.src.model.Penjemputan;
import database.config;

public class LayarLocationController {
    private JTextArea fieldAlamat;
    private JTextField fieldKota, fieldKodePos;
    private JButton tombolKirim, tombolBack2;
    private JTextField idPenjemputan;

    // Konstruktor untuk menerima data penjemputan dan komponen form
    public LayarLocationController(JTextArea fieldAlamat, JTextField fieldKota, JTextField fieldKodePos, JButton tombolKirim, JButton tombolBack2, JTextField penjemputan) {
        this.fieldAlamat = fieldAlamat;
        this.fieldKota = fieldKota;
        this.fieldKodePos = fieldKodePos;
        this.tombolKirim = tombolKirim;
        this.tombolBack2 = tombolBack2;
        this.idPenjemputan = penjemputan; // ID Penjemputan yang digunakan untuk mengaitkan data

        setupActions();
    }

    private void setupActions() {
        tombolKirim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLocationDetails();
            }
        });

        tombolBack2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigasi kembali ke halaman sebelumnya jika diperlukan
            }
        });
    }

    private void saveLocationDetails() {
        String alamat = fieldAlamat.getText();
        String kota = fieldKota.getText();
        String kodePos = fieldKodePos.getText();

        // Validasi input
        if (alamat.isEmpty() || kota.isEmpty() || kodePos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!");
            return;
        }

        // Simpan data lokasi ke database
        try (Connection conn = config.getConnection()) {
            String queryLokasi = "INSERT INTO Lokasi (idPenjemputan, alamatLengkap, kota, kodePos) "
                    + "VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(queryLokasi)) {
                stmt.setInt(1, Integer.parseInt(idPenjemputan.getText())); // Menggunakan ID Penjemputan yang sudah ada
                stmt.setString(2, alamat);
                stmt.setString(3, kota);
                stmt.setString(4, kodePos);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Lokasi penjemputan berhasil disimpan.");
                // Lanjutkan ke halaman berikutnya jika diperlukan
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan lokasi: " + ex.getMessage());
        }
    }
}
