package Viterby;

public class Convolutional_encoder {
	private byte encoder_size;
	private byte encoder_itterations;
	private byte[][] encoder_states_full ;
	private byte[][] outputStream;
	private String input;
	private byte[] symbol;
	private byte[] convertedOutputStream;
	private byte[] wrongOutputStream;
	private StateDiagram diagram;
	public String outputStreamString;
	public String wrongStreamString;
	private UI ui;
	private long speed;

	public void encoder_start(String input , byte input_encoder_size){
		encoder_size = input_encoder_size;
		outputStreamString ="";
		wrongStreamString="";
		this.input = input;
		char letter;
		speed = 30;
		symbol = new byte[encoder_size+(input.length()-1)];
		//System.out.println("Входнаяпоследовательность");
		for(int i=0 ; i<input.length();i++){
			letter =input.charAt(i);
			symbol[i] =(byte) Character.getNumericValue(letter);
			if(symbol[i] > 0b1){
				symbol[i]=0;
			}
			//System.out.print(symbol[i]);
		}
		encoder_itterations = (byte)symbol.length;
		//System.out.println();
		//System.out.println("Количествоэлементов  = "+input.length());
		
		// реализациякодера
		
		// 1 созданиесодержимогорегистра
		encoder_states_full=encoder_registersContent();
		//diagram.go(encoder_states_full,encoder_size);
		//2 созданиевыходнойпоследовательности
		outputStream = encoder_outputStream();
		// 3 переводвыходнойпоследовательности в байты
		convertedOutputStream = encoder_ConvertOutputStream();
		// 4 введениеошибки
		wrongOutputStream = encoder_wrongOutputStream(convertedOutputStream);
		//System.out.println();
		// 5 диаграммасостояний
		

		//System.out.println("Код ->"+input);
	}
	private byte[][] encoder_registersContent(){
		encoder_states_full  =new byte[encoder_itterations][encoder_size];//массиврегистра
		
		for(byte i= 0,z=0 ; i<encoder_itterations ; i++){
			//System.out.println("****************** "+(i+1)+" итерация ***********");
			
			encoder_states_full[i][z]=symbol[i];
			//System.out.print(encoder_states_full[i][z]);
			ui.firstIndex.setText(""+encoder_states_full[i][z]);
			try{
				Thread.sleep(speed);
			}catch(Exception ex){}
			if(i!= 0){
				for(byte y = 1 ; y<encoder_size;y++){
					encoder_states_full[i][y]=encoder_states_full[i-1][y-1];
					//System.out.print(encoder_states_full[i][y]);
					if(y ==1){
						ui.secondIndex.setText(""+encoder_states_full[i][y]);	
					}else{
						ui.thirdIndex.setText(""+encoder_states_full[i][y]);	
					}
					try{
						Thread.sleep(speed);
					}catch(Exception ex){}
				}	
			}else{
				for(byte y = 1 ; y<encoder_size;y++){
					ui.thirdIndex.setText(""+encoder_states_full[i][y]);
					try{
						Thread.sleep(speed);
					}catch(Exception ex){}
				}
			}
			//System.out.println();
		}
		return encoder_states_full;
	}
	private byte[][] encoder_outputStream(){
		outputStream = new byte[encoder_itterations][2];
		for(byte i=0,z=0 ; i<encoder_itterations;i++){
				for(byte y =0;y<encoder_size;y++){
					outputStream[i][z]^=encoder_states_full[i][y];
				}
		}
		for(byte i=0,z=1; i<encoder_itterations;i++){
			outputStream[i][z]=(byte)(encoder_states_full[i][0]^encoder_states_full[i][encoder_size-1]);
		}
		System.out.println("******* Передоваемые кодовые слова************");
		for(byte i=0 ; i<encoder_itterations;i++){
			for(byte y =0;y<2;y++){
				System.out.print(outputStream[i][y]);
				outputStreamString +=outputStream[i][y];
				ui.outputStreamText.setText(outputStreamString);
				try{
					Thread.sleep(speed);
				}catch(Exception ex){}
			}
		System.out.print(" ");	
		outputStreamString += " - ";
		ui.outputStreamText.setText(outputStreamString);
	}
		System.out.println();
		return outputStream;
	}
	private byte[] encoder_ConvertOutputStream(){
		String[] x = new String[encoder_itterations];
		String[] y = new String[encoder_itterations];
		String[] xy = new String[encoder_itterations];
		byte[] xx = new byte[encoder_itterations];
		//System.out.println("**********Выходнаяпоследовательность в числах**********");
		for(byte i = 0,k=0;i<x.length;i++){
			x[i]=""+outputStream[i][k];
			k++;
			y[i]=""+outputStream[i][k];
			k--;
			xy[i] = x[i]+y[i];
			xx[i] = (byte) Integer.parseInt(xy[i],2);
			//outputStreamString +=" "+Integer.toBinaryString(xx[i])+" - ";
			//System.out.println(Byte.valueOf(xx[i]));
		}
		x = null;
		y = null;
		xy = null;
		return xx;
	}
	private byte[] encoder_wrongOutputStream(byte[] outputStream) {
		int random = 0 + (int) (Math.random()*outputStream.length);
		if(outputStream[random] == 3){
			outputStream[random] =2;	
		}else{
			outputStream[random] = 1;
		}
		//byte[] a = {0b11,0b01,0b01,0b10,0b01,0b01,0b11,0b00,0b01,0b11,0b00,0b00,0b00};
		System.out.println("*************Принятые слова (ошибка)***********");
		for(int i = 0 ; i<outputStream.length ; i++){
			System.out.print(Integer.toBinaryString(outputStream[i])+" ");
			wrongStreamString+=Integer.toBinaryString(outputStream[i])+" - ";
		}
		System.out.println();
		//return a;
		return outputStream;
	}
	public void setUi(UI ui){
		this.ui = ui;
	}
	public void go(StateDiagram diagram){
		for(byte i = 0; i<convertedOutputStream.length;i++){
			diagram.go(encoder_states_full,encoder_size,wrongOutputStream[i]);
		}
	}
}
