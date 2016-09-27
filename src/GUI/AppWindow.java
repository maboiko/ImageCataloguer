package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.Controller;
import exceptions.AttributeNotFoundException;
import utilities.FromFileCreator;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class AppWindow extends JFrame {

	private JPanel contentPane;
	private JTextField sourceName;
	private JTextField destName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String sortingFeature;
	private ArrayList<File> imagesToSort;
	private JButton checkedSortButton;
	private JFileChooser filechooser = new JFileChooser();
	private JPanel panel = new JPanel();
	private Task task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow frame = new AppWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppWindow() {
		setBackground(Color.WHITE);
		setFont(new Font("Cambria", Font.BOLD, 18));
		setTitle("PicSorter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		sourceName = new JTextField();
		sourceName.setEditable(false);
		sourceName.setBackground(Color.WHITE);
		sourceName.setFont(new Font("Cambria", Font.PLAIN, 14));
		sourceName.setColumns(10);
		
		JButton btnSelectSource = new JButton("Select");
		btnSelectSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checkTask();
				fillSourceName();
			}
		});
		btnSelectSource.setBackground(new Color(230, 230, 250));
		btnSelectSource.setFont(new Font("Cambria", Font.BOLD, 12));
		
		destName = new JTextField();
		destName.setEditable(false);
		destName.setBackground(Color.WHITE);
		destName.setFont(new Font("Cambria", Font.PLAIN, 14));
		destName.setColumns(10);
		
		JButton btnSelectDest = new JButton("Select");
		btnSelectDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillDestName();
			}
		});
		btnSelectDest.setBackground(new Color(230, 230, 250));
		btnSelectDest.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JRadioButton sortName = new JRadioButton("Name");
		buttonGroup.add(sortName);
		sortName.setBackground(new Color(211, 211, 211));
		sortName.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortName.setActionCommand("name");
		sortName.addActionListener(new FeatureActionListener());
		
		JRadioButton sortColor = new JRadioButton("Color");
		buttonGroup.add(sortColor);
		sortColor.setBackground(new Color(211, 211, 211));
		sortColor.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortColor.setActionCommand("color");
		sortColor.addActionListener(new FeatureActionListener());
		
		JRadioButton sortYear = new JRadioButton("Year");
		buttonGroup.add(sortYear);
		sortYear.setBackground(new Color(211, 211, 211));
		sortYear.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortYear.setActionCommand("year");
		sortYear.addActionListener(new FeatureActionListener());
		
		JRadioButton sortMonthYear = new JRadioButton("Month of year");
		buttonGroup.add(sortMonthYear);
		sortMonthYear.setBackground(new Color(211, 211, 211));
		sortMonthYear.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortMonthYear.setActionCommand("monthyear");
		sortMonthYear.addActionListener(new FeatureActionListener());
		
		JRadioButton sortOrient = new JRadioButton("Orientation");
		buttonGroup.add(sortOrient);
		sortOrient.setBackground(new Color(211, 211, 211));
		sortOrient.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortOrient.setActionCommand("orientation");
		sortOrient.addActionListener(new FeatureActionListener());
		
		JRadioButton sortExten = new JRadioButton("Extension");
		buttonGroup.add(sortExten);
		sortExten.setBackground(new Color(211, 211, 211));
		sortExten.setFont(new Font("Cambria", Font.PLAIN, 12));
		sortExten.setActionCommand("extension");
		sortExten.addActionListener(new FeatureActionListener());
		
		checkedSortButton = new JButton("Sort");
		checkedSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTask();
				doSort();
			}
		});
		checkedSortButton.setEnabled(false);
		checkedSortButton.setBackground(new Color(70, 130, 180));
		checkedSortButton.setFont(new Font("Cambria", Font.BOLD, 16));	
		
		JTextPane txtpnHghghg = new JTextPane();
		txtpnHghghg.setBackground(new Color(211, 211, 211));
		txtpnHghghg.setFont(new Font("Cambria", Font.PLAIN, 15));
		txtpnHghghg.setEditable(false);
		txtpnHghghg.setText("Folder with the images to sort:");
		
		JTextPane txtpnHhh = new JTextPane();
		txtpnHhh.setBackground(new Color(211, 211, 211));
		txtpnHhh.setEditable(false);
		txtpnHhh.setFont(new Font("Cambria", Font.PLAIN, 15));
		txtpnHhh.setText("Folder where the sorted images will be stored:");
		
		JTextPane txtpnSortingFeature = new JTextPane();
		txtpnSortingFeature.setBackground(new Color(211, 211, 211));
		txtpnSortingFeature.setEditable(false);
		txtpnSortingFeature.setFont(new Font("Cambria", Font.PLAIN, 15));
		txtpnSortingFeature.setText("Sorting feature");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addComponent(txtpnHghghg, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(sourceName, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(btnSelectSource, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtpnHhh, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(destName, 313, 313, 313)
									.addGap(18)
									.addComponent(btnSelectDest, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(checkedSortButton, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtpnSortingFeature, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
									.addGap(16))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(sortName)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(sortColor)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sortYear)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sortMonthYear)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(sortOrient)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(sortExten)))
							.addGap(10))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtpnHghghg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSelectSource, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(sourceName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnHhh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(destName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectDest))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnSortingFeature, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(sortName)
						.addComponent(sortColor)
						.addComponent(sortYear)
						.addComponent(sortMonthYear)
						.addComponent(sortOrient)
						.addComponent(sortExten))
					.addGap(18)
					.addComponent(checkedSortButton)
					.addContainerGap())
		);
		panel.setBackground(Color.WHITE);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.setLayout(gl_contentPane);
	}
	
	private void fillSourceName() {
		File sourceDir = selectWithFileChooser();
		checkTask();
		panel.removeAll();
		imagesToSort = Controller.findImagesToSort(sourceDir.getAbsolutePath());
		
			if(imagesToSort != null) {
				sourceName.setText(sourceDir.getAbsolutePath());
				task = new Task();
				task.start();
			}
			else {
				sourceName.setText(null);
				JOptionPane.showMessageDialog(null,
	                    "There are no JPEG or PNG images in the selected folder. Please, select another folder!", 
	                    "No files to sort", JOptionPane.WARNING_MESSAGE);
			}

		checkIfSortEnable();
	}
	
	private void fillDestName() {
		File destDir = selectWithFileChooser();
		if(destDir != null) {
			destName.setText(destDir.getAbsolutePath());
			checkIfSortEnable();
		}
	}
	
	private File selectWithFileChooser() {
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		File result = null;
		int state = filechooser.showOpenDialog(null);
		if(state == JFileChooser.APPROVE_OPTION) 
			result = filechooser.getSelectedFile();
		else throw new RuntimeException("No directory to work with");
		
		return result;
	}
	
	private void checkIfSortEnable() {
		System.out.println("Checking button");
		if(!sourceName.getText().trim().equals("") && !destName.getText().trim().equals("") && sortingFeature != null)
			checkedSortButton.setEnabled(true);
		else
			checkedSortButton.setEnabled(false);
	}
	
	private void doSort() {
		try {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Controller.processSorting(sortingFeature, destName.getText(), imagesToSort);
			setCursor(null);

			JOptionPane.showMessageDialog(null,
	                   ("Process has been finished successfully! Your sorted files are in a new folder in the directory: " + destName.getText()), 
	                    "Files are sorted", JOptionPane.WARNING_MESSAGE);
		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,
                   "Files can not be copied!", 
                    "Sorting failed", JOptionPane.WARNING_MESSAGE);
		}
		catch (AttributeNotFoundException e){
			JOptionPane.showMessageDialog(null,
                    e.getMessage(), 
                    "Sorting failed", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private BufferedImage resizeImage(BufferedImage original) {
		int newHeight = (int) (0.8 * panel.getHeight());
		int newWidth = (original.getWidth() * newHeight) / original.getHeight();
		
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, original.getType());
		Graphics2D grafImg = resizedImage.createGraphics();
		grafImg.drawImage(original, 0, 0, newWidth, newHeight,  null);
		grafImg.dispose();
		
		return resizedImage;
	}
	
	private void fillPanelWithIcons(File image){
		
		BufferedImage imageIcon = resizeImage(FromFileCreator.createBufferedImage(image));
		
		JPanel smallPanel = new JPanel();
		smallPanel.setPreferredSize(new Dimension(imageIcon.getWidth(), imageIcon.getHeight()));
		smallPanel.setBackground(Color.WHITE);
		
		JLabel labelForImage = new JLabel();
		
		smallPanel.add(labelForImage);
		ImageIcon icon = new ImageIcon(imageIcon);
		labelForImage.setIcon(icon);
		smallPanel.revalidate();
		smallPanel.repaint();
		
		panel.add(smallPanel);
		panel.revalidate();
		panel.repaint();	
	}
	
	private void checkTask() {
		if (task != null && !task.isInterrupted()) {
			task.interrupt();
		    try {
				task.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class FeatureActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			sortingFeature = arg0.getActionCommand();
			System.out.println(sortingFeature);
			checkIfSortEnable();
			
		}
		
	}
	
	private class Task extends Thread {

		@Override
		public void run() {
			
			for(File image: imagesToSort) {
				if(!this.isInterrupted())
					fillPanelWithIcons(image);
				else
					break;
			}
		}

		
		
		
	
	}
}
