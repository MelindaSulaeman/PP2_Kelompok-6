package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LayarRiwayatPenjemputan extends JFrame {
    private JPanel panel;
    private JComboBox<String> filterSelect;
    private DefaultTableModel tableModel;

    public LayarRiwayatPenjemputan() {
        setTitle("Riwayat Penjemputan Sampah");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        placeComponents();

        setSize(1000, 600); // Ukuran frame
        setLocationRelativeTo(null); // Pusatkan frame
        setVisible(true);
    }

    private void placeComponents() {
        // Panel atas untuk judul
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titlePanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Riwayat Penjemputan Sampah", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Font lebih kecil
        titleLabel.setForeground(new Color(34, 139, 34)); // Hijau
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Panel tengah untuk filter dan tabel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        add(contentPanel, BorderLayout.CENTER);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterPanel.add(filterLabel);

        String[] filterOptions = {"Semua", "Terbaru", "Terlama"};
        filterSelect = new JComboBox<>(filterOptions);
        filterPanel.add(filterSelect);

        JButton applyFilterButton = new JButton("Terapkan");
        applyFilterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applyFilterButton.setBackground(new Color(34, 139, 34));
        applyFilterButton.setForeground(Color.WHITE);
        applyFilterButton.setPreferredSize(new Dimension(100, 30));
        filterPanel.add(applyFilterButton);

        contentPanel.add(filterPanel, BorderLayout.NORTH);

        // Tabel
        String[] columnNames = {"No", "Kategori", "Berat (kg)", "Poin", "Tanggal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);

        // Header minimalis
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(34, 139, 34));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 255, 240));
                }
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Tombol kembali
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(Color.WHITE);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(34, 139, 34));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(100, 30));
        backPanel.add(backButton);

        add(backPanel, BorderLayout.SOUTH);

        loadAllData();

        applyFilterButton.addActionListener(e -> {
            String selectedFilter = (String) filterSelect.getSelectedItem();
            if ("Semua".equals(selectedFilter)) {
                loadAllData();
            } else if ("Terbaru".equals(selectedFilter)) {
                loadNewestData();
            } else if ("Terlama".equals(selectedFilter)) {
                loadOldestData();
            }
        });

        backButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Kembali ke halaman sebelumnya!"));
    }

    private void loadAllData() {
        tableModel.setRowCount(0);
        for (int i = 1; i <= 20; i++) {
            tableModel.addRow(new Object[]{i, "Sampah Elektronik", i * 2, i * 2 * 200, "01/01/2025"});
        }
    }

    private void loadNewestData() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{1, "Sampah Plastik", 10, 2000, "30/12/2025"});
        tableModel.addRow(new Object[]{2, "Sampah Kertas", 8, 1600, "28/12/2025"});
        tableModel.addRow(new Object[]{3, "Sampah Elektronik", 15, 3000, "27/12/2025"});
    }

    private void loadOldestData() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{1, "Sampah Organik", 5, 1000, "01/01/2024"});
        tableModel.addRow(new Object[]{2, "Sampah Plastik", 12, 2400, "05/01/2024"});
        tableModel.addRow(new Object[]{3, "Sampah Elektronik", 7, 1400, "10/01/2024"});
    }
}

class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(34, 139, 34); // Hijau
    }
}
