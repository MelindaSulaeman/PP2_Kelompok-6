package PP2_Kelompok_6.src.view;

import PP2_Kelompok_6.src.model.Kategori;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;

public class LayarJenisSampah extends JPanel {
    private JButton tombolKembali;
    private JPanel contentPanel;
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
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(warnaLatar);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel labelJudul = new JLabel("Jenis dan Kategori Sampah Elektronik");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 24));
        labelJudul.setForeground(warnaPrimer);
        headerPanel.add(labelJudul);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(warnaLatar);
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(warnaLatar);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        tombolKembali = createStyledButton("Kembali ke Menu Utama", "/resources/icons/back.png");
        bottomPanel.add(tombolKembali);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateCategoryCards(List<Kategori> listKategori) {
        contentPanel.removeAll();
        for (Kategori kategori : listKategori) {
            contentPanel.add(createCategoryCard(kategori.getNamaKategori(),"", kategori.getIcon()));
            contentPanel.add(Box.createVerticalStrut(15));
        }
        contentPanel.revalidate();
        contentPanel.repaint();
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

        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(warnaKartu);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + iconPath));
            Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            iconPanel.add(iconLabel);
        } catch (Exception e) {
            System.out.println("Icon not found: " + "/icons/" + iconPath);
        }

        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBackground(warnaKartu);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(warnaPrimer);
        textPanel.add(titleLabel, gbc);

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

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

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