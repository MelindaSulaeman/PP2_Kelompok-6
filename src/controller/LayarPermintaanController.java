package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.controller;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model.Masyarakat;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model.Penjemputan;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.model.Lokasi;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.database.config;
import PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.view.LayarPermintaan;
import java.util.Map;

public class LayarPermintaanController {
    private JTextField fieldNama, fieldEmail, fieldTelepon, fieldBerat, fieldKota, fieldKodePos;
    private JTextArea fieldAlamat, fieldDeskripsi;
    private JButton tombolNext1, tombolNext2, tombolKirim, tombolKembali;
    private JComboBox<String> comboJenisSampah;
    private JSpinner dateSpinner;
    private int idMasyarakatBaru = 0;
    private volatile boolean isProcessing = false;
    private boolean isPersonalDataSaved = false;
    private boolean isLocationDataSaved = false;
    private LayarPermintaan layarPermintaan;

    public LayarPermintaanController(JTextField fieldNama, JTextField fieldEmail, JTextField fieldTelepon,
                                     JButton tombolNext1, JButton tombolKembali,
                                     JComboBox<String> comboJenisSampah, JTextField fieldBerat,
                                     JSpinner dateSpinner, JTextArea fieldDeskripsi,
                                     JButton tombolNext2,
                                     JTextArea fieldAlamat, JTextField fieldKota,
                                     JTextField fieldKodePos, JButton tombolKirim, LayarPermintaan layarPermintaan) {
        this.fieldNama = fieldNama;
        this.fieldEmail = fieldEmail;
        this.fieldTelepon = fieldTelepon;
        this.tombolNext1 = tombolNext1;
        this.tombolKembali = tombolKembali;
        this.comboJenisSampah = comboJenisSampah;
        this.fieldBerat = fieldBerat;
        this.dateSpinner = dateSpinner;
        this.fieldDeskripsi = fieldDeskripsi;
        this.tombolNext2 = tombolNext2;
        this.fieldAlamat = fieldAlamat;
        this.fieldKota = fieldKota;
        this.fieldKodePos = fieldKodePos;
        this.tombolKirim = tombolKirim;
        this.layarPermintaan = layarPermintaan;
        setupActions();

    }


    private synchronized void setupActions() {
        // Remove existing action listeners to prevent duplicate registrations
        for (ActionListener al : tombolNext1.getActionListeners()) {
            tombolNext1.removeActionListener(al);
        }
        for (ActionListener al : tombolNext2.getActionListeners()) {
            tombolNext2.removeActionListener(al);
        }
        for (ActionListener al : tombolKirim.getActionListeners()) {
            tombolKirim.removeActionListener(al);
        }

        tombolNext1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProcessing) {
                    return; // Prevent double processing
                }
                synchronized (LayarPermintaanController.this) {
                    if (!isPersonalDataSaved) {
                        isProcessing = true;
                        try {
                            if (savePersonalData()) {
                                layarPermintaan.nextPage("PAGE_2");
                            }
                        } finally {
                            isProcessing = false;
                        }
                    } else {
                        layarPermintaan.nextPage("PAGE_2");
                    }
                }
            }
        });

        tombolNext2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProcessing) {
                    return; // Prevent double processing
                }
                synchronized (LayarPermintaanController.this) {
                    if (!isLocationDataSaved) {
                        isProcessing = true;
                        try {
                            if (saveLocationData()) {
                                layarPermintaan.nextPage("PAGE_3");
                            }
                        } finally {
                            isProcessing = false;
                        }
                    } else {
                        layarPermintaan.nextPage("PAGE_3");
                    }
                }
            }
        });

        tombolKirim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isProcessing) {
                    return; // Prevent double processing
                }
                synchronized (LayarPermintaanController.this) {
                    if (layarPermintaan.validatePage3()) {
                        isProcessing = true;
                        try {
                            saveWasteData();
                        } finally {
                            isProcessing = false;
                        }
                    }
                }
            }
        });
    }

    private synchronized boolean savePersonalData() {
        if (isPersonalDataSaved || !layarPermintaan.validatePage1()) {
            return isPersonalDataSaved;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);

            // Check if data already exists
            String checkQuery = "SELECT COUNT(*) FROM masyarakat WHERE email = ? AND noTelp = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setString(1, fieldEmail.getText());
            stmt.setString(2, fieldTelepon.getText());
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Data sudah ada dalam sistem!");
                return false;
            }

            // If not exists, proceed with insert
            String insertQuery = "INSERT INTO masyarakat (nama, noTelp, email, totalSampah, totalPoin) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fieldNama.getText());
            stmt.setString(2, fieldTelepon.getText());
            stmt.setString(3, fieldEmail.getText());
            stmt.setFloat(4, 0.0f);
            stmt.setInt(5, 0);

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idMasyarakatBaru = rs.getInt(1);
                    isPersonalDataSaved = true;
                    conn.commit();
                    JOptionPane.showMessageDialog(null, "Data personal telah disimpan.");
                    return true;
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private synchronized boolean saveLocationData() {
        if (isLocationDataSaved || !layarPermintaan.validatePage2()) {
            return isLocationDataSaved;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);

            // Check if location data already exists
            String checkQuery = "SELECT COUNT(*) FROM lokasi WHERE idMasyarakat = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setInt(1, idMasyarakatBaru);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Location data already exists
            }

            String query = "INSERT INTO lokasi (idMasyarakat, alamatLengkap, kota, kodePos) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMasyarakatBaru);
            stmt.setString(2, fieldAlamat.getText());
            stmt.setString(3, fieldKota.getText());
            stmt.setString(4, fieldKodePos.getText());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                isLocationDataSaved = true;
                conn.commit();
                JOptionPane.showMessageDialog(null, "Data lokasi telah disimpan.");
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private synchronized void resetForm() {
        isProcessing = false;
        isPersonalDataSaved = false;
        isLocationDataSaved = false;
        idMasyarakatBaru = 0;

        fieldNama.setText("");
        fieldEmail.setText("");
        fieldTelepon.setText("");
        fieldAlamat.setText("");
        fieldKota.setText("");
        fieldKodePos.setText("");
        fieldBerat.setText("");
        fieldDeskripsi.setText("");
        comboJenisSampah.setSelectedIndex(0);
        dateSpinner.setValue(new Date());

        layarPermintaan.nextPage("PAGE_1"); // Kembali ke halaman pertama
    }


    private void saveWasteData() {
        if (!isPersonalDataSaved) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data. Data personal belum tersimpan!");
            return;
        }
        if (!isLocationDataSaved) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data. Data lokasi belum tersimpan!");
            return;
        }

        if (idMasyarakatBaru == 0) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data. Data personal belum tersimpan!");
            return;
        }

        Connection conn = null;
        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);
            String namaKategori = (String) comboJenisSampah.getSelectedItem();
            if (namaKategori.equals("Pilih Jenis Sampah")) {
                JOptionPane.showMessageDialog(null, "Pilih jenis sampah yang benar!");
                return;
            }
            int idKategori = layarPermintaan.getCategoryMap().get(namaKategori);
            if (idKategori == 0) {
                JOptionPane.showMessageDialog(null, "Gagal mengambil ID Kategori!");
                return;
            }
            String beratText = fieldBerat.getText();
            double beratSampah;
            try {
                beratSampah = Double.parseDouble(beratText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Berat sampah harus berupa angka!");
                return;
            }
            String deskripsi = fieldDeskripsi.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tglPenjemputan = (Date) dateSpinner.getValue();
            String statusPenjemputan = "pending";
            int poinDikumpulkan = (int) (beratSampah * 100);

            Penjemputan penjemputan = new Penjemputan(0, idMasyarakatBaru, namaKategori, idKategori, beratSampah, deskripsi, statusPenjemputan, poinDikumpulkan, tglPenjemputan);

            // Insert into penjemputan table
            String queryPenjemputan = "INSERT INTO penjemputan (idMasyarakat, namaSampah, idKategori, beratSampah, deskripsi, statusPenjemputan, poinDikumpulkan, tglPenjemputan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtPenjemputan = conn.prepareStatement(queryPenjemputan)) {
                stmtPenjemputan.setInt(1, penjemputan.getIdMasyarakat());
                stmtPenjemputan.setString(2, penjemputan.getNamaSampah());
                stmtPenjemputan.setInt(3, penjemputan.getIdKategori());
                stmtPenjemputan.setDouble(4, penjemputan.getBeratSampah());
                stmtPenjemputan.setString(5, penjemputan.getDeskripsi());
                stmtPenjemputan.setString(6, penjemputan.getStatusPenjemputan());
                stmtPenjemputan.setInt(7, penjemputan.getPoinDikumpulkan());
                stmtPenjemputan.setDate(8, new java.sql.Date(penjemputan.getTglPenjemputan().getTime()));
                System.out.println("Query saveAllData penjemputan: " + stmtPenjemputan);
                int rowsAffected = stmtPenjemputan.executeUpdate();
                if (rowsAffected <= 0) {
                    JOptionPane.showMessageDialog(null, "Gagal menyimpan data penjemputan. Tidak ada baris yang terpengaruh");
                    conn.rollback();
                    isPersonalDataSaved = false;
                    isLocationDataSaved = false;
                    return;
                }
            }
            conn.commit();
            JOptionPane.showMessageDialog(null, "Permintaan berhasil dikirim!");
            isPersonalDataSaved = false;
            isLocationDataSaved = false;


        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gagal rollback transaksi: " + rollbackEx.getMessage());
                }
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gagal menutup koneksi: " + closeEx.getMessage());
                }
            }
        }
    }
}