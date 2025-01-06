package view;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LayarJenisSampah extends JPanel {
    private JButton tombolKembali;
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76); 
private Color warnaAksen = new Color(45, 136, 45); 
    private Color warnaKartu = Color.WHITE;

    public LayarJenisSampah() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(warnaLatar);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel labelJudul = new JLabel("Jenis dan Kategori Sampah Elektronik");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 24));
        labelJudul.setForeground(warnaPrimer);
        headerPanel.add(labelJudul);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(warnaLatar);
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        String[][] jenisSampah = {
            {"Komputer & Laptop", "PC, Laptop, Monitor, Keyboard, Mouse", "/resources/icons/computer.png"},
            {"Ponsel & Tablet", "Smartphone, Tablet, Pengisi Daya", "/resources/icons/phone.png"},
            {"Peralatan Rumah Tangga", "TV, Kulkas, Mesin Cuci", "/resources/icons/home.png"},
            {"Komponen Elektronik", "Baterai, PCB, Kabel", "/resources/icons/chip.png"}
        };

        for (String[] jenis : jenisSampah) {
            contentPanel.add(createCategoryCard(jenis[0], jenis[1], jenis[2]));
            contentPanel.add(Box.createVerticalStrut(15));
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(warnaLatar);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        tombolKembali = createStyledButton("Kembali ke Menu Utama", "/resources/icons/back.png");
        bottomPanel.add(tombolKembali);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCategoryCard(String title, String description, String iconPath) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(warnaKartu);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(800, 100));

        // Icon Panel
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(warnaKartu);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            iconPanel.add(iconLabel);
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(warnaKartu);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(warnaPrimer);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(new Color(102, 102, 102));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(descLabel);

        card.add(iconPanel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(warnaAksen);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add icon if available
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

    public JButton getTombolKembali() {
        return tombolKembali;
    }
}