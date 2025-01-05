package PP2_TUBES_Masyarakat_PenjemputanSampah_A.src.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class LayarTotalSampahPoin extends JPanel {
    private JLabel labelTotalSampah;
    private JLabel labelTotalPoin;
    private JTable tableRiwayat;
    private JButton exitButton;

    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76);
    private Color warnaAksen = new Color(45, 136, 45);
    private Color warnaKartu = Color.WHITE;
    private Color warnaHeader = new Color(34, 197, 94);

    public LayarTotalSampahPoin() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(warnaLatar);

        // Header Section
        JPanel headerTitle = new JPanel(new BorderLayout());
        headerTitle.setBackground(warnaHeader);
        headerTitle.setPreferredSize(new Dimension(getWidth(), 40));

        JPanel headerContent = new JPanel(new BorderLayout());
        headerContent.setBackground(warnaHeader);
        headerContent.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel titleLabel = new JLabel("Riwayat Point", JLabel.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        exitButton = new JButton("←");
        exitButton.setFont(new Font("Montserrat", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(warnaHeader);
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        headerContent.add(exitButton, BorderLayout.WEST);
        headerContent.add(titleLabel, BorderLayout.CENTER);
        headerTitle.add(headerContent, BorderLayout.CENTER);

        // Cards Container
        JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setBackground(warnaLatar);
        cardsContainer.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        cardsPanel.setBackground(warnaLatar);
        cardsPanel.setMaximumSize(new Dimension(500, 150));
        cardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Card Sampah
        JPanel cardSampah = createInfoCard("Total Sampah: 200 Kg", "/icons/garbage.png");
        JPanel cardPoint = createInfoCard("Total Poin: 350 Poin", "/icons/point.png");

        cardsPanel.add(cardSampah);
        cardsPanel.add(cardPoint);
        cardsContainer.add(cardsPanel);

        // Table Section
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(warnaLatar);
        tablePanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JLabel tableTitle = new JLabel("Riwayat Penjemputan");
        tableTitle.setFont(new Font("Montserrat", Font.BOLD, 15));
        tableTitle.setForeground(warnaAksen);
        tableTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        // Table Setup
        String[] columnNames = {"No", "Kategori", "Berat (Kg)", "Poin", "Waktu"};
        Object[][] data = {
            {"1", "Komputer", "15", "50", "01/01/2025"},
            {"2", "Ponsel", "5", "20", "02/01/2025"},
            {"3", "Peralatan Rumah Tangga", "20", "70", "03/01/2025"},
            {"4", "Peralatan Rumah Tangga", "20", "70", "03/01/2025"},
            {"5", "Peralatan Rumah Tangga", "20", "70", "03/01/2025"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRiwayat = new JTable(model);
        setupTable(tableRiwayat);

        JScrollPane tableScrollPane = new JScrollPane(tableRiwayat);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tableScrollPane.getViewport().setBackground(warnaKartu);

        // Custom ScrollBar
        tableScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = warnaPrimer;
                this.trackColor = warnaKartu;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add all components to main content
        mainContent.add(cardsContainer, BorderLayout.NORTH);
        mainContent.add(tablePanel, BorderLayout.CENTER);

        // Add everything to the main panel
        add(headerTitle, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String text, String iconPath) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(warnaKartu);
        card.setPreferredSize(new Dimension(350, 70));
        card.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, new Color(200, 200, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Montserrat", Font.BOLD, 14));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image image = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(image));
            iconLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
            card.add(iconLabel, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println("Icon not found: " + iconPath);
        }

        card.add(label, BorderLayout.CENTER);
        return card;
    }

    private void setupTable(JTable table) {
        table.setFont(new Font("Montserrat", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(warnaKartu);

        // Setup header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Montserrat", Font.BOLD, 13));
        header.setBackground(warnaPrimer);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);  // No
        columnModel.getColumn(1).setPreferredWidth(200); // Kategori
        columnModel.getColumn(2).setPreferredWidth(25);  // Berat
        columnModel.getColumn(3).setPreferredWidth(25);  // Poin
        columnModel.getColumn(4).setPreferredWidth(100); // Waktu
    }

    public JButton getTombolKembali() {
        return exitButton;
    }
}

