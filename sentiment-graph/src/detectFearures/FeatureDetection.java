package detectFearures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class FeatureDetection {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner ev_reviews = new Scanner(new File("C:/Users/skohail/Desktop/semeval2016/Restaurants_test.txt"));	
/*	String []aspects_list={"laptop", "screen", "money", "machine", "graphics", "speakers", 
			"computer", "type", "performance", "keyboard", "apple", "force", "webcam", 
			"investment", "interface", "design", "edge", "work", "isolation", "pad", 
			"toshiba", "sense", "piece", "netbook", "product", "touchpad", "purchase", 
			"brand", "use", "start", "user", "size", "system", "pc", "programs", 
			"support", "time", "service", "picture", "working", "device", "life", 
			"iphoto", "price", "office", "part", "battery", "equipment", "drive", 
			"browser", "processer", "update", "motherboard", "mouse", "color", 
			"quality", "memory", "program", "software"
};*/
	String []aspects_list={"food", "service", "prices", "atmosphere", "staff",
			"pizza", "decor", "restaurant", "place", "filet", "stuff", 
			"penang", "ambience", "bread", "waiter", "operation", 
			"sushi", "meal", "menu", "vibe", "drinks", "visitor", 
			"dinner", "trattoria", "night", "ingredients", "fish", 
			"wine", "combination", "hall", "manager", "evening", 
			"appetizer", "tasting", "seating", "touch", "goat", 
			"accompaniment", "course", "specials", "dough", "kind", 
			"chicken", "resturant", "atomosphere", "list", "flair", 
			"balance", "filling", "shrimp", "italian", "dishes", "setting"};
	FileWriter results = new FileWriter("C:/Users/skohail/Desktop/semeval2016/EV_Restaurants_test.txt");
		String line="";
	String []tokens;
	//int number_of_features=0;
	int number_of_detected_features=0;
	String detected_features="";
	//String un_detected_features="";
		ev_reviews.useDelimiter("\n");
		while (ev_reviews.hasNext()){
			
			line = ev_reviews.next().replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
			tokens=line.split(" ");
			
		//	for (int i=0;i<tokens.length;i++){
				for (int x=0;x<aspects_list.length;x++){
				//if(tokens[i].startsWith("#")){
				//	number_of_features++;
					if(Arrays.asList(tokens).contains(aspects_list[x].toLowerCase().trim()))
						detected_features=detected_features+"1, ";
						else
							detected_features=detected_features+"0, ";
					
				//	}
				//	else{
				//		un_detected_features=un_detected_features+" "+tokens[i].replaceAll("#", "").trim();	
				//	}
											
		//		}
			}
			results.write(detected_features.trim()+"\n");
			//number_of_features=0;
			number_of_detected_features=0;
			detected_features="";
		//	un_detected_features="";
		}
		results.close();
		ev_reviews.close();
	}

}
