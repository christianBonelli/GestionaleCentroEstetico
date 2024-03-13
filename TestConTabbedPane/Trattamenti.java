package TestConTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Trattamenti extends JFrame {

    //definizione degli attributi
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "SQLpassword10_";

    //blocco di query per la manipolazione dei record
    private static final String INSERT_QUERY = "INSERT INTO trattamenti(nome, durata, prezzo, disponibilita) VALUES(?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM trattamenti";
    private static final String UPDATE_QUERY = "UPDATE trattamenti SET nome = ?, durata = ?, prezzo = ?, disponibilita = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM trattamenti WHERE id = ?";

    
	private JTextArea outputArea;
    
    public Trattamenti(Main main) {
    	
    	
        setSize(900, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton addButton = new JButton("Aggiungi Trattamento");
        JButton viewButton = new JButton("Visualizza Trattamento");
        JButton updateButton = new JButton("Modifica Trattamento");
        JButton deleteButton = new JButton("Elimina Trattamento");

        // Primo pannello di bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Secondo pannello di bottoni
        
        JButton home = new JButton("Home");
  String trattamenti = "Lista dei Trattamenti";
    	
    	
    	JLabel fraseIniziale = new JLabel(trattamenti, SwingConstants.CENTER);
    	fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
    	fraseIniziale.setOpaque(true);
    	fraseIniziale.setBackground(Color.decode("#ffd1dc"));

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(2,1));

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
				printTrattamenti();
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

        
        //GESTIONE PULSANTI CAMBIO PAGINA
        
        
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
            	dispose();
                main.setVisible(true);
            }
        });
        
        add(buttonPanel, BorderLayout.SOUTH);
        add(buttonPanel2, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //setVisible(true);
    }
    //METODI CRUD
    

	//metodo per inserimento dati
    private void insertTrattamento(String nome, double durata, double prezzo, boolean disponibilita) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
            pstmt.setString(1, nome);
            pstmt.setDouble(2, durata);
            pstmt.setDouble(3, prezzo);
            pstmt.setBoolean(4, disponibilita);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	//metodo modifica
	private void updateTrattamento(String nome, double durata, double prezzo, boolean disponibilita, int id) {
		try{
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
		
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);
			pstmt.setString(1, nome);
			pstmt.setDouble(2, durata);
			pstmt.setDouble(3, prezzo);
			pstmt.setBoolean(4, disponibilita);
			pstmt.setInt(5, id);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//metodo visualizzazione 
	private void printTrattamenti() {
		outputArea.setText("");
		try{
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				double durata = rs.getDouble("durata");
				double prezzo = rs.getDouble("prezzo");
				boolean disponibilita = rs.getBoolean("disponibilita");
				if(disponibilita == true) {
					outputArea.append("Riepilogo Trattamento" + "\nID: " + id + "\nTRATTAMENTO: " + nome+ "\nDURATA: " + durata +  "\nPREZZO: " + prezzo + "\nDISPONIBILITA: NON DISPONIBILE" +  "\n");
				}else {
					outputArea.append("Riepilogo Trattamento"+ "\nID: " + id + "\nTRATTAMENTO: " + nome+ "\nDURATA: " + durata +  "\nPREZZO: " + prezzo + "\nDISPONIBILITA: DISPONIBILE" +  "\n");
				}
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//metodo eliminazione
	private void deleteTrattamento(int id) {
		try{
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
		
			PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//metodi per la gestione utente e l'inserimento delle informazioni per gestire i metodi che dialogano con MySql
	
	private void aggiungi() {
		String nome = JOptionPane.showInputDialog("Inserisci il nome del servizio");
		if(nome != null && !nome.isEmpty()) {
			double durata = Double.parseDouble(JOptionPane.showInputDialog("inserisci la durata del trattamento(HH.mm)"));
			boolean disponibilita = false;
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("inserisci il prezzo del trattamento"));
			insertTrattamento(nome, durata ,prezzo,disponibilita);
			outputArea.append("Trattamento aggiunto: " + nome + " - Prezzo: " + prezzo+ " DISPONIBILITA: DISPONIBILE" + "\n");
		}
	}
	
	private void modifica() {
		boolean disponibilita = false;
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id del trattamento da modificare"));
		String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome del trattamento");
		double durata = Double.parseDouble(JOptionPane.showInputDialog("Inserisci durata del trattamento(HH.mm)"));
		String risposta = JOptionPane.showInputDialog("Inserisci SI se disponibile, e NO se non disponibile");
		if(risposta.equalsIgnoreCase("SI")) {
			disponibilita = false;
		}else{
			disponibilita = true;
		}
		double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci nuovo prezzo del trattamento"));
		updateTrattamento(nome, durata, prezzo, disponibilita, id);
		outputArea.append("Trattamento modificato: ID " + id + " , NOME: " + nome + " DURATA: " + durata+ " , PREZZO: "+ prezzo + " DISPONIBILITA: " + risposta +"\n");
		
	}
	
	private void elimina() {
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci id del servizio da eliminare"));
		deleteTrattamento(id);
		outputArea.append("Trattamento eliminato: ID: " + id + "\n");
	}
   
}