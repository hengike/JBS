package hu.elte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Signal<T> {
	
	String type = "default";

	List<Signal> dependentSignals = new ArrayList<Signal>();

	public enum TimeUnit {
		Seconds(1000), Minute(60000), Hour(3600000);
		Integer miliseconds;

		TimeUnit(Integer miliseconds) {
			this.miliseconds = miliseconds;
		}

		public Integer getMiliseconds() {
			return this.miliseconds;
		}
	}

	boolean outputToFile = false;

	T value;
	public static final Integer constantValue = 1;

	public static void generateConstantSignal() {
		Signal<Integer> signal = new Signal<Integer>() {
		};
	}

	public T getValue() {
		return value;
	}

	public <U> Signal join(Signal<U> second, BiFunction<T,U,?> function) {
		Signal first = this;
		Signal sum = new Signal() {
			public void change() {
				this.setValue(function.apply( (T)first.getValue(), (U)second.getValue()));
			}
		};
		//sum.change();
		first.addDependency(sum);
		second.addDependency(sum);
		sum.type = "joined"; 
		return sum;
	}

	private void writeToFile(String string) {
		try {
			if (!Files.exists(Paths.get("output.joined"))) {
				Files.createFile(Paths.get("output.joined"));
			}
			Files.write(Paths.get("output.joined"), string.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public <U> Signal accumulate(BiFunction<T,U,?> function, U t) {
		Signal first = this;
		Signal sum = new Signal() {
			public void change() {
				this.setValue(function.apply((T)first.getValue(), t));
			}
		};
		first.addDependency(sum);
		sum.type = "accumulated"; 
		return sum;

	}

	public void setValue(T value) {
		this.value = value;
	}

	public void addDependency(Signal signal) {
		dependentSignals.add(signal);
	}

	public void change() {
		System.out.println("Not overriden change in:" + this.type);
	}
	public void giveSignal(){
		this.change();
		for (Signal act : dependentSignals) {
			act.giveSignal();
		}
	}

	public Signal map(Function<T,?> function) {
		Signal<T> first = this;
		Signal sum = new Signal() {
			public void change() {
				this.setValue(function.apply(first.value));
			}
		};
		first.addDependency(sum);
		sum.type = "maped"; 
		return sum;
	}




}
