package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class LayarPermintaan extends JPanel {
    private JTextField fieldNama;
    private JTextArea fieldAlamat;
    private JComboBox<String> comboJenisSampah;
    private JTextArea fieldDeskripsi;
    private JButton tombolKirim;
    private JButton tombolKembali;
    
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76); 
    private Color warnaAksen = new Color(45, 136, 45); 
    private Color warnaKartu = Color.WHITE;

    public LayarPermintaan() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(warnaLatar);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel labelJudul = new JLabel("Formulir Permintaan Penjemputan");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 24));
        labelJudul.setForeground(warnaPrimer);
        headerPanel.add(labelJudul);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(warnaKartu);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Set maximum width for form panel
        formPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        // Nama
        fieldNama = createStyledTextField();
        formPanel.add(createFormField("Nama Lengkap", fieldNama));
        formPanel.add(Box.createVerticalStrut(15));

        // Alamat
        fieldAlamat = createStyledTextArea(3);
        formPanel.add(createFormField("Alamat Lengkap", new JScrollPane(fieldAlamat)));
        formPanel.add(Box.createVerticalStrut(15));

        // Jenis Sampah
        comboJenisSampah = createStyledComboBox(new String[]{
            "Pilih Jenis Sampah",
            "Komputer & Laptop",
            "Ponsel & Tablet",
            "Peralatan Rumah Tangga",
            "Komponen Elektronik"
        });
        formPanel.add(createFormField("Jenis Sampah", comboJenisSampah));
        formPanel.add(Box.createVerticalStrut(15));

        // Deskripsi
        fieldDeskripsi = createStyledTextArea(4);
        formPanel.add(createFormField("Deskripsi Sampah", new JScrollPane(fieldDeskripsi)));
        formPanel.add(Box.createVerticalStrut(20));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(warnaKartu);
        
        tombolKirim = createStyledButton("Kirim Permintaan", "/icons/send.png", true);
        tombolKembali = createStyledButton("Kembali", "/icons/back.png", false);
        
        buttonPanel.add(tombolKirim);
        buttonPanel.add(tombolKembali);
        
        formPanel.add(buttonPanel);

        // Membuat wrapper panel for horizontal centering
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(warnaLatar);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 20, 20, 20); // Add padding

        // Menambah form panel ke wrapper
        wrapperPanel.add(formPanel, gbc);

        // Membuat scroll pane
        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(warnaLatar);

        // Set kolom header view ke header panel
        scrollPane.setColumnHeaderView(headerPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createFormField(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(warnaKartu);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(new Color(51, 51, 51));
        
        panel.add(labelComponent);
        panel.add(Box.createVerticalStrut(5));
        panel.add(component);
        
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return textField;
    }

    private JTextArea createStyledTextArea(int rows) {
        JTextArea textArea = new JTextArea(rows, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        return textArea;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return comboBox;
    }

    private JButton createStyledButton(String text, String iconPath, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(isPrimary ? warnaPrimer : warnaAksen);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));

        // Add icon if available
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

        // Hover 
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(isPrimary ? warnaAksen : warnaPrimer);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(isPrimary ? warnaPrimer : warnaAksen);
            }
        });

        return button;
    }

    // Getter methods
    public JTextField getFieldNama() { return fieldNama; }
    public JTextArea getFieldAlamat() { return fieldAlamat; }
    public JComboBox<String> getComboJenisSampah() { return comboJenisSampah; }
    public JTextArea getFieldDeskripsi() { return fieldDeskripsi; }
    public JButton getTombolKirim() { return tombolKirim; }
    public JButton getTombolKembali() { return tombolKembali; }
}