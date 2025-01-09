import java.awt.*;
import javax.swing.*;

import controller.*;
import view.*;

public class Main extends JFrame {
        private JPanel panelUtama;
        private CardLayout tataLetak;
        private LayarBeranda layarBeranda;
        private LayarJenisSampah layarJenisSampah;
        private LayarPermintaan layarPermintaan;
        private LayarRiwayatPenjemputan layarRiwayatPenjemputan;
        private LayarTotalSampahPoin layarTotalSampahPoin;
        private LayarMitra layarMitra;
        private LayarJenisSampahController layarJenisSampahController;

        public Main() {
                setTitle("E-Waste");
                setSize(800, 600);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                initComponents();
                setupEventListeners();
                setupControllers();
        }

        private void initComponents() {
                tataLetak = new CardLayout();
                panelUtama = new JPanel(tataLetak);

                layarBeranda = new LayarBeranda();
                layarJenisSampah = new LayarJenisSampah();
                layarPermintaan = new LayarPermintaan();
                layarRiwayatPenjemputan = new LayarRiwayatPenjemputan();
                layarTotalSampahPoin = new LayarTotalSampahPoin();
                layarMitra = new LayarMitra();

                panelUtama.add(layarBeranda, "BERANDA");
                panelUtama.add(layarJenisSampah, "JENIS_SAMPAH");
                panelUtama.add(layarPermintaan, "PERMINTAAN");
                panelUtama.add(layarRiwayatPenjemputan, "RIWAYAT_PENJEMPUTAN");
                panelUtama.add(layarTotalSampahPoin, "TOTAL_POIN");
                panelUtama.add(layarMitra, "Mitra");

                add(panelUtama);
        }

        private void setupEventListeners() {
                layarBeranda.getTombolLihatJenis().addActionListener(e -> {
                        tataLetak.show(panelUtama, "JENIS_SAMPAH");
                        layarJenisSampahController.loadDataKategori();
                });

                layarBeranda.getTombolPermintaan().addActionListener(e ->
                        tataLetak.show(panelUtama, "PERMINTAAN"));

                layarBeranda.getTombolRiwayat().addActionListener(e ->
                        tataLetak.show(panelUtama, "RIWAYAT_PENJEMPUTAN"));

                layarBeranda.getTombolPoin().addActionListener(e ->
                        tataLetak.show(panelUtama, "TOTAL_POIN"));

                layarBeranda.getTombolKurir().addActionListener(e -> {
                        tataLetak.show(panelUtama, "Mitra");
                });

                layarJenisSampah.getTombolKembali().addActionListener(e ->
                        tataLetak.show(panelUtama, "BERANDA"));

                layarPermintaan.getTombolKembali().addActionListener(e ->
                        tataLetak.show(panelUtama, "BERANDA"));

                layarMitra.getTombolKembali().addActionListener(e ->
                        tataLetak.show(panelUtama, "BERANDA"));
                setupRiwayatListeners();
                setupPoinListeners();
        }

        private void setupControllers() {
                layarJenisSampahController = new LayarJenisSampahController(layarJenisSampah);
                new LayarPermintaanController(
                        layarPermintaan.getFieldNama(),
                        layarPermintaan.getFieldEmail(),
                        layarPermintaan.getFieldTelepon(),
                        layarPermintaan.getTombolNext1(),
                        layarPermintaan.getTombolKembali(),
                        layarPermintaan.getComboJenisSampah(),
                        layarPermintaan.getFieldBerat(),
                        layarPermintaan.getDateSpinner(),
                        layarPermintaan.getFieldDeskripsi(),
                        layarPermintaan.getTombolNext2(),
                        layarPermintaan.getFieldAlamat(),
                        layarPermintaan.getFieldKota(),
                        layarPermintaan.getFieldKodePos(),
                        layarPermintaan.getTombolKirim(),
                        layarPermintaan,
                        () -> tataLetak.show(panelUtama, "BERANDA")
                );
                new LayarTotalSampahPointController(layarTotalSampahPoin);
        }
        private void setupRiwayatListeners() {
                layarRiwayatPenjemputan.getTombolKembali().addActionListener(e ->
                        tataLetak.show(panelUtama, "BERANDA"));
        }

        private void setupPoinListeners() {
                layarTotalSampahPoin.getTombolKembali().addActionListener(e ->
                        tataLetak.show(panelUtama, "BERANDA"));
        }


        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                        Main app = new Main();
                        app.setLocationRelativeTo(null);
                        app.setVisible(true);
                });
        }
}