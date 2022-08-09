package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.security.KeyStore.Entry;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.boolex.Matcher;
import net.bytebuddy.build.EntryPoint;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
	
	@Mock MainController moc;

	@InjectMocks
	private MainController underTest;
	
	@Autowired
     private MainController mainController ;
	 @Autowired
     private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	
	private Map<String,int[]> map;
	

	public MainControllerTest() {
		super();
		  this.mockMvc = mockMvc;
	}


	
	
	@Test
	void getTopPlayerOrderdByScore() throws JsonProcessingException, Exception {
		// Arrange
	
		     map = new HashMap<>();
		     int [] arr1 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		     int [] arr2 = {4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		     int [] arr3 = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};

		    map.put("abdelrhman", arr1);
		    map.put("adel", arr2);
		    map.put("ameen", arr3);
        		
		   
		   int x = 300 ;

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/topplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(x)));		//change performance according to name
				
	}
	
	
	
	
	
	
	
	
	
	
	// check Get annotation 
	// get player performance by name  
	@Test
	void CheckPlayerPerformanceByName() throws JsonProcessingException, Exception {
		// Arrange
	
		    Player name = new Player();  // each name and his performance 
		    name.setName("adel");  // set name 
		    // player  : false
		    // abdelrhman : false
		    // adel : true
		    // ameen : true

		   
		  

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/getplayers").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(name)));

		// Assert
		
		result.andExpect(MockMvcResultMatchers.jsonPath("$.performance", CoreMatchers.is(true)));		//change performance according to name
				
	}
	
	
	
	
	
	//Logic cases

    // case 1 : check if player can get 0 score 
	
	@Test
	void CanPlayerGetZeroScore() throws JsonProcessingException, Exception {
		// Arrange
		map = new HashMap<>();
		   int [] arr = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		    map.put("abdelrhman", arr);
		 

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(0)));
		
				
	}
	
	 // case 2 : check if player can get Full score
	
		@Test
		void canPlayerGetStrikeInLastFrame() throws JsonProcessingException, Exception {
			// Arrange
			map = new HashMap<>();
			   int [] arr = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};
			    map.put("abdelrhman", arr);
			 

			// Act
			final ResultActions result = this.mockMvc
					.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(this.objectMapper.writeValueAsString(map)));

			// Assert
			result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(300)));
			
					
		}
	

    // case 3 : check if player can get spare followed by three
	
	@Test
	void canPlayerGetSpareFollowedByThree() throws JsonProcessingException, Exception {
		// Arrange
		map = new HashMap<>();
		   int [] arr = {5,5,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		    map.put("abdelrhman", arr);
		 

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(16)));
		
				
	}
	
    // case 4 : check if player can get Strike Followed by 3 , 3  
	
	@Test
	void canPlayerGetStrike() throws JsonProcessingException, Exception {
		// Arrange
		map = new HashMap<>();
		   int [] arr = {10,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		    map.put("abdelrhman", arr);
		 

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(22)));
		
				
	}
	
	
    // case 5 : Can all score be one  
	
	@Test
	void CanAllScoreBeOne() throws JsonProcessingException, Exception {
		// Arrange
		map = new HashMap<>();
		   int [] arr = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		    map.put("abdelrhman", arr);
		 

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(20)));
		
				
	}
	
    // case 6 : check ordinary game score   
	
	@Test
	void CanPlayerGetSpareInLastOfScore() throws JsonProcessingException, Exception {
		// Arrange
		map = new HashMap<>();
		 int [] arr = {1,2,3,4,5,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		    map.put("abdelrhman", arr);
		 

		// Act
		final ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/oneplayer").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(map)));

		// Assert
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totla_score", CoreMatchers.is(33)));
		
				
	}
   
	
	// bouns order test  
	@Test
	void check_oder_by_score() throws JsonProcessingException, Exception {
		// Arrange
	
		// order list by score
		 List<Player> orderd_list = new ArrayList<>();
		 int [] rolls = new int[21];
		 Player player1 = new Player("apdo",rolls,250,true); 
		 Player player2 = new Player("apdo",rolls,200,true);
		 Player player3 = new Player("apdo",rolls,100,true); 
		 orderd_list.add(player1);
		 orderd_list.add(player2);
		 orderd_list.add(player3);
		  
		 // unorder list 
		 List<Player> unorder_list = new ArrayList<>();
		 int [] nrolls = new int[21];
		 Player nplayer1 = new Player("apdo",nrolls,100,true); 
		 Player nplayer2 = new Player("apdo",nrolls,250,true);
		 Player nplayer3 = new Player("apdo",nrolls,200,true); 
		 unorder_list.add(nplayer2);
		 unorder_list.add(nplayer3);
		 unorder_list.add(nplayer1);
		 

		// check order function by sent unorder list and receive order lias according to total_score
		
		 when(moc.order_players_accordint_to_score(unorder_list)).thenReturn(orderd_list);	// send unorder list and receive order list	
	}
	
	

	
	
}
