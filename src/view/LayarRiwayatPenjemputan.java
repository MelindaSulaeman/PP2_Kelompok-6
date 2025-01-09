package view;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.LayarRiwayatPenjemputanController;

public class LayarRiwayatPenjemputan extends JPanel {
    private JPanel panel;
    private JComboBox<String> filterSelect;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private LayarRiwayatPenjemputanController controller;
    private JTable table;

    public LayarRiwayatPenjemputan() {
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        controller = new LayarRiwayatPenjemputanController();
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

        loadData("pending_first");

        applyFilterButton.addActionListener(e -> {
            String selectedFilter = (String) filterSelect.getSelectedItem();
            if ("Semua".equals(selectedFilter)) {
                loadData("all");
            } else if ("Terbaru".equals(selectedFilter)) {
                loadData("newest");
            } else if ("Terlama".equals(selectedFilter)) {
                loadData("oldest");
            }
        });
    }

    private void setupTable(JPanel contentPanel) {
        String[] columnNames = {"ID", "Kategori", "Berat (kg)", "Poin", "Tanggal", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(34, 139, 34));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 255, 240));
                }
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        table.getColumnModel().getColumn(5).setCellRenderer(new LinkRenderer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == 5) {
                    int idPenjemputan = (int) table.getValueAt(row, 0);
                    String status = (String) table.getValueAt(row, 5);
                    openStatusPerjalanan(idPenjemputan, status);
                }
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
        });

        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void loadData(String filter) {
        tableModel.setRowCount(0);
        List<Object[]> listData = controller.getPenjemputanData(filter);

        if (listData != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Object[] rowData : listData) {
                String tgl = null;
                if (rowData[4] != null) {
                    Date tglPenjemputan = (Date) rowData[4];
                    tgl = dateFormat.format(tglPenjemputan);
                }
                tableModel.addRow(new Object[]{
                        rowData[0],
                        rowData[1],
                        rowData[2],
                        rowData[3],
                        tgl,
                        rowData[5]
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data dari database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openStatusPerjalanan(int idPenjemputan, String status) {
        System.out.println("ID Penjemputan: " + idPenjemputan + ", Status: " + status);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            LayarStatusPenjemputan layarStatusPenjemputan = new LayarStatusPenjemputan(idPenjemputan);
            layarStatusPenjemputan.setStatusColor(status);
            frame.add(layarStatusPenjemputan);
            frame.setUndecorated(true);
            frame.pack();
            frame.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
            frame.setVisible(true);
        });
    }

    public JButton getTombolKembali() {
        return backButton;
    }

    class LinkRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            String status = (String) value;
            label.setText("<html><u>" + value + "</u></html>");
            label.setHorizontalAlignment(JLabel.CENTER);

            if ("Pending".equals(status)) {
                label.setForeground(Color.RED);
            } else {
                label.setForeground(new Color(34, 139, 34)); // Hijau
            }

            return label;
        }
    }
}