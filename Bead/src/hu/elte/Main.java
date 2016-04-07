package hu.elte;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import hu.elte.Signal.TimeUnit;

public class Main {
	
	
	public static long start;
	
	public static void main(String[] args){
		start = System.currentTimeMillis();
		Time time = new Time();
		ConsoleSignal console = new ConsoleSignal();
		Thread consoleThread = new Thread(console);
		consoleThread.start();
		Signal<String> timer = time.every(TimeUnit.Seconds);
		time.start = System.currentTimeMillis();
		timer.value = String.valueOf(System.currentTimeMillis() - time.start);
		Thread timeThread = new Thread(timer);
		timeThread.start();
		
		Signal<String> joined =timer.joinFileOutput(console, (a,b)->a+b);
		joined.outputToFile = true;
		Thread joinedThread = new Thread(joined);
		
		joinedThread.start();
		
		

	}
	
	
}
