package main.java.gg.bayes.challenge.rest.controller;


import java.util.ArrayList;
import java.util.Scanner;
import main.java.gg.bayes.challenge.service.impl.HeroSpells;
import main.java.gg.bayes.challenge.service.impl.HeroDamage;
import main.java.gg.bayes.challenge.rest.model.MatchService;

public class MatchController {
	
	public static void main(String[] args){
		startChhallenge("/Users/sarveshtiwari/eclipse-workspace/bayes-dota/src/main/java/gg/bayes/challenge/config/player.txt", "/Users/sarveshtiwari/eclipse-workspace/bayes-dota/src/main/java/gg/bayes/challenge/config/opponent.txt");
	}

	
	// Controls the game
	private static void startChhallenge(String fileNameOne, String fileNameTwo){
		
		
		ArrayList<HeroSpells> listSpells = MatchService.readSpells("/Users/sarveshtiwari/eclipse-workspace/bayes-dota/src/main/java/gg/bayes/challenge/config/spells.txt");

		// Creates game players and displays the information
		System.out.println(":: Players :: ");
		HeroDamage player = MatchService.readInformation(fileNameOne);
		HeroDamage enemy =  MatchService.readInformation(fileNameTwo);
		System.out.println(MatchService.readInformation(fileNameOne));
		System.out.println(MatchService.readInformation(fileNameTwo));
		
		// Displays the spell information
		player.setSpells(listSpells);
		System.out.println(":: Spells :: ");
		for (int i = 0; i < listSpells.size(); i++) {
			System.out.print(listSpells.get(i));
		}
		
		
		// Allows user to control the player battle by input command
		while(player.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0){
			Scanner in = new Scanner(System.in);
			System.out.println("Enter a command :");
			System.out.println("(Attack / Quit / Name of HeroSpells) ");
			String userInput = in.nextLine();			
			
			// When the input command is "attack"
			if (userInput.equalsIgnoreCase("attack")) {
				startAttack(player, enemy);
				startAttack(enemy, player);
				
			// When the input command is "quit"
			} else if (userInput.equalsIgnoreCase("quit")) {
				System.out.println("\nGoodbye! \n(The user has terminated the game)");
				return;
				
			// When the input command is a type of spell
			} else if (!userInput.equalsIgnoreCase("attack") || userInput.equalsIgnoreCase("quit")) {
				double tempDamage = player.castSpell(userInput);
				System.out.println(enemy.getName() + " takes " + tempDamage + " damage!");
				enemy.takeDamage(tempDamage);
				System.out.println(enemy.getName() + "'s current health : " + enemy.getCurrentHealth());
				System.out.println();
			}
		// The player loses the battle
		} if (player.getCurrentHealth() <= 0){
			System.out.println("Oh no! You lost!");
			enemy.increaseWins();
			System.out.println(enemy.getName() + "'s number of wins : " + enemy.getNumWin());
			MatchService.writeInformation(enemy, "opponent.txt");
			
		// The player wins the battle
		} else if (enemy.getCurrentHealth() <= 0){
			System.out.println("Congratulation! You won!");
			player.increaseWins();
			System.out.println(player.getName() + "'s number of wins : " + player.getNumWin());
			MatchService.writeInformation(player, "player.txt");
		}
	}
	
	
	// Allows the player to attack the counterpart and the proper attack value is subtracted from the current health
	private static void startAttack(HeroDamage one, HeroDamage two) {
		double attackValue = one.calcAttack();
		
		System.out.println(one.getName() + " attacks for " + attackValue + " damage!");
		two.takeDamage(attackValue);
		
		if(two.getCurrentHealth() > 0){
			System.out.println(two.getName() + " takes " + attackValue + " damage! \n");
			System.out.println("\t Name : " + two.getName());
			System.out.println("\t [Health: " + two.getCurrentHealth() + "]\n");
		}else {
			System.out.println(two.getName() + " takes " + attackValue + " damage! \n");
			System.out.println("\t Name : " + two.getName());
			System.out.println("\t [Health: " + two.getCurrentHealth() + "]\n");
			System.out.println(two.getName() + " is knocked out.");
		}
	}
	
	
}
