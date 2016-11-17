import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class recordsTable extends JPanel {
	
	private Object[][] dataArray;
	private int numRecords;
	private JLabel borrowLabel;
	private JTextField borrowField;
	private JButton borrowButton;
	private JButton deleteButton;
	private JTextField deleteField;
	private JLabel deleteLabel;
	private JTable table;
	DefaultTableModel dm;
	private JButton logOffButton;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JMenuItem menuItem2;
	private JLabel bookTitleLabel;
	private JLabel authorLabel;
	private JLabel pubDateLabel;
	private JLabel isbnLabel;
	private JTextField bookTitleField;
	private JTextField authorField;
	private JTextField pubDateField;
	private JTextField isbnField;
	private JButton saveButton;
	private JButton logoffButton;
	ArrayList<String> replaceArray;
	ArrayList<String> replaceStaffArray;
	ArrayList<String> Array;
	ArrayList<String> deletionArray;
	GridBagConstraints c = new GridBagConstraints();
	
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public recordsTable(){
		super(new GridBagLayout());
		
		String line;
		String[] header = {"Book Title", "Author", "Pub/Rel Date", "ISBN-10", "Status"};
				
		try 
		{
			BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
			line = inputStream.readLine();
			numRecords = Integer.parseInt(line);
			dataArray = new Object[numRecords][5];
			for(int row = 0; row < numRecords; row++){
				for(int column = 0; column < 5; column++){
					line = inputStream.readLine();
					dataArray[row][column] = line;
															
				}
			}
			
			inputStream.close();	
			
		}
		catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
		}
		
		dm = new DefaultTableModel();
		dm.setDataVector(dataArray, header);
		table = new JTable(dm);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 160));
		table.setFillsViewportHeight(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		c.gridx = 0;
		c.gridy = 0;
		setBackground(Color.CYAN);
		
		add(scrollPane, c);
		
		
		
		}
	
		void createTableGui(){
			new recordsTable();
			final JFrame frame = new JFrame("Library Management System");
			frame.setSize(1500, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			
			recordsTable newContentPane = new recordsTable();
			newContentPane.setOpaque(true);
			frame.setContentPane(newContentPane);
			logOffButton = new JButton("Log Off");
			logOffButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
					new Gui();
				}
			});
			
			borrowLabel = new JLabel("Which book would you like to check out?");
			borrowField = new JTextField(15);
			borrowLabel.setLabelFor(borrowField);
			borrowButton = new JButton("Borrow");
			borrowButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					 replaceArray = new ArrayList<String>();
					 String lines, bookToBorrow;
					 bookToBorrow = borrowField.getText();
					
					try{
			    		BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
			    		
			    		while((lines = inputStream.readLine()) != null){
			    			if(bookToBorrow.equals(lines)){
			    				replaceArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceArray.add("Checked Out");
			    				while((lines = inputStream.readLine()) != null){
			    					replaceArray.add(lines);
			    	    		}
			    				break;
			    			}
			    			else
			    			{
			    				replaceArray.add(lines);
			    			}
			    		}
			    		
			    		inputStream.close();
			    	}
			    	catch(FileNotFoundException exc)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException e){
						JOptionPane.showMessageDialog(null, "File Read Error", "File Read Error", JOptionPane.ERROR_MESSAGE);
					}
			    	
			    	try{
			    		File output = new File("records.txt");
			    		FileWriter outputStream = new FileWriter(output, false);
			    		String[] strArray = new String[replaceArray.size()];
			    		for(int l = 0; l < strArray.length; l++){
			    			outputStream.write(replaceArray.get(l).toString()+"\r\n");
			    		}
			    		outputStream.close();
			    	}
			    	
			    	catch(FileNotFoundException exce)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException ece){
			    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
			    	}
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.DATE, 14);
			    	JOptionPane.showMessageDialog(null, "Your due date to return this book is " + c.getTime());
			    	frame.dispose();
					recordsTable bookTable = new recordsTable();
					bookTable.createTableGui();
			    	
				}
			});
			
			c.gridx = 0;
			c.gridy = 2;
			frame.add(borrowLabel, c);
			c.gridx = 0;
			c.gridy = 3;
			frame.add(borrowField, c);
			c.gridx = 0;
			c.gridy = 4;
			frame.add(borrowButton, c);
			
			c.gridx = 2;
			c.gridy = 2;
			frame.add(logOffButton, c);
			frame.setVisible(true);
		}
		
		void createTableGuiStaff(){
			new recordsTable();
			final JFrame frame = new JFrame("Library Management System");
			frame.setSize(1500, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			
			recordsTable newContentPane = new recordsTable();
			newContentPane.setOpaque(true);
			frame.setContentPane(newContentPane);
			logOffButton = new JButton("Log Off");
			logOffButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
					new Gui();
				}
			});
			
			borrowLabel = new JLabel("Which book would you like to check in?");
			borrowField = new JTextField(15);
			borrowLabel.setLabelFor(borrowField);
			borrowButton = new JButton("Check In");
			borrowButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					replaceStaffArray = new ArrayList<String>();
					String lines, bookToBorrow;
					bookToBorrow = borrowField.getText();
					
					try{
			    		BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
			    		
			    		while((lines = inputStream.readLine()) != null){
			    			if(bookToBorrow.equals(lines)){
			    				replaceStaffArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceStaffArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceStaffArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceStaffArray.add(lines);
			    				lines = inputStream.readLine();
			    				replaceStaffArray.add("Available");
			    				while((lines = inputStream.readLine()) != null){
			    					replaceStaffArray.add(lines);
			    	    		}
			    				break;
			    			}
			    			else
			    			{
			    				replaceStaffArray.add(lines);
			    			}
			    		}
			    		
			    		inputStream.close();
			    	}
			    	catch(FileNotFoundException exc)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException e){
						JOptionPane.showMessageDialog(null, "File Read Error", "File Read Error", JOptionPane.ERROR_MESSAGE);
					}
			    	
			    	try{
			    		File output = new File("records.txt");
			    		FileWriter outputStream = new FileWriter(output, false);
			    		String[] strArray = new String[replaceStaffArray.size()];
			    		for(int l = 0; l < strArray.length; l++){
			    			outputStream.write(replaceStaffArray.get(l).toString()+"\r\n");
			    		}
			    		outputStream.close();
			    	}
			    	
			    	catch(FileNotFoundException exce)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException ece){
			    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
			    	}
					
			    	JOptionPane.showMessageDialog(null, borrowField.getText() + " is now available to borrow");
			    	frame.dispose();
					recordsTable bookTable = new recordsTable();
					bookTable.createTableGuiStaff();
			    	
				}
			});
			
			c.gridx = 0;
			c.gridy = 2;
			frame.add(borrowLabel, c);
			c.gridx = 0;
			c.gridy = 3;
			frame.add(borrowField, c);
			c.gridx = 0;
			c.gridy = 4;
			frame.add(borrowButton, c);
			
			c.gridx = 2;
			c.gridy = 2;
			frame.add(logOffButton, c);
			menuBar = new JMenuBar();
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_A);
			menuBar.add(menu);
			
			menuItem = new JMenuItem("Add books to System", KeyEvent.VK_T);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent eve){
					frame.dispose();
					final JFrame sframe = new JFrame("Library Management System");
					sframe.setSize(600, 400);
					sframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					sframe.setLocation(dim.width/2-sframe.getSize().width/2, dim.height/2-sframe.getSize().height/2);
					sframe.setVisible(true);
					
					JPanel panel = new JPanel(new GridBagLayout());
					panel.setBackground(Color.CYAN);
					
					menuBar = new JMenuBar();
					menu = new JMenu("File");
					menu.setMnemonic(KeyEvent.VK_A);
					menuBar.add(menu);
					
					menuItem = new JMenuItem("Book Returns", KeyEvent.VK_T);
					menuItem.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent eve){
							sframe.dispose();
							recordsTable bookTable = new recordsTable();
							bookTable.createTableGuiStaff();
						}
					});
					menu.add(menuItem);
					
					menuItem2 = new JMenuItem("Book Deletion", KeyEvent.VK_T);
					menuItem2.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent eve){
							frame.dispose();
							recordsTable deleteTable = new recordsTable();
							deleteTable.createDeleteTable();
						}
					});
					menu.add(menuItem2);
					
					bookTitleLabel = new JLabel("Book Title: ");
					bookTitleField = new JTextField(15);
					bookTitleLabel.setLabelFor(bookTitleField);
					
					authorLabel = new JLabel("Author: ");
					authorField = new JTextField(15);
					authorLabel.setLabelFor(authorField);
					
					pubDateLabel = new JLabel("Pub/Rel Date: ");
					pubDateField = new JTextField(15);
					pubDateLabel.setLabelFor(pubDateField);
					
					isbnLabel = new JLabel("ISBN-10: ");
					isbnField = new JTextField(15);
					isbnLabel.setLabelFor(isbnField);
					
					saveButton = new JButton("Save");
					saveButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							
							String inputBookTitle = bookTitleField.getText();
							String inputAuthor = authorField.getText();
							String inputPubDate = pubDateField.getText();
							String inputISBN = isbnField.getText();
							String checkOut = "Available";
							String numOfRecords, input;
							int numRecords;
							Array = new ArrayList<String>();
							
							try
							{
								BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
								numOfRecords = inputStream.readLine();
								numRecords = Integer.parseInt(numOfRecords);
								numRecords++;
								numOfRecords = "" + numRecords;
								Array.add(numOfRecords);
								while((input = inputStream.readLine()) != null){
			    					Array.add(input);
			    	    		}
								inputStream.close();
							}
							catch(FileNotFoundException ec)
							{
								JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
							}
							catch(IOException ec)
							{
								JOptionPane.showMessageDialog(null, "File Read Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
							}
							
							try{
					    		File output = new File("records.txt");
					    		FileWriter outputStream = new FileWriter(output, false);
					    		String[] strArray = new String[Array.size()];
					    		for(int l = 0; l < strArray.length; l++){
					    			if(l == strArray.length - 1)
					    			{
					    				outputStream.write(Array.get(l).toString());
					    			}
					    			else
					    				{
					    					outputStream.write(Array.get(l).toString()+"\r\n");
					    				}
					    		}
					    		outputStream.close();
					    	}
					    	
					    	catch(FileNotFoundException exce)
					    	{
					    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
					    	}
					    	catch(IOException ece){
					    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
					    	}
							
							try
							{										
								PrintWriter outputStream = new PrintWriter(new BufferedWriter(new FileWriter("records.txt", true)));
								outputStream.println();
								outputStream.println(inputBookTitle);
								outputStream.println(inputAuthor);
								outputStream.println(inputPubDate);
								outputStream.println(inputISBN);
								outputStream.print(checkOut);
								outputStream.close();
							}
							catch(FileNotFoundException ex){
								JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
							}
							catch(IOException ex){
								JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
							}
							
							JOptionPane.showMessageDialog(null, "Successfully added book to records");
							bookTitleField.setText(null);
							authorField.setText(null);
							pubDateField.setText(null);
							isbnField.setText(null);
						}
					});
					
					logoffButton = new JButton("Log Off");
					logoffButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							frame.dispose();
							new Gui();
						}
					});
					
					GridBagConstraints constraint = new GridBagConstraints();
					constraint.gridx = 0;
					constraint.gridy = 0;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(bookTitleLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 0;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(bookTitleField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 1;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(authorLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 1;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(authorField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 2;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(pubDateLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 2;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(pubDateField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 3;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(isbnLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 3;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(isbnField, constraint);
					constraint.gridx = 1;
					constraint.gridy = 4;
					panel.add(saveButton, constraint);
					constraint.gridx = 2;
					constraint.gridy = 5;
					panel.add(logoffButton, constraint);
					sframe.setJMenuBar(menuBar);
					sframe.add(panel);
				}
			});
			
			menuItem2 = new JMenuItem("Book Deletion", KeyEvent.VK_T);
			menuItem2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent eve){
					frame.dispose();
					recordsTable deleteTable = new recordsTable();
					deleteTable.createDeleteTable();
				}
			});
			
			menu.add(menuItem);
			menu.add(menuItem2);
			frame.setJMenuBar(menuBar);
			frame.setVisible(true);
		}
	  
		public void createDeleteTable(){
			new recordsTable();
			final JFrame frame = new JFrame("Library Management System");
			frame.setSize(1500, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			
			recordsTable newContentPane = new recordsTable();
			newContentPane.setOpaque(true);
			frame.setContentPane(newContentPane);
			logOffButton = new JButton("Log Off");
			logOffButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
					new Gui();
				}
			});
			
			deleteLabel = new JLabel("Which book would you like to delete from the system?");
			deleteField = new JTextField(15);
			deleteLabel.setLabelFor(deleteField);
			deleteButton = new JButton("Delete");
			deleteButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev){
					deletionArray = new ArrayList<String>();
					String numOfRecords, bookToDelete, lines;
					int numRecords;
					bookToDelete = deleteField.getText();
					
					try
					{
						BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
						numOfRecords = inputStream.readLine();
						numRecords = Integer.parseInt(numOfRecords);
						numRecords--;
						numOfRecords = "" + numRecords;
						deletionArray.add(numOfRecords);
						while((lines = inputStream.readLine()) != null){
	    					deletionArray.add(lines);
	    	    		}
						inputStream.close();
					}
					catch(FileNotFoundException ec)
					{
						JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
					}
					catch(IOException ec)
					{
						JOptionPane.showMessageDialog(null, "File Read Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
					}
					
					try{
			    		File output = new File("records.txt");
			    		FileWriter outputStream = new FileWriter(output, false);
			    		String[] strArray = new String[deletionArray.size()];
			    		for(int l = 0; l < strArray.length; l++){
			    			if(l == strArray.length - 1)
			    			{
			    				outputStream.write(deletionArray.get(l).toString());
			    			}
			    			else
			    				{
			    					outputStream.write(deletionArray.get(l).toString()+"\r\n");
			    				}
			    		}
			    		outputStream.close();
			    	}
			    	
			    	catch(FileNotFoundException exce)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException ece){
			    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
			    	}
					
					deletionArray = new ArrayList<String>();
					
					try{
			    		BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
			    		
			    		while((lines = inputStream.readLine()) != null){
			    			if(bookToDelete.equals(lines)){
			    				lines = inputStream.readLine();
			    				lines = inputStream.readLine();
			    				lines = inputStream.readLine();
			    				lines = inputStream.readLine();
			    				while((lines = inputStream.readLine()) != null){
			    					deletionArray.add(lines);
			    	    		}
			    				break;
			    			}
			    			else
			    			{
			    				deletionArray.add(lines);
			    			}
			    		}
			    		
			    		inputStream.close();
			    	}
			    	catch(FileNotFoundException exc)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException e){
						JOptionPane.showMessageDialog(null, "File Read Error", "File Read Error", JOptionPane.ERROR_MESSAGE);
					}
			    	
			    	try{
			    		File output = new File("records.txt");
			    		FileWriter outputStream = new FileWriter(output, false);
			    		String[] outArray = new String[deletionArray.size()];
			    		for(int l = 0; l < outArray.length; l++){
			    			if(l == outArray.length - 1)
			    			{
			    				outputStream.write(deletionArray.get(l).toString());
			    			}
			    			else
			    			{
			    				outputStream.write(deletionArray.get(l).toString()+"\r\n");
			    			}
			    			
			    		}
			    		outputStream.close();
			    	}
			    	
			    	catch(FileNotFoundException exce)
			    	{
			    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
			    	}
			    	catch(IOException ece){
			    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
			    		System.exit(0);
			    	}
					
			    	JOptionPane.showMessageDialog(null, deleteField.getText() + " has been deleted");
			    	frame.dispose();
					recordsTable bookTable = new recordsTable();
					bookTable.createDeleteTable();
			    	
				}
			});
			
			c.gridx = 0;
			c.gridy = 2;
			frame.add(deleteLabel, c);
			c.gridx = 0;
			c.gridy = 3;
			frame.add(deleteField, c);
			c.gridx = 0;
			c.gridy = 4;
			frame.add(deleteButton, c);
			
			c.gridx = 2;
			c.gridy = 2;
			frame.add(logOffButton, c);
			menuBar = new JMenuBar();
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_A);
			menuBar.add(menu);
			
			menuItem = new JMenuItem("Add books to System", KeyEvent.VK_T);
			menuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent eve){
					frame.dispose();
					final JFrame frame = new JFrame("Library Management System");
					frame.setSize(600, 400);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
					frame.setVisible(true);
					
					JPanel panel = new JPanel(new GridBagLayout());
					panel.setBackground(Color.CYAN);
					
					menuBar = new JMenuBar();
					menu = new JMenu("File");
					menu.setMnemonic(KeyEvent.VK_A);
					menuBar.add(menu);
					
					menuItem = new JMenuItem("Book Returns", KeyEvent.VK_T);
					menuItem.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent eve){
							frame.dispose();
							recordsTable bookTable = new recordsTable();
							bookTable.createTableGuiStaff();
						}
					});
					menu.add(menuItem);
					
					menuItem2 = new JMenuItem("Book Deletion", KeyEvent.VK_T);
					menuItem2.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent eve){
							frame.dispose();
							recordsTable deleteTable = new recordsTable();
							deleteTable.createDeleteTable();
						}
					});
					menu.add(menuItem2);
					
					bookTitleLabel = new JLabel("Book Title: ");
					bookTitleField = new JTextField(15);
					bookTitleLabel.setLabelFor(bookTitleField);
					
					authorLabel = new JLabel("Author: ");
					authorField = new JTextField(15);
					authorLabel.setLabelFor(authorField);
					
					pubDateLabel = new JLabel("Pub/Rel Date: ");
					pubDateField = new JTextField(15);
					pubDateLabel.setLabelFor(pubDateField);
					
					isbnLabel = new JLabel("ISBN-10: ");
					isbnField = new JTextField(15);
					isbnLabel.setLabelFor(isbnField);
					
					saveButton = new JButton("Save");
					saveButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							
							String inputBookTitle = bookTitleField.getText();
							String inputAuthor = authorField.getText();
							String inputPubDate = pubDateField.getText();
							String inputISBN = isbnField.getText();
							String checkOut = "Available";
							String numOfRecords, input;
							int numRecords;
							Array = new ArrayList<String>();
							
							try
							{
								BufferedReader inputStream = new BufferedReader(new FileReader("records.txt"));
								numOfRecords = inputStream.readLine();
								numRecords = Integer.parseInt(numOfRecords);
								numRecords++;
								numOfRecords = "" + numRecords;
								Array.add(numOfRecords);
								while((input = inputStream.readLine()) != null){
			    					Array.add(input);
			    	    		}
								inputStream.close();
							}
							catch(FileNotFoundException ec)
							{
								JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
							}
							catch(IOException ec)
							{
								JOptionPane.showMessageDialog(null, "File Read Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
							}
							
							try{
					    		File output = new File("records.txt");
					    		FileWriter outputStream = new FileWriter(output, false);
					    		String[] strArray = new String[Array.size()];
					    		for(int l = 0; l < strArray.length; l++){
					    			if(l == strArray.length - 1)
					    			{
					    				outputStream.write(Array.get(l).toString());
					    			}
					    			else
					    				{
					    					outputStream.write(Array.get(l).toString()+"\r\n");
					    				}
					    		}
					    		outputStream.close();
					    	}
					    	
					    	catch(FileNotFoundException exce)
					    	{
					    		JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
					    	}
					    	catch(IOException ece){
					    		JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
					    	}
							
							try
							{										
								PrintWriter outputStream = new PrintWriter(new BufferedWriter(new FileWriter("records.txt", true)));
								outputStream.println();
								outputStream.println(inputBookTitle);
								outputStream.println(inputAuthor);
								outputStream.println(inputPubDate);
								outputStream.println(inputISBN);
								outputStream.print(checkOut);
								outputStream.close();
							}
							catch(FileNotFoundException ex){
								JOptionPane.showMessageDialog(null, "File Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
							}
							catch(IOException ex){
								JOptionPane.showMessageDialog(null, "File Write Error", "File Write Error", JOptionPane.ERROR_MESSAGE);
							}
							
							JOptionPane.showMessageDialog(null, "Successfully added book to records");
							bookTitleField.setText(null);
							authorField.setText(null);
							pubDateField.setText(null);
							isbnField.setText(null);
						}
					});
					
					logoffButton = new JButton("Log Off");
					logoffButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							frame.dispose();
							new Gui();
						}
					});
					
					GridBagConstraints constraint = new GridBagConstraints();
					constraint.gridx = 0;
					constraint.gridy = 0;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(bookTitleLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 0;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(bookTitleField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 1;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(authorLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 1;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(authorField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 2;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(pubDateLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 2;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(pubDateField, constraint);
					constraint.gridx = 0;
					constraint.gridy = 3;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(isbnLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 3;
					constraint.insets = new Insets(10, 0, 0, 0);
					panel.add(isbnField, constraint);
					constraint.gridx = 1;
					constraint.gridy = 4;
					panel.add(saveButton, constraint);
					constraint.gridx = 2;
					constraint.gridy = 5;
					panel.add(logoffButton, constraint);
					frame.setJMenuBar(menuBar);
					frame.add(panel);
				}
			});
			
			menuItem2 = new JMenuItem("Book Check In", KeyEvent.VK_T);
			menuItem2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent eve){
					frame.dispose();
					recordsTable deleteTable = new recordsTable();
					deleteTable.createTableGuiStaff();
				}
			});
			
			menu.add(menuItem);
			menu.add(menuItem2);
			frame.setJMenuBar(menuBar);
			frame.setVisible(true);
		}
		
		
	  public int getRowByValue(TableModel model, String value){
			for(int i = model.getRowCount() - 1; i >= 0; --i){
				for(int j = model.getColumnCount() - 1; j >= 0; --j){
					if(model.getValueAt(i, j).equals(value)){
						return i;
					}
				}
			}
			return -1;
		}

	  
	}

	
