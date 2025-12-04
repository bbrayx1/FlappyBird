import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int boardWidth = Configuracion.SCREEN_WIDTH;
    int boardHeight = Configuracion.SCREEN_HEIGHT;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    String nickname = "Player"; 
    int conteoRegresivo = 3; 
    boolean enCuentaRegresiva = true; 
    Timer timerCuenta; 

    int velocidadActual = Configuracion.VELOCIDAD_DESPLAZAMIENTO;
    int frecuenciaActual = Configuracion.FRECUENCIA_TUBERIAS;

    class Bird {
        int x = Configuracion.AVE_POS_X;
        int y = Configuracion.AVE_POS_Y;
        int width = Configuracion.AVE_ANCHO;
        int height = Configuracion.AVE_ALTO;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    class Pipe {
        int x = boardWidth;
        int y = 0;
        int width = Configuracion.TUBERIA_ANCHO;
        int height = Configuracion.TUBERIA_ALTO;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    Bird bird;
    int velocityY = 0;
    int gravity = Configuracion.GRAVEDAD;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        cargarImagenes();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        placePipeTimer = new Timer(frecuenciaActual, e -> placePipes());
        
        gameLoop = new Timer(1000/60, this); 

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                pedirNickname();
                iniciarCuentaRegresiva();
            }
        });
    }

    void pedirNickname() {
        String input = JOptionPane.showInputDialog(this, "¡Bienvenido! Ingresa tu Nickname:", "Nuevo Juego", JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            nickname = input;
        }
    }

    void iniciarCuentaRegresiva() {
        enCuentaRegresiva = true;
        conteoRegresivo = 3;
        
        timerCuenta = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conteoRegresivo--; 

                if (conteoRegresivo < 0) {
                    timerCuenta.stop(); 
                    enCuentaRegresiva = false; 
                    
                    gameLoop.start(); 
                    placePipeTimer.start();
                }
                repaint(); 
            }
        });
        timerCuenta.start();
    }
    
    void cargarImagenes() {
        try {
            String rutaAve = "resources/bird-" + Configuracion.BIRD_SKIN + ".png";
            String rutaFondo = "resources/bg-" + Configuracion.BACKGROUND_SKIN + ".png";
            String rutaTuberia = "resources/pipe-" + Configuracion.PIPE_SKIN + ".png";
            
            String rutaTopPipe = "resources/toppipe-" + Configuracion.PIPE_SKIN + ".png"; 
         
            backgroundImg = new ImageIcon(getClass().getResource(rutaFondo)).getImage();
            birdImg = new ImageIcon(getClass().getResource(rutaAve)).getImage();
            bottomPipeImg = new ImageIcon(getClass().getResource(rutaTuberia)).getImage();
            topPipeImg = new ImageIcon(getClass().getResource(rutaTopPipe)).getImage(); 
            
        } catch (Exception e) {
            System.out.println("¡ERROR! No se encontró alguna imagen. Usando modo texto.");
        }
    }

    void placePipes() {
        int pipeHeight = Configuracion.TUBERIA_ALTO;
        int randomPipeY = (int) (0 - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
    
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
    
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);
        
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));

        if (enCuentaRegresiva) {
            g.setFont(new Font("Arial", Font.BOLD, 100)); 
            
            if (conteoRegresivo > 0) {
                g.drawString(String.valueOf(conteoRegresivo), boardWidth/2 - 30, boardHeight/2);
            } else {
                g.drawString("¡YA!", boardWidth/2 - 80, boardHeight/2);
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Hola, " + nickname, boardWidth/2 - 100, boardHeight/2 - 100);

        } else if (gameOver) {
            g.drawString("Game Over", 10, 35);
            g.drawString("Score: " + (int)score, 10, 70); 
            
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Presiona ESPACIO para reiniciar", 10, 110);
            
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + (int)score, 10, 35);
        }
    }

    public void move() {
        if (!enCuentaRegresiva) {
            velocityY += gravity;
            bird.y += velocityY;
            bird.y = Math.max(bird.y, 0); 
    
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.x += velocidadActual; 
    
                if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                    score += 0.5; 
                    pipe.passed = true;
                    verificarDificultad(); 
                }
    
                if (collision(bird, pipe)) {
                    gameOver = true;
                }
            }
    
            if (bird.y > boardHeight) {
                gameOver = true;
            }
        }
    }
    
    void verificarDificultad() {
        if (Configuracion.POINTS_TO_ACCELERATE > 0 && score % Configuracion.POINTS_TO_ACCELERATE == 0) {
            velocidadActual -= 1; 
            System.out.println("¡Nivel subido! Nueva velocidad: " + velocidadActual);
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   
               a.x + a.width > b.x &&   
               a.y < b.y + b.height &&  
               a.y + a.height > b.y;    
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!enCuentaRegresiva) {
                velocityY = Configuracion.VELOCIDAD_SALTO;
            }

            if (gameOver) {
                bird.y = Configuracion.AVE_POS_Y;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                velocidadActual = Configuracion.VELOCIDAD_DESPLAZAMIENTO;
                
                cargarImagenes(); 
                
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}