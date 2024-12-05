import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
class BrickMap {  
    public int brickLayout[][];  
    public int brickWidth;  
    public int brickHeight;  
    public BrickMap(int rows, int columns) {  
        brickLayout = new int[rows][columns];  
        for (int i = 0; i < brickLayout.length; i++) {  
            for (int j = 0; j < brickLayout[0].length; j++) {  
                brickLayout[i][j] = 1;  
            }  
        }  
        brickWidth = 540 / columns;  
        brickHeight = 150 / rows;  
    }  
    public void draw(Graphics2D g) {  
        for (int i = 0; i < brickLayout.length; i++) {  
            for (int j = 0; j < brickLayout[0].length; j++) {  
                if (brickLayout[i][j] > 0) {  
                    g.setColor(new Color(0xFFAA33)); // Modified brick color (Orange)  
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);  
                    g.setStroke(new BasicStroke(4));  
                    g.setColor(Color.BLACK);  
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);  
                }  
            }  
        }  
    }  
    public void setBrickValue(int value, int row, int col) {  
        brickLayout[row][col] = value;  
    }  
}  
class GamePanel extends JPanel implements KeyListener, ActionListener {  
    private boolean isPlaying = true;  
    private int playerScore = 0;  
    private int totalBricks = 21;  
    private Timer timer;  
    private int delay = 8;  
    private int playerX = 310;  
    private int ballPosX = 120;  
    private int ballPosY = 350;  
    private int ballXDir = -1;  
    private int ballYDir = -2;  
    private BrickMap brickMap;  
    public GamePanel() {  
        brickMap = new BrickMap(3, 7);  
        addKeyListener(this);  
        setFocusable(true);  
        setFocusTraversalKeysEnabled(false);  
        timer = new Timer(delay, this);  
        timer.start();  
    }  
    public void paint(Graphics g) {  
        // Background color  
        g.setColor(new Color(0xFFFFE0)); // Modified background color (Light Yellow)  
        g.fillRect(1, 1, 692, 592);  
        brickMap.draw((Graphics2D) g);  
        g.fillRect(0, 0, 3, 592);  
        g.fillRect(0, 0, 692, 3);  
        g.fillRect(691, 0, 3, 592);  
        g.setColor(new Color(0x4169E1)); // Modified paddle color (Royal Blue)  
        g.fillRect(playerX, 550, 100, 12);  
        g.setColor(new Color(0xDC143C)); // Modified ball color (Crimson)  
        g.fillOval(ballPosX, ballPosY, 20, 20);  
        g.setColor(Color.black);  
        g.setFont(new Font("MV Boli", Font.BOLD, 25));  
        g.drawString("Score: " + playerScore, 520, 30);  
        if (totalBricks <= 0) {  
            isPlaying = false;  
            ballXDir = 0;  
            ballYDir = 0;  
            g.setColor(new Color(0XFF6464));  
            g.setFont(new Font("MV Boli", Font.BOLD, 30));  
            g.drawString("You Won, Score: " + playerScore, 190, 300);  
            g.setFont(new Font("MV Boli", Font.BOLD, 20));  
            g.drawString("Press Enter to Restart.", 230, 350);  
        }  
        if (ballPosY > 570) {  
            isPlaying = false;  
            ballXDir = 0;  
            ballYDir = 0;  
            g.setColor(Color.BLACK);  
            g.setFont(new Font("MV Boli", Font.BOLD, 30));  
            g.drawString("Game Over, Score: " + playerScore, 190, 300);  
            g.setFont(new Font("MV Boli", Font.BOLD, 20));  
            g.drawString("Press Enter to Restart", 230, 350);  
        }  
        g.dispose();  
    }  
    @Override  
    public void actionPerformed(ActionEvent arg0) {  
        timer.start();  
        if (isPlaying) {  
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {  
                ballYDir = -ballYDir;  
            }  
            for (int i = 0; i < brickMap.brickLayout.length; i++) {  
                for (int j = 0; j < brickMap.brickLayout[0].length; j++) {  
                    if (brickMap.brickLayout[i][j] > 0) {  
                        int brickX = j * brickMap.brickWidth + 80;  
                        int brickY = i * brickMap.brickHeight + 50;  
                        int brickWidth = brickMap.brickWidth;  
                        int brickHeight = brickMap.brickHeight;  
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);  
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);  
                        Rectangle brickRect = rect;  
                        if (ballRect.intersects(brickRect)) {  
                            brickMap.setBrickValue(0, i, j);  
                            totalBricks--;  
                            playerScore += 5;  
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width)  
                                ballXDir = -ballXDir;  
                            else {  
                                ballYDir = -ballYDir;  
                            }  
                        }  
                    }  
                }  
            }  
            ballPosX += ballXDir;  
            ballPosY += ballYDir;  
            if (ballPosX < 0) {  
                ballXDir = -ballXDir;  
            }  
            if (ballPosY < 0) {  
                ballYDir = -ballYDir;  
            }  
            if (ballPosX > 670) {  
                ballXDir = -ballXDir;  
            }  
        }  
        repaint();  
    }  
    @Override  
    public void keyTyped(KeyEvent arg0) {  
    }  
    @Override  
    public void keyPressed(KeyEvent arg0) {  
        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {  
            if (playerX >= 600) {  
                playerX = 600;  
            } else {  
                moveRight();  
            }  
        }  
        if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {  
            if (playerX < 10) {  
                playerX = 10;  
            } else {  
                moveLeft();  
            }  
        }  
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {  
            if (!isPlaying) {  
                isPlaying = true;  
                ballPosX = 120;  
                ballPosY = 350;  
                ballXDir = -1;  
                ballYDir = -2;  
                playerScore = 0;  
                totalBricks = 21;  
                brickMap = new BrickMap(3, 7);  
                repaint();  
            }  
        }  
    }  
    public void moveRight() {  
        isPlaying = true;  
        playerX += 50;  
    }  
    public void moveLeft() {  
        isPlaying = true;  
        playerX -= 50;  
    }  
    @Override  
    public void keyReleased(KeyEvent arg0) {  
    }  
}  
public class BrickBreakerGame {  
    public static void main(String[] args) {  
        JFrame frame = new JFrame();  
        GamePanel gamePanel = new GamePanel();  
        frame.setBounds(10, 10, 700, 600);  
        frame.setTitle("Brick Breaker");  
        frame.setResizable(false);  
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.add(gamePanel);  
    }  
}  
