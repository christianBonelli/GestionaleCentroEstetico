package TestConTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Tessere extends JFrame {
	
	  //definizione degli attributi
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "SQLpassword10_";
    
    
    //blocco di query per la manipolazione dei record
    private static final String INSERT_QUERY = "INSERT INTO Tessere(nome,cognome,telefono,indirizzo,punteggioTessera) VALUES(?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Tessere";
    private static final String UPDATE_QUERY = "UPDATE Tessere SET punteggioTessera = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Tessere WHERE id = ?";
    
    private JTextArea outputArea;
    
    
    public Tessere(Main main) {
		setTitle("Tessere");
		 setSize(900, 720);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        outputArea = new JTextArea();
	        outputArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(outputArea);

	        JButton addButton = new JButton("Aggiungi tessera");
	        JButton viewButton = new JButton("Visualizza tessere");
	        JButton updateButton = new JButton("Modifica tessera");
	        JButton deleteButton = new JButton("Elimina tessera");
	        
	        // Primo pannello di bottoni
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout(1, 4));

	        buttonPanel.add(addButton);
	        buttonPanel.add(viewButton);
	        buttonPanel.add(updateButton);
	        buttonPanel.add(deleteButton);

	        // Secondo pannello di bottoni
	        JButton home = new JButton("Home");
	        
	        String tessere = "Qui potrai gestire le tessere dei clienti";
			
			
			JLabel fraseIniziale = new JLabel(tessere, SwingConstants.CENTER);
			fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
			fraseIniziale.setOpaque(true);
			fraseIniziale.setBackground(Color.decode("#ffd1dc"));
			

	        JPanel buttonPanel2 = new JPanel();
	        buttonPanel2.setLayout(new GridLayout(2, 1));

	        buttonPanel2.add(home);
	        buttonPanel2.add(fraseIniziale);
	        
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
					printTessera();
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
	            	dispose();//nasconde alla vista dell utente la finestra 1
	                //Appuntamenti appuntamenti = new Appuntamenti(Trattamenti.this); //mostrare seconda finestra
	               // appuntamenti.setVisible(true);
	            	main.setVisible(true);
	            }
	        });
	        
	        add(buttonPanel, BorderLayout.SOUTH);
	        add(buttonPanel2, BorderLayout.NORTH);
	        add(scrollPane, BorderLayout.CENTER);
	       
    }
    

	//metodo per inserimento dati
			private void insertTessera( String nome, String  cognome, String telefono, String indirizzo,int punteggiotessera ) {
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
					pstmt.setString(1, nome);
					pstmt.setString(2, cognome);
					pstmt.setString(3, telefono);
					pstmt.setString(4, indirizzo);
					pstmt.setInt(5,punteggiotessera);
					
					pstmt.executeUpdate();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			private void updateTessera(int punteggiotessera, int id) {
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
					pstmt.setInt(1, punteggiotessera);
					pstmt.setInt(2, id);
					
					pstmt.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			//metodo visualizzazione 
			private void printTessera() {
				outputArea.setText("");
				try{
					Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
				
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
					while(rs.next()) {
						int id = rs.getInt("id");
						String nome = rs.getString("nome");
						String cognome = rs.getString("cognome");
						String telefono = rs.getString("telefono");
						String indirizzo = rs.getString("indirizzo");
						int punteggio=rs.getInt("punteggiotessera");
						
						
						outputArea.append("Riepilogo Dati" + "\nID: " + id + "\nNome: " + nome +"\nTognome: " + cognome +"\nTelefono: " + telefono +"\nIndirizzo: " + indirizzo +"\nPunteggio: " + punteggio + "\n");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			//metodo eliminazione
			private void deleteTessera(int id) {
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
				//String nome = JOptionPane.showInputDialog("Inserisci il nome del servizio");
				//if(nome != null && !nome.isEmpty()) {
					
				   String nome = JOptionPane.showInputDialog("Inserisci il nome:");
				   String cognome = JOptionPane.showInputDialog("Inserisci il cognome:");
				   String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono:");
			       String indirizzo = JOptionPane.showInputDialog("Inserisci l'indirizzo:");
					int punteggio = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il punteggio della tessera"));
					
					insertTessera(nome,cognome,telefono,indirizzo,punteggio);
					outputArea.append(  " , nome: " + nome +" , cognome: " + cognome +" , telefono: " + telefono +" , indirizzo: " + indirizzo+ "Punteggio: " + punteggio + "\n");
				//}
			}
			
				private void modifica() {
				
				
				int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id della tessera da modificare"));
				int punteggio = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il punteggio da modificare"));
				updateTessera(punteggio,id);
				outputArea.append("ID: " + id + " , punteggio: " + punteggio  + "\n");
			}

			private void elimina() {
				int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id della tessera da eliminare"));
				deleteTessera(id);
				outputArea.append("tessera eliminata: ID: " + id + "\n");
			}



}