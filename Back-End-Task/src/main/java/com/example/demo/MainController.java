package com.example.demo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MainController {
	
	

	private final MainService mainService;
   @Autowired
	public MainController(MainService mainService) {
		super();
		this.mainService = mainService;
	}
	
	
   // for one player 
   @RequestMapping(path = "/oneplayer")
   public  Player get_one_player_information(@RequestBody Map<String, int[]> map)
   {
	   List<Player> my_list = mainService.get_player_information(map);
	   return my_list.get(0);
   }
   
   
// for all player orderd by score
   @GetMapping(path = "/topplayer")
   public  Player get_top_player_accordingto_score(@RequestBody Map<String, int[]> map)
   {
	   List<Player> my_list = new ArrayList<>();
	   
	   my_list = mainService.get_player_information(map);
	   List<Player> nmy_list= mainService.order_players_accordint_to_score(my_list);
	   return nmy_list.get(0);
   }
   
   
   
   
   // return player performance search by name ;
   @GetMapping(path = "/getplayers")
   public Player get_player_performance(@RequestBody Player name)
   {
	   //create map for many players with difference performance
	    Map<String,int[]> map = new HashMap<>();
		   int [] arr1 = {5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};  //bad less than 150
		   int [] arr2 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};  //bad less than 150
		   int [] arr3 = {10,10,10,10,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0};  //good greater than 150 and less than 250
		   int [] arr4 = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};// excellent more than 250

		    map.put("player", arr1);
		    map.put("abdelrhman", arr2);	    
		    map.put("adel", arr3);
		    map.put("ameen", arr4);
		    
		    // player  : bad      false
		    // abdelrhman : bad   false
		    // adel : good        true
		    // ameen : good       true
		    
		    List<Player> my_list  = mainService.get_player_information(map);
			    
		   Player player_info = new Player();  
		 for(int i=0;i<my_list.size();i++)
		 {
			 if(my_list.get(i).getName().equals(name.getName()))
				 return  my_list.get(i);
		 }
	 
		
	   
	   return player_info;
   }
 
   
	
   // Bonus ordering players according to their score 
   
   
   @RequestMapping(path = "/order")
   public  List<Player>  order_players_accordint_to_score(List<Player> UnOrderList)
   {

	   return mainService.order_players_accordint_to_score(UnOrderList);
   }
   
   
   
   
   
}
