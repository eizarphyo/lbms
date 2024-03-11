package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.AuthorController;
import controller.BookController;
import model.AuthorModel;
import model.BookModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddBookView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitle;
	private JTextField txtAuthorName;
	private JTextField txtPrice;
	private JTextArea txtDescription;
	private JComboBox<Object> cmbStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddBookView dialog = new AddBookView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddBookView() {
		setBounds(100, 100, 600, 479);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTitle = new JLabel("Add A New Book!");
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblTitle.setBounds(10, 11, 566, 30);
			contentPanel.add(lblTitle);
		}
		{
			JPanel formPanel = new JPanel();
			formPanel.setBounds(10, 52, 566, 332);
			contentPanel.add(formPanel);
			formPanel.setLayout(null);
			{
				JLabel lblTitle = new JLabel("Title:");
				lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblTitle.setBounds(10, 11, 186, 30);
				formPanel.add(lblTitle);
			}
			
			txtTitle = new JTextField();
			txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtTitle.setBounds(206, 12, 350, 30);
			formPanel.add(txtTitle);
			txtTitle.setColumns(10);
			
			JLabel lblAuthorName = new JLabel("Author Name:");
			lblAuthorName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblAuthorName.setBounds(10, 52, 186, 30);
			formPanel.add(lblAuthorName);
			
			txtAuthorName = new JTextField();
			txtAuthorName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtAuthorName.setBounds(206, 53, 350, 30);
			formPanel.add(txtAuthorName);
			txtAuthorName.setColumns(10);
			
			JLabel lblDescription = new JLabel("Book Description:");
			lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDescription.setBounds(10, 142, 186, 30);
			formPanel.add(lblDescription);
			
			JLabel lblPrice = new JLabel("Price:");
			lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblPrice.setBounds(10, 101, 186, 30);
			formPanel.add(lblPrice);
			
			txtPrice = new JTextField();
			txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtPrice.setBounds(206, 94, 350, 30);
			formPanel.add(txtPrice);
			txtPrice.setColumns(10);
			
			txtDescription = new JTextArea();
			txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtDescription.setBounds(206, 147, 350, 100);
			formPanel.add(txtDescription);
			
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblStatus.setBounds(10, 273, 186, 30);
			formPanel.add(lblStatus);
			
			cmbStatus = new JComboBox<>();
			cmbStatus.setModel(new DefaultComboBoxModel(new String[] {"--Select--", "Available", "Not Available"}));
			cmbStatus.setBounds(206, 277, 350, 30);
			formPanel.add(cmbStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 395, 586, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton btnAdd = new JButton("Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(txtTitle.getText().isBlank() || txtAuthorName.getText().isBlank() || 
								txtDescription.getText().isBlank() || txtPrice.getText().isBlank() ||
								cmbStatus.getSelectedItem().equals("--Select--")
								 ) {
							JOptionPane.showMessageDialog(null, "Please enter required fields!");
						} 
						
						BookModel book = new BookModel();
						
						AuthorModel author = new AuthorModel();
						author.setName(txtAuthorName.getText());
						
						int authorId = AuthorController.hasDuplicate(author);
						
						if(authorId > 0) {
							book.setAuthorId(authorId);
						} else {
							AuthorController aCtl = new AuthorController();
							aCtl.insert(author);
							
							book.setAuthorId(aCtl.getIdByName(author));
						}
						
						book.setTitle(txtTitle.getText());
						book.setDescription(txtDescription.getText());
						book.setAvailable(cmbStatus.getSelectedItem().equals("Available") ? true : false);
						
						try {
							book.setPrice(Integer.parseInt(txtPrice.getText()));
							
							BookController bCtl = new BookController();
							
							int ok = bCtl.insert(book);
							
							if (ok == 1) {
								JOptionPane.showMessageDialog(null, "A new Book added to the library! Thank you!");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "Couldn't add the book.");
							}
							
						} catch (NumberFormatException excp) {
							JOptionPane.showMessageDialog(null, "Invalid price. Please re-enter");
						}
					}
				});
				btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnAdd.setActionCommand("");
				buttonPane.add(btnAdd);
				getRootPane().setDefaultButton(btnAdd);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
}
