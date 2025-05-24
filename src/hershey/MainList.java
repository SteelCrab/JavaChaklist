package hershey;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import java.util.Date;
import java.text.SimpleDateFormat;

// Modern Theme Configuration
class ModernTheme {
    // Color Palettes
    public static class BlueTheme {
        public static final Color PRIMARY = new Color(33, 150, 243);
        public static final Color PRIMARY_DARK = new Color(21, 101, 192);
        public static final Color PRIMARY_LIGHT = new Color(144, 202, 249);
        public static final Color ACCENT = new Color(255, 193, 7);
        public static final Color BACKGROUND = new Color(250, 250, 250);
        public static final Color SURFACE = Color.WHITE;
        public static final Color TEXT_PRIMARY = new Color(33, 33, 33);
        public static final Color TEXT_SECONDARY = new Color(117, 117, 117);
        public static final Color DIVIDER = new Color(224, 224, 224);
    }
    
    public static class PinkTheme {
        public static final Color PRIMARY = new Color(233, 30, 99);
        public static final Color PRIMARY_DARK = new Color(173, 20, 87);
        public static final Color PRIMARY_LIGHT = new Color(248, 187, 208);
        public static final Color ACCENT = new Color(255, 152, 0);
        public static final Color BACKGROUND = new Color(250, 250, 250);
        public static final Color SURFACE = Color.WHITE;
        public static final Color TEXT_PRIMARY = new Color(33, 33, 33);
        public static final Color TEXT_SECONDARY = new Color(117, 117, 117);
        public static final Color DIVIDER = new Color(224, 224, 224);
    }
    
    // Fonts
    public static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font CAPTION_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Dimensions
    public static final int CORNER_RADIUS = 12;
    public static final int PADDING = 16;
    public static final int SPACING = 8;
}

// Custom UI Components
class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color backgroundColor;
    
    public RoundedPanel(int cornerRadius, Color backgroundColor) {
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }
    
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        g2d.dispose();
        super.paintComponent(g);
    }
}

class ModernButton extends JButton {
    private Color baseColor;
    private Color currentColor;
    private Color hoverColor;
    private Color textColor;
    private boolean isElevated;
    
    public ModernButton(String text, Color backgroundColor, Color textColor, boolean isElevated) {
        super(text);
        this.baseColor = backgroundColor;
        this.currentColor = backgroundColor;
        this.textColor = textColor;
        this.isElevated = isElevated;
        this.hoverColor = backgroundColor.darker();
        
        setupButton();
    }
    
    private void setupButton() {
        setForeground(textColor);
        setFont(ModernTheme.BODY_FONT);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentColor = hoverColor;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                currentColor = baseColor;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isElevated) {
            // Draw shadow
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 
                            ModernTheme.CORNER_RADIUS, ModernTheme.CORNER_RADIUS);
        }
        
        g2d.setColor(currentColor);
        g2d.fillRoundRect(0, 0, getWidth() - (isElevated ? 2 : 0), getHeight() - (isElevated ? 2 : 0), 
                         ModernTheme.CORNER_RADIUS, ModernTheme.CORNER_RADIUS);
        
        g2d.dispose();
        super.paintComponent(g);
    }
}

class ModernTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor;
    
    public ModernTextField(String placeholder) {
        this.placeholder = placeholder;
        this.placeholderColor = ModernTheme.BlueTheme.TEXT_SECONDARY;
        
        setupTextField();
    }
    
    private void setupTextField() {
        setFont(ModernTheme.BODY_FONT);
        setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(ModernTheme.CORNER_RADIUS, ModernTheme.BlueTheme.DIVIDER),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        setBackground(ModernTheme.BlueTheme.SURFACE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (getText().isEmpty() && !hasFocus()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(placeholderColor);
            g2d.setFont(getFont());
            
            FontMetrics fm = g2d.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            
            g2d.drawString(placeholder, x, y);
            g2d.dispose();
        }
    }
}

class RoundedBorder implements Border {
    private int radius;
    private Color color;
    
    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1, 1);
    }
    
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }
}

// Modern Task Item Component
class TaskItem extends RoundedPanel {
    private String taskText;
    private boolean isCompleted;
    private boolean isStarred;
    private JLabel textLabel;
    private ModernButton completeButton;
    private ModernButton starButton;
    private TaskItemListener listener;
    
    public interface TaskItemListener {
        void onTaskCompleted(TaskItem task);
        void onTaskStarred(TaskItem task, boolean starred);
    }
    
    public TaskItem(String taskText, TaskItemListener listener) {
        super(ModernTheme.CORNER_RADIUS, ModernTheme.BlueTheme.SURFACE);
        this.taskText = taskText;
        this.listener = listener;
        this.isCompleted = false;
        this.isStarred = false;
        
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(ModernTheme.SPACING, 0));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        
        // Complete button
        completeButton = new ModernButton("✓", ModernTheme.BlueTheme.PRIMARY, Color.WHITE, false);
        completeButton.setPreferredSize(new Dimension(32, 32));
        completeButton.addActionListener(e -> {
            isCompleted = true;
            listener.onTaskCompleted(this);
        });
        
        // Task text
        textLabel = new JLabel(taskText);
        textLabel.setFont(ModernTheme.BODY_FONT);
        textLabel.setForeground(ModernTheme.BlueTheme.TEXT_PRIMARY);
        
        // Star button
        starButton = new ModernButton("★", Color.LIGHT_GRAY, ModernTheme.BlueTheme.TEXT_SECONDARY, false);
        starButton.setPreferredSize(new Dimension(32, 32));
        starButton.addActionListener(e -> {
            isStarred = !isStarred;
            starButton.setForeground(isStarred ? ModernTheme.BlueTheme.ACCENT : ModernTheme.BlueTheme.TEXT_SECONDARY);
            listener.onTaskStarred(this, isStarred);
        });
        
        add(completeButton, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
        add(starButton, BorderLayout.EAST);
    }
    
    public String getTaskText() {
        return taskText;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public boolean isStarred() {
        return isStarred;
    }
    
    public void setCompletedStyle() {
        setBackgroundColor(new Color(220, 255, 220));
        textLabel.setForeground(ModernTheme.BlueTheme.TEXT_SECONDARY);
    }
}

// Modern List Panel
class ModernListPanel extends JPanel implements TaskItem.TaskItemListener {
    private Vector<TaskItem> activeTasks;
    private Vector<TaskItem> completedTasks;
    private JPanel activeTasksPanel;
    private JPanel completedTasksPanel;
    private ModernTextField inputField;
    private ModernButton addButton;
    private JLabel dateLabel;
    private String tabName;
    private Color themeColor;
    
    public ModernListPanel(String tabName, boolean isBlueTheme) {
        this.tabName = tabName;
        this.themeColor = isBlueTheme ? ModernTheme.BlueTheme.PRIMARY : ModernTheme.PinkTheme.PRIMARY;
        this.activeTasks = new Vector<>();
        this.completedTasks = new Vector<>();
        
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(0, ModernTheme.SPACING));
        setBackground(ModernTheme.BlueTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        // Header with date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        dateLabel = new JLabel("Today - " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()));
        dateLabel.setFont(ModernTheme.HEADING_FONT);
        dateLabel.setForeground(themeColor);
        headerPanel.add(dateLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content area
        JPanel contentPanel = new JPanel(new BorderLayout(0, 24));
        contentPanel.setOpaque(false);
        
        // Input area
        JPanel inputPanel = createInputPanel();
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Tasks area
        JPanel tasksContainer = new JPanel(new BorderLayout(0, 16));
        tasksContainer.setOpaque(false);
        
        // Active tasks
        activeTasksPanel = new JPanel();
        activeTasksPanel.setLayout(new BoxLayout(activeTasksPanel, BoxLayout.Y_AXIS));
        activeTasksPanel.setOpaque(false);
        
        JScrollPane activeScrollPane = new JScrollPane(activeTasksPanel);
        activeScrollPane.setBorder(null);
        activeScrollPane.setOpaque(false);
        activeScrollPane.getViewport().setOpaque(false);
        
        // Completed tasks
        completedTasksPanel = new JPanel();
        completedTasksPanel.setLayout(new BoxLayout(completedTasksPanel, BoxLayout.Y_AXIS));
        completedTasksPanel.setOpaque(false);
        
        JScrollPane completedScrollPane = new JScrollPane(completedTasksPanel);
        completedScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(),
            "Completed",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            ModernTheme.CAPTION_FONT,
            ModernTheme.BlueTheme.TEXT_SECONDARY
        ));
        completedScrollPane.setOpaque(false);
        completedScrollPane.getViewport().setOpaque(false);
        
        tasksContainer.add(activeScrollPane, BorderLayout.CENTER);
        tasksContainer.add(completedScrollPane, BorderLayout.SOUTH);
        
        contentPanel.add(tasksContainer, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        RoundedPanel inputPanel = new RoundedPanel(ModernTheme.CORNER_RADIUS, ModernTheme.BlueTheme.SURFACE);
        inputPanel.setLayout(new BorderLayout(ModernTheme.SPACING, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        
        inputField = new ModernTextField("Add a new task...");
        inputField.addActionListener(e -> addTask());
        
        addButton = new ModernButton("Add", themeColor, Color.WHITE, true);
        addButton.addActionListener(e -> addTask());
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        return inputPanel;
    }
    
    private void addTask() {
        String taskText = inputField.getText().trim();
        if (!taskText.isEmpty()) {
            TaskItem taskItem = new TaskItem(taskText, this);
            activeTasks.add(taskItem);
            activeTasksPanel.add(taskItem);
            activeTasksPanel.add(Box.createVerticalStrut(ModernTheme.SPACING));
            
            inputField.setText("");
            revalidate();
            repaint();
        }
    }
    
    @Override
    public void onTaskCompleted(TaskItem task) {
        activeTasks.remove(task);
        completedTasks.add(task);
        
        activeTasksPanel.remove(task);
        completedTasksPanel.add(task);
        completedTasksPanel.add(Box.createVerticalStrut(ModernTheme.SPACING));
        
        // Add completed styling
        task.setCompletedStyle();
        
        revalidate();
        repaint();
    }
    
    @Override
    public void onTaskStarred(TaskItem task, boolean starred) {
        // Handle star functionality
        System.out.println("Task " + task.getTaskText() + " starred: " + starred);
    }
}

// Modern Tab Manager
class ModernTabManager extends JFrame {
    private JTabbedPane tabbedPane;
    private List<ModernListPanel> listPanels;
    private boolean isBlueTheme = true;
    
    public ModernTabManager() {
        listPanels = new ArrayList<>();
        setupUI();
        createDefaultTab();
    }
    
    private void setupUI() {
        setTitle("Modern To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Modern menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ModernTheme.BODY_FONT);
        tabbedPane.setBackground(ModernTheme.BlueTheme.BACKGROUND);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(ModernTheme.BlueTheme.SURFACE);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ModernTheme.BlueTheme.DIVIDER));
        
        JMenu tabMenu = new JMenu("Tabs");
        tabMenu.setFont(ModernTheme.BODY_FONT);
        
        JMenuItem addTabItem = new JMenuItem("Add New Tab");
        addTabItem.setFont(ModernTheme.BODY_FONT);
        addTabItem.addActionListener(e -> showAddTabDialog());
        
        JMenu themeMenu = new JMenu("Theme");
        themeMenu.setFont(ModernTheme.BODY_FONT);
        
        JMenuItem blueThemeItem = new JMenuItem("Blue Theme");
        blueThemeItem.setFont(ModernTheme.BODY_FONT);
        blueThemeItem.addActionListener(e -> switchTheme(true));
        
        JMenuItem pinkThemeItem = new JMenuItem("Pink Theme");
        pinkThemeItem.setFont(ModernTheme.BODY_FONT);
        pinkThemeItem.addActionListener(e -> switchTheme(false));
        
        tabMenu.add(addTabItem);
        themeMenu.add(blueThemeItem);
        themeMenu.add(pinkThemeItem);
        
        menuBar.add(tabMenu);
        menuBar.add(themeMenu);
        
        return menuBar;
    }
    
    private void showAddTabDialog() {
        String tabName = JOptionPane.showInputDialog(this, "Enter tab name:", "Add New Tab", JOptionPane.PLAIN_MESSAGE);
        if (tabName != null && !tabName.trim().isEmpty()) {
            addTab(tabName.trim());
        }
    }
    
    private void addTab(String tabName) {
        ModernListPanel listPanel = new ModernListPanel(tabName, isBlueTheme);
        listPanels.add(listPanel);
        tabbedPane.addTab(tabName, listPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }
    
    private void createDefaultTab() {
        addTab("My Tasks");
    }
    
    private void switchTheme(boolean isBlue) {
        this.isBlueTheme = isBlue;
        // Theme switching logic would go here
        // For now, new tabs will use the selected theme
    }
}

// Main class - 패키지 없이 직접 실행 가능
public class MainList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set system look and feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new ModernTabManager().setVisible(true);
        });
    }
}