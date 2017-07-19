package Viterby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UI implements ActionListener{
	public JFrame frame;
	private JPanel panel;
	private JPanel informationPanel;
	private JLabel encoderImage;
	private JLabel titleLabel;
	private JLabel textLabel;
	private JLabel text2Label;
	public JTextArea outputStreamText;
	private JScrollPane scroll;
	private JTextField textField;
	private UIDrawPanel drawPanel;
	private JButton button;
	private Font font;
	
	

	private Convolutional_encoder encoder;
	private String inputStream;
	public JTextField firstIndex;
	public JTextField secondIndex;
	public JTextField thirdIndex;
	public boolean start = false;
	private JButton decoderButton;
	
	
	private TestUii decoderUi;
	public UI(){

		frame = new JFrame("Encoder");
		font = new Font("Arial",Font.ITALIC,20);	
		panel = new JPanel();
		panel.setLayout(null);
		
		//Toolkit tk = Toolkit.getDefaultToolkit();
		int xsize = 1366;
		int ysize = 768;
		//int xsize = (int)tk.getScreenSize().getWidth();
		//int ysize = (int)tk.getScreenSize().getHeight();
		
		//frame.setSize(xsize,ysize);
		//frame.setUndecorated(true);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setAlwaysOnTop(false);
		frame.setSize(xsize, ysize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		
		titleLabel = new JLabel("Свёрточныйкодер");
		titleLabel.setFont(new Font("TimesNewRoman",Font.BOLD,40));
		titleLabel.setSize(400,50);
		titleLabel.setLocation(xsize/3, 1);
	
		textLabel = new JLabel("Входная последовательность (биты (1 или 0)): ");
		textLabel.setFont(new Font("Arial",Font.ITALIC,17));	
		textLabel.setSize(400,50);
		textLabel.setLocation(xsize/9, ysize/6);
		
		textField = new JTextField(200);
		textField.setFont(font);
		textField.setSize(400,40);
		textField.setLocation(textLabel.getX()+textLabel.getWidth(), textLabel.getY());
		
		button = new JButton("Закодировать");
		button.setFont(font);
		button.setSize(200,40);
		button.setLocation(textField.getX()+textField.getWidth()+20, textField.getY());
		button.addActionListener(this);
		
		encoderImage = new JLabel();
		encoderImage.setIcon(new ImageIcon(getClass().getResource("/encoder.png")));
		encoderImage.setSize(600,400);
		encoderImage.setLocation(xsize/3,textField.getY()+30);
		
		text2Label = new JLabel("Выходные кодовые слова : ");
		text2Label.setFont(font);
		text2Label.setSize(300,200);
		text2Label.setLocation((int) (xsize/3.5),(textLabel.getY()+encoderImage.getHeight()-50) );
		//outputStreamLabel = new JLabel("11 00 11 00 11 00 11");
		//outputStreamLabel.setFont(font);
		//outputStreamLabel.setSize(1000,200);
		//outputStreamLabel.setLocation(text2Label.getX()+text2Label.getWidth(), text2Label.getY());
		
		outputStreamText = new JTextArea("Empty");
		
		//outputStreamText.setLineWrap(true);
		//outputStreamText.setWrapStyleWord(true);
		outputStreamText.setEditable(false);
		outputStreamText.setFont(font);
		//outputStreamText.setSize(100,);
		//outputStreamText.setLocation(text2Label.getX()+text2Label.getWidth(), text2Label.getY());
		
		scroll = new JScrollPane(outputStreamText);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setSize(400,50);
		scroll.setLocation(text2Label.getX()+text2Label.getWidth(),text2Label.getY()+80);
		
		drawPanel = new UIDrawPanel();
		drawPanel.setLayout(null);
		drawPanel.setSize(xsize,ysize);
		drawPanel.setLocation(0, 0);
		
		decoderButton = new JButton("Запустьдекодер");
		decoderButton.setFont(font);
		decoderButton.setSize(button.getWidth(),button.getHeight());
		decoderButton.setLocation(xsize/2-100, ysize-100);
		decoderButton.addActionListener(new DecoderButton());
		
		frame.getContentPane().add(titleLabel);
		frame.getContentPane().add(textLabel);
		frame.getContentPane().add(textField);
		frame.getContentPane().add(text2Label);
		frame.getContentPane().add(scroll);
		frame.getContentPane().add(button);
		frame.getContentPane().add(encoderImage);
		frame.getContentPane().add(drawPanel);

		
		frame.setVisible(true);
		
		
		encoder = new Convolutional_encoder();
		
		firstIndex = new JTextField("0");
		firstIndex.setSize(35 , 45);
		firstIndex.setLocation(textField.getX()+85, textField.getY()+encoderImage.getHeight()/2+30);
		firstIndex.setFont(font);

		secondIndex = new JTextField("0");
		secondIndex.setSize(35 , 45);
		secondIndex.setLocation(firstIndex.getX()+firstIndex.getHeight()+10, firstIndex.getY());
		secondIndex.setFont(font);
		
		thirdIndex = new JTextField("0");
		thirdIndex.setSize(35 , 45);
		thirdIndex.setLocation(secondIndex.getX()+secondIndex.getHeight()+10, secondIndex.getY());
		thirdIndex.setFont(font);
		

		
		frame.getContentPane().add(firstIndex);
		frame.getContentPane().add(secondIndex);
		frame.getContentPane().add(thirdIndex);
		
		while(true){
			if(start){
				inputStream = textField.getText();
				encoder.setUi(this);
				encoder.encoder_start(inputStream, (byte)3);
				//addInformation();
				createDecoderButton();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		start = true;
	}
	private void createDecoderButton(){
		frame.getContentPane().add(decoderButton);
		decoderButton.repaint();
		start=false;
		}
class UIDrawPanel extends JPanel {
	public void paintComponent(Graphics g){
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//g.drawLine(10, 70, 200, 70);
		//g.drawString(firstIndex.getText(),firstIndex.getX(),firstIndex.getY());
	}
}
class DecoderButton implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent arg0) {
		decoderUi = new TestUii();
		decoderUi.go();
		decoderUi.setEncoder(encoder);
	}
	
}
}


