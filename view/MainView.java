package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.BookController;
import model.BookModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JPanel panel;
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private JButton btnReturn;
	private JButton btnAdd;
	private JTable tblBooks;

	private DefaultTableModel dtm = new DefaultTableModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MainView dialog = new MainView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainView frame = new MainView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 11, 866, 43);
		contentPane.add(panel);
		panel.setLayout(null);

		lblTitle = new JLabel("Welcome to Fantistic Library!");
		lblTitle.setBounds(0, 0, 866, 43);
		panel.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));

		tablePanel = new JPanel();
		tablePanel.setBounds(10, 65, 866, 279);
		contentPane.add(tablePanel);
		tablePanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 866, 279);
		tablePanel.add(scrollPane);

		tblBooks = new JTable();
		scrollPane.setViewportView(tblBooks);

		JPanel btnPanel = new JPanel();
		btnPanel.setBounds(10, 355, 866, 57);
		contentPane.add(btnPanel);
		btnPanel.setLayout(null);

		JButton btnBorrow = new JButton("Borrow Book");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BorrowBookView dialog = new BorrowBookView();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});
		btnBorrow.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnBorrow.setBounds(221, 11, 147, 35);
		btnPanel.add(btnBorrow);

		btnReturn = new JButton("Return Book");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReturnBookView dialog = new ReturnBookView();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception excep) {
					excep.printStackTrace();
				}
			}
		});
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReturn.setBounds(397, 11, 147, 35);
		btnPanel.add(btnReturn);

		btnAdd = new JButton("Add New Book");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddBookView dialog = new AddBookView();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception excep) {
					excep.printStackTrace();
				}

			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBounds(575, 11, 147, 35);
		btnPanel.add(btnAdd);

		createTable();
		showBooks();
	}

	public void setColumnWidth(int i, int width) {
		DefaultTableColumnModel tcm = (DefaultTableColumnModel) tblBooks.getColumnModel();
		TableColumn tc = tcm.getColumn(i);
		tc.setPreferredWidth(width);
	}

	public void createTable() {
		dtm.addColumn("ID");
		dtm.addColumn("Title");
		dtm.addColumn("Author");
		dtm.addColumn("Description");
		dtm.addColumn("Price");
		dtm.addColumn("Status");

		tblBooks.setModel(dtm);
		setColumnWidth(0, 10);
		setColumnWidth(1, 200);
		setColumnWidth(2, 80);
		setColumnWidth(3, 100);
		setColumnWidth(4, 50);
		setColumnWidth(5, 30);
	}

	public void showBooks() {
		BookController ctl = new BookController();

		List<BookModel> books = ctl.getAllBooks();
		String[] row = new String[6];

		dtm.setRowCount(0);

		for (BookModel book : books) {
			row[0] = book.getId() + "";
			row[1] = book.getTitle();
			row[2] = book.getAuthorName();
			row[3] = book.getDescription();
			row[4] = book.getPrice() + "";
			row[5] = book.isAvailable() ? "Available" : "Not Available";

			dtm.addRow(row);
		}
	}
}
