package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.LayarMitraController;

public class LayarMitra extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;

    public LayarMitra() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Daftar Penjemputan", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(34, 139, 34));
        add(titleLabel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Menambahkan halaman ke CardLayout
        cards.add(createPage("Pending", "Konfirmasi Penjemputan", "Penjemputan", "Page2", "Page3"), "Page1");
        cards.add(createPage("Penjemputan", "Konfirmasi Pengantaran", "Pengantaran", "Page3", "Page1"), "Page2");
        cards.add(createPage("Pengantaran", "Konfirmasi Selesai", "Selesai", "Page1", "Page2"), "Page3");

        add(cards, BorderLayout.CENTER);
        cardLayout.show(cards, "Page1"); // Tampilkan Page1 sebagai halaman awal
    }

    private JPanel createPage(String statusFilter, String buttonText, String newStatus, String nextPage, String previousPage) {
        JPanel page = new JPanel(new BorderLayout());

        JTable pageTable = setupTable(statusFilter); // Tabel dengan data berdasarkan status filter
        JScrollPane scrollPane = new JScrollPane(pageTable);
        page.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Tombol Konfirmasi
        JButton confirmButton = createButton(buttonText, e -> updateStatus(pageTable, newStatus));

        // Tombol Next
        JButton nextButton = createButton("Next", e -> {
            refreshTable(pageTable, statusFilter); // Refresh data saat berpindah halaman
            cardLayout.show(cards, nextPage);
        });

        // Tombol Back
        JButton backButton = createButton("Back", e -> {
            refreshTable(pageTable, statusFilter); // Refresh data saat berpindah halaman
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
        DefaultTableModel tableModel = controller.getPenjemputanData(statusFilter); // Ambil data dari Controller
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true); // Menambahkan fitur sortir tabel
        return table;
    }

    private void refreshTable(JTable table, String statusFilter) {
        LayarMitraController controller = new LayarMitraController();
        DefaultTableModel tableModel = controller.getPenjemputanData(statusFilter); // Ambil data terbaru dari Controller
        table.setModel(tableModel); // Set model baru dengan data terbaru
    }

    private void updateStatus(JTable pageTable, String newStatus) {
        int selectedRow = pageTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Mengambil ID Penjemputan dari kolom pertama
                int idPenjemputan = Integer.parseInt(pageTable.getValueAt(selectedRow, 0).toString());
                LayarMitraController controller = new LayarMitraController();
                controller.updatePenjemputanStatus(idPenjemputan, newStatus);

                // Perbarui data status di JTable
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
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 30));
        button.addActionListener(actionListener);
        return button;
    }
}
