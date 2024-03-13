package TestConTabbedPane;


import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;




	public class Fatture extends JFrame {
	    
	        private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico1";
	        private static final String DB_USER = "root";
	        private static final String DB_PASSWORD = "SQLpassword10_";

	        

			private static final String INSERT_QUERY = "INSERT INTO fatture(nome, cognome, telefono, pec,partitaIva, codiceFiscale, prezzo, dataFattura) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

			private static final String SELECT_ALL_QUERY = "SELECT * FROM fatture";
			
			private static final String UPDATE_QUERY = "UPDATE fatture SET nome = ?, cognome = ?, telefono = ?, pec = ?, partitaIva = ?, codiceFiscale = ?, prezzo = ?,dataFattura = ? WHERE id = ?";
			
			private static final String DELETE_QUERY = "DELETE FROM fatture WHERE id = ?";	
	        
	        private JTextArea outputArea;
	        
	    
	        //costruttore interfaccia grafica
	    public Fatture(Main main) {
	        setTitle("Fatture");
	        setSize(900, 720);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        outputArea = new JTextArea();
	        outputArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(outputArea);
	        
	       


	        JButton addButton = new JButton("Aggiungi Fattura");
	        JButton viewButton = new JButton("Visualizza Fattura");
	        JButton deleteButton = new JButton("Elimina Fattura");
	        JButton updateButton = new JButton("Modifica Fattura");
	        
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
	            public void actionPerformed(ActionEvent e) {
	               
	                aggiungi();
	                outputArea.append("Fattura aggiunta");
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
	            public void actionPerformed(ActionEvent e) {
	                visualizzaVendite();
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
	            public void actionPerformed(ActionEvent e) {
	
	                elimina();
	                outputArea.append("Fattura eliminata");
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
	        String fatture = "Qui potrai gestire le tue Fatture";
			
			
			JLabel fraseIniziale = new JLabel(fatture, SwingConstants.CENTER);
			fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
			fraseIniziale.setOpaque(true);
			fraseIniziale.setBackground(Color.decode("#ffd1dc"));
			
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
	        try {
	            // Creazione di un nuovo documento PDF
	            PDDocument document = new PDDocument();
	            PDPage page = new PDPage();
	            document.addPage(page);

	            // Apertura di uno stream per il contenuto della pagina
	            PDPageContentStream contentStream = new PDPageContentStream(document, page);

	            // Aggiunta di testo al documento
	            contentStream.beginText();
	            contentStream.setFont(PDType1Font.HELVETICA, 12); 
	            contentStream.newLineAtOffset(50, 700);
	            contentStream.showText("Fattura");
	            contentStream.endText();

	            // Chiusura dello stream di contenuto
	            contentStream.close();

	            // Salvataggio del documento
	         //   document.save("C:/Users/chris/Downloads/corso java progetti visual studio/fattura.pdf");
	            document.close();

	            System.out.println("Documento PDF creato con successo!");
	        } catch (IOException e) {
	            // Gestione delle eccezioni di IO
	            e.printStackTrace();
	        } catch (Exception e) {
	            // Gestione generica delle eccezioni
	            e.printStackTrace();
	        }
	       

	        
	        //creazione del pannello per la gestione dei bottoni
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout());
	        //aggiunta dei bottoni al pannello appena creato
	        buttonPanel.add(addButton);
	        buttonPanel.add(viewButton);
	        buttonPanel.add(updateButton);
	        buttonPanel.add(deleteButton);

	        //aggiunta dei pannelli jscrollpanel e buttonpanel al frame
	        JPanel buttonPanel2 = new JPanel();
	        buttonPanel2.setLayout(new GridLayout(2, 1));

	        buttonPanel2.add(home);
	        buttonPanel2.add(fraseIniziale);
	        add(buttonPanel, BorderLayout.SOUTH);
	        add(buttonPanel2, BorderLayout.NORTH);
	        add(scrollPane, BorderLayout.CENTER);
	    }
	    
	    //creazione dei METODI
	    
	    //METODO INSERIMENTO
		private void aggiungiVendita(String nome, String cognome, String telefono, String pec ,String partitaIva, String codiceFiscale, double prezzo, Timestamp dataFattura) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
				pstmt.setString(1, nome);
				pstmt.setString(2, cognome);
				pstmt.setString(3, telefono);
				pstmt.setString(4, pec);
				pstmt.setString(5, partitaIva);
				pstmt.setString(6, codiceFiscale);
				pstmt.setDouble(7, prezzo);
				pstmt.setTimestamp(8, dataFattura);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

	  //METODO MODIFICA
		private void modificaVendite(int id, String nome, String cognome, String telefono, String pec, String partitaIva, String codiceFiscale, double prezzo, Timestamp dataFattura) {
		    try {
		        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		        PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
		        pstmt.setString(1, nome);
		        pstmt.setString(2, cognome);
		        pstmt.setString(3, telefono);
		        pstmt.setString(4, pec);
		        pstmt.setString(5, partitaIva);
		        pstmt.setString(6, codiceFiscale);
		        pstmt.setDouble(7, prezzo);
		        pstmt.setTimestamp(8, dataFattura);
		        pstmt.setInt(9, id); // Assicurati di impostare l'ID all'ultimo parametro
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Puoi gestire l'eccezione in modo più specifico qui
		    }
		}
	
	
	    
	    
	    
	    //METODO VISUALIZZA
	    private void visualizzaVendite() {
			outputArea.setText("");
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
				// Ciclo per stampare i dati
				while(rs.next()){
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String telefono = rs.getString("telefono");
					String pec = rs.getString("pec");
					String partitaIva = rs.getString("partitaIva");
					String codiceFiscale = rs.getString("codiceFiscale");
					double prezzo = rs.getDouble("prezzo");
					Timestamp dataFattura = rs.getTimestamp("dataFattura");
					outputArea.append("Riepilogo Fattura:"+ "\nID:"+ id + "\nNome: " + nome + "\nCognome: " + cognome + "\ntelefono: " + telefono +  "\nPec: " + pec + "\nPartita Iva: "+ partitaIva +"\nCodice Fiscale: " + codiceFiscale + "\nPrezzo: " + prezzo + "\n");
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    
	    //METODO ELIMINA
	    private void eliminaVendita(int id) {
	        try {
	        	Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	        	
	        	PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
	        	pstmt.setInt(1, id);
	        	pstmt.execute();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    private void modifica() {
	    	
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID della fattura da modificare:"));
			String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome:");
			String cognome = JOptionPane.showInputDialog("Inserisci il nuovo cognome:");
			String telefono = JOptionPane.showInputDialog("Inserisci il nuovo telefono:");
			String pec = JOptionPane.showInputDialog("Inserisci la nuova Pec:");
			String partitaIva = JOptionPane.showInputDialog("Inserisci la nuova partitaIva:");
			String codiceFiscale = JOptionPane.showInputDialog("Inserisci il nuovo codice fiscale:");
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il nuovo prezzo della fattura:"));
			Timestamp dataFattura = getDataFromUserInput();
			modificaVendite(id, nome, cognome, telefono, pec ,partitaIva, codiceFiscale, prezzo, dataFattura);
			outputArea.append("Fattura Modificata: \nNome: " + nome + "\nCognome: " + cognome + "\ntelefono: " + telefono +  "\nPec: " + pec + "\nPartita Iva: "+ partitaIva +"\nCodice Fiscale: " + codiceFiscale + "\nPrezzo: " + prezzo + "\n");
		}
	    
	    
	    
	    
		private void aggiungi() {
			String nome = JOptionPane.showInputDialog("Inserisci il nome:");
			String cognome = JOptionPane.showInputDialog("Inserisci il cognome:");
			String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono:");
			String pec = JOptionPane.showInputDialog("Inserisci la pec:");
			String partitaIva = JOptionPane.showInputDialog("Inserisci la partitaIva:");
			String codiceFiscale = JOptionPane.showInputDialog("Inserisci il codice fiscale:");
			Timestamp datafattura = getDataFromUserInput();
			if (nome != null && !nome.isEmpty() && cognome != null && !cognome.isEmpty() && partitaIva != null && !partitaIva.isEmpty()) {
				double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il prezzo della Fattura:"));
				aggiungiVendita(nome, cognome, telefono, pec,partitaIva, codiceFiscale, prezzo, datafattura);
				outputArea.append("Fattura Aggiunta: \nNome: " + nome + "\nCognome: " + cognome + "\ntelefono: " + telefono +  "\nPec: " + pec + "\nPartita Iva: "+ partitaIva +"\nCodice Fiscale: " + codiceFiscale + "\nPrezzo: " + prezzo + "\n");
			}
		}
	    
	    
	    
	    private void elimina() {
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID della fattura da eliminare:"));
			eliminaVendita(id);
			outputArea.append("Vendita eliminata ID " + id + "\n");
		}
	    
	    
	    private static Timestamp getDataFromUserInput() {
	        String inputDate = JOptionPane.showInputDialog("Inserisci la data della Fattura (formato dd/MM/yyyy HH:MM)");
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        try {
	            java.util.Date parsedDate = dateFormat.parse(inputDate);
	            return new Timestamp(parsedDate.getTime());
	        } catch (ParseException e) {
	            System.out.println("Formato data non valido.");
	            e.printStackTrace();
	            return null;
	        }
	    }
	}
	
	