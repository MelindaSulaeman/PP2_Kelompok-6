package view;

import controller.LayarTotalSampahPointController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

public class LayarTotalSampahPoin extends JPanel {
    public JLabel labelTotalSampah;
    public JLabel labelTotalPoin;
    public JTable tableRiwayat;
    private JButton exitButton;
    private LayarTotalSampahPointController controller;

    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76);
    private Color warnaAksen = new Color(45, 136, 45);
    private Color warnaKartu = Color.WHITE;
    private Color warnaHeader = new Color(34, 197, 94);

    public LayarTotalSampahPoin() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
        initController();
    }

    public void initController() {
        controller = new LayarTotalSampahPointController(this);
    }

    private void initComponents() {
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(warnaLatar);

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

        JPanel cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setBackground(warnaLatar);
        cardsContainer.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        cardsPanel.setBackground(warnaLatar);
        cardsPanel.setMaximumSize(new Dimension(500, 150));
        cardsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel cardSampah = createInfoCard("Total Sampah: 0 Kg", "/icons/garbage.png");
        labelTotalSampah = (JLabel) cardSampah.getComponent(1);

        JPanel cardPoint = createInfoCard("Total Poin: 0 Poin", "/icons/point.png");
        labelTotalPoin = (JLabel) cardPoint.getComponent(1);

        cardsPanel.add(cardSampah);
        cardsPanel.add(cardPoint);
        cardsContainer.add(cardsPanel);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(warnaLatar);
        tablePanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JLabel tableTitle = new JLabel("Riwayat Penjemputan");
        tableTitle.setFont(new Font("Montserrat", Font.BOLD, 15));
        tableTitle.setForeground(warnaAksen);
        tableTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        String[] columnNames = {"No", "Kategori", "Berat (Kg)", "Poin", "Waktu"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
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

        mainContent.add(cardsContainer, BorderLayout.NORTH);
        mainContent.add(tablePanel, BorderLayout.CENTER);

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

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Montserrat", Font.BOLD, 13));
        header.setBackground(warnaPrimer);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(25);
        columnModel.getColumn(3).setPreferredWidth(25);
        columnModel.getColumn(4).setPreferredWidth(100);
    }

    public JButton getTombolKembali() {
        return exitButton;
    }

    private class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}