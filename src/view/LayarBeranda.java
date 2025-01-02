package PP2_Kelompok_6.src.view;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LayarBeranda extends JPanel {
    private JButton tombolLihatJenis;
    private JButton tombolPermintaan;
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76);
private Color warnaAksen = new Color(45, 136, 45);

    public LayarBeranda() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(warnaLatar);
        headerPanel.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel labelJudul = new JLabel(" ");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 32));
        labelJudul.setForeground(warnaPrimer);
        labelJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelSubJudul = new JLabel("E-Waste");
        labelSubJudul.setFont(new Font("Arial", Font.BOLD, 32));
        labelSubJudul.setForeground(warnaPrimer);
        labelSubJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelDeskripsi = new JLabel("Solusi pengelolaan sampah elektronik yang aman dan efisien");
        labelDeskripsi.setFont(new Font("Arial", Font.PLAIN, 16));
        labelDeskripsi.setForeground(new Color(102, 102, 102));
        labelDeskripsi.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(labelJudul);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(labelSubJudul);
        headerPanel.add(Box.createVerticalStrut(15));
        headerPanel.add(labelDeskripsi);

        // Panel Tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(warnaLatar);
        buttonPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        tombolLihatJenis = createStyledButton("Lihat Jenis dan Kategori Sampah", "/icons/category.png");
        tombolPermintaan = createStyledButton("Ajukan Permintaan Penjemputan", "/icons/truck.png");

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(tombolLihatJenis);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(tombolPermintaan);
        buttonPanel.add(Box.createVerticalGlue());

        // Panel Utama
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(warnaAksen);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(400, 50));
        button.setPreferredSize(new Dimension(400, 50));

        // Menambahkan icon jika tersedia
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaPrimer);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(warnaAksen);
            }
        });

        return button;
    }

    public JButton getTombolLihatJenis() {
        return tombolLihatJenis;
    }

    public JButton getTombolPermintaan() {
        return tombolPermintaan;
    }
}