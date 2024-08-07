import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class BrickBreakerGame extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int BALL_SIZE = 15;
    private static final int BRICK_WIDTH = 75;
    private static final int BRICK_HEIGHT = 30;
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 10;
    private static final int TIMER_DELAY = 10;
    
    private Timer timer;
    private Rectangle paddle;
    private Rectangle ball;
    private int ballXVelocity = 1;
    private int ballYVelocity = -1;
    private List<Rectangle> bricks;
    private int score = 0;

    public BrickBreakerGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        
        paddle = new Rectangle(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 30, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Rectangle(WIDTH / 2 - BALL_SIZE / 2, HEIGHT / 2 - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE);
        
        bricks = new ArrayList<>();
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLS; col++) {
                bricks.add(new Rectangle(col * (BRICK_WIDTH + 5) + 30, row * (BRICK_HEIGHT + 5) + 30, BRICK_WIDTH, BRICK_HEIGHT));
            }
        }
        
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddle.x -= 10;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    paddle.x += 10;
                }
                paddle.x = Math.max(0, Math.min(WIDTH - PADDLE_WIDTH, paddle.x));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.WHITE);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);

        g.setColor(Color.RED);
        for (Rectangle brick : bricks) {
            g.fillRect(brick.x, brick.y, brick.width, brick.height);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ball.x += ballXVelocity;
        ball.y += ballYVelocity;

        // Ball collision with walls
        if (ball.x <= 0 || ball.x >= WIDTH - BALL_SIZE) {
            ballXVelocity = -ballXVelocity;
        }
        if (ball.y <= 0) {
            ballYVelocity = -ballYVelocity;
        }
        if (ball.y >= HEIGHT) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over. Your score: " + score);
            System.exit(0);
        }

        // Ball collision with paddle
        if (ball.intersects(paddle)) {
            ballYVelocity = -ballYVelocity;
            ball.y = paddle.y - BALL_SIZE;
        }

        // Ball collision with bricks
        List<Rectangle> bricksToRemove = new ArrayList<>();
        for (Rectangle brick : bricks) {
            if (ball.intersects(brick)) {
                ballYVelocity = -ballYVelocity;
                bricksToRemove.add(brick);
                score++;
            }
        }
        bricks.removeAll(bricksToRemove);

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Game");
        BrickBreakerGame game = new BrickBreakerGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
