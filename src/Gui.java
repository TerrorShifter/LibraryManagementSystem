import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
public class Gui {

		ArrayList<String> nameArr = new ArrayList<String>();
		ArrayList<Integer> pwordArr = new ArrayList<Integer>();
		private JButton studentButton;
		private JButton staffButton;
		private JButton loginButton;
		private JButton saveButton;
		private JButton logoffButton;
		private JLabel loginLabel;
		private JLabel loginLabel2;
		private JLabel bookTitleLabel;
		private JLabel authorLabel;
		private JLabel pubDateLabel;
		private JLabel isbnLabel;
		private JTextField username;
		private JPasswordField password;
		private JTextField bookTitleField;
		private JTextField authorField;
		private JTextField pubDateField;
		private JTextField isbnField;
		private JButton backButton;
		private JMenuBar menuBar;
		private JMenu menu;
		private JMenuItem menuItem, menuItem2;
		private JFrame frame;
		private JPanel panel;
		ArrayList<String> Array;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		public Gui(){
			frame = new JFrame("Library Management System");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			panel = new JPanel(new GridBagLayout());
			panel.setBackground(Color.CYAN);
			
			ImageIcon titleScreen = new ImageIcon("LibraryMS.jpg");
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(titleScreen);
			
			loginLabel = new JLabel("Select User Login");
			
			studentButton = new JButton("Student");	
			studentButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
					recordsTable bookTable = new recordsTable();
					bookTable.createTableGui();
				}
			});
			
			staffButton = new JButton("Staff");
			staffButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
					frame = new JFrame("Library Management System");
					frame.setVisible(true);
					frame.setSize(400, 200);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
										
					panel = new JPanel(new GridBagLayout());
					panel.setBackground(Color.CYAN);
					
					loginLabel = new JLabel("Enter Username: ");
					username = new JTextField(15);
					loginLabel.setLabelFor(username);
					
					loginLabel2 = new JLabel("Enter Password");
					password = new JPasswordField(10);
					loginLabel2.setLabelFor(password);
					
					backButton = new JButton("Back");
					backButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent en){
							frame.dispose();
							new Gui();
						}
					});
					
					loginButton = new JButton("Login");
					loginButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							 String inputname = username.getText();
							 char[] inputpword = password.getPassword();
							 String line;
													
								try{
									BufferedReader inputStream = new BufferedReader(new FileReader("staffUsernamePassword.txt"));
									
									while((line = inputStream.readLine()) != null){
										int indexOfSpace = line.indexOf(" ");
										String Uname = line.substring(0, indexOfSpace);
										int Pword = Integer.parseInt(line.substring(indexOfSpace).trim());
										
										nameArr.add(Uname);
										pwordArr.add(Pword);
									}

									inputStream.close();
								}
								catch(FileNotFoundException ex){
									System.out.println("File not found");
								}
								catch(IOException ec){
									System.out.println("Error");
								}
					            if (isPasswordCorrect(inputname, inputpword)) {
					                JOptionPane.showMessageDialog(frame, "Login Successful");
					         		                
					                frame.dispose();
									frame = new JFrame("Library Management System");
									frame.setSize(600, 400);
									frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
									frame.setVisible(true);
									
									panel = new JPanel(new GridBagLayout());
									panel.setBackground(Color.CYAN);
									
									menuBar = new JMenuBar();
									menu = new JMenu("File");
									menu.setMnemonic(KeyEvent.VK_A);
									menuBar.add(menu);
									
									menuItem = new JMenuItem("Book Check In", KeyEvent.VK_T);
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
					            else {
					                JOptionPane.showMessageDialog(frame,
					                    "Invalid username or password. Try again.",
					                    "Error Message",
					                    JOptionPane.ERROR_MESSAGE);
					            }
						}
					});
					
					GridBagConstraints constraint = new GridBagConstraints();
					constraint.gridx = 0;
					constraint.gridy = 0;
					panel.add(loginLabel, constraint);
					constraint.gridx = 1;
					constraint.gridy = 0;
					panel.add(username, constraint);
					constraint.gridx = 0;
					constraint.gridy = 1;
					panel.add(loginLabel2, constraint);
					constraint.gridx = 1;
					constraint.gridy = 1;
					panel.add(password, constraint);
					constraint.gridx = 1;
					constraint.gridy = 2;
					panel.add(loginButton, constraint);
					constraint.gridx = 0;
					constraint.gridy = 2;
					panel.add(backButton, constraint);
					frame.add(panel);
				}
			});
			
			GridBagConstraints constraint = new GridBagConstraints();
			
			constraint.insets = new Insets(20, 0, 20, 0);
			constraint.gridx = 0;
			constraint.gridy = 1;
			panel.add(loginLabel, constraint);
			
			constraint.insets = new Insets(20, 20, 0, 20);
			constraint.gridx = 0;
			constraint.gridy = 0;
			panel.add(imageLabel, constraint);
			
			constraint.insets = new Insets(0, 0, 20, 150);
			constraint.gridx = 0;
			constraint.gridy = 2;
			panel.add(studentButton, constraint);
			
			constraint.insets = new Insets(0, 150, 20, 0);
			constraint.gridx = 0;
			constraint.gridy = 2;
			panel.add(staffButton, constraint);
						
			frame.add(panel);
			frame.pack();
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			frame.setVisible(true);
		}
		
		
		private boolean isPasswordCorrect(String inputname, char[] inputpword) {
	       boolean isCorrect = false, found1 = false, found2 = false;
	       int pword = Integer.parseInt(new String(inputpword));
	       int count = 0, quantity = 0;

	       for(String uname : nameArr){
	    	   if(inputname.equals(uname)){
	    		   found1 = true;
	    		   break;
	    	   }
	    	   count++;
	       }
	       for(int psword : pwordArr){
	      	   if(psword == pword){
	    		   found2 = true;
	    		   break;
	    	   }
	    	   quantity++;
	       }
	       if(count == quantity && found1 && found2){
	    	   isCorrect = true;
	       }
	       
	       return isCorrect;
	    }


}
