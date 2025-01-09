package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LayarRiwayatPenjemputan extends JPanel {
    private JPanel panel;
    private JComboBox<String> filterSelect;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public LayarRiwayatPenjemputan() {
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        placeComponents();
    }

    private void placeComponents() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titlePanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Riwayat Penjemputan Sampah", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(34, 139, 34));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        add(contentPanel, BorderLayout.CENTER);

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

        setupTable(contentPanel);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(Color.WHITE);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        backButton = new JButton("Back");
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
    }


    private void setupTable(JPanel contentPanel) {
        String[] columnNames = {"No", "Kategori", "Berat (kg)", "Poin", "Tanggal", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);

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

                if (column == 5 && "On Progress".equals(value)) {
                    c.setForeground(Color.RED);
                } else {
                     c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(34, 139, 34);
                this.trackColor = Color.WHITE;
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
        
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void loadAllData() {
         tableModel.setRowCount(0);
         tableModel.addRow(new Object[]{1, "Sampah Elektronik", 15, 3000, "20/02/2025", "On Progress"});
        for (int i = 2; i <= 20; i++) {
           
             String status = i % 3 == 0 ? "Sukses" : "Sukses";
              tableModel.addRow(new Object[]{i, "Sampah Elektronik", i * 2, i * 2 * 200, "01/01/2025", status});
           
        }
    }


    private void loadNewestData() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{1, "Sampah Elektronik", 15, 3000, "30/12/2025", "Sukses"});
        tableModel.addRow(new Object[]{2, "Sampah Elektronik", 8, 1600, "28/12/2025", "Sukses"});
         tableModel.addRow(new Object[]{3, "Sampah Elektronik", 10, 2000, "27/12/2025", "On Progress"});
    }

    private void loadOldestData() {
        tableModel.setRowCount(0);
           tableModel.addRow(new Object[]{1, "Sampah Elektronik", 15, 3000, "10/01/2024", "On Progress"});
        tableModel.addRow(new Object[]{2, "Sampah Elektronik", 12, 2400, "05/01/2024", "Sukses"});
        tableModel.addRow(new Object[]{3, "Sampah Elektronik", 7, 1400, "01/01/2024", "Sukses"});

    }

    public JButton getTombolKembali() {
        return backButton;
    }
}