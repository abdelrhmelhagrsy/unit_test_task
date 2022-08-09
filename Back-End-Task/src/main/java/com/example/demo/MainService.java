package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class MainService {
	
	private Player player ;
    private List<Player> my_list ; 
    
	public List<Player> get_player_information(Map<String, int[]> map) {
		   my_list = new ArrayList<>();
		
		 for (String name : map.keySet()) 
	        {
	            // search  for value
	            int[] arr = map.get(name);
	            player = new Player();
	            player.setName(name);
	            player.setArr(arr);
	            player.setTotla_score(0);
	            player.setPerformance(false);
	            my_list.add(player);
	        }
		 for (Player player : my_list) 
		 {
			 int total_score = score(player.getArr()); // calculate score for each player 
			 player.setTotla_score(total_score);
			 if(total_score < 150)
			 player.setPerformance(false);    // less than 150 points bad play
		
			 else
				 player.setPerformance(true);   // greater than 150 points good play
			
		 }
		
		return my_list;
	}

	// calculate score for player
	private int score(int[] arr) {
		int [] rolls = new int[21];
		rolls = arr ; 
		int total_score = 0;
		int cursor = 0;
		
		for(int i=0;i<10;i++)
		{
			if(IsStrice(rolls[cursor]))
			{
				total_score+=10+rolls[cursor+1]+rolls[cursor+2];
				cursor++;
			}
			else if(IsSpare(rolls[cursor],rolls[cursor+1]))
			{
				total_score+= 10+ rolls[cursor+2];
				cursor+=2;
			}
		
			else
			{
				total_score+= rolls[cursor]+rolls[cursor+1];
				cursor+=2;
			}
			
			
		}
		
		
		return total_score;
	}
	
	
	private boolean IsStrice(int number)
	{
		if(number==10)
			return true;
		else
			return false;
	}
	

	private boolean IsSpare(int number1,int number2)
	{
		int totale= number1+number2;
		if(totale==10)
			return true;
		else
			return false;
	}
	
	//
	
	
	   public  List<Player>  order_players_accordint_to_score(List<Player> UnOrderList)
	   {
		/*   Player player ;
		   for(int i=UnOrderList.size()-1;i>=0;i--)
		   {
			   for(int y=0;y<i-1;y++)
			   {
				   if(UnOrderList.get(i).getTotla_score()>UnOrderList.get(y).getTotla_score())
				   {
					   player = new  Player();
					   player = UnOrderList.get(i);
					   UnOrderList.set(i,UnOrderList.get(y));
					   UnOrderList.set(y,player);
					   
					   
				   }
			   }
		   }*/
		   
		   UnOrderList.sort(Comparator.comparingInt(Player::getTotla_score));
	        Collections.reverse(UnOrderList);


		   
		   return UnOrderList;
	   }
	   
	
}
