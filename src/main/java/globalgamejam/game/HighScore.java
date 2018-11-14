package globalgamejam.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class HighScore implements Serializable{

	private static final long serialVersionUID = 182903812903812908L;
	
	private int[] topScore;
	private int length;
	
	public HighScore(){
		this.topScore = new int[10];
		this.length = 10;
	}
	
	public void put(int data){
		ArrayList<Integer> scores = new ArrayList<Integer>();
		boolean alreadyExist = false;
		for(int i : topScore){
			scores.add(i);
			if(i == data){
				alreadyExist = true;
				break;
			}
		}
		
		if(!alreadyExist){
			scores.add(data);
			scores.sort(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o2.compareTo(o1);
				}
			});
			
			for(int i = 0;i < this.topScore.length;i++){
				this.topScore[i] = scores.get(i);
			}
		}

	}
	
	public int[] getTopScores(){
		return this.topScore;
	}
	
	public int getTopScore(){
		return this.topScore[0];
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0;i < this.topScore.length;i++){
			sb.append(this.topScore[i]);
			if(i != this.topScore.length - 1)sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
