package TestConTabbedPane;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Main extends JFrame{
	private JLabel dateTimeLabel;
	private JTextArea outputArea;
	
	
	public Main() {
	setTitle("Homepage");	
	setSize(900, 720);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// Label per la data e l'ora
    dateTimeLabel = new JLabel();
    dateTimeLabel.setFont(new Font("Arial", Font.BOLD, 20));
    dateTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
 // Aggiornamento della data e dell'ora in tempo reale
    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date now = new Date();
            dateTimeLabel.setText(dateFormat.format(now));
        }
    });
    timer.start();
	
	//Icona Clienti --------------------
	ImageIcon iconClient = new ImageIcon("C:/Users/chris/Downloads/client.png");
	Image iconaClienti = iconClient.getImage();
	Image clientiScalato = iconaClienti.getScaledInstance(40,40, Image.SCALE_SMOOTH);
	ImageIcon iconaScalata = new ImageIcon(clientiScalato);
	
	//Icona dipendenti-------------
	ImageIcon iconDipendenti = new ImageIcon("C:/Users/chris/Downloads/dipendenti.png");
	Image iconGet = iconDipendenti.getImage();
	Image dipendentiScalato = iconGet.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon icondaDipendentiScalato = new ImageIcon(dipendentiScalato);
	
	//Icona Appuntamenti--------------
	ImageIcon iconApp = new ImageIcon("C:/Users/chris/Downloads/appuntamenti.png");
	Image iconaAppuntamenti = iconApp.getImage();
	Image iconScalata = iconaAppuntamenti.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon iconaAppScalato = new ImageIcon(iconScalata);
	
	//Icona Stanze----------
	ImageIcon iconStanze = new ImageIcon("C:/Users/chris/Downloads/stanze.png");
	Image iconaStanze = iconStanze.getImage();
	Image iconaStanzeScalata = iconaStanze.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon iconaStanzeScalato = new ImageIcon(iconaStanzeScalata);
	
	//Icona attrezzature---------
	ImageIcon iconAttrezzatura = new ImageIcon("C:/Users/chris/Downloads/attrezzatura.png");
	Image iconaAttrezzature = iconAttrezzatura.getImage();
	Image iconaAttrezzaturaScalata = iconaAttrezzature.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon iconaAttrezzatureScalata = new ImageIcon(iconaAttrezzaturaScalata);

	//Icona Trattamenti--------
	ImageIcon iconTrattamenti = new ImageIcon("C:/Users/chris/Downloads/trattamenti.png");
	Image iconaTrattamenti = iconTrattamenti.getImage();
	Image iconaTrattamentiScalata = iconaTrattamenti.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon iconaTrattamentiFinale = new ImageIcon(iconaTrattamentiScalata);
	
	//Icona fattura--------
	ImageIcon iconFatture = new ImageIcon("C:/Users/chris/Downloads/fattura.png");
	Image iconaFatture = iconFatture.getImage();
	Image iconaFatturaScalata = iconaFatture.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	ImageIcon iconaFatturaScalato = new ImageIcon(iconaFatturaScalata);
	
	//Icona tessera------------
		ImageIcon iconTessera = new ImageIcon("C:/Users/chris/Downloads/tessera.png");
		Image iconaTessera = iconTessera.getImage();
		Image iconaTesseraScalata = iconaTessera.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon iconaTessereScalato = new ImageIcon(iconaTesseraScalata);
	 
	String benvenuti = " \nDa qui potrete gestire il vostro Centro Estetico in modo Facile e Veloce.";
	
	JLabel intro = new JLabel(benvenuti, SwingConstants.CENTER);
	intro.setFont(new Font("Arial", Font.BOLD, 20));

	
	
	
	
	// Secondo pannello di bottoni
	JButton clienti = new JButton(iconaScalata);
	clienti.setBackground(Color.decode("#ffd1dc"));
	JButton dipendenti = new JButton(icondaDipendentiScalato);
	dipendenti.setBackground(Color.decode("#ffd1dc"));
	JButton appuntamenti = new JButton(iconaAppScalato);
	appuntamenti.setBackground(Color.decode("#ffd1dc"));
	JButton stanze = new JButton(iconaStanzeScalato);
	stanze.setBackground(Color.decode("#ffd1dc"));
	JButton attrezzatura = new JButton(iconaAttrezzatureScalata);
	attrezzatura.setBackground(Color.decode("#ffd1dc"));
	JButton trattamenti = new JButton(iconaTrattamentiFinale);
	trattamenti.setBackground(Color.decode("#ffd1dc"));
	JButton fatture = new JButton(iconaFatturaScalato);
	fatture.setBackground(Color.decode("#ffd1dc"));
	JButton tessera = new JButton(iconaTessereScalato);
	tessera.setBackground(Color.decode("#ffd1dc"));
	
	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new GridLayout(1, 8));

	
	buttonPanel2.add(clienti);
	buttonPanel2.add(dipendenti);
	buttonPanel2.add(appuntamenti);
	buttonPanel2.add(stanze);
	buttonPanel2.add(attrezzatura);
	buttonPanel2.add(trattamenti);
	buttonPanel2.add(fatture);
	buttonPanel2.add(tessera);
	clienti.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			//setVisible(false);
			Clienti clienti = new Clienti(Main.this);
			clienti.setVisible(true);
		}
	});
		dipendenti.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					//setVisible(false);
					Dipendenti dipendenti = new Dipendenti(Main.this);
					dipendenti.setVisible(true);
				}
			});
		appuntamenti.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//setVisible(false);
				Appuntamenti appuntamenti = new Appuntamenti(Main.this);
				appuntamenti.setVisible(true);
			}
		});
		trattamenti.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						Trattamenti trattamenti = new Trattamenti(Main.this);
						trattamenti.setVisible(true);
					}
				});
		attrezzatura.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Attrezzature attrezzatura  = new Attrezzature(Main.this);
				attrezzatura.setVisible(true);
			}
		});
		stanze.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						Stanze stanze = new Stanze(Main.this);
						stanze.setVisible(true);
					}
				});
		fatture.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Fatture fatture = new Fatture(Main.this);
				fatture.setVisible(true);
			}
		});
		tessera.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Tessere tessera = new Tessere(Main.this);
				tessera.setVisible(true);
			}
		});
	
	
	
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(buttonPanel2, BorderLayout.NORTH);
	getContentPane().add(intro, BorderLayout.CENTER);
	getContentPane().add(dateTimeLabel, BorderLayout.SOUTH);
	
	
	
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main().setVisible(true);
				// Creare un'istanza di Login
		       Login login = new Login();
			}
		});
       
    }
	
	}
	
	

