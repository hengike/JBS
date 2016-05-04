package hu.elte;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import hu.elte.Signal.TimeUnit;

public class Main {

	public static long start;

	public static void main(String[] args) throws IOException {
		
		IntegerWrapper ad = new IntegerWrapper();

		
		Signal sign = new Signal<Integer>() {};
		sign.setValue(1);		
		Signal signAccumulated =sign.accumulate((c,x) -> c + "", 1);
		signAccumulated.change();
		System.out.println(signAccumulated.getValue().getClass());
		
		Signal signJoined = sign.join(signAccumulated, (line, count) -> count + ": " + line);
		signJoined.change();			
		System.out.println(signJoined.getValue().getClass());
		
		Signal mappedSignal = sign.map(i -> i+ "");
		mappedSignal.change();
		System.out.println(mappedSignal.getValue().getClass());

	}

}
