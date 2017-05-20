package it.polimi.ingsw.gc_12.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.gc_12.card.Card;
import it.polimi.ingsw.gc_12.effect.Effect;
import it.polimi.ingsw.gc_12.resource.FaithPoint;
import it.polimi.ingsw.gc_12.resource.MilitaryPoint;
import it.polimi.ingsw.gc_12.resource.Money;
import it.polimi.ingsw.gc_12.resource.Resource;
import it.polimi.ingsw.gc_12.resource.Servant;
import it.polimi.ingsw.gc_12.resource.Stone;
import it.polimi.ingsw.gc_12.resource.VictoryPoint;
import it.polimi.ingsw.gc_12.resource.Wood;

public class AskCards {
	public List<Card> buildCards(){
        Scanner scan = new Scanner(System.in);
        int id=1;
		List<Resource> requirements=new ArrayList<>();
		List<Card> cards=new ArrayList<>();
		List<Effect> effects=new ArrayList<>();
        while(true){
        	System.out.println("Insert the name of the Card");
    		String name=scan.nextLine();
    		requirements=AskResource();
        	System.out.println("If you want to exit from the create card tool write exit, if not do anything else");
    		String choice=scan.nextLine();
    		if (choice=="exit") {
				break;
			}
    		id=id+1;
    		Card card=new Card(id, name, requirements, effects);
    		cards.add(card);
        }
		scan.close();
        return cards;
	}
	private List<Resource> AskResource(){
		Resource resource=null;
		int value=0;
        Scanner scan1 = new Scanner(System.in);
		List<Resource> requirements=new ArrayList<>();
		while(true){
			System.out.println("Insert requirements");
			System.out.println(" 1 FaithPoint \n 2 MilitaryPoint \n 3 VictoryPoints \n 4 Servants \n 5 Money \n 6 Stone \n 7 Wood \n 8 Exit create resource tool");
			int resourcechoice=scan1.nextInt();
			System.out.println(resourcechoice);
			scan1.nextLine();
			switch(resourcechoice){
				case 1:
					System.out.println("Insert value of Faith Points");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new FaithPoint(value);
					break;

				case 2:
					System.out.println("Insert value of Military Points");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new MilitaryPoint(value);
					break;

				case 3:
					System.out.println("Insert value of Victory Points");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new VictoryPoint(value);
					break;

				case 4:
					System.out.println("Insert value of Servants");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new Servant(value);
					break;

				case 5:
					System.out.println("Insert value of Money");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new Money(value);
					break;
				case 6:
					System.out.println("Insert value of Stone");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new Stone(value);
					break;
				case 7:
					System.out.println("Insert value of Wood");
					value=scan1.nextInt();
					scan1.nextLine();
					resource=new Wood(value);
					break;
				case 8:				
					break;
			}
			if(resourcechoice==8){
				break;
			}
			requirements.add(resource);
			
		}
		scan1.close();
		return requirements;
	}
}
