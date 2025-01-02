package PP2_Kelompok_6.src.view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LayarTotalSampahPoin extends JPanel {
    private JLabel labelTotalSampah;
    private JLabel labelTotalPoin;
    private JTable tableRiwayat;

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

        JPanel headerTitle = new JPanel(new BorderLayout());
        headerTitle.setBackground(warnaHeader);
        headerTitle.setPreferredSize(new Dimension(getWidth(), 40));

        JPanel headerContent = new JPanel(new BorderLayout());
        headerContent.setBackground(warnaHeader);
        headerContent.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel titleLabel = new JLabel("Riwayat Point", JLabel.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JButton exitButton = new JButton("←");
        exitButton.setFont(new Font("Montserrat", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(warnaHeader);
        exitButton.setBorder(null);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

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

        Dimension cardSize = new Dimension(350, 70);

        JPanel cardSampah = new JPanel(new BorderLayout(10, 0));
        cardSampah.setBackground(warnaKartu);
        cardSampah.setPreferredSize(cardSize);
        cardSampah.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, new Color(200, 200, 200)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        labelTotalSampah = new JLabel("Total Sampah: 200 Kg");
        labelTotalSampah.setFont(new Font("Montserrat", Font.BOLD, 14));

        ImageIcon iconSampah = new ImageIcon("/icons/garbage.png");
        Image imageSampah = iconSampah.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabelSampah = new JLabel(new ImageIcon(imageSampah));
        iconLabelSampah.setBorder(new EmptyBorder(0, 5, 0, 5));

        cardSampah.add(iconLabelSampah, BorderLayout.WEST);
        cardSampah.add(labelTotalSampah, BorderLayout.CENTER);

        JPanel cardPoint = new JPanel(new BorderLayout(10, 0));
        cardPoint.setBackground(warnaKartu);
        cardPoint.setPreferredSize(cardSize);
        cardPoint.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, new Color(200, 200, 200)),
                new EmptyBorder(10, 15, 10, 15)
        ));

        labelTotalPoin = new JLabel("Total Poin: 350 Poin");
        labelTotalPoin.setFont(new Font("Montserrat", Font.BOLD, 14));

        ImageIcon iconPoint = new ImageIcon("/icons/point.png");
        Image imagePoint = iconPoint.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabelPoint = new JLabel(new ImageIcon(imagePoint));
        iconLabelPoint.setBorder(new EmptyBorder(0, 5, 0, 5));

        cardPoint.add(iconLabelPoint, BorderLayout.WEST);
        cardPoint.add(labelTotalPoin, BorderLayout.CENTER);

        cardsPanel.add(cardSampah);
        cardsPanel.add(cardPoint);
        cardsContainer.add(cardsPanel);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(warnaLatar);
        tablePanel.setBorder(new EmptyBorder(10, 20, 20, 20));

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
        tableRiwayat.setFont(new Font("Montserrat", Font.PLAIN, 13));
        tableRiwayat.setRowHeight(30);
        tableRiwayat.setShowGrid(true);
        tableRiwayat.setGridColor(new Color(230, 230, 230));
        tableRiwayat.setBackground(warnaKartu);

        JTableHeader header = tableRiwayat.getTableHeader();
        header.setFont(new Font("Montserrat", Font.BOLD, 13));
        header.setBackground(warnaPrimer);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableRiwayat.getColumnCount(); i++) {
            tableRiwayat.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = tableRiwayat.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(25);
        columnModel.getColumn(3).setPreferredWidth(25);
        columnModel.getColumn(4).setPreferredWidth(100);

        JScrollPane tableScrollPane = new JScrollPane(tableRiwayat);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tableScrollPane.getViewport().setBackground(warnaKartu);

        tableScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = warnaPrimer;
                this.trackColor = warnaKartu;
            }
        });

        JLabel tableTitle = new JLabel("Riwayat Penjemputan");
        tableTitle.setFont(new Font("Montserrat", Font.BOLD, 15));
        tableTitle.setForeground(warnaAksen);
        tableTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        mainContent.add(cardsContainer, BorderLayout.NORTH);
        mainContent.add(tablePanel, BorderLayout.CENTER);

        JScrollPane mainScrollPane = new JScrollPane(mainContent);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = warnaPrimer;
                this.trackColor = warnaLatar;
            }
        });

        add(headerTitle, BorderLayout.NORTH);
        add(mainScrollPane, BorderLayout.CENTER);
    }
}
