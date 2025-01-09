package controller;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import model.Lokasi;
import model.Penjemputan;
import database.config;

public class LayarLocationController {
    private JTextArea fieldAlamat;
    private JTextField fieldKota, fieldKodePos;
    private JButton tombolKirim, tombolBack2;
    private JTextField idPenjemputan;

    public LayarLocationController(JTextArea fieldAlamat, JTextField fieldKota, JTextField fieldKodePos, JButton tombolKirim, JButton tombolBack2, JTextField penjemputan) {
        this.fieldAlamat = fieldAlamat;
        this.fieldKota = fieldKota;
        this.fieldKodePos = fieldKodePos;
        this.tombolKirim = tombolKirim;
        this.tombolBack2 = tombolBack2;
        this.idPenjemputan = penjemputan;

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
            }
        });
    }

    private void saveLocationDetails() {
        String alamat = fieldAlamat.getText();
        String kota = fieldKota.getText();
        String kodePos = fieldKodePos.getText();

        if (alamat.isEmpty() || kota.isEmpty() || kodePos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!");
            return;
        }

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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan lokasi: " + ex.getMessage());
        }
    }
}