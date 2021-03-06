package hu.elte;

import java.util.function.BiFunction;
import java.util.function.Function;

public class IntegerWrapper {
	Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void join (IntegerWrapper second, BiFunction<Integer, Integer, Integer> function){
		System.out.println(function.apply(this.getValue(), second.getValue()));
	}
	
	public void map( Function<Integer, Integer> function){
		value = function.apply(value);
	}
	
	/*public IntegerWrapper accumulate(BiFunction<Integer, Integer, Integer> function, T t){
		
	}*/
}
