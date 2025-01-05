package view;

import controller.LayarPermintaanController;
import database.config;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.sql.*;

import java.util.HashMap;
import java.util.Map;

public class LayarPermintaan extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;


    private JTextField fieldNama;
    private JTextField fieldEmail;
    private JTextField fieldTelepon;


    private JTextArea fieldAlamat;
    private JTextField fieldKota;
    private JTextField fieldKodePos;
    private JTextField idPenjemputan;


    private JComboBox<String> comboJenisSampah;
    private JTextField fieldBerat;
    private JSpinner dateSpinner;
    private JTextArea fieldDeskripsi;


    private JButton tombolNext1;
    private JButton tombolNext2;
    private JButton tombolBack1;
    private JButton tombolBack2;
    private JButton tombolKirim;
    private JButton tombolKembali;

    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76);
    private Color warnaAksen = new Color(45, 136, 45);
    private Color warnaKartu = Color.WHITE;
    private Map<String, Integer> categoryMap = new HashMap<>();

    public LayarPermintaan() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
        loadDataToComboBox();
    }

    private void loadDataToComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Pilih Jenis Sampah");

        try (Connection conn = config.getConnection()) {
            String query = "SELECT idKategori, namaKategori FROM kategori";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idKategori = rs.getInt("idKategori");
                    String namaKategori = rs.getString("namaKategori");
                    categoryMap.put(namaKategori, idKategori);
                    model.addElement(namaKategori);
                    System.out.println("Data Kategori: " + idKategori + " " + namaKategori);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data kategori sampah: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        comboJenisSampah.setModel(model);
    }


    private void initComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(warnaLatar);

        cardPanel.add(createPersonalDataPage(), "PAGE_1");
        cardPanel.add(createLocationPage(), "PAGE_2");
        cardPanel.add(createWasteDetailsPage(), "PAGE_3");

        add(cardPanel, BorderLayout.CENTER);
        setupController();
    }

    private void setupController() {
        new LayarPermintaanController(fieldNama, fieldEmail, fieldTelepon,
                tombolNext1, tombolKembali,
                comboJenisSampah, fieldBerat,
                dateSpinner, fieldDeskripsi,
                tombolNext2,
                fieldAlamat, fieldKota,
                fieldKodePos, tombolKirim, this);
    }


    private JPanel createPersonalDataPage() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(warnaLatar);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(warnaKartu);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Data Personal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(warnaPrimer);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        fieldNama = createStyledTextField();
        fieldEmail = createStyledTextField();
        fieldTelepon = createStyledTextField();

        contentPanel.add(createFormField("Nama Lengkap *", fieldNama));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Email *", fieldEmail));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Nomor Telepon *", fieldTelepon));
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(warnaKartu);
        tombolNext1 = createStyledButton("Selanjutnya", "/icons/next.png", true);
        tombolKembali = createStyledButton("Kembali", "/icons/back.png", false);

        buttonPanel.add(tombolKembali);
        buttonPanel.add(tombolNext1);
        contentPanel.add(buttonPanel);

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(warnaLatar);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 20, 20, 20);

        containerPanel.add(contentPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(warnaLatar);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createLocationPage() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(warnaLatar);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(warnaKartu);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Lokasi Penjemputan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(warnaPrimer);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        fieldAlamat = createStyledTextArea(3);
        fieldKota = createStyledTextField();
        fieldKodePos = createStyledTextField();
        idPenjemputan = createStyledTextField();

        contentPanel.add(createFormField("Alamat Lengkap *", new JScrollPane(fieldAlamat)));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Kota *", fieldKota));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Kode Pos *", fieldKodePos));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createFormField("", idPenjemputan));
        contentPanel.add(Box.createVerticalStrut(20));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(warnaKartu);
        tombolBack1 = createStyledButton("Kembali", "/icons/back.png", false);
        tombolNext2 = createStyledButton("Selanjutnya", "/icons/next.png", true);

        buttonPanel.add(tombolBack1);
        buttonPanel.add(tombolNext2);
        contentPanel.add(buttonPanel);

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(warnaLatar);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 20, 20, 20);

        containerPanel.add(contentPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(warnaLatar);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        idPenjemputan.setVisible(false);
        return mainPanel;
    }

    private JPanel createWasteDetailsPage() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(warnaLatar);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(warnaKartu);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Detail Sampah Elektronik");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(warnaPrimer);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        comboJenisSampah = createStyledComboBox(new String[]{"Pilih Jenis Sampah"});
        fieldBerat = createStyledTextField();
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        fieldDeskripsi = createStyledTextArea(4);

        contentPanel.add(createFormField("Jenis Sampah *", comboJenisSampah));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Berat (kg) *", fieldBerat));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Tanggal Penjemputan *", dateSpinner));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(createFormField("Deskripsi Sampah *", new JScrollPane(fieldDeskripsi)));
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(warnaKartu);

        tombolBack2 = createStyledButton("Kembali", "/icons/back.png", false);
        tombolKirim = createStyledButton("Kirim Permintaan", "/icons/send.png", true);

        buttonPanel.add(tombolBack2);
        buttonPanel.add(tombolKirim);
        contentPanel.add(buttonPanel);

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(warnaLatar);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 20, 20, 20);

        containerPanel.add(contentPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(warnaLatar);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
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

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

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

    private JPanel createFormField(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(warnaKartu);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(new Color(51, 51, 51));
        labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(labelComponent);
        panel.add(Box.createVerticalStrut(5));
        panel.add(component);

        return panel;
    }

    private JPanel createBasePanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(warnaKartu);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(warnaPrimer);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private JScrollPane wrapInScrollPane(JPanel panel) {
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(warnaLatar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        wrapperPanel.add(panel, gbc);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    public boolean validatePage1() {
        if (fieldNama.getText().trim().isEmpty()) {
            showError("Nama harus diisi!");
            return false;
        }
        if (fieldEmail.getText().trim().isEmpty() || !fieldEmail.getText().contains("@")) {
            showError("Email tidak valid!");
            return false;
        }
        if (fieldTelepon.getText().trim().isEmpty()) {
            showError("Nomor telepon harus diisi!");
            return false;
        }
        return true;
    }

    public boolean validatePage2() {
        if (fieldAlamat.getText().trim().isEmpty()) {
            showError("Alamat harus diisi!");
            return false;
        }
        if (fieldKota.getText().trim().isEmpty()) {
            showError("Kota harus diisi!");
            return false;
        }
        if (fieldKodePos.getText().trim().isEmpty()) {
            showError("Kode pos harus diisi!");
            return false;
        }
        return true;
    }


    public boolean validatePage3() {
        if (comboJenisSampah.getSelectedIndex() == 0) {
            showError("Pilih jenis sampah!");
            return false;
        }
        try {
            double berat = Double.parseDouble(fieldBerat.getText().trim());
            if (berat <= 0) {
                showError("Berat harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Berat harus berupa angka!");
            return false;
        }
        if (fieldDeskripsi.getText().trim().isEmpty()) {
            showError("Deskripsi sampah harus diisi!");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void nextPage(String page) {
        cardLayout.show(cardPanel, page);
    }

    public JButton getTombolNext1() { return tombolNext1; }
    public JButton getTombolNext2() { return tombolNext2; }
    public JButton getTombolBack1() { return tombolBack1; }
    public JButton getTombolBack2() { return tombolBack2; }
    public JButton getTombolKirim() { return tombolKirim; }
    public JButton getTombolKembali() { return tombolKembali; }

    public JTextField getFieldNama() { return fieldNama; }
    public JTextField getFieldEmail() { return fieldEmail; }
    public JTextField getFieldTelepon() { return fieldTelepon; }

    public JComboBox<String> getComboJenisSampah() { return comboJenisSampah; }
    public JTextField getFieldBerat() { return fieldBerat; }
    public JSpinner getDateSpinner() { return dateSpinner; }
    public JTextArea getFieldDeskripsi() { return fieldDeskripsi; }

    public JTextArea getFieldAlamat() { return fieldAlamat; }
    public JTextField getFieldKota() { return fieldKota; }
    public JTextField getFieldKodePos() { return fieldKodePos; }
    public JTextField getFieldidPenjemputan() { return idPenjemputan; }

    public Map<String, Integer> getCategoryMap() {
        return categoryMap;
    }
}