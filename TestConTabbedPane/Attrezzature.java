package TestConTabbedPane;


	import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Attrezzature extends JFrame {
	
	//database
	//definizione degli attributi per il database
	private static final String DB_URL ="jdbc:mysql://localhost:3306/centroestetico1";
	private static final String DB_USER ="root";
	private static final String DB_PASSWORD = "SQLpassword10_";
	
	//definizione del blocco di query per la manipolazione dei record
	
			//CREAZIONE QUERY PER INSERIMENTO DATI NELLA TABELLA
			private static final String INSERT_QUERY = "INSERT INTO attrezzature(nome,disponibilita) VALUES(?,?)";
		
			//CREAZIONE QUERY PER LEGGERE DATI NELLA TABELLA
			private static final String SELECT_ALL_QUERY= "SELECT * FROM attrezzature";
			
			//AGGIORNARE
			private static final String UPDATE_QUERY = "UPDATE attrezzature SET nome =?,disponibilita =? WHERE id=?";
			
			//CANCELLARE
			private static final String DELETE_QUERY = "DELETE FROM attrezzature WHERE id= ?";
		
			//ATTRIBUTO PER OUTPUT DATI DA DATABASE SU INTERFACCIA GRAFICA(GUI)
			private JTextArea outputArea;
	
	
	public Attrezzature(Main main) {
		//impostazione della finestra
	JFrame frame = new JFrame("Servizi");
	setTitle("Attrezzatura");
	setSize(900, 720);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//inizializzare l'oggetto di output
	outputArea = new JTextArea();
	outputArea.setEditable(false);//non modificabile la casella di testo
	
	//creazione del panello per contenere nel frame il nostro output
	JScrollPane scrollPane = new JScrollPane(outputArea);//creiamo la sua istanza
	
	//definizione & creazione dei bottoni necessari per la gestione del gestionale
	JButton addButton = new JButton("Aggiungi Attrezzatura");
	JButton viewButton = new JButton("Visualizza Attrezzatura");
	JButton updateButton = new JButton("Modifica Attrezzatura");
	JButton deleteButton = new JButton("Elimina Attrezzatura");
	//-------------------------------
			//Cambio colore bottoni
			addButton.setBackground(Color.decode("#157347"));
			addButton.setForeground(Color.BLACK);
			
			//AddButton Hoover
			addButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					//QUando il mouse entra nel bottone cambia colore
					addButton.setForeground(Color.WHITE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					 // Quando il mouse esce dal pulsante, il colore del testo ritorna al colore predefinito BLACK
					addButton.setForeground(Color.BLACK);
				}
			});
	//gestione degli eventi onClick
	addButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//in attesa di inserimento della funzione da richiamare
			aggiungi();
		}
	});
	//----------------------------------------
			//Cambio colore bottone view
			viewButton.setBackground(Color.decode("#31d2f2"));
			viewButton.setForeground(Color.BLACK);
			
			viewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					//QUando il mouse entra nel bottone il testo cambia colore da nero a bianco
					viewButton.setForeground(Color.WHITE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					viewButton.setForeground(Color.BLACK);
				}
			});
	viewButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//in attesa di inserimento della funzione da richiamare
			//unico metodo che va bene crearlo direttamente con Mysql
			printServizio();
		}
	});
	//----------------------
			updateButton.setBackground(Color.decode("#ffca2c"));
			updateButton.setForeground(Color.BLACK);
			
			//Button hoover
			updateButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					updateButton.setForeground(Color.WHITE);
				}
				 @Override
				 public void mouseExited(MouseEvent e) {
					 updateButton.setForeground(Color.BLACK);
				 }
			});
	updateButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//in attesa di inserimento della funzione da richiamare
			modifica();
		}
	});
	//------------------
			deleteButton.setBackground(Color.decode("#dc3545"));
			deleteButton.setForeground(Color.BLACK);
			//Event listener Hoover mouse
			deleteButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					deleteButton.setForeground(Color.white);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					deleteButton.setForeground(Color.black);
				}
			});
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					elimina();
				}
			});
	deleteButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//in attesa di inserimento della funzione da richiamare
			elimina();
		}
	});
	
	//creazione del pannello per la gestione dei bottoni
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new GridLayout(1,4));
	//aggiunta dei bottoni al pannello apenna creato
	buttonPanel.add(addButton);
	buttonPanel.add(viewButton);
	buttonPanel.add(updateButton);
	buttonPanel.add(deleteButton);
	
	//BOTTONI PER CAMBIARE FINESTRE
	// Secondo pannello di bottoni
	
	String attrezzaure = "Lista delle Attrezzature";
	
	
	JLabel fraseIniziale = new JLabel(attrezzaure, SwingConstants.CENTER);
	fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
	fraseIniziale.setOpaque(true);
	fraseIniziale.setBackground(Color.decode("#ffd1dc"));
	    JButton home = new JButton("Home ");
	    home.setBackground(Color.decode("#6f2cf3"));
		home.setForeground(Color.WHITE);
		
	    Font font = new Font(home.getFont().getName(), Font.PLAIN, 20); // Imposta il font a 20 punti
        home.setFont(font);
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new GridLayout(2, 4));
		buttonPanel2.add(home);
		buttonPanel2.add(fraseIniziale);
		//Event listener
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				home.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				home.setForeground(Color.white);
			}
		});
			//creazione evento
			home.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();//nasconde alla vista dell'utente la finestra
					main.setVisible(true);
					
				}
			});
			
	
			//aggiunta dei pannelli jscrollpane e buttonpanel al frame
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			getContentPane().add(buttonPanel2, BorderLayout.NORTH);
			}
	
	//fuori dal costruttore si creano i metodi per il nostro applicativo
	
			
			//METODO INSERIMENTO
			private void insertServizio(String nome, boolean disponibilita) {
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
					pstmt.setString(1, nome);
					pstmt.setBoolean(2, disponibilita);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
			//METODO MODIFICA
			private void updateServizio(String nome, boolean disponibilita, int id) {
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
					pstmt.setString(1, nome);
					pstmt.setBoolean(2, disponibilita);
					pstmt.setInt(3, id);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
			//METODO ELIMINAZIONE
			private void deleteServizio(int id) {
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			//METODO VISUALIZZAZIONE
			private void printServizio() {
				//ripulire il contenuto della textarea
				outputArea.setText("");
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
					//ciclo oer far stampare i dati
					while(rs.next()) {
						int id = rs.getInt("id");
						String nome = rs.getString("nome");
						boolean disponibilita = rs.getBoolean("disponibilita");
						if(disponibilita == true) {
							outputArea.append("ID" + id + " \nAttrezzatura: " + nome + "\nDisponibilià: NON DISPONIBILE" + "\n");
						}else {
							outputArea.append("ID" + id + " \nAttrezzatura: " + nome + "\nDisponibilià: DISPONIBILE" + "\n");
						}
					
					
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			//METODI PER LA GESTIONE UTENTE E L'INSERIMENTE DELLE INFORMAZIONI PER 
			//GESTIRE I METODI CHE DIALOGANO CON MySql
			
			//METODO AGGIUNGI
			private void aggiungi() {
				String nome = JOptionPane.showInputDialog("Inserisci il nome dell'Attrezzatura:");
				//per far inserire all'uente per forza qualcosa
				if(nome != null && !nome.isEmpty()) {
					//perche dobbiamo convertire il Boolean in una stringa		
					boolean disponibilita = false;
					insertServizio(nome,disponibilita);
					outputArea.append("Servizio aggiunto: "+ nome +" - Disponibilità: Disponibile" + "\n");//per scrivere nel pannello
				}
			}
			
			//METODO MODIFICA
			private void modifica() {
				//perche dobbiamo convertire Int in una stringa
				int id = Integer.parseInt(JOptionPane.showInputDialog("Insersci ID dell'attrezzatura da modificare"));
				String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome dell'Attrezzatura");
				//perche dobbiamo convertire il booleano in una stringa
				boolean disponibilita =false;
				
				String risposta = JOptionPane.showInputDialog("Inserisci SI se disponibile, e NO se non disponibile");
				if(risposta.equalsIgnoreCase("SI")) {
					disponibilita = false;
				}else{
					disponibilita = true;
				}
				
				updateServizio(nome,disponibilita,id);
				//per scrivere nel pannello
				outputArea.append("Attrezzatura modificata: ID "+id +" Nome: "+  nome +" - Disponibilita: "+ risposta + "\n");
			}
			
			//METODO ELIMINA
			private void elimina() {
				//perche dobbiamo convertire Int in una stringa
				int id = Integer.parseInt(JOptionPane.showInputDialog("Insersci ID del servizio da eliminare"));
				deleteServizio(id);
				//per scrivere nel pannello
				outputArea.append("Servizio eliminato: ID "+ id+"\n");
			}
			
	}