package PP2_Kelompok_6.src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public class LayarKonfirmasi extends JPanel {
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaHeader = new Color(34, 197, 94);

    public LayarKonfirmasi() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private void initComponents() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(warnaHeader);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 40));

        JPanel headerContent = new JPanel(new BorderLayout());
        headerContent.setBackground(warnaHeader);
        headerContent.setBorder(new EmptyBorder(0, 15, 0, 15));

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

        JLabel titleLabel = new JLabel("Pending Approval", JLabel.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        headerContent.add(exitButton, BorderLayout.WEST);
        headerContent.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(headerContent);

        // Main Content Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(warnaLatar);
        mainPanel.setBorder(new EmptyBorder(50, 20, 20, 20));

        // Icon
        ImageIcon icon = new ImageIcon("/icons/point.png");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text Labels
        JLabel titleText = new JLabel("Menunggu Konfirmasi");
        titleText.setFont(new Font("Montserrat", Font.BOLD, 16));
        titleText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel") {
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
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });

        // Add components with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(iconLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(titleText);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(cancelButton);
        mainPanel.add(Box.createVerticalGlue());

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}
