import java.awt.*;
import javax.swing.*;
import view.*;

public class Main extends JFrame {
    private JPanel panelUtama;
    private CardLayout tataLetak;
    private LayarBeranda layarBeranda;
    private LayarJenisSampah layarJenisSampah;
    private LayarPermintaan layarPermintaan;
    private LayarRiwayatPenjemputan layarRiwayatPenjemputan;
    private LayarTotalSampahPoin layarTotalSampahPoin;

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
        layarRiwayatPenjemputan = new LayarRiwayatPenjemputan();
        layarTotalSampahPoin = new LayarTotalSampahPoin();
        
        // Tambahkan ke panel utama
        panelUtama.add(layarBeranda, "BERANDA");
        panelUtama.add(layarJenisSampah, "JENIS_SAMPAH");
        panelUtama.add(layarPermintaan, "PERMINTAAN");
        panelUtama.add(layarRiwayatPenjemputan, "RIWAYAT_PENJEMPUTAN");
        panelUtama.add(layarTotalSampahPoin, "TOTAL_POIN");
        
        add(panelUtama);
    }

    private void setupEventListeners() {
        // Event listener untuk layar beranda
        layarBeranda.getTombolLihatJenis().addActionListener(e -> 
            tataLetak.show(panelUtama, "JENIS_SAMPAH"));
        
        layarBeranda.getTombolPermintaan().addActionListener(e -> 
            tataLetak.show(panelUtama, "PERMINTAAN"));
        
        // Tambahkan tombol untuk riwayat dan poin di beranda
        layarBeranda.getTombolRiwayat().addActionListener(e -> 
            tataLetak.show(panelUtama, "RIWAYAT_PENJEMPUTAN"));
            
        layarBeranda.getTombolPoin().addActionListener(e -> 
            tataLetak.show(panelUtama, "TOTAL_POIN"));
        
        // Event listener untuk layar jenis sampah
        layarJenisSampah.getTombolKembali().addActionListener(e -> 
            tataLetak.show(panelUtama, "BERANDA"));
        
        // Event listeners untuk layar permintaan
        setupPermintaanListeners();
        
        // Event listeners untuk layar riwayat
        setupRiwayatListeners();
        
        // Event listeners untuk layar poin
        setupPoinListeners();
    }

    private void setupPermintaanListeners() {
        // Navigation buttons
        layarPermintaan.getTombolKembali().addActionListener(e -> 
            tataLetak.show(panelUtama, "BERANDA"));
            
        layarPermintaan.getTombolNext1().addActionListener(e -> {
            if (layarPermintaan.validatePage1()) {
                layarPermintaan.nextPage("PAGE_2");
            }
        });
        
        layarPermintaan.getTombolNext2().addActionListener(e -> {
            if (layarPermintaan.validatePage2()) {
                layarPermintaan.nextPage("PAGE_3");
            }
        });
        
        layarPermintaan.getTombolBack1().addActionListener(e -> 
            layarPermintaan.nextPage("PAGE_1"));
            
        layarPermintaan.getTombolBack2().addActionListener(e -> 
            layarPermintaan.nextPage("PAGE_2"));
        
        layarPermintaan.getTombolKirim().addActionListener(e -> {
            if (layarPermintaan.validatePage3()) {
                JOptionPane.showMessageDialog(this,
                    "Permintaan penjemputan telah berhasil dikirim!\nTim kami akan menghubungi Anda segera.",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
                tataLetak.show(panelUtama, "BERANDA");
            }
        });
    }
    
    private void setupRiwayatListeners() {
        // Tombol kembali di layar riwayat
        layarRiwayatPenjemputan.getTombolKembali().addActionListener(e -> 
            tataLetak.show(panelUtama, "BERANDA"));
    }
    
    private void setupPoinListeners() {
        // Tombol kembali di layar poin
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