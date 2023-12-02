package com.self.learn;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;


public class Panel extends JPanel implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private int timeInterval = 400;
	private int score = 0;
	private boolean collision = false;
	private boolean game_over = false;
	private boolean inMenu = true;
	private boolean isPlayButtonPressed = false;
	private boolean isQuitButtonPressed = false;
	private boolean isPaused = false;


	// Initializing Objects
	private Matador matador = new Matador();	
	private Timer timer = new Timer(timeInterval, new TimerListener());
	private ArrayList<Bull> list = new ArrayList<Bull>();
	private Random random = new Random();

	private Image logo = new ImageIcon("logo.png").getImage(); // logo
	private Image background = new ImageIcon("bg.png").getImage(); // background image
	private Image gameOver = new ImageIcon("gameover.png").getImage(); // for game over prompt

	// Panel constructor
	public Panel() {
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.white));
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseListener(this);

		timer.start();
	}

	// Checking collision between Bulls and Matador
	public void checkCollision() {
		int matadorX1 = matador.getxPos();
		int matadorX2 = matadorX1 + matador.getWidth();
		int matadorY1 = matador.getyPos();
		int matadorY2 = matadorY1 + matador.getHeight();
		for (Bull bull : list) {
			int bullX1 = bull.getxPos();
			int bullX2 = bullX1 + bull.getLato();
			int bullY1 = bull.getyPos();
			// Simply compare the bounds of each component
			if (((bullX1 > matadorX1 && bullX1 < matadorX2) || (bullX2 > matadorX1 && bullX2 < matadorX2))
					&& (bullY1 > matadorY1 && bullY1 < matadorY2)) {
				timer.stop();
				this.removeKeyListener(this); // stop
				collision = true; // collision detected
			}
		}
	}
	
//	private void playGameOverSound() {
//	    try {
//	        File audioFile = new File("Game_Over.wav"); // Replace with your game over sound file
//	        Clip gameOverClip = AudioSystem.getClip();
//	        gameOverClip.open(AudioSystem.getAudioInputStream(audioFile));
//	        gameOverClip.start();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}


	// Paints components
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, null);

		if (!inMenu && !game_over) {
			matador.drawMatador(g);
			for (Bull bull : list) {
				bull.drawBull(g);
			}
		}
		repaint();

		// prompt the game over image if collision is true
		if (collision) {
			g.drawImage(gameOver, 95, 150, 150, 150, null);
			game_over = true;
			inMenu = true;
//			playGameOverSound();
		}

		if (inMenu && !collision)
			g.drawImage(logo, 10, 145, 293, 180, null); // show game logo

		if (game_over || inMenu) {

			int playButtonX = getWidth() / 2 - 50;
			int playButtonY = getHeight() / 2 + 50;
			int quitButtonX = playButtonX;
			int quitButtonY = playButtonY + 50;

			int ButtonWidth = 100;
			int ButtonHeight = 40;

			// Play Button
			if (isPlayButtonPressed) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.CYAN);
			}
			g.fillRect(playButtonX, playButtonY, ButtonWidth, ButtonHeight);
			g.drawRect(playButtonX, playButtonY, ButtonWidth, ButtonHeight);
			g.setFont(new Font("Arial", Font.BOLD, 18));

			String playText = "Play";
			int playTextWidth = g.getFontMetrics().stringWidth(playText);

			int playTextX = playButtonX + ButtonWidth / 2 - playTextWidth / 2; // Calculate the x-coordinate for
																				// centering the text
			int playTextY = playButtonY + ButtonHeight / 2 + 5; // Adjust the y-coordinate for centering the text

			if (isPlayButtonPressed) {
				g.setColor(Color.RED);
				inMenu = false;
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString(playText, playTextX, playTextY);

			// Quit Button
			if (isQuitButtonPressed) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.CYAN);
			}
			g.fillRect(quitButtonX, quitButtonY, ButtonWidth, ButtonHeight);
			g.drawRect(quitButtonX, quitButtonY, ButtonWidth, ButtonHeight);
			g.setFont(new Font("Arial", Font.BOLD, 18));

			String quitText = "Quit";
			int quitTextWidth = g.getFontMetrics().stringWidth(quitText);
			int quitTextX = quitButtonX + ButtonWidth / 2 - quitTextWidth / 2;
			int quitTextY = quitButtonY + ButtonHeight / 2 + 5;

			if (isQuitButtonPressed) {
				g.setColor(Color.RED);
				System.exit(0);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString(quitText, quitTextX, quitTextY);
		}
		if (isPaused) {
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("Arial", Font.BOLD, 24));
	        String pauseMessage = "Paused";
	        int messageWidth = g.getFontMetrics().stringWidth(pauseMessage);
	        g.drawString(pauseMessage, (getWidth() - messageWidth) / 2, getHeight() / 2);
	    }
		

		// Displays the score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		String scoreText = "Score: " + score * timeInterval / 1000;
		g.drawString(scoreText, 10, 30);
	}

	private void resetGame() {
		matador.setxPos(124);
		matador.setyPos(520);

		// Clear lists
		list.clear();

		// Initialize game state
		collision = false;
		game_over = false;
		score = 0;
		timer.start();

		this.addKeyListener(this);
		this.addMouseListener(this);
	}
	//Pause method
	public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop(); // Pauses the game timer
        } else {
            timer.start(); // Resumes the game timer
        }
    }

	// Key Listener
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			matador.moveUp();
			checkCollision();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			matador.moveDown();
			checkCollision();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			matador.moveLeft();
			checkCollision();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			matador.moveRight();
			checkCollision();
		}
		//Pause
		if (keyCode == 'P' || keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_NUMPAD0) {
	        togglePause();
	    }
		
	}
	private void playClickSound() {
	    try {
	        File clickFile = new File("Click.wav"); // Replace with the path to your click sound file
	        Clip clickClip = AudioSystem.getClip();
	        clickClip.open(AudioSystem.getAudioInputStream(clickFile));
	        clickClip.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Mouse Listener
	@Override
	public void mouseClicked(MouseEvent e) {
		if (game_over) {
			// Handle menu options
			int playButtonX = getWidth() / 2 - 50;
			int playButtonY = getHeight() / 2 + 50;
			int quitButtonX = playButtonX;
			int quitButtonY = playButtonY + 50;

			int ButtonWidth = 100;
			int ButtonHeight = 40;

			if (e.getX() >= playButtonX && e.getX() <= playButtonX + ButtonWidth &&
					e.getY() >= playButtonY && e.getY() <= playButtonY + ButtonHeight) {
				resetGame();
			} else if (e.getX() >= quitButtonX && e.getX() <= quitButtonX + ButtonWidth &&
					e.getY() >= quitButtonY && e.getY() <= quitButtonY + ButtonHeight) {
				System.exit(0);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    int playButtonX = getWidth() / 2 - 50;
	    int playButtonY = getHeight() / 2 + 50;
	    int quitButtonX = playButtonX;
	    int quitButtonY = playButtonY + 50;

	    int ButtonWidth = 100;
	    int ButtonHeight = 40;

	    if (e.getX() >= playButtonX && e.getX() <= playButtonX + ButtonWidth &&
	            e.getY() >= playButtonY && e.getY() <= playButtonY + ButtonHeight) {
	        isPlayButtonPressed = true;
	        playClickSound(); // Play click sound when the play button is clicked
	    } else if (e.getX() >= quitButtonX && e.getX() <= quitButtonX + ButtonWidth &&
	            e.getY() >= quitButtonY && e.getY() <= quitButtonY + ButtonHeight) {
	        isQuitButtonPressed = true;
	        playClickSound(); // Play click sound when the quit button is clicked
	    }
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		int playButtonX = getWidth() / 2 - 50;
		int playButtonY = getHeight() / 2 + 50;
		int quitButtonX = playButtonX;
		int quitButtonY = playButtonY + 50;

		int ButtonWidth = 100;
		int ButtonHeight = 40;

		if (e.getX() >= playButtonX && e.getX() <= playButtonX + ButtonWidth &&
				e.getY() >= playButtonY && e.getY() <= playButtonY + ButtonHeight) {
			isPlayButtonPressed = false;
		} else if (e.getX() >= quitButtonX && e.getX() <= quitButtonX + ButtonWidth &&
				e.getY() >= quitButtonY && e.getY() <= quitButtonY + ButtonHeight) {
			isQuitButtonPressed = false;
		}
	}

	// Timer Listener to generate bulls randomly
	public class TimerListener implements ActionListener {
	    private int bullSpacing = 3; // Adjust the spacing between bulls
	    private int counter = 0; // Counter for bullsSpacing

	    private File bullSoundFile = new File("BullSound.wav");
	    private Clip bullSoundClip;

	    public TimerListener() {
	        try {
	            bullSoundClip = AudioSystem.getClip();
	            bullSoundClip.open(AudioSystem.getAudioInputStream(bullSoundFile));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (!isPaused && !inMenu) {
	            counter++;
	            // Check if the counter has reached the desired spacing value
	            if (counter >= bullSpacing) {
	                Integer x = random.nextInt(275);
	                list.add(new Bull(x));
	                counter = 0; // Reset the counter

	                // Play the bull sound when a new bull is added
	                if (bullSoundClip != null) {
	                    bullSoundClip.setFramePosition(0); // Rewind the clip to the beginning
	                    bullSoundClip.start(); // Play the sound
	                }
	            }
	            for (Bull bull : list) {
	                checkCollision(); // check collisions every time
	                bull.moveDown();
	            }
	            score++;
	        }
	    }
	    

	    // Properly close the resources when the panel is being destroyed
	    public void closeResources() {
	        if (bullSoundClip != null) {
	            bullSoundClip.close();
	        }
	    }
	    
	}
	

}



