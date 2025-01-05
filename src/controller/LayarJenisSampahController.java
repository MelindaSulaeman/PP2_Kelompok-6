package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.controller;

import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.database.config;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model.Kategori;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.view.LayarJenisSampah;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayarJenisSampahController {

    private LayarJenisSampah layarJenisSampah;

    public LayarJenisSampahController(LayarJenisSampah layarJenisSampah) {
        this.layarJenisSampah = layarJenisSampah;
        setupActions();
        loadDataKategori();
    }
    private void setupActions() {
        layarJenisSampah.getTombolKembali().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    private void loadDataKategori() {
        try (Connection conn = config.getConnection()) {
            String query = "SELECT idKategori, namaKategori, icon FROM kategori";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                List<Kategori> listKategori = new ArrayList<>();
                while (rs.next()) {
                    int idKategori = rs.getInt("idKategori");
                    String namaKategori = rs.getString("namaKategori");
                    String icon = rs.getString("icon");
                    listKategori.add(new Kategori(idKategori, namaKategori, icon));
                }
                layarJenisSampah.updateCategoryCards(listKategori);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(layarJenisSampah, "Gagal memuat data kategori: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}