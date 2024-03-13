package TestConTabbedPane;



import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class Login extends JFrame {
	private static final String url = "jdbc:mysql://localhost:3306/centroestetico1";
	private static final String username = "root";
	private static final String password = "SQLpassword10_";

	private static final String RUOLO = "SELECT * FROM login WHERE nome_utente = '%s' AND passworddipendenti = '%s'";
	
	private JTextField txtUsername;
	private JLabel lblUsername, lblPassword, lblDescription, lblEmpty;
	private JButton btnLogin;
	private JPasswordField passwordField;
	
	//Costruttore per il Login
	public Login() {
		setTitle("Login Centro Estetico Amministratore");
		setSize(900,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		lblDescription = new JLabel("Login:");
		lblEmpty = new JLabel("");
		
		lblUsername = new JLabel("Username");
		txtUsername = new JTextField(50);

		lblPassword = new JLabel("Password");
		passwordField = new JPasswordField(50);
		
		btnLogin = new JButton("Login");
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Controlla se il campo di testo txtUsername non è vuoto
				if (!txtUsername.getText().isEmpty()) {
					 // Ottiene la password digitata dall'utente e la memorizza come array di caratteri
					char[] pass = passwordField.getPassword();
					// Converte l'array di caratteri della password in una stringa
					String passwordUser = new String(pass);
					// Ottiene il nome utente inserito dall'utente
					String userName = txtUsername.getText();
					if (pass.length != 0) {
						 // Controlla se è stata inserita una password (la lunghezza dell'array di caratteri non è zero)
						 // Esegue la funzione di login con il nome utente e la password inseriti
						int rs = login(txtUsername.getText(), passwordUser);
						 // Avvia uno switch basato sul risultato della funzione di login
						switch(rs) {
						 // Se il risultato è 1 (ovvero l'utente è un amministratore)
						case 1: 
							 // Mostra un messaggio di benvenuto all'amministratore con il nome utente
							JOptionPane.showMessageDialog(null, "Benvenuto Amministratore "  + txtUsername.getText());
							 // Pulisce i campi di testo e la password
							cleanData(txtUsername, passwordField);
							 // Chiude la finestra corrente
							dispose();
							// Apre una nuova finestra principale
							new Main();
						    break;
						case 0:
			            	JOptionPane.showMessageDialog(null, "Password o Username non validi.");
							break;
						default:
							 // Se il risultato non è né 1 né 0 (potenziale errore)
							  // Mostra un messaggio di errore generico
							JOptionPane.showMessageDialog(null, "Errore.");
							break;
						}						
					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserito lo username");
					}
				}
			}
		}

		);
		
		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(lblDescription);
		panel.add(lblEmpty);
		panel.add(lblUsername);
		panel.add(txtUsername);
		panel.add(lblPassword);
		panel.add(passwordField);

		panel.add(btnLogin);

		add(panel);
		setVisible(true);
		
	}
	 
	//Metodo che cancella i campi di testo dopo aver inserito i dati
	public static void cleanData(JTextField txtUsername, JPasswordField passwordField) {
		txtUsername.setText("");
		passwordField.setText("");
	}
	/*In sostanza, questo metodo si occupa di eseguire il login di un utente nel sistema,
	 *  controllando se il nome utente e la password forniti corrispondono a un record nel database e
	 *  restituendo il ruolo dell'utente (1 per amministratore, 0 altrimenti).
	  */
	private static int login(String nome_utente, String passworddipendenti) {
		/*Viene creato un oggetto query utilizzando String.format() per formattare una stringa SQL,
		 *  sostituendo dei segnaposto %s con i valori forniti nome_utente e passworddipendenti
		 * Questa stringa contiene una query SQL che probabilmente seleziona il ruolo dell'utente dato il nome utente e la password.*/
		String query = String.format(RUOLO, nome_utente, passworddipendenti);
		System.out.println(query);
		//Aperta connessione al database
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			//Se la query restituisce almeno una riga (cioè rs.next() restituisce true),
			if(rs.next()) {
				String ruolo = rs.getString("ruolo");
				//viene estratto il ruolo dall'oggetto ResultSet e viene verificato se è uguale a "amministratore". 
				//Se lo è, viene restituito 1, altrimenti viene restituito 0.
				if(ruolo.equals("amministratore")) {
					return 1;
				} else
					return 0;
			}
			//Se la query non restituisce alcun risultato o si verifica un'eccezione, il metodo restituisce 0.
			return 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Errore durante la connessione al database");
			e.printStackTrace();
		}
		return 0;
	}
}

