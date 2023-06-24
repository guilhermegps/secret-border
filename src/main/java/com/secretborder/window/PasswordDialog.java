package com.secretborder.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import lombok.Getter;

public class PasswordDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblPasswordsDontMatch;
	private JButton okButton;
	@Getter
	private JPasswordField pwfPasswd;
	@Getter
	private JPasswordField pwfConfirm;
	@Getter
	private String passWord = null;
	
	public static String showPasswordDialog(Component parentComponent) {
		PasswordDialog dialog = new PasswordDialog();
		try {
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			if(parentComponent != null)
				dialog.setLocationRelativeTo(parentComponent);
			dialog.setVisible(true);
			
			return dialog.getPassWord();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dialog.dispose();
			dialog = null;
		}
	}

	/**
	 * Create the dialog.
	 */
	public PasswordDialog() {
		setTitle("Password");
		setBounds(100, 100, 400, 180);
		setResizable(false);
		setModalityType(DEFAULT_MODALITY_TYPE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 30, 346, 30, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			{
				JLabel lblPassword = new JLabel("Password:");
				GridBagConstraints gbc_lblPassword = new GridBagConstraints();
				gbc_lblPassword.anchor = GridBagConstraints.WEST;
				gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblPassword.gridx = 1;
				gbc_lblPassword.gridy = 0;
				panel.add(lblPassword, gbc_lblPassword);
			}
			{
				pwfPasswd = new JPasswordField();
				pwfPasswd.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						compareBoth();
					}
				});
				pwfPasswd.setColumns(50);
				GridBagConstraints gbc_pwfPasswd = new GridBagConstraints();
				gbc_pwfPasswd.fill = GridBagConstraints.HORIZONTAL;
				gbc_pwfPasswd.insets = new Insets(0, 0, 5, 5);
				gbc_pwfPasswd.gridx = 1;
				gbc_pwfPasswd.gridy = 1;
				panel.add(pwfPasswd, gbc_pwfPasswd);
			}
			{
				JLabel lblConfirmPassword = new JLabel("Confirm Password:");
				GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
				gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
				gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblConfirmPassword.gridx = 1;
				gbc_lblConfirmPassword.gridy = 2;
				panel.add(lblConfirmPassword, gbc_lblConfirmPassword);
			}
			{
				pwfConfirm = new JPasswordField();
				pwfConfirm.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						compareBoth();
					}
				});
				pwfConfirm.setColumns(50);
				GridBagConstraints gbc_pwfConfirm = new GridBagConstraints();
				gbc_pwfConfirm.insets = new Insets(0, 0, 5, 5);
				gbc_pwfConfirm.fill = GridBagConstraints.HORIZONTAL;
				gbc_pwfConfirm.gridx = 1;
				gbc_pwfConfirm.gridy = 3;
				panel.add(pwfConfirm, gbc_pwfConfirm);
			}
			{
				lblPasswordsDontMatch = new JLabel("Passwords don't match");
				lblPasswordsDontMatch.setVisible(false);
				lblPasswordsDontMatch.setForeground(Color.RED);
				lblPasswordsDontMatch.setFont(new Font("Dialog", Font.BOLD, 10));
				GridBagConstraints gbc_lblPasswordsDontMatch = new GridBagConstraints();
				gbc_lblPasswordsDontMatch.insets = new Insets(0, 0, 0, 5);
				gbc_lblPasswordsDontMatch.gridx = 1;
				gbc_lblPasswordsDontMatch.gridy = 4;
				panel.add(lblPasswordsDontMatch, gbc_lblPasswordsDontMatch);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(compareBoth()) {
							passWord = String.valueOf(pwfPasswd.getPassword());
							dispose();
							return;
						}
						
						JOptionPane.showMessageDialog(contentPanel, "The passwords are different", "Attention!", JOptionPane.WARNING_MESSAGE);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private boolean compareBoth() {
		var passWd = pwfPasswd.getPassword();
		var confirm = pwfConfirm.getPassword();
		
		if(passWd!=null 
				&& passWd.length>0
				&& Arrays.equals(passWd, confirm)) {
			lblPasswordsDontMatch.setVisible(false);
			okButton.setEnabled(true);
			return true;
		}

		okButton.setEnabled(false);
		lblPasswordsDontMatch.setVisible(true);
		return false;
	}

}
