package Viterby;

public class Link {
	private byte state;
	private byte Hemming;
	public byte SumHemming;
	private byte branchWord;
	public byte index;
	public byte nextState;
	public byte currentState;
	
	private String state_String;
	private char[] letters;
	private byte[] bytes;
	private byte encoder_size;
	
	
	public Link(byte InputState,byte InputBranchWord,byte InputIndex,byte InputEncoder_size,byte Sumhemming){
		currentState = InputState;
		SumHemming = Sumhemming;
		index = InputIndex;
		state_String = "";
		state = InputState;
		encoder_size = InputEncoder_size;
		
		defineBranchWord(state);
		calcHemming(InputBranchWord);
		nextState(InputBranchWord);
	}
	private void nextState(byte InputBranchWord) {
		String sum=""+bytes[0];
		for(byte i = 1 ; i<encoder_size-1;i++){
			sum+=bytes[i];
		}
		nextState = (byte) Integer.parseInt((sum),2);
		//System.out.println(Integer.toBinaryString(InputBranchWord));
		//System.out.println(Integer.toBinaryString(state)+" -> "+sum+" (Метрика : "+Hemming+" ) (Индекс ="+index+")");
	}
	private void calcHemming(byte InputBranchWord){
		//System.out.println("Метрикадлясостоянияиз");
		Hemming = 0;
		if((InputBranchWord^branchWord) == 3){
			Hemming = 2;
		}else if((InputBranchWord^branchWord) == 2){
			Hemming = 1;
		}else{
			Hemming = (byte)(InputBranchWord^branchWord) ;
		}
		SumHemming+=Hemming;
		//System.out.println("Метрика : "+Hemming);
		//System.out.println("Суммарнаяметрика : "+SumHemming);
	}
	private void defineBranchWord(byte state){
		//System.out.println();
		letters = new char[encoder_size-1];
		if(state == 0b01){
			for(byte i = 0 ; i<letters.length-1;i++){
				state_String+="0";
			}
			state_String+="1";
		}else{
			state_String = Integer.toBinaryString(state);
		}
		letters = state_String.toCharArray();	
		//System.out.println("Длясостояния (индекс "+index+") :"+state_String);
		
		bytes = new byte[encoder_size];
		// сумматор 1
		for(byte i = 0 ; i<letters.length;i++){
			bytes[i+1] = (byte) Integer.parseInt((""+letters[i]),2);
		}
		byte sum1 = 0;
		byte sum2 = 0;
		for(byte i = 0; i<encoder_size;i++){
			bytes[0] =index;
			sum1^=bytes[i];
		}
		for(byte i =2;i<encoder_size;i+=2){
			sum2 = (byte) (bytes[i]^bytes[i-2]);	
		}
		branchWord = (byte) Integer.parseInt((""+sum1+sum2),2);
		
	}
}
