package Viterby;

import java.util.ArrayList;

public class StateDiagram {
	private byte[][] encoder_states;
	private State[] states;
	public ArrayList<Byte>checkList = new ArrayList<Byte>();
	private ArrayList<State[]>statesList;
	private ArrayList<State[]>statesListdouble;
	private byte encoder_size=3;
	private int counter;
	private byte definedWordsCount;
	private byte branchWord;
	private byte iterator;
	private boolean check;
	
	private String[] state_String;
	private String[] stateReversed_String;
	private String doubledState_String;
	private byte[] state_byte;
	private byte[] stateReversed_byte;
	private byte doubledState_byte;
	public String definedWords;
	private TestUii uii;
	
	public StateDiagram(TestUii uii){
		counter = 0;
		statesList = new ArrayList<State[]>();
		statesListdouble = new ArrayList<State[]>();
		doubledState_String ="";
		definedWordsCount = 0;
		definedWords="";
		iterator = 2;
		this.uii = uii;
		//branchWord = InputBranchWord;
		//1 Созданиесостояний
	}
	public void go(byte[][] input_encoder_states,byte input_encoder_size,byte InputBranchWord){
		encoder_states = input_encoder_states;
		encoder_size = input_encoder_size;
		state_String = new String[encoder_states.length];
		stateReversed_String = new String[encoder_states.length];
		state_byte= new byte[encoder_states.length];
		stateReversed_byte= new byte[encoder_states.length];
		
		branchWord = InputBranchWord;
		definedWordsCount = 0;
		//Созданиевводныхсостояний
		createStates(encoder_states);
		//2 СозданиеДиаграммысостояний
		createDiagram(branchWord);
	}
	private void createStates(byte[][] encoder_states){
		//***************
		//1 взятиевсехбит, кромепервого ,длясозданиясостояний
		/*
		for(byte i = 0; i< encoder_states.length ; i++){
			state_String[i]="";
			for (byte k = 1 ; k < encoder_size;k++){
				state_String[i]+=Byte.toString(encoder_states[i][k]);
			}
			state_byte[i] = (byte)(Integer.parseInt(state_String[i],2));
			check(state_byte[i]);
			//System.out.println(Integer.toBinaryString(xx[i]));
		}*/
		check((byte)0b00);
		check((byte)0b10);
		check((byte)0b01);
		check((byte)0b11);
	}
	public void check(byte checkBytes){
		if(checkList.isEmpty()){
			checkList.add(checkBytes);
		}else if(!checkList.contains(checkBytes)){
			checkList.add(checkBytes);
		}
	}
	private void createDiagram(byte InputBranchWord){
		counter++;
		System.out.println("Cлово : "+Integer.toBinaryString(InputBranchWord)+" (Моментвремени "+counter+ " )");
		//switch -> case 1 , case 2 , case 3, default;
		if(counter ==1){
			states = new State[1];
			states[0]  = new State((byte)0b00,encoder_size);
			states[0].SumHemming=0;
			statesList.add(states);
			statesListdouble.add(states);
			states[0].createLinks(InputBranchWord);
			states[0].finalLastState = null;
			states[0].finalLastStateLink = null;
			states[0].lastState2 =states[0];
			states[0].lastState1 = states[0];
			uii.drawState(statesListdouble,counter);
			
		}else if(counter==2){
			states = new State[2];
			
			states[0]  = new State((byte)0b00,encoder_size);
			states[0].finalLastState = statesList.get(0)[0];
			states[0].lastState1 = statesList.get(0)[0];
			states[0].lastState2 = statesList.get(0)[0];
			states[0].SumHemming = statesList.get(0)[0].links.get(0).SumHemming;
			states[0].finalLastStateLink = statesList.get(0)[0].links.get(0);
			
			states[1]  = new State((byte)0b10,encoder_size);
			states[1].finalLastState = statesList.get(0)[0];
			states[1].lastState1 = statesList.get(0)[0];
			states[1].lastState2 = statesList.get(0)[0];
			states[1].SumHemming = statesList.get(0)[0].links.get(1).SumHemming;
			states[1].finalLastStateLink = statesList.get(0)[0].links.get(1);
			
			states[0].createLinks(InputBranchWord);
			states[1].createLinks(InputBranchWord);
			
			statesList.add(states);
			statesListdouble.add(states);
			uii.drawState(statesList,counter);
			
		}else if(counter == 3){
			states = new State[(int)Math.pow(2,(encoder_size-1))];
			
			states[0]  = new State((byte)0b00,encoder_size);
			states[0].SumHemming = statesList.get(1)[0].links.get(0).SumHemming;
			states[0].finalLastState = statesList.get(1)[0];
			states[0].finalLastStateLink = statesList.get(1)[0].links.get(0);
			states[0].lastState1 = statesList.get(1)[0];
			states[0].lastState2 = statesList.get(1)[0];
			
			states[1]  = new State((byte)0b10,encoder_size);
			states[1].SumHemming = statesList.get(1)[0].links.get(1).SumHemming;
			states[1].finalLastState = statesList.get(1)[0];
			states[1].finalLastStateLink = statesList.get(1)[0].links.get(1);
			states[1].lastState1 = statesList.get(1)[0];
			states[1].lastState2 = statesList.get(1)[0];
			
			states[2]  = new State((byte)0b01,encoder_size);
			states[2].SumHemming = statesList.get(1)[1].links.get(0).SumHemming;
			states[2].finalLastState = statesList.get(1)[1];
			states[2].finalLastStateLink = statesList.get(1)[1].links.get(0);
			states[2].lastState1 = statesList.get(1)[1];
			states[1].lastState2 = statesList.get(1)[1];
			
			states[3]  = new State((byte)0b11,encoder_size);
			states[3].SumHemming = statesList.get(1)[1].links.get(1).SumHemming;
			states[3].finalLastState = statesList.get(1)[1];
			states[3].finalLastStateLink = statesList.get(1)[1].links.get(1);
			states[3].lastState1 = statesList.get(1)[1];
			states[3].lastState2 = statesList.get(1)[1];
			
			states[0].createLinks(InputBranchWord);
			states[1].createLinks(InputBranchWord);
			states[2].createLinks(InputBranchWord);
			states[3].createLinks(InputBranchWord);
			
			
			statesList.add(states);
			statesListdouble.add(states);
			uii.drawState(statesListdouble,counter);
		}else{
			states = new State[(int)Math.pow(2,(encoder_size-1))];
				for(byte i= 0 ;i<states.length;i++){
					states[i] = new State(checkList.get(i),encoder_size);
					for(byte y=0;y<states.length;y++){
						for(byte z = 0; z<2;z++){
							//System.out.println(statesList.get(counter-2)[y].links.get(z).nextState);
							if(statesList.get(counter-iterator)[y].links.get(z).nextState ==states[i].state){
								states[i].count++;
								if(states[i].count> 1){
									states[i].lastState2=statesList.get(counter-iterator)[y];	
									states[i].lastStateLink2 = statesList.get(counter-iterator)[y].links.get(z);
								}else{
									states[i].lastState1=statesList.get(counter-iterator)[y];
									states[i].lastStateLink1 = statesList.get(counter-iterator)[y].links.get(z); 
								}
							}
						}
					}
				}
				
				statesList.add(states);
				statesListdouble.add(states);
				uii.drawState(statesListdouble,counter);

				System.out.println(statesList.size());
				
				for(byte i = 0; i<states.length;i++){
					deleteLinks(states[i],(byte)counter);
				}
				
				for(int i=statesList.size()-2;i>= 0;i--){
					deleteDeadLinks(statesList.get(i),0);
				}
				
				for(int i=0;i<=statesList.size()-2;i++){
					defineWords(statesList.get(i));
				}
				
				for(int i = 0 ; i<definedWordsCount;i++){
					statesList.remove(0);
				}
				System.out.println("Код ->" + definedWords);
				
				
				for(byte i= 0 ;i<checkList.size();i++){
					states[i].createLinks(InputBranchWord);
				}	
		}
	}
	private void deleteLinks(State currentState,byte indx){
		//System.out.println(currentState.lastStateLink2.SumHemming);
		if(currentState.lastStateLink1.SumHemming>currentState.lastStateLink2.SumHemming){
			currentState.lastState1.links.remove((currentState.lastStateLink1));
			currentState.SumHemming = currentState.lastStateLink2.SumHemming;
			currentState.finalLastStateLink = currentState.lastStateLink2;
			currentState.finalLastState = currentState.lastState2;
			System.out.println(Integer.toBinaryString(currentState.lastStateLink2.currentState)+" -> "+
					Integer.toBinaryString(currentState.state)+" (Метрика : "+currentState.SumHemming+" ) (Индекс ="+currentState.lastStateLink2.index+")");
			//if(currentState.lastState1.links.isEmpty()){
				//currentState.lastState1.dead = true;
			//}
		}else{
			currentState.lastState2.links.remove((currentState.lastStateLink2));
			currentState.SumHemming = currentState.lastStateLink1.SumHemming;
			currentState.finalLastStateLink = currentState.lastStateLink1;
			currentState.finalLastState = currentState.lastState1;
			System.out.println(Integer.toBinaryString(currentState.lastStateLink1.currentState)+" -> "+
					Integer.toBinaryString(currentState.state)+" (Метрика : "+currentState.SumHemming+" ) (Индекс ="+currentState.lastStateLink1.index+")");
			//if(currentState.lastState2.links.isEmpty()){
				//currentState.lastState2.dead = true;
			//}
		}
	}
	private void deleteDeadLinks(State[] currentStates,int t){
		for(byte i = 0;i<currentStates.length; i++){
			if(currentStates[i].links.isEmpty()){
				currentStates[i].dead = true;
			}
			if(currentStates[i].dead){
				currentStates[i].finalLastState.links.remove(currentStates[i].finalLastStateLink);
			}
		}
	}
	private void defineWords(State[] currentState){
		byte count = 0;
		byte indx = 0;
		for(byte i = 0;i<currentState.length;i++){
			if(!currentState[i].dead){
				count++;
				indx = i;
			}
		}
		if(count == 1 &&currentState[indx].links.size()==1){
			definedWordsCount++;
			//System.out.println("Живое - "+currentState[indx].state + " - >" +currentState[indx].links.get(0).index);
			iterator++;
			definedWords +=currentState[indx].links.get(0).index;
	}
	}
}

