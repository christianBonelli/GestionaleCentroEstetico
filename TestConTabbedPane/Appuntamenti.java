package TestConTabbedPane;

import javax.swing.*;




import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Appuntamenti extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/centroestetico1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "SQLpassword10_";

    private static final String INSERT_QUERY = "INSERT INTO Appuntamenti(data_ora,nome, cognome,telefono,trattamento,stanza,attrezzatura) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Appuntamenti";
    private static final String UPDATE_QUERY = "UPDATE Appuntamenti SET nome = ?,cognome = ?, telefono = ?,trattamento = ?,stanza = ?,attrezzatura= ?, data_ora = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Appuntamenti WHERE id = ?";

    private JTextArea outputArea;
   
    public Appuntamenti(Main main) {
        setTitle("Appuntamenti");
        setSize(900, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton addButton = new JButton("Aggiungi Appuntamento");
        JButton viewButton = new JButton("Visualizza Appuntamento");
        JButton updateButton = new JButton("Modifica Appuntamento");
        JButton deleteButton = new JButton("Elimina Appuntamento");

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
                printAppuntamenti();
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
        String appuntamenti = "Appuntamenti";
				
		
		JLabel fraseIniziale = new JLabel(appuntamenti, SwingConstants.CENTER);
		fraseIniziale.setFont(new Font("Arial", Font.BOLD, 20));
		fraseIniziale.setOpaque(true);
		fraseIniziale.setBackground(Color.decode("#ffd1dc"));
    
    JButton home = new JButton("Home");
    home.setBackground(Color.decode("#6f2cf3"));
	home.setForeground(Color.WHITE);
	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new GridLayout(2, 4));
	buttonPanel2.add(home);
	buttonPanel2.add(fraseIniziale);
	
	
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
    
	//EventListener Per cambiare pagina
	home.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
			main.setVisible(true);
		}
	});
	
	 getContentPane().setLayout(new BorderLayout());
     getContentPane().add(scrollPane, BorderLayout.CENTER);
     getContentPane().add(buttonPanel, BorderLayout.SOUTH);
     getContentPane().add(buttonPanel2, BorderLayout.NORTH);
    }
    private void insertAppuntamento(Timestamp data_ora,String nome,String  cognome, String telefono,String trattamento,String stanza,String attrezzatura) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
            pstmt.setTimestamp(1, data_ora);
            pstmt.setString(2, nome);
			pstmt.setString(3, cognome);
			pstmt.setString(4, telefono);
			  pstmt.setString(5, trattamento);
				pstmt.setString(6, stanza);
				pstmt.setString(7, attrezzatura);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAppuntamento(int id, Timestamp data_ora, String nome, String cognome, String telefono, String trattamento, String stanza, String attrezzatura) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY);

            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.setString(3, telefono);
            pstmt.setString(4, trattamento);
            pstmt.setString(5, stanza);
            pstmt.setString(6, attrezzatura);
            pstmt.setTimestamp(7, data_ora);
            pstmt.setInt(8, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printAppuntamenti() {
        outputArea.setText("");
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String telefono = rs.getString("telefono");
				  String trattamento = rs.getString("trattamento");
					String stanza = rs.getString("stanza");
					String attrezzatura = rs.getString("attrezzatura");
                Timestamp data_ora = rs.getTimestamp("data_ora");
                outputArea.append("Riepilogo Appuntamenti: \nID: " + id + "\nData: " + data_ora + "\nNome:" + nome + "\nCognome:" + cognome + " \nTelefono: " + telefono + "\nTrattamento: " + trattamento + " \nStanza " + stanza +"\nAttrezzatura " + attrezzatura + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAppuntamento(int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void aggiungi() {
        Timestamp data_ora = getDataFromUserInput();
		String nome = JOptionPane.showInputDialog("Inserisci il nome:");
		String cognome = JOptionPane.showInputDialog("Inserisci il cognome:");
		String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono:");
		String trattamento = JOptionPane.showInputDialog("Inserisci il trattamento:");
		String stanza = JOptionPane.showInputDialog("Inserisci la stanza:");
		String attrezzatura = JOptionPane.showInputDialog("Inserisci l attrezzatura:");
        if (data_ora != null) {
            insertAppuntamento(data_ora,nome,cognome,telefono,trattamento,stanza,attrezzatura);
            outputArea.append("Appuntamento aggiunto: " + data_ora + "\nNome " + nome + "\nCognome " + cognome + "\nTelefono " + telefono + "\nTrattamento " + trattamento + "\nStanza " + stanza +"\nAttrezzatura " + attrezzatura + "\n");
        }
    }

    private void modifica() {
        String inputId = JOptionPane.showInputDialog("Inserisci ID dell'appuntamento da modificare");
        if (inputId != null && !inputId.isEmpty()) {
            try {
                int id = Integer.parseInt(inputId);
                Appuntamento appuntamentoEsistente = getAppuntamentoById(id);
                if (appuntamentoEsistente != null) {
                    String nome = JOptionPane.showInputDialog("Inserisci il nome (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getNome());
                    String cognome = JOptionPane.showInputDialog("Inserisci il cognome (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getCognome());
                    String telefono = JOptionPane.showInputDialog("Inserisci il numero di telefono (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getTelefono());
                    String trattamento = JOptionPane.showInputDialog("Inserisci il trattamento (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getTrattamento());
                    String stanza = JOptionPane.showInputDialog("Inserisci la stanza (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getStanza());
                    String attrezzatura = JOptionPane.showInputDialog("Inserisci l'attrezzatura (schiaccia ok se non vuoi modificare):", appuntamentoEsistente.getAttrezzatura());
                    Timestamp data_ora = getDataFromUserInput();
                    if (data_ora == null) {
                        data_ora = appuntamentoEsistente.getDataOra();
                    }

                    nome = nome.isEmpty() ? appuntamentoEsistente.getNome() : nome;
                    cognome = cognome.isEmpty() ? appuntamentoEsistente.getCognome() : cognome;
                    telefono = telefono.isEmpty() ? appuntamentoEsistente.getTelefono() : telefono;
                    trattamento = trattamento.isEmpty() ? appuntamentoEsistente.getTrattamento() : trattamento;
                    stanza = stanza.isEmpty() ? appuntamentoEsistente.getStanza() : stanza;
                    attrezzatura = attrezzatura.isEmpty() ? appuntamentoEsistente.getAttrezzatura() : attrezzatura;

                    updateAppuntamento(id, data_ora, nome, cognome, telefono, trattamento, stanza, attrezzatura);
                    printAppuntamenti();
                    outputArea.append("Appuntamento modificato: ID " + id + "\nData e Ora: " + data_ora + "\nNome: " + nome + "\nCognome: " + cognome + "\nTelefono: " + telefono + "\nTrattamento: " + trattamento + "\nStanza: " + stanza + "\nAttrezzatura: " + attrezzatura + "\n");
                    outputArea.revalidate();
                    outputArea.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "ID non valido o appuntamento non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID non valido. Inserisci un numero intero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID non inserito. Inserisci un ID valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    class Appuntamento {
        private int id;
        private Timestamp dataOra;
        private String nome;
        private String cognome;
        private String telefono;
        private String trattamento;
        private String stanza;
        private String attrezzatura;

        
        public Appuntamento() {
        }

        
        public int getId() {
            return id;
        }

        public Timestamp getDataOra() {
            return dataOra;
        }

        public String getNome() {
            return nome;
        }

        public String getCognome() {
            return cognome;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getTrattamento() {
            return trattamento;
        }

        public String getStanza() {
            return stanza;
        }

        public String getAttrezzatura() {
            return attrezzatura;
        }

        
        public void setId(int id) {
            this.id = id;
        }

        public void setDataOra(Timestamp dataOra) {
            this.dataOra = dataOra;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setCognome(String cognome) {
            this.cognome = cognome;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public void setTrattamento(String trattamento) {
            this.trattamento = trattamento;
        }

        public void setStanza(String stanza) {
            this.stanza = stanza;
        }

        public void setAttrezzatura(String attrezzatura) {
            this.attrezzatura = attrezzatura;
        }
    }

   
    private Appuntamento getAppuntamentoById(int id) {
        Appuntamento appuntamento = null;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Appuntamenti WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                appuntamento = new Appuntamento();
                appuntamento.setId(rs.getInt("id"));
                appuntamento.setDataOra(rs.getTimestamp("data_ora"));
                appuntamento.setNome(rs.getString("nome"));
                appuntamento.setCognome(rs.getString("cognome"));
                appuntamento.setTelefono(rs.getString("telefono"));
                appuntamento.setTrattamento(rs.getString("trattamento"));
                appuntamento.setStanza(rs.getString("stanza"));
                appuntamento.setAttrezzatura(rs.getString("attrezzatura"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appuntamento;
    }
    private void elimina() {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci ID dell'appuntamento da eliminare"));
        deleteAppuntamento(id);
        outputArea.append("Appuntamento eliminato: ID: " + id + "\n");
    }

    private static Timestamp getDataFromUserInput() {
        String inputDate = JOptionPane.showInputDialog("Inserisci la data dell'appuntamento (formato dd/MM/yyyy HH:mm)");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

		
