package hu.elte;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import hu.elte.Signal.TimeUnit;

public class Main {

	public static long start;

	public static void main(String[] args) {
		Signal<Integer> countTime = TimeSignal.every(TimeUnit.Seconds).accumulate((c, x) -> c + 1, 0);
		Signal<String> lineSignal = ConsoleSignal.lastInput().join(countTime, (line, count) -> count + ": " + line);
		lineSignal.map(line -> System.out.println(line));


	}

}
