package secretborder.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;

public class HOME {

	private JFrame frmSecretBorder;
	private final JPanel panel_btn = new JPanel();
	private JTextField tf_pubKey;
	private JTextField tf_privKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HOME window = new HOME();
					window.frmSecretBorder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HOME() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSecretBorder = new JFrame();
		frmSecretBorder.setTitle("Secret Border - A Secure Nostr Identity Generator");
		frmSecretBorder.setBounds(100, 100, 500, 270);
		frmSecretBorder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmSecretBorder.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_fields = new JPanel();
		frmSecretBorder.getContentPane().add(panel_fields, BorderLayout.WEST);
		GridBagLayout gbl_panel_fields = new GridBagLayout();
		gbl_panel_fields.columnWidths = new int[]{33, 389, 54, 0};
		gbl_panel_fields.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_fields.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_fields.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_fields.setLayout(gbl_panel_fields);
		
		JLabel lblPublicKey = new JLabel("Public Key:");
		GridBagConstraints gbc_lblPublicKey = new GridBagConstraints();
		gbc_lblPublicKey.anchor = GridBagConstraints.WEST;
		gbc_lblPublicKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicKey.gridx = 1;
		gbc_lblPublicKey.gridy = 1;
		panel_fields.add(lblPublicKey, gbc_lblPublicKey);
		
		tf_pubKey = new JTextField();
		tf_pubKey.setEditable(false);
		tf_pubKey.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_tf_pubKey = new GridBagConstraints();
		gbc_tf_pubKey.anchor = GridBagConstraints.WEST;
		gbc_tf_pubKey.gridwidth = 3;
		gbc_tf_pubKey.insets = new Insets(0, 0, 5, 0);
		gbc_tf_pubKey.gridx = 1;
		gbc_tf_pubKey.gridy = 2;
		panel_fields.add(tf_pubKey, gbc_tf_pubKey);
		tf_pubKey.setColumns(40);
		
		JLabel lblPrivateKey = new JLabel("Private Key:");
		GridBagConstraints gbc_lblPrivateKey = new GridBagConstraints();
		gbc_lblPrivateKey.gridwidth = 3;
		gbc_lblPrivateKey.anchor = GridBagConstraints.WEST;
		gbc_lblPrivateKey.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrivateKey.gridx = 1;
		gbc_lblPrivateKey.gridy = 3;
		panel_fields.add(lblPrivateKey, gbc_lblPrivateKey);
		
		tf_privKey = new JTextField();
		tf_privKey.setEditable(false);
		tf_privKey.setHorizontalAlignment(SwingConstants.CENTER);
		tf_privKey.setColumns(40);
		GridBagConstraints gbc_tf_privKey = new GridBagConstraints();
		gbc_tf_privKey.anchor = GridBagConstraints.WEST;
		gbc_tf_privKey.insets = new Insets(0, 0, 5, 0);
		gbc_tf_privKey.gridwidth = 3;
		gbc_tf_privKey.gridx = 1;
		gbc_tf_privKey.gridy = 4;
		panel_fields.add(tf_privKey, gbc_tf_privKey);
		
		JButton btnShow = new JButton("SHOW");
		btnShow.setPreferredSize(new Dimension(40, 20));
		btnShow.setMargin(new Insets(1, 1, 1, 1));
		btnShow.setFont(new Font("Dialog", Font.BOLD, 8));
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.insets = new Insets(0, 0, 5, 0);
		gbc_btnShow.gridx = 2;
		gbc_btnShow.gridy = 5;
		panel_fields.add(btnShow, gbc_btnShow);
		frmSecretBorder.getContentPane().add(panel_btn, BorderLayout.SOUTH);
		
		JButton btnGenerateNew = new JButton("Generate New");
		btnGenerateNew.setHorizontalAlignment(SwingConstants.LEFT);
		panel_btn.add(btnGenerateNew);
		
		JButton btnImport = new JButton("Import");
		panel_btn.add(btnImport);
		
		JButton btnExport = new JButton("Export");
		panel_btn.add(btnExport);
	}

}
