package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.HashMap;
import java.util.Map;
import controller.LayarStatusPenjemputanController;

public class LayarStatusPenjemputan extends JPanel {
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaKartu = Color.WHITE;
    private Color warnaHeader = new Color(34, 197, 94);
    private Color warnaText = new Color(51, 51, 51);
    private Color warnaSubText = new Color(115, 115, 115);
    private Map<String, Color> statusColorMap = new HashMap<>();
    private JPanel timelinePanel;
    private String status;
    private LayarStatusPenjemputanController controller;
    private JButton cancelButton;
    private int idPenjemputan;

    public LayarStatusPenjemputan(int idPenjemputan) {
        this.idPenjemputan = idPenjemputan;
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        setupStatusColorMap();
        initComponents();
        controller = new LayarStatusPenjemputanController(this, idPenjemputan);

        String currentStatus = controller.getCurrentStatus(idPenjemputan);
        setStatusColor(currentStatus);
    }

    private void setupStatusColorMap() {
        statusColorMap.put("Pending", Color.RED);
        statusColorMap.put("Penjemputan", new Color(34, 139, 34));
        statusColorMap.put("Pengantaran", new Color(34, 139, 34));
        statusColorMap.put("Selesai", new Color(34, 139, 34));
    }

    public void setStatusColor(String status) {
        this.status = status;
        if (timelinePanel != null) {
            applyStatusColor();
            setButtonCancel();
            revalidate();
            repaint();
        }
    }

    private void applyStatusColor() {
        if (status != null && timelinePanel != null) {
            Component[] components = timelinePanel.getComponents();
            boolean shouldColor = true;

            for (Component component : components) {
                if (component instanceof JPanel) {
                    JPanel itemPanel = (JPanel) component;
                    Component[] itemComponents = itemPanel.getComponents();

                    for (Component comp : itemComponents) {
                        if (comp instanceof JPanel) {
                            JPanel textPanel = (JPanel) comp;
                            Component[] textComponents = textPanel.getComponents();

                            for (Component textComp : textComponents) {
                                if (textComp instanceof JLabel) {
                                    JLabel label = (JLabel) textComp;
                                    String labelText = label.getText();

                                    if (labelText != null && statusColorMap.containsKey(labelText)) {
                                        if (shouldColor) {
                                            label.setForeground(new Color(34, 139, 34));
                                        } else {
                                            label.setForeground(warnaSubText);
                                        }

                                        if (labelText.equalsIgnoreCase(status)) {
                                            shouldColor = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setButtonCancel() {
        cancelButton.setVisible("Pending".equalsIgnoreCase(status));
    }

    private void initComponents() {

        JPanel headerTitle = new JPanel(new BorderLayout());
        headerTitle.setBackground(warnaHeader);
        headerTitle.setPreferredSize(new Dimension(getWidth(), 40));

        JPanel headerContent = new JPanel(new BorderLayout());
        headerContent.setBackground(warnaHeader);
        headerContent.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel titleLabel = new JLabel("Status Penjemputan", JLabel.CENTER);
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
            controller.close();
        });

        headerContent.add(exitButton, BorderLayout.WEST);
        headerContent.add(titleLabel, BorderLayout.CENTER);
        headerTitle.add(headerContent, BorderLayout.CENTER);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(warnaLatar);
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));

        RoundedPanel statusCard = new RoundedPanel(new BorderLayout(), 20);
        statusCard.setBackground(warnaKartu);
        statusCard.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel statusTitle = new JLabel("Detail Status Penjemputan", JLabel.CENTER);
        statusTitle.setFont(new Font("Montserrat", Font.BOLD, 18));
        statusTitle.setForeground(warnaText);

        JPanel statusContent = new JPanel(new BorderLayout());
        statusContent.setBackground(warnaKartu);
        statusContent.add(statusTitle, BorderLayout.NORTH);
        statusContent.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.CENTER);

        timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(warnaKartu);
        timelinePanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        addTimelineItem(timelinePanel, "Pending", "Permintaan dibuat", "order");
        addTimelineItem(timelinePanel, "Penjemputan", "Driver Menuju Lokasi", "pickup");
        addTimelineItem(timelinePanel, "Pengantaran", "Sampah Sedang di kirim ke bank sampah", "delivery");
        addTimelineItem(timelinePanel, "Selesai", "Sampah telah sampai di bank sampah", "arrived");

        statusContent.add(timelinePanel, BorderLayout.SOUTH);
        statusCard.add(statusContent);
        mainContent.add(statusCard);

        cancelButton = new JButton("Cancel") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        cancelButton.setOpaque(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBackground(new Color(220, 38, 38));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Montserrat", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setBorder(new EmptyBorder(5, 15, 5, 15));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah anda yakin ingin membatalkan penjemputan?",
                    "Konfirmasi Pembatalan",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.cancelPenjemputan(idPenjemputan)) {
                    JOptionPane.showMessageDialog(this, "Penjemputan berhasil dibatalkan");
                    Window window = SwingUtilities.getWindowAncestor(this);
                    if (window != null) {
                        window.dispose();
                    }
                }
            }
        });
        cancelButton.setVisible(false);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        mainContent.add(cancelButton);

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.setBackground(warnaLatar);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(76, 153, 76);
                this.trackColor = warnaLatar;
            }
        });

        add(headerTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(500, 600));
    }

    private void addTimelineItem(JPanel container, String title, String description, String iconName) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridBagLayout());
        itemPanel.setBackground(warnaKartu);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icons/" + iconName + ".png"));
        Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setBackground(warnaKartu);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        titleLabel.setForeground(warnaText);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        descLabel.setForeground(warnaSubText);

        textPanel.add(titleLabel);
        textPanel.add(descLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        itemPanel.add(iconLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 20);
        itemPanel.add(textPanel, gbc);

        container.add(itemPanel);

        if (!iconName.equals("arrived")) {
            JPanel linePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor("Pending".equals(status) ? Color.RED : new Color(76, 153, 76));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(32, 0, 32, getHeight());
                }
            };
            linePanel.setPreferredSize(new Dimension(getWidth(), 20));
            linePanel.setBackground(warnaKartu);
            container.add(linePanel);
        }
    }

    private static class RoundedPanel extends JPanel {
        private int radius;

        RoundedPanel(LayoutManager layout, int radius) {
            super(layout);
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
        }
    }
}
