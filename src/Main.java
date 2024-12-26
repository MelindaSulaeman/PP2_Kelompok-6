import java.awt.*;
import javax.swing.*;
import view.*;

public class Main extends JFrame {
    private JPanel panelUtama;
    private CardLayout tataLetak;
    private LayarBeranda layarBeranda;
    private LayarJenisSampah layarJenisSampah;
    private LayarPermintaan layarPermintaan;

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
        
        // Tambahkan ke panel utama
        panelUtama.add(layarBeranda, "BERANDA");
        panelUtama.add(layarJenisSampah, "JENIS_SAMPAH");
        panelUtama.add(layarPermintaan, "PERMINTAAN");
        
        add(panelUtama);
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
        layarPermintaan.getTombolKembali().addActionListener(e -> 
            tataLetak.show(panelUtama, "BERANDA"));
        
        layarPermintaan.getTombolKirim().addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Permintaan penjemputan telah berhasil dikirim!\nTim kami akan menghubungi Anda segera.",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE);
            tataLetak.show(panelUtama, "BERANDA");
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