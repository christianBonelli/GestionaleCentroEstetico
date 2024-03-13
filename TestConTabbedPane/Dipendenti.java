package TestConTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Dipendenti extends JFrame {
	
	  //definizione degli attributi
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "SQLpassword10_";

    //blocco di query per la manipolazione dei record
    private static final String INSERT_QUERY = "INSERT INTO Dipendenti(nome,nome_utente,cognome,telefono,indirizzo,email,stipendio,mansione,passworddipendenti) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Dipendenti";
    private static final String UPDATE_QUERY = "UPDATE Dipendenti SET nome = ?, nome_utente = ?, cognome = ?, telefono = ? , indirizzo = ? , email = ? , stipendio = ? , mansione = ? , passworddipendenti = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM dipendenti WHERE id = ?";

    
	private JTextArea outputArea;
	
	public Dipendenti(Main main) {
		setTitle("Dipendenti");
		
		 setSize(900, 720);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        outputArea = new JTextArea();
	        outputArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(outputArea);
	        
	        String dipendentiGestione = "Qui potrai gestire i tuoi Dipendenti";
			
			
			JLabel fraseIniziale = new JLabel(dipendentiGestione, SwingConstants.CENTER);
			fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
			fraseIniziale.setOpaque(true);
			fraseIniziale.setBackground(Color.decode("#ffd1dc"));

	        JButton addButton = new JButton("Aggiungi dipendente");
	        JButton viewButton = new JButton("Visualizza dipendente");
	        JButton updateButton = new JButton("Modifica dipendente");
	        JButton deleteButton = new JButton("Elimina dipendente");
	        
	        // Primo pannello di bottoni
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout(1, 4));

	        buttonPanel.add(addButton);
	        buttonPanel.add(viewButton);
	        buttonPanel.add(updateButton);
	        buttonPanel.add(deleteButton);

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

	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					aggiungi();
	            }
	        });
	        //---------------
	        //Cambio colore viewbutton
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
					printDipendenti();
	            }
	        });
	        //-------------------
	        //Cambio colore update button
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
	        //Cambio colore deletebutton
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
	        
	        //GESTIONE PULSANTI CAMBIO PAGINA
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
	        home.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	//nasconde alla vista dell utente la finestra 1
	                //Appuntamenti appuntamenti = new Appuntamenti(Trattamenti.this); //mostrare seconda finestra
	               // appuntamenti.setVisible(true);
	            	dispose();
					main.setVisible(true);
	            	
	            }
	        });
	        JPanel buttonPanel2 = new JPanel();
	        buttonPanel2.setLayout(new GridLayout(2, 1));
	        buttonPanel2.add(home);
	        buttonPanel2.add(fraseIniziale);
	        add(buttonPanel, BorderLayout.SOUTH);
	        add(buttonPanel2, BorderLayout.NORTH);
	        add(scrollPane, BorderLayout.CENTER);
	      
	    }
	

	//metodo per inserimento dati
			private void insertDipendente(String nome,String nome_utente,String cognome,String telefono,String indirizzo,String email,Double stipendio,String mansione,String passworddipendenti) {
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
					pstmt.setString(1, nome);
					pstmt.setString(2, nome_utente);
					pstmt.setString(3, cognome);
					pstmt.setString(4, telefono);
					pstmt.setString(5, indirizzo);
					pstmt.setString(6, email);
					pstmt.setDouble(7, stipendio);
					pstmt.setString(8, mansione);
					pstmt.setString(9, passworddipendenti);
					pstmt.executeUpdate();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			private void updateDipendente(String nome,String nome_utente,String cognome,String telefono,String indirizzo,String email,Double stipendio,String mansione,String passworddipendenti, int id) {
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
					pstmt.setString(1, nome);
					pstmt.setString(2, nome_utente);
					pstmt.setString(3, cognome);
					pstmt.setString(4, telefono);
					pstmt.setString(5, indirizzo);
					pstmt.setString(6, email);
					pstmt.setDouble(7, stipendio);
					pstmt.setString(8, mansione);
					pstmt.setString(9, passworddipendenti);
					pstmt.setInt(10, id);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			//metodo visualizzazione 
			private void printDipendenti() {
				outputArea.setText("");
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
					while(rs.next()) {
						int id = rs.getInt("id");
						String nome = rs.getString("nome");
						String cognome = rs.getString("cognome");
						String nome_utente = rs.getString("nome_utente");
						String telefono = rs.getString("telefono");
						String indirizzo = rs.getString("indirizzo");
						String email = rs.getString("email");
						Double stipendio= rs.getDouble("stipendio");
						String mansione = rs.getString("mansione");
						String password = rs.getString("passworddipendenti");
						
						
						outputArea.append("Riepilogo Dipendenti: " + "ID: " + id  +  "\nNome: " + nome + "\nCognome: " + cognome + "\nNome Utente: " + nome_utente + "\nTelefono: " + telefono + "\nIndirizzo : " + indirizzo + "\nEmail: " + email + "\nStipendio: " + stipendio +"\nMansione: " + mansione + "\nPassword: " + password + "\n");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			//metodo eliminazione
			private void deleteDipendente(int id) {
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			private void aggiungi() {
				String nome = JOptionPane.showInputDialog("Inserisci il nome deldipendente");
				if(nome != null && !nome.isEmpty()) {
					String cognome = JOptionPane.showInputDialog("Inserisci  cognome del dipendente:");
					String nome_utente = JOptionPane.showInputDialog("Inserisci  nome utente del dipendente");
					String  telefono = JOptionPane.showInputDialog("Inserisci  numero di telefono:");
					String indirizzo = JOptionPane.showInputDialog("Inserisci l'indirizzo");
					String email = JOptionPane.showInputDialog("Inserisci l'email");
					double stipendio = Double.parseDouble(JOptionPane.showInputDialog("Inserisci lo stipendio del dipendente"));
					String mansione = JOptionPane.showInputDialog("Inserisci la  mansione");
					String password = JOptionPane.showInputDialog("Inserisci la  password");
		           
					
					
					insertDipendente(nome, nome_utente, cognome, telefono, indirizzo,email,stipendio,mansione,password);
					outputArea.append("Riepilogo: " +  "\nNome: " + nome + "\nCognome: " + cognome + "\nNome Utente: " + nome_utente + "\nTelefono: " + telefono + "\nIndirizzo : " + indirizzo + "\nEmail: " + email + "\nStipendio: " + stipendio +"\nMansione: " + mansione + "\nPassword: " + password + "\n");
				}
			}
			
			private void modifica() {
				int id;
				try {
			    id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id del dipendente da modificare"));

			    // Ottieni i valori correnti per l'ID specificato
			    String nome = getCurrentValue("nome", id);
			    String cognome = getCurrentValue("cognome", id);
			    String nome_utente = getCurrentValue("nome_utente", id);
			    String telefono = getCurrentValue("telefono", id);
			    String indirizzo = getCurrentValue("indirizzo", id);
			    String email = getCurrentValue("email", id);
			    String stipendioStr = getCurrentValue("stipendio", id);
			    String mansione = getCurrentValue("mansione", id);
			    String password = getCurrentValue("passworddipendenti", id);

			    // Richiedi all'utente di inserire i nuovi valori, utilizzando i valori correnti come predefiniti
			    nome = JOptionPane.showInputDialog("Inserisci il nuovo nome del dipendente(lascia vuoto per mantenere il valore esistente):", nome);
			    cognome = JOptionPane.showInputDialog("Inserisci il nuovo cognome del dipendente(lascia vuoto per mantenere il valore esistente):", cognome);
			    nome_utente = JOptionPane.showInputDialog("Inserisci il nuovo nome utente del dipendente(lascia vuoto per mantenere il valore esistente):", nome_utente);
			    telefono = JOptionPane.showInputDialog("Inserisci il nuovo numero di telefono da modificare(lascia vuoto per mantenere il valore esistente):", telefono);
			    indirizzo = JOptionPane.showInputDialog("Inserisci il nuovo indirizzo(lascia vuoto per mantenere il valore esistente):", indirizzo);
			    email = JOptionPane.showInputDialog("Inserisci la nuova email(lascia vuoto per mantenere il valore esistente):", email);
			    stipendioStr = JOptionPane.showInputDialog("Inserisci nuovo stipendio del dipendente(lascia vuoto per mantenere il valore esistente):", stipendioStr);
			    mansione = JOptionPane.showInputDialog("Inserisci la nuova mansione(lascia vuoto per mantenere il valore esistente):", mansione);
			    password = JOptionPane.showInputDialog("Inserisci la nuova password(lascia vuoto per mantenere il valore esistente):", password);

			    // Verifica se i campi sono vuoti o nulli e, in caso affermativo, utilizza i valori correnti
			    if (nome == null || nome.isEmpty()) {
			        nome = getCurrentValue("nome", id);
			    }
			    if (cognome == null || cognome.isEmpty()) {
			        cognome = getCurrentValue("cognome", id);
			    }
			    if (nome_utente == null || nome_utente.isEmpty()) {
			        nome_utente = getCurrentValue("nome_utente", id);
			    }
			    if (telefono == null || telefono.isEmpty()) {
			        telefono = getCurrentValue("telefono", id);
			    }
			    if (indirizzo == null || indirizzo.isEmpty()) {
			        indirizzo = getCurrentValue("indirizzo", id);
			    }
			    if (email == null || email.isEmpty()) {
			        email = getCurrentValue("email", id);
			    }
			    if (stipendioStr == null || stipendioStr.isEmpty()) {
			        stipendioStr = getCurrentValue("stipendio", id);
			    }
			    if (mansione == null || mansione.isEmpty()) {
			        mansione = getCurrentValue("mansione", id);
			    }
			    if (password == null || password.isEmpty()) {
			        password = getCurrentValue("passworddipendenti", id);
			    }

			    // Converti lo stipendio da String a Double
			    double stipendio = Double.parseDouble(stipendioStr);

			    updateDipendente(nome, cognome, nome_utente, telefono, indirizzo, email, stipendio, mansione, password, id);
			    outputArea.append("Riepilogo: " + "ID: " + id + "\nNome: " + nome + "\nCognome: " + cognome + "\nNome Utente: " + nome_utente + "\nTelefono: " + telefono + "\nIndirizzo : " + indirizzo + "\nEmail: " + email + "\nStipendio: " + stipendio +"\nMansione: " + mansione + "\nPassword: " + password + "\n");
			}catch(NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Inserisci un ID valido.", "Errore", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
				
			}

			private String getCurrentValue(String columnName, int id) {
			    String currentValue = "";
			    try {
			        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			        PreparedStatement pstmt = conn.prepareStatement("SELECT " + columnName + " FROM Dipendenti WHERE id = ?");
			        pstmt.setInt(1, id);
			        ResultSet rs = pstmt.executeQuery();
			        if (!rs.next()) {
			        	 JOptionPane.showMessageDialog(null, "L'ID inserito non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
				        	return null;
			            
			        }else {
			        	 currentValue = rs.getString(columnName);
			        }
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    return currentValue;
			}
			private void elimina() {
				int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id del dipendente da eliminare"));
				deleteDipendente(id);
				outputArea.append("Dipendente rimosso: ID: " + id + "\n");
			}
}

		    