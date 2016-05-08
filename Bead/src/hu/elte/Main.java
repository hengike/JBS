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
		Signal<Integer> sign = new Signal<Integer>() {};
		sign.setValue(1);
		
		Signal signAccumulated =sign.accumulate((c,x) -> c + "", 1);
		signAccumulated.change();
		System.out.println(signAccumulated.getValue().getClass());
		
		Signal signAccumulatedDouble = sign.accumulate((c,x) -> c + 0.2, 1);
		signAccumulatedDouble.change();
		System.out.println(signAccumulatedDouble.getValue().getClass());
		
		Signal signJoined = sign.join(signAccumulated, (line, count) -> count + ": " + line);
		signJoined.change();			
		System.out.println(signJoined.getValue().getClass());
		
		Signal signJoinedDouble = sign.join(signAccumulated, (line, count) ->  line +0.01);
		signJoinedDouble.change();
		System.out.println(signJoinedDouble.getValue().getClass());
		
		Signal signMapped = sign.map(i -> i+ "");
		signMapped.change();
		System.out.println(signMapped.getValue().getClass());
		
		Signal signMappedDouble = sign.map(i -> 0.01+i);
		signMappedDouble.change();
		System.out.println(signMappedDouble.getValue().getClass());



	}

}
