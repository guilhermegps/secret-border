package com.secretborder.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bouncycastle.util.Arrays;

import com.secretborder.crypto.Bech32Prefix;
import com.secretborder.crypto.Crypto;
import com.secretborder.util.FileUtil;
import com.secretborder.util.KeyUtil;

public class HOME {

	private JFrame frmSecretBorder;
	private final JPanel panel_btn = new JPanel();
	private JTextField tfNpub;
	private JTextField tfHexPub;
	private JPasswordField pfHexSec;
	private JPasswordField pfNsec;
	private JButton btnShow;
	private JButton btnCopyNpub;
	private JButton btnCopyHexPub;
	private JButton btnCopyNsec;
	private JButton btnCopyHexSec;
	private JButton btnExport;
	private JButton btnImport;
	private byte[] privKey;
	private boolean visibleSec = false;

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
		frmSecretBorder.setResizable(false);
		frmSecretBorder.setTitle("Secret Border - A Secure Nostr Identity Generator");
		frmSecretBorder.setBounds(100, 100, 520, 270);
		frmSecretBorder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmSecretBorder.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_fields = new JPanel();
		frmSecretBorder.getContentPane().add(panel_fields, BorderLayout.WEST);
		GridBagLayout gbl_panel_fields = new GridBagLayout();
		gbl_panel_fields.columnWidths = new int[]{16, 435, 37, 0};
		gbl_panel_fields.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_fields.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_fields.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_fields.setLayout(gbl_panel_fields);
		
		JLabel lblPublicKey = new JLabel("Public Key:");
		GridBagConstraints gbc_lblPublicKey = new GridBagConstraints();
		gbc_lblPublicKey.anchor = GridBagConstraints.WEST;
		gbc_lblPublicKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicKey.gridx = 1;
		gbc_lblPublicKey.gridy = 1;
		panel_fields.add(lblPublicKey, gbc_lblPublicKey);
		
		tfNpub = new JTextField();
		tfNpub.setToolTipText("npub");
		tfNpub.setHorizontalAlignment(SwingConstants.CENTER);
		tfNpub.setEditable(false);
		tfNpub.setColumns(40);
		GridBagConstraints gbc_tfNpub = new GridBagConstraints();
		gbc_tfNpub.insets = new Insets(0, 0, 5, 5);
		gbc_tfNpub.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNpub.gridx = 1;
		gbc_tfNpub.gridy = 2;
		panel_fields.add(tfNpub, gbc_tfNpub);
		
		btnCopyNpub = new JButton("COPY");
		btnCopyNpub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(tfNpub.getText());
			}
		});
		btnCopyNpub.setVisible(false);
		btnCopyNpub.setPreferredSize(new Dimension(40, 20));
		btnCopyNpub.setFont(new Font("Dialog", Font.PLAIN, 8));
		btnCopyNpub.setMargin(new Insets(1, 1, 1, 1));
		GridBagConstraints gbc_btnCopyNpub = new GridBagConstraints();
		gbc_btnCopyNpub.anchor = GridBagConstraints.WEST;
		gbc_btnCopyNpub.insets = new Insets(0, 0, 5, 5);
		gbc_btnCopyNpub.gridx = 2;
		gbc_btnCopyNpub.gridy = 2;
		panel_fields.add(btnCopyNpub, gbc_btnCopyNpub);
		
		tfHexPub = new JTextField();
		tfHexPub.setToolTipText("hex");
		tfHexPub.setHorizontalAlignment(SwingConstants.CENTER);
		tfHexPub.setEditable(false);
		tfHexPub.setColumns(40);
		GridBagConstraints gbc_tfHexPub = new GridBagConstraints();
		gbc_tfHexPub.insets = new Insets(0, 0, 5, 5);
		gbc_tfHexPub.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHexPub.gridx = 1;
		gbc_tfHexPub.gridy = 3;
		panel_fields.add(tfHexPub, gbc_tfHexPub);
		
		btnCopyHexPub = new JButton("COPY");
		btnCopyHexPub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(tfHexPub.getText());
			}
		});
		btnCopyHexPub.setVisible(false);
		btnCopyHexPub.setPreferredSize(new Dimension(40, 20));
		btnCopyHexPub.setMargin(new Insets(1, 1, 1, 1));
		btnCopyHexPub.setFont(new Font("Dialog", Font.PLAIN, 8));
		GridBagConstraints gbc_btnCopyHexPub = new GridBagConstraints();
		gbc_btnCopyHexPub.anchor = GridBagConstraints.WEST;
		gbc_btnCopyHexPub.insets = new Insets(0, 0, 5, 5);
		gbc_btnCopyHexPub.gridx = 2;
		gbc_btnCopyHexPub.gridy = 3;
		panel_fields.add(btnCopyHexPub, gbc_btnCopyHexPub);
		
		JLabel lblPrivateKey = new JLabel("Private Key:");
		GridBagConstraints gbc_lblPrivateKey = new GridBagConstraints();
		gbc_lblPrivateKey.gridwidth = 3;
		gbc_lblPrivateKey.anchor = GridBagConstraints.WEST;
		gbc_lblPrivateKey.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrivateKey.gridx = 1;
		gbc_lblPrivateKey.gridy = 4;
		panel_fields.add(lblPrivateKey, gbc_lblPrivateKey);
		
		pfNsec = new JPasswordField();
		pfNsec.setToolTipText("nsec");
		pfNsec.setHorizontalAlignment(SwingConstants.CENTER);
		pfNsec.setEditable(false);
		pfNsec.setColumns(40);
		GridBagConstraints gbc_pfNsec = new GridBagConstraints();
		gbc_pfNsec.insets = new Insets(0, 0, 5, 5);
		gbc_pfNsec.fill = GridBagConstraints.HORIZONTAL;
		gbc_pfNsec.gridx = 1;
		gbc_pfNsec.gridy = 5;
		panel_fields.add(pfNsec, gbc_pfNsec);
		
		btnCopyNsec = new JButton("COPY");
		btnCopyNsec.setVisible(false);
		btnCopyNsec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(String. valueOf(pfNsec.getPassword()));
			}
		});
		btnCopyNsec.setPreferredSize(new Dimension(40, 20));
		btnCopyNsec.setMargin(new Insets(1, 1, 1, 1));
		btnCopyNsec.setFont(new Font("Dialog", Font.PLAIN, 8));
		GridBagConstraints gbc_btnCopyNsec = new GridBagConstraints();
		gbc_btnCopyNsec.anchor = GridBagConstraints.WEST;
		gbc_btnCopyNsec.insets = new Insets(0, 0, 5, 5);
		gbc_btnCopyNsec.gridx = 2;
		gbc_btnCopyNsec.gridy = 5;
		panel_fields.add(btnCopyNsec, gbc_btnCopyNsec);
		
		pfHexSec = new JPasswordField();
		pfHexSec.setToolTipText("hex");
		pfHexSec.setHorizontalAlignment(SwingConstants.CENTER);
		pfHexSec.setEditable(false);
		pfHexSec.setColumns(40);
		GridBagConstraints gbc_pfHexSec = new GridBagConstraints();
		gbc_pfHexSec.insets = new Insets(0, 0, 5, 5);
		gbc_pfHexSec.fill = GridBagConstraints.HORIZONTAL;
		gbc_pfHexSec.gridx = 1;
		gbc_pfHexSec.gridy = 6;
		panel_fields.add(pfHexSec, gbc_pfHexSec);
		
		btnShow = new JButton("SHOW/HIDE");
		btnShow.setEnabled(false);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(visibleSec) {
					changeVisionSec(false);
					return;
				}
				
				int option = JOptionPane.showConfirmDialog(frmSecretBorder, "Do you want to show your secret key?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.OK_OPTION) {
					changeVisionSec(true);
				}
			}
		});
		
		btnCopyHexSec = new JButton("COPY");
		btnCopyHexSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(String. valueOf(pfHexSec.getPassword()));
			}
		});
		btnCopyHexSec.setVisible(false);
		btnCopyHexSec.setPreferredSize(new Dimension(40, 20));
		btnCopyHexSec.setMargin(new Insets(1, 1, 1, 1));
		btnCopyHexSec.setFont(new Font("Dialog", Font.PLAIN, 8));
		GridBagConstraints gbc_btnCopyHexSec = new GridBagConstraints();
		gbc_btnCopyHexSec.anchor = GridBagConstraints.WEST;
		gbc_btnCopyHexSec.insets = new Insets(0, 0, 5, 5);
		gbc_btnCopyHexSec.gridx = 2;
		gbc_btnCopyHexSec.gridy = 6;
		panel_fields.add(btnCopyHexSec, gbc_btnCopyHexSec);
		btnShow.setToolTipText("Show and hide your secret");
		btnShow.setPreferredSize(new Dimension(80, 20));
		btnShow.setMargin(new Insets(1, 1, 1, 1));
		btnShow.setFont(new Font("Dialog", Font.BOLD, 8));
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.anchor = GridBagConstraints.EAST;
		gbc_btnShow.insets = new Insets(0, 0, 5, 5);
		gbc_btnShow.gridx = 1;
		gbc_btnShow.gridy = 7;
		panel_fields.add(btnShow, gbc_btnShow);
		frmSecretBorder.getContentPane().add(panel_btn, BorderLayout.SOUTH);
		
		JButton btnGenerateNew = new JButton("Generate New");
		btnGenerateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateKeys();
			}
		});
		btnGenerateNew.setHorizontalAlignment(SwingConstants.LEFT);
		panel_btn.add(btnGenerateNew);
		
		btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frmSecretBorder, "Do you want to load your secret key from your disk?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.OK_OPTION) {
					loadSecret();
				}
			}
		});
		panel_btn.add(btnImport);
		
		btnExport = new JButton("Export");
		btnExport.setEnabled(false);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frmSecretBorder, "Do you want to backup your secret key on your disk?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.OK_OPTION) {
					backupSecret();
				}
			}
		});
		panel_btn.add(btnExport);
	}
	
	private void generateKeys() {
		privKey = Crypto.generatePrivateKey();
		loadKeys();
	}
	
	private void loadKeys() {
		var pubKey = Crypto.genPubKey(privKey);

		changeVisionSec(false);
		pfNsec.setText(KeyUtil.bytesToBech32(privKey, Bech32Prefix.NSEC));
		pfHexSec.setText(KeyUtil.bytesToHex(privKey));
		tfNpub.setText(KeyUtil.bytesToBech32(pubKey, Bech32Prefix.NPUB));
		tfHexPub.setText(KeyUtil.bytesToHex(pubKey));
		
		btnExport.setEnabled(true);
		btnShow.setEnabled(true);
		btnCopyNpub.setVisible(true);
		btnCopyHexPub.setVisible(true);
	}
	
	private void changeVisionSec(boolean visibleSec) {
		btnCopyHexSec.setVisible(visibleSec);
		btnCopyNsec.setVisible(visibleSec);
		pfNsec.setEchoChar(visibleSec ? (char) 0 : '*');
		pfHexSec.setEchoChar(visibleSec ? (char) 0 : '*');

		btnCopyNsec.setVisible(visibleSec);
		btnCopyHexSec.setVisible(visibleSec);
		
		this.visibleSec = visibleSec;
	}
	
	private void copyToClipboard(String text) {
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	private void writeFile(byte[] fileBytes) {
		FileDialog dialog = new FileDialog(frmSecretBorder, "Save", FileDialog.SAVE);
	    dialog.setVisible(true);
	    File files[] = dialog.getFiles();
	    if(files!=null){
	    	File file = files[0];
        	String path = file.getAbsolutePath();
        	FileUtil.writeFile(fileBytes, path);
        }
	}

	private byte[] readFile() {
		try {
			FileDialog dialog = new FileDialog(frmSecretBorder, "Open", FileDialog.LOAD);
		    dialog.setVisible(true);
		    File files[] = dialog.getFiles();
		    if(!Arrays.isNullOrEmpty(files))
		    	return FileUtil.getBytes(files[0]);
		    
		    JOptionPane.showMessageDialog(frmSecretBorder, "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
		    return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void backupSecret() {
		var password = JOptionPane.showInputDialog(frmSecretBorder, "Input your password", "Password for encryption!", JOptionPane.WARNING_MESSAGE);
		var secret = Crypto.encrypt(privKey, password);
		
		writeFile(secret);
		JOptionPane.showMessageDialog(frmSecretBorder, "Your secret has been securely backed up", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void loadSecret() {
		var fileBytes = readFile();
		var password = JOptionPane.showInputDialog(frmSecretBorder, "Input your password", "Password for encryption!", JOptionPane.WARNING_MESSAGE);
		
		try {
			privKey = Crypto.decrypt(fileBytes, password);
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(frmSecretBorder, "It wasn't possible to decrypt the file. Are you sure that the password is correct?", "File not decrypted", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		loadKeys();
		JOptionPane.showMessageDialog(frmSecretBorder, "Your secret has been securely loaded", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
}
