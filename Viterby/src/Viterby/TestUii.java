package Viterby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class TestUii implements ActionListener {
	protected JFrame frame;
	protected JScrollPane scroller;
	protected JLabel textLabel;
	protected JPanel textPanel;
	protected JPanel informationPanel;
	protected JPanel informationPanel2;
	protected JLabel informationLabel1;
	protected JLabel informationLabel2;
	protected JLabel informationLabel3;
	protected JTextArea informationLabel11;
	protected JTextArea informationLabel22;
	
	JScrollPane scroll1;
	JScrollPane scroll2;
	
	Rectangle[] rect;
	ArrayList<Rectangle[]>rectangles;
	ArrayList<State[]>statesList;
	MyDrawPanel panel;
	JButton button;
	Font font;
	Font font2;
	Font font3;
	boolean drawFullDiagram = false;
	boolean drawRightDiagram = false;
	
	private Convolutional_encoder encoder;
	private StateDiagram diagram;
	
	
	private int x = 50;
	private int y =50;
	private int width = 10;
	private int height = 10;
	private int iteratorX = 100;
	private int iteratorY = 100;
	
	int xx = 0;
	int count;
	
	public void go(){
		frame = new JFrame("Decoder");
		button = new JButton("Построить диаграмму");
		button.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		count = 1;
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xsize = (int)tk.getScreenSize().getWidth();
		int ysize = (int)tk.getScreenSize().getHeight();
		
		frame.setSize(xsize,ysize);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new MyDrawPanel();
		textPanel = new JPanel();
		font = new Font("Arial",Font.BOLD,30);
		font2 =new Font("Arial",Font.ITALIC,20); 
		font3 =new Font("Arial",Font.ITALIC,20); 


		
		informationLabel1 =new JLabel("Входные кодовые слова:");
		informationLabel1.setFont(font2);
		informationLabel2 =new JLabel("Декодированная");
		informationLabel2.setFont(font2);
		informationLabel3 =new JLabel("последовательнсоть:");
		informationLabel3.setFont(font2);
		
		informationLabel11 =new JTextArea(5,5);
		informationLabel11.setFont(font3);
		informationLabel11.setWrapStyleWord(true);
		informationLabel11.setLineWrap(true);
		informationLabel22 =new JTextArea(5,5);
		informationLabel22.setFont(font3);
		informationLabel11.setWrapStyleWord(true);
		informationLabel22.setLineWrap(true);
		
		scroll1 = new JScrollPane(informationLabel11);
		//scroll1.setPreferredSize(new Dimension(100,20));
		scroll1.setSize(5,5);
		scroll2 = new JScrollPane(informationLabel22);
		scroll2.setSize(5,5);
		//scroll2.setPreferredSize(new Dimension(100,20));
		
		
		informationPanel = new JPanel();
		informationPanel.setLayout(new BoxLayout(informationPanel,BoxLayout.Y_AXIS));
		informationPanel.setSize(5,5);
		informationPanel2 = new JPanel();
		informationPanel2.setLayout(new BoxLayout(informationPanel2,BoxLayout.Y_AXIS));
		informationPanel2.setSize(5,5);
		
		informationPanel.add(informationLabel1);
		informationPanel.add(scroll1);
		
		informationPanel2.add(informationLabel2);
		informationPanel2.add(informationLabel3);
		informationPanel2.add(scroll2);
		
		scroller = new JScrollPane(panel);
		scroller.setBackground(Color.white);
		//scroller.setLayout(new Bor);
		
		//frame.setContentPane(scroller);
		textLabel = new JLabel("Решетчатая диаграмма состояний");
		textLabel.setFont(font);
		textPanel.add(textLabel);
		
		
		
		frame.getContentPane().add(BorderLayout.CENTER,scroller);
		frame.getContentPane().add(BorderLayout.SOUTH,button);
		frame.getContentPane().add(BorderLayout.NORTH,textPanel);
		frame.getContentPane().add(BorderLayout.WEST,informationPanel);
		frame.getContentPane().add(BorderLayout.EAST,informationPanel2);
		
		frame.setVisible(true);
		
		rectangles = new ArrayList<Rectangle[]>();
		
		diagram = new StateDiagram(this);
		
	}
	public void setEncoder(Convolutional_encoder ecoder){
		encoder = ecoder;
	}
	private void createRectangles(Rectangle[] rectangl,int count) {
		for(int i = 0; i<rectangl.length;i++){
			rectangl[i] = new Rectangle(iteratorX*count,iteratorY*i+100,width,height);
			xx = iteratorX*count;
			panel.setPreferredSize(new Dimension(xx,400));
			panel.scrollRectToVisible(rectangl[i]);
			statesList.get(count-1)[i].point.setLocation(rectangl[i].getLocation());
		}
		scroller.setPreferredSize(panel.getSize());
		try{Thread.sleep(20);}catch(Exception Ex){}
		rectangles.add(rectangl);
		panel.repaint();
		this.count = count;
	}
	public void drawState(ArrayList<State[]>stateList,int count){
		this.statesList = stateList;
		for(int i = 0 ; i<statesList.size();i++){
			rect = new Rectangle[statesList.get(i).length];
		}
		createRectangles(rect,count);
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand()=="Построить диаграмму"){
		encoder.go(diagram);
		button.setText("Декодировать");
		informationLabel11.setText(encoder.wrongStreamString+"\n");
		drawFullDiagram = true;
		frame.pack();
		}else if(ev.getActionCommand()=="Декодировать"){
			drawFullDiagram = false;
			drawRightDiagram = true;
			informationLabel22.setText(diagram.definedWords+"\n");	
		}
		frame.repaint();
	}
	class MyDrawPanel extends JPanel{
		Rectangle rect;
		public void go(Rectangle rect){
		this.rect = rect;
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(drawFullDiagram){				
				for(int i = 0 ; i<statesList.size();i++){
					for(int y = 0 ;y<statesList.get(i).length;y++){
						//scrollRectToVisible(rectangles.get(i)[y]);
						g.setFont(new Font("Arial",Font.ITALIC,15));
						g.setColor(Color.black);
						g.fillOval(statesList.get(i)[y].point.x,statesList.get(i)[y].point.y, rectangles.get(i)[y].width, rectangles.get(i)[y].height);
						g.setColor(Color.blue);
						g.drawString(""+(statesList.get(i)[y].SumHemming),statesList.get(i)[y].point.x,statesList.get(i)[y].point.y-5);
						g.setFont(new Font("Arial",Font.ITALIC,12));
						g.drawString("("+Integer.toBinaryString(statesList.get(i)[y].state)+")", statesList.get(i)[y].point.x,statesList.get(i)[y].point.y+30);
						g.setFont(new Font("Arial",Font.ITALIC,15));
						if(statesList.get(i)[y].state == 0b00){
							g.setColor(Color.red);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2);
							g.setColor(Color.green);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2+iteratorY);
						}else if(statesList.get(i)[y].state == 0b10){
							g.setColor(Color.red);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2+iteratorY);
							g.setColor(Color.green);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2+iteratorY*2);
						}else if(statesList.get(i)[y].state == 0b01){
							g.setColor(Color.red);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2-iteratorY*2);
							g.setColor(Color.green);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2-iteratorY);
						}else if(statesList.get(i)[y].state == 0b11){
							g.setColor(Color.red);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2-iteratorY);
							g.setColor(Color.green);
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].point.x+width/2+ iteratorX,statesList.get(i)[y].point.y+height/2);
						}
					}
				}
				
			}else if(drawRightDiagram){
				for(int i = statesList.size()-1;i>0;i--){
					for(int y =0;y<statesList.get(i).length;y++){
						g.setFont(new Font("Arial",Font.ITALIC,15));
						g.setColor(Color.black);
						g.fillOval(statesList.get(i)[y].point.x,statesList.get(i)[y].point.y, rectangles.get(i)[y].width, rectangles.get(i)[y].height);
						g.setColor(Color.blue);
						if(!statesList.get(i)[y].dead){
							g.drawString(""+(statesList.get(i)[y].finalLastStateLink.SumHemming),statesList.get(i)[y].point.x,statesList.get(i)[y].point.y-5);
							g.setFont(new Font("Arial",Font.ITALIC,12));
							g.drawString("("+Integer.toBinaryString(statesList.get(i)[y].state)+")", statesList.get(i)[y].point.x,statesList.get(i)[y].point.y+30);
							g.setFont(new Font("Arial",Font.ITALIC,15));	
						}
						if(statesList.get(i)[y].state == 0b00 && !statesList.get(i)[y].dead){
							if(statesList.get(i)[y].finalLastStateLink.index == 0){
								g.setColor(Color.red);
							}else{
								g.setColor(Color.green);
							}
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].finalLastState.point.x+width/2,statesList.get(i)[y].finalLastState.point.y+height/2);
						}else if(statesList.get(i)[y].state == 0b10 && !statesList.get(i)[y].dead){
							if(statesList.get(i)[y].finalLastStateLink.index == 0){
								g.setColor(Color.red);
							}else{
								g.setColor(Color.green);
							}
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].finalLastState.point.x+width/2,statesList.get(i)[y].finalLastState.point.y+height/2);
						}else if(statesList.get(i)[y].state == 0b01 && !statesList.get(i)[y].dead){
							if(statesList.get(i)[y].finalLastStateLink.index == 0){
								g.setColor(Color.red);
							}else{
								g.setColor(Color.green);
							}
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].finalLastState.point.x+width/2,statesList.get(i)[y].finalLastState.point.y+height/2);
						}else if(statesList.get(i)[y].state == 0b11 && !statesList.get(i)[y].dead){
							if(statesList.get(i)[y].finalLastStateLink.index == 0){
								g.setColor(Color.red);
							}else{
								g.setColor(Color.green);
							}
							g.drawLine(statesList.get(i)[y].point.x+width/2,statesList.get(i)[y].point.y+height/2,statesList.get(i)[y].finalLastState.point.x+width/2,statesList.get(i)[y].finalLastState.point.y+height/2);
						}
					}
				}
			}
		}
	}
}

