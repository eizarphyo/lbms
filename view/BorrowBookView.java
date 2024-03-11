package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.BookController;
import controller.BorrowedBooksController;
import controller.BorrowedByController;
import controller.BorrowerController;
import model.BookModel;
import model.BorrowedBooksModel;
import model.BorrowedByModel;
import model.BorrowerModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class BorrowBookView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtEmail;
	private JList<String> listAvaBooks;

	List<BookModel> books = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BorrowBookView dialog = new BorrowBookView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BorrowBookView() {
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel txtTitle = new JLabel("Borrow Books!");
			txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
			txtTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
			txtTitle.setBounds(10, 11, 566, 35);
			contentPanel.add(txtTitle);
		}
		{
			JPanel infoPanel = new JPanel();
			infoPanel.setBounds(10, 57, 566, 72);
			contentPanel.add(infoPanel);
			infoPanel.setLayout(null);
			{
				JLabel txtName = new JLabel("Name:");
				txtName.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtName.setBounds(10, -1, 120, 30);
				infoPanel.add(txtName);
			}
			{
				txtName = new JTextField();
				txtName.setBounds(163, 0, 393, 30);
				infoPanel.add(txtName);
				txtName.setColumns(10);
			}
			{
				JLabel lblEmail = new JLabel("Email:");
				lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblEmail.setBounds(10, 40, 120, 30);
				infoPanel.add(lblEmail);
			}
			{
				txtEmail = new JTextField();
				txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtEmail.setBounds(163, 41, 393, 30);
				infoPanel.add(txtEmail);
				txtEmail.setColumns(10);
			}
		}

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 149, 566, 1);
		contentPanel.add(separator);

		JPanel booksPanel = new JPanel();
		booksPanel.setBounds(10, 161, 566, 208);
		contentPanel.add(booksPanel);
		booksPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 566, 173);
		booksPanel.add(scrollPane);

		listAvaBooks = new JList<String>(getBooks());
		scrollPane.setViewportView(listAvaBooks);
		listAvaBooks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		listAvaBooks.setSelectedIndices(new int[] { 0 });

//		listAvaBooks.setModel(new AbstractListModel() {
//			String[] values = new String[] {"Atomic Habits", "The Subtle Art Of Not Giving A Fuck", "Can't Hurt Me"};
//			public int getSize() {
//				return values.length;
//			}
//			public Object getElementAt(int index) {
//				return values[index];
//			}
//		});
		listAvaBooks.setSelectedIndex(0);

		JLabel txtInform = new JLabel("Please select the books you want to borrow:");
		txtInform.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtInform.setBounds(0, 0, 566, 30);
		booksPanel.add(txtInform);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtName.getText().isBlank() || txtEmail.getText().isBlank()) {
							JOptionPane.showMessageDialog(null, "You must enter your name and email to borrow books!");
							txtName.requestFocus();
							return;
						}

						try {

							int lastId = borrow();

							for (int i : listAvaBooks.getSelectedIndices()) {
								BookModel book = books.get(i);
								book.setAvailable(false);
								System.out.println(book.getTitle());

								BookController bCtl = new BookController();
								bCtl.update(book);

								BorrowedBooksModel borrowedBook = new BorrowedBooksModel();
								borrowedBook.setBorrowedById(lastId);
								borrowedBook.setBookId(book.getId());

								BorrowedBooksController borrBookCtl = new BorrowedBooksController();
								borrBookCtl.insert(borrowedBook);

							}
							JOptionPane.showMessageDialog(null, "Congratulations! The books are now yours!");
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Something went wrong. Please try again.");
						}
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

	String[] getBooks() {

		BookController ctl = new BookController();

		books = ctl.getAvailableBooks();

		String[] list = new String[books.size()];

		int i = 0;
		for (BookModel book : books) {
			list[i] = book.getTitle();
			i++;
		}
		return list;
	}

	int borrow() {
		BorrowerModel borrower = new BorrowerModel();
		borrower.setName(txtName.getText());
		borrower.setEmail(txtEmail.getText());

		int borrId = BorrowerController.hasDuplicate(borrower);

		BorrowedByModel borrowedBy = new BorrowedByModel();
		if (borrId > 0) {
			borrowedBy.setBorrowerId(borrId);
		} else {
			BorrowerController bCtl = new BorrowerController();
			bCtl.insert(borrower);

			borrowedBy.setBorrowerId(borrower.getId());
		}

		BorrowedByController borrByCtl = new BorrowedByController();
		
		// setting date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));  
		borrowedBy.setDate(dtf.format(now));
		
		// created a borrowedBy
		borrByCtl.insert(borrowedBy);

		// latest borroweId
		return borrByCtl.getLatestBorrowedBy(borrowedBy);
	}
}
