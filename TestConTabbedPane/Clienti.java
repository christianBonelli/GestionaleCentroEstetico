package TestConTabbedPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class Clienti extends JFrame{
	private static final  String DB_URL ="jdbc:mysql://localhost:3306/centroestetico1";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "SQLpassword10_";
	
	private JTextArea outputArea;
	
	//Query per inserire un servizio
		private static final String INSERT_QUERY = "INSERT INTO clienti(nome, cognome, telefono, indirizzo,trattamenti_preferito, pec, codicefiscale, partitaiva) VALUES(?,?,?,?,?,?,?,?)";
		//Query per leggere tutti i servizi
		private static final String SELECT_ALL_QUERY = "SELECT * FROM clienti";
		//QUery per aggiornare
		
		private static final String UPDATE_QUERY = "UPDATE clienti SET nome = ?, cognome = ?, telefono = ?, indirizzo = ?, trattamenti_preferito = ?, pec = ?, codicefiscale = ?, partitaiva = ? WHERE id = ?";

		//Query per eliminare
		private static final String DELETE_QUERY = "DELETE FROM clienti WHERE id = ?";
	
		
	public Clienti(Main main) {
		
		setSize(900, 720);
		setTitle("Clienti");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//Output
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		
		String clientiBenvenuto = "Qui potrai gestire i tuoi clienti";
				
		
		JLabel fraseIniziale = new JLabel(clientiBenvenuto, SwingConstants.CENTER);
		fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
		fraseIniziale.setOpaque(true);
		fraseIniziale.setBackground(Color.decode("#ffd1dc"));
		
		//Creazione pannello per contenere nel frame il nostro output
		JScrollPane scrollPane = new JScrollPane(outputArea);
		
		//Bottoni CRUD
		JButton addButton = new JButton("Aggiungi Cliente");
		JButton viewButton = new JButton("Visualizza Clienti");
		JButton updateButton = new JButton("Modifica Cliente");
		JButton deleteButton = new JButton("Elimina Cliente");
		
		//Aggiunta dei bottoni ad un pannello
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));
		
		buttonPanel.add(addButton);
		buttonPanel.add(viewButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		
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
		
		//Event Listenere dei bottoni
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				printClienti();
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
		//------------------
		//Creazione bottnoi e pannello per collegare le finestre
		
		JButton home = new JButton("Home");
		home.setBackground(Color.decode("#6f2cf3"));
		home.setForeground(Color.WHITE);
		
		 //Cambio granezza font
        Font font = new Font(home.getFont().getName(), Font.PLAIN, 20); // Imposta il font a 20 punti
        home.setFont(font);
        
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
		

		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new GridLayout(2, 4));
		buttonPanel2.add(home);
		buttonPanel2.add(fraseIniziale);
		//EventListener Per cambiare pagina
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				main.setVisible(true);
			}
		});
		
		
		
		
		//Aggiunta del tutto al frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(buttonPanel2, BorderLayout.NORTH);
		
		
	}
	//Metodi per inserire dati clienti
	private void insertClienti(String nome, String  cognome, String telefono, String indirizzo,String trattamenti_preferito, String pec, String codicefiscale, String partitaiva) {
		try {
			//Connessione al Database: Viene stabilita una connessione al database utilizzando le credenziali fornite (DB_URL, DB_USER, DB_PASSWORD).
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			//Preparazione della Query di Inserimento: Viene preparata una PreparedStatement utilizzando la query di inserimento specificata in INSERT_QUERY.
			PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
			// Impostazione dei valori dei parametri della query
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, telefono);
			pstmt.setString(4, indirizzo);
			pstmt.setString(5, trattamenti_preferito);
			pstmt.setString(6, pec);
			pstmt.setString(7, codicefiscale);
			pstmt.setString(8, partitaiva);
			// Esecuzione dell'inserimento
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			  // Gestione delle eccezioni
			e.printStackTrace();	
		}
		
	}
	
	//Metodo aggiorna cliente
	private void updateCliente(String campo, String valore, int id) {
	    try {
	        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	        /*Creazione della Query di Aggiornamento: Viene costruita una stringa di query SQL dinamica che esegue un'operazione di aggiornamento sulla tabella "clienti".
	         *  La variabile campo rappresenta il nome del campo da aggiornare, valore è il nuovo valore da assegnare al campo e id è l'identificativo del cliente da aggiornare.
	         * */
	        String query = "UPDATE clienti SET " + campo + " = ? WHERE id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, valore);
	        pstmt.setInt(2, id);
	        //Esecuzione dell'Aggiornamento: Viene eseguito l'aggiornamento nel database utilizzando il metodo executeUpdate() della PreparedStatement.
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
		//questa parte di codice si occupa di recuperare i dati dei clienti dal database e stamparli su un'area di testo.
		//Metodi per stampare lista clienti
		private void printClienti() {
			outputArea.setText("");
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				  // Creazione dello Statement
				Statement stmt = conn.createStatement();
				 // Esecuzione della query per selezionare tutti i clienti
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
				// Iterazione sui risultati della query
				while(rs.next()) {
					  // Estrazione dei dati di ciascun cliente dal ResultSet
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String indirizzo = rs.getString("indirizzo");
					String trattamenti_preferito = rs.getString("trattamenti_preferito");
					String telefono = rs.getString("telefono");
					String pec = rs.getString("pec");
					String codicefiscale = rs.getString("codicefiscale");
					String partitaiva = rs.getString("partitaiva");
					 // Aggiunta dei dati del cliente all'area di testo outputArea
					outputArea.append("Riepilogo Dati: ID: " + id  + "\nNome: " + nome + ", \nCognome: " + cognome +
							"\nTelefono: " + telefono + "\nIndirizzo: " + indirizzo + "\ntrattamenti: " + trattamenti_preferito + "\nPec: " + pec + "\nCodice Fiscale: " + codicefiscale +
							"\nPartita Iva " + partitaiva);
				}
			}catch(SQLException e) {
				e.printStackTrace();	
			}
		}
		
		
		//Metodo elimina clienti
		private void deleteCliente(int id) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	private void aggiungi() { 
		//Il metodo utilizza la classe JOptionPane per mostrare una serie di finestre di dialogo che chiedono all'utente di inserire le informazioni relative al nuovo cliente, 
		String nome = JOptionPane.showInputDialog("Inserisci il nome:");
		String cognome = JOptionPane.showInputDialog("Inserisci il cognome:");
		String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono:");
		String indirizzo = JOptionPane.showInputDialog("Inserisci l'indirizzo:");
		String trattamenti_preferito = JOptionPane.showInputDialog("Inserisci il trattamento:");
		String pec = JOptionPane.showInputDialog("Inserisci la pec:");
		String codicefiscale = JOptionPane.showInputDialog("Inserisci il codice fiscale:");
		String partitaiva = JOptionPane.showInputDialog("Inserisci la PartitaIva:");
		//Viene effettuato un controllo per assicurarsi che l'utente abbia effettivamente inserito un nome. Se l'utente preme "Annulla" o lascia vuoto il campo, il valore di nome sarà null o vuoto.
		if(nome != null && !nome.isEmpty()) {
			// Se l'utente ha inserito un nome, i dati del cliente vengono passati al metodo insertClienti() per essere salvati nel database.
			insertClienti(nome, cognome, telefono, indirizzo,trattamenti_preferito, pec, codicefiscale, partitaiva); //I dati vengono salvati nel database
			//Aggiunta dei dati nella finestra per visualizzare i dati
			outputArea.append("Cliente aggiunto \nNome: " + nome + ", \nCognome: " + cognome +
					"\nTelefono: " + telefono + "\nIndirizzo: " + indirizzo +"\ntrattamento preferito: " + trattamenti_preferito+ "\nPec: " + pec + "\nCodice Fiscale: " + codicefiscale +
					"\nPartita Iva " + partitaiva);
		}
	}

	private void modifica() {
		int id;
		try {
			//Viene utilizzata la finestra di dialogo di input di JOptionPane per chiedere all'utente di inserire l'ID del cliente che desidera modificare.
			//L'ID inserito viene convertito da stringa a intero usando Integer.parseInt().
	    id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id del cliente da modificare: "));

	    /*Viene chiamato il metodo recuperaValoriCliente(id) per ottenere i valori attuali del cliente dal database.
	     *  Questi valori vengono memorizzati in un array di stringhe valoriAttuali.
	     * */
	    String[] valoriAttuali = recuperaValoriCliente(id);

	    // Mostra la finestra di dialogo per la modifica con i valori attuali come predefiniti
	    String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome del cliente: ", valoriAttuali[0]);
	    String cognome = JOptionPane.showInputDialog("Inserisci il nuovo cognome del cliente:", valoriAttuali[1]);
	    String telefono = JOptionPane.showInputDialog("Inserisci il nuovo numero di telefono del cliente: \nSchiaccia OK se non vuoi inserire niente", valoriAttuali[2]);
	    String indirizzo = JOptionPane.showInputDialog("Inserisci il nuovo indirizzo del cliente: \nSchiaccia OK se non vuoi inserire niente", valoriAttuali[3]);
	    String pec = JOptionPane.showInputDialog("Inserisci la nuova pec: \nSchiaccia OK se non vuoi inserire niente", valoriAttuali[4]);
	    String codicefiscale = JOptionPane.showInputDialog("Inserisci il nuovo Codice Fiscale del cliente: \nSchiaccia OK se non vuoi inserire niente", valoriAttuali[5]);
	    String partitaiva = JOptionPane.showInputDialog("Inserisci la nuova Partita Iva: \nSchiaccia OK se non vuoi inserire niente", valoriAttuali[6]);

	    // Verifica se i campi sono vuoti e, in caso affermativo, non aggiornarli
	    //Se l'utente inserisce nuovi valori per i dettagli del cliente e non lascia vuoti i campi, i metodi updateCliente() vengono chiamati per aggiornare i corrispondenti campi nel database.
	    if (nome != null && !nome.isEmpty()) {
	    	//Ogni metodo updateCliente() è chiamato con il nome del campo da aggiornare, il nuovo valore e l'ID del cliente.
	        updateCliente("nome", nome, id);
	    }
	    if (cognome != null && !cognome.isEmpty()) {
	        updateCliente("cognome", cognome, id);
	    }
	    if (telefono != null && !telefono.isEmpty()) {
	        updateCliente("telefono", telefono, id);
	    }
	    if (indirizzo != null && !indirizzo.isEmpty()) {
	        updateCliente("indirizzo", indirizzo, id);
	    }
	    if (pec != null && !pec.isEmpty()) {
	        updateCliente("pec", pec, id);
	    }
	    if (codicefiscale != null && !codicefiscale.isEmpty()) {
	        updateCliente("codicefiscale", codicefiscale, id);
	    }
	    if (partitaiva != null && !partitaiva.isEmpty()) {
	        updateCliente("partitaiva", partitaiva, id);
	    }

	    // Aggiorna l'outputArea con i nuovi dati
	    outputArea.append("Cliente Modificato: ID: " + id + "\nNome: " + nome + ", \nCognome: " + cognome +
	            "\nTelefono: " + telefono + "\nIndirizzo: " + indirizzo + "\nPec: " + pec + "\nCodice Fiscale: " + codicefiscale +
	            "\nPartita Iva " + partitaiva + "\n");
	}catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Inserisci un ID valido.", "Errore", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
	}
	
	
	
	//è un metodo per recuperare i valori attuali di un cliente dal database in base all'ID del cliente specificato.
	private String[] recuperaValoriCliente(int id) {
		//Viene creato un array di stringhe valori con dimensione 7. Questo array verrà utilizzato per memorizzare i valori recuperati dal database.
	    String[] valori = new String[7]; // Array per memorizzare i valori recuperati
	    try {
	    	//Viene stabilita una connessione al database utilizzando le credenziali fornite.
	        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	        /*Viene creata una query SQL parametrizzata che seleziona i valori nome, cognome, 
	         * telefono, indirizzo, pec, codicefiscale e partitaiva dalla tabella clienti dove l'id corrisponde al valore fornito.
	         * */
	        String query = "SELECT nome, cognome, telefono, indirizzo, pec, codicefiscale, partitaiva FROM clienti WHERE id = ?";
	        //Viene preparata una PreparedStatement e viene impostato il parametro id con il valore specificato.
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        //Viene controllato se il ResultSet rs contiene almeno una riga
	        if (!rs.next()) {
	        	  JOptionPane.showMessageDialog(null, "L'ID inserito non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	return null;
	        }else {
	        	//Se esiste un cliente con l'ID specificato, vengono recuperati i valori delle colonne nome, cognome, telefono, indirizzo, 
	        	//pec, codicefiscale e partitaiva dal ResultSet rs e memorizzati nell'array valori.
	        	
	        	//Viene utilizzato l'operatore ternario per verificare se i valori recuperati sono nulli e assegnare una stringa vuota in tal caso.
	        	  valori[0] = rs.getString("nome") != null ? rs.getString("nome") : "";
		            valori[1] = rs.getString("cognome") != null ? rs.getString("cognome") : "";
		            valori[2] = rs.getString("telefono") != null ? rs.getString("telefono") : "";
		            valori[3] = rs.getString("indirizzo") != null ? rs.getString("indirizzo") : "";
		            valori[4] = rs.getString("pec") != null ? rs.getString("pec") : "";
		            valori[5] = rs.getString("codicefiscale") != null ? rs.getString("codicefiscale") : "";
		            valori[6] = rs.getString("partitaiva") != null ? rs.getString("partitaiva") : "";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    //Viene restituito l'array valori contenente i valori recuperati dal database.
	    return valori;
	}
	//Metodo per eliminare un cliente in base all'id di input
	private void elimina() {
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id del servizio da eliminare: "));
		deleteCliente(id);
		outputArea.append("Cliente eliminato: ID" + id + "\n" );
	}
}

