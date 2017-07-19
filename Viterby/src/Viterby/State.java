package Viterby;

import java.awt.Point;
import java.util.ArrayList;

public class State  {
	
	public byte state;
	public ArrayList<Link>links;
	public byte SumHemming;
	private final byte index0 = 0;
	private final byte index1 = 1;
	private byte encoder_size;
	public byte count;
	public boolean dead;
	public State lastState1;
	public State lastState2;
	public Link lastStateLink1;
	public Link lastStateLink2;
	public Link finalLastStateLink;
	public State finalLastState;
	public Point point;
	
	
	public State(byte state,byte InputEncoder_Size){
		this.state = state;
		count = 0;
		dead = false;
		encoder_size = InputEncoder_Size;
		//System.out.println(this.state);
		links = new ArrayList<>();
		point = new Point();
	}
	public void createLinks(byte InputBranchWord){
		links.add(new Link(state,InputBranchWord,index0,encoder_size,SumHemming));
		links.add(new Link(state,InputBranchWord,index1,encoder_size,SumHemming));
	}
}
