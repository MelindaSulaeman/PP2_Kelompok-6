package PP2_Kelompok_6.src;

import java.awt.*;
import javax.swing.*;
import PP2_Kelompok_6.src.view.*;

public class Main extends JFrame {
    private JPanel panelUtama;
    private CardLayout tataLetak;
    private LayarBeranda layarBeranda;
    private LayarJenisSampah layarJenisSampah;
    private LayarPermintaan layarPermintaan;
    private LayarTotalSampahPoin layarTotalSampahPoin;
    private LayarKonfirmasi layarKonfirmasi;
    private LayarStatusPenjemputan layarStatusPenjemputan;

    public Main() {
        setTitle("E-Waste");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        setupEventListeners();
    }

    private void initComponents() {
        tataLetak = new CardLayout();
        panelUtama = new JPanel(tataLetak);

        // Inisialisasi halaman
        layarBeranda = new LayarBeranda();
        layarJenisSampah = new LayarJenisSampah();
        layarPermintaan = new LayarPermintaan();
        layarKonfirmasi = new LayarKonfirmasi(); // Menambahkan layarKonfirmasi
        layarStatusPenjemputan = new LayarStatusPenjemputan(); // Menambahkan layarStatusPenjemputan
        layarTotalSampahPoin = new LayarTotalSampahPoin(); // Menambahkan layarTotalSampahPoin

        // Tambahkan ke panel utama
        panelUtama.add(layarBeranda, "BERANDA");
        panelUtama.add(layarJenisSampah, "JENIS_SAMPAH");
        panelUtama.add(layarPermintaan, "PERMINTAAN");
        panelUtama.add(layarKonfirmasi, "KONFIRMASI");
        panelUtama.add(layarStatusPenjemputan, "STATUS");
        panelUtama.add(layarTotalSampahPoin, "TOTAL POINT");

        add(panelUtama);
    }

    private boolean validateForm() {
        String nama = layarPermintaan.getFieldNama().getText().trim();
        String alamat = layarPermintaan.getFieldAlamat().getText().trim();
        String jenisSampah = (String) layarPermintaan.getComboJenisSampah().getSelectedItem();
        String deskripsi = layarPermintaan.getFieldDeskripsi().getText().trim();

        StringBuilder errorMessage = new StringBuilder("Mohon lengkapi data berikut:\n");
        boolean isValid = true;

        if (nama.isEmpty()) {
            errorMessage.append("- Nama Lengkap\n");
            isValid = false;
        }

        if (alamat.isEmpty()) {
            errorMessage.append("- Alamat Lengkap\n");
            isValid = false;
        }

        if (jenisSampah.equals("Pilih Jenis Sampah")) {
            errorMessage.append("- Jenis Sampah\n");
            isValid = false;
        }

        if (deskripsi.isEmpty()) {
            errorMessage.append("- Deskripsi Sampah\n");
            isValid = false;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(this,
                    errorMessage.toString(),
                    "Data Tidak Lengkap",
                    JOptionPane.WARNING_MESSAGE);
        }

        return isValid;
    }

    private void setupEventListeners() {
        // Event listener untuk layar beranda
        layarBeranda.getTombolLihatJenis().addActionListener(e ->
                tataLetak.show(panelUtama, "JENIS_SAMPAH"));

        layarBeranda.getTombolPermintaan().addActionListener(e ->
                tataLetak.show(panelUtama, "PERMINTAAN"));

        // Event listener untuk layar jenis sampah
        layarJenisSampah.getTombolKembali().addActionListener(e ->
                tataLetak.show(panelUtama, "BERANDA"));

        // Event listener untuk layar permintaan
        layarPermintaan.getTombolKembali().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin kembali?\nData yang telah diisi akan hilang.",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Reset form fields
                layarPermintaan.getFieldNama().setText("");
                layarPermintaan.getFieldAlamat().setText("");
                layarPermintaan.getComboJenisSampah().setSelectedIndex(0);
                layarPermintaan.getFieldDeskripsi().setText("");

                tataLetak.show(panelUtama, "BERANDA");
            }
        });

        layarPermintaan.getTombolKirim().addActionListener(e -> {
            if (validateForm()) {
                JOptionPane.showMessageDialog(this,
                        "Permintaan penjemputan telah berhasil dikirim!\nTim kami akan menghubungi Anda segera.",
                        "Berhasil",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset form fields after successful submission
                layarPermintaan.getFieldNama().setText("");
                layarPermintaan.getFieldAlamat().setText("");
                layarPermintaan.getComboJenisSampah().setSelectedIndex(0);
                layarPermintaan.getFieldDeskripsi().setText("");

                tataLetak.show(panelUtama, "BERANDA");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}

