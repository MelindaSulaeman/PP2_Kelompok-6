package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.LayarMitraController;

public class LayarMitra extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;
    private JButton backToBerandaButton;
    private JPanel contentPanel;
    private  Color warnaLatar = new Color(240, 242, 245);
    private Color warnaPrimer = new Color(76, 153, 76);
    private Color warnaAksen = new Color(45, 136, 45);
    private Color warnaKartu = Color.WHITE;

    public LayarMitra() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(warnaLatar);

        JLabel titleLabel = new JLabel("Daftar Penjemputan", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(warnaPrimer);
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Menambahkan halaman ke CardLayout
        cards.add(createPage("Pending", "Konfirmasi Penjemputan", "Penjemputan", "Page2", "Page3"), "Page1");
        cards.add(createPage("Penjemputan", "Konfirmasi Pengantaran", "Pengantaran", "Page3", "Page1"), "Page2");
        cards.add(createPage("Pengantaran", "Konfirmasi Selesai", "Selesai", "Page1", "Page2"), "Page3");

        contentPanel.add(cards, BorderLayout.CENTER);
        cardLayout.show(cards, "Page1");
        add(contentPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(warnaLatar);
        backToBerandaButton = createButton("Kembali ke Beranda", e -> {

        });
        bottomPanel.add(backToBerandaButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createPage(String statusFilter, String buttonText, String newStatus, String nextPage, String previousPage) {
        JPanel page = new JPanel(new BorderLayout());

        JTable pageTable = setupTable(statusFilter);
        JScrollPane scrollPane = new JScrollPane(pageTable);
        page.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton confirmButton = createButton(buttonText, e -> updateStatus(pageTable, newStatus));

        JButton nextButton = createButton("Next", e -> {
            refreshTable(pageTable, statusFilter);
            cardLayout.show(cards, nextPage);
        });

        JButton backButton = createButton("Back", e -> {
            refreshTable(pageTable, statusFilter);
            cardLayout.show(cards, previousPage);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(nextButton);

        page.add(buttonPanel, BorderLayout.SOUTH);

        return page;
    }

    private JTable setupTable(String statusFilter) {
        LayarMitraController controller = new LayarMitraController();
        DefaultTableModel tableModel = controller.getPenjemputanData(statusFilter);
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    private void refreshTable(JTable table, String statusFilter) {
        LayarMitraController controller = new LayarMitraController();
        DefaultTableModel tableModel = controller.getPenjemputanData(statusFilter);
        table.setModel(tableModel);
    }

    private void updateStatus(JTable pageTable, String newStatus) {
        int selectedRow = pageTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int idPenjemputan = Integer.parseInt(pageTable.getValueAt(selectedRow, 0).toString());
                LayarMitraController controller = new LayarMitraController();
                controller.updatePenjemputanStatus(idPenjemputan, newStatus);

                DefaultTableModel model = (DefaultTableModel) pageTable.getModel();
                model.setValueAt(newStatus, selectedRow, 8); // Kolom 8 untuk Status
                JOptionPane.showMessageDialog(null, "Status berhasil diperbarui menjadi '" + newStatus + "'.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID Penjemputan tidak valid: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silakan pilih baris untuk dikonfirmasi.");
        }
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(warnaAksen);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 30));
        button.addActionListener(actionListener);
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
        return backToBerandaButton;
    }
}