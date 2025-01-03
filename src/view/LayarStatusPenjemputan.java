package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class LayarStatusPenjemputan extends JPanel {
    private Color warnaLatar = new Color(240, 242, 245);
    private Color warnaKartu = Color.WHITE;
    private Color warnaHeader = new Color(34, 197, 94);
    private Color warnaText = new Color(51, 51, 51);
    private Color warnaSubText = new Color(115, 115, 115);

    public LayarStatusPenjemputan() {
        setLayout(new BorderLayout());
        setBackground(warnaLatar);
        initComponents();
    }

    private static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
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

    private void initComponents() {
        // Header Panel
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
            Window window = SwingUtilities.getWindowAncestor(LayarStatusPenjemputan.this);
            if (window != null) {
                window.dispose();
            }
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

        JLabel statusTitle = new JLabel("Detail Status Penjemputan");
        statusTitle.setFont(new Font("Montserrat", Font.BOLD, 18));
        statusTitle.setForeground(warnaText);

        JPanel mapPanel = new JPanel();
        mapPanel.setPreferredSize(new Dimension(0, 200));
        mapPanel.setBackground(Color.LIGHT_GRAY);
        mapPanel.setBorder(new RoundedBorder(10, Color.GRAY));

        JPanel timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(warnaKartu);
        timelinePanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        addTimelineItem(timelinePanel, "Pending", "Permintaan dibuat", "15.40", "order");
        addTimelineItem(timelinePanel, "Pick-up", "Driver Menuju Lokasi", "15.41", "pickup");
        addTimelineItem(timelinePanel, "Delivery", "Sampah Sedang di kirim ke bank sampah", "15.55", "delivery");
        addTimelineItem(timelinePanel, "Arrived", "Sampah telah sampai di bank sampah", "16.08", "arrived");

        JPanel statusContent = new JPanel();
        statusContent.setLayout(new BoxLayout(statusContent, BoxLayout.Y_AXIS));
        statusContent.setBackground(warnaKartu);
        statusContent.add(statusTitle);
        statusContent.add(Box.createRigidArea(new Dimension(0, 15)));
        statusContent.add(mapPanel);
        statusContent.add(timelinePanel);

        statusCard.add(statusContent);
        mainContent.add(statusCard);

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
    }

    private void addTimelineItem(JPanel container, String title, String description, String time, String iconName) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBackground(warnaKartu);
        itemPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        ImageIcon icon = new ImageIcon("Testing/src/resources/" + iconName + ".png");
        Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 10));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(warnaKartu);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        titleLabel.setForeground(warnaText);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        descLabel.setForeground(warnaSubText);

        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descLabel, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        timeLabel.setForeground(warnaSubText);

        itemPanel.add(iconLabel, BorderLayout.WEST);
        itemPanel.add(textPanel, BorderLayout.CENTER);
        itemPanel.add(timeLabel, BorderLayout.EAST);

        container.add(itemPanel);

        if (!iconName.equals("arrived")) {
            JPanel linePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(76, 153, 76));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(12, 0, 12, getHeight());
                }
            };
            linePanel.setPreferredSize(new Dimension(24, 20));
            linePanel.setBackground(warnaKartu);
            container.add(linePanel);
        }
    }
}