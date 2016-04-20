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
		/*
		 * Time<Integer> asd = new Time<Integer>() {
		 * 
		 * @Override public void run() { while (true) { Consumer<String> c = (x)
		 * -> System.out.print(this.value + "---------->"); try {
		 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } } } }; asd.value =
		 * constantValue; new Thread(asd).start();
		 */
	}


	public T getValue() {
		return value;
	}

	public Signal<T> join(Signal second, BiFunction function) {
		Signal<T> first = this;
		Signal<T> sum = new Signal<T>() {
			public void change() {
				this.setValue((T)function.apply(first.getValue(), second.getValue()));
			}
		};
		//sum.change();
		first.addDependency(sum);
		second.addDependency(sum);
		return sum;
	}

	/*public Signal<T> joinFileOutput(Signal<T> second, BiFunction<T, T, T> function) {
		Signal<T> first = this;
		Signal<T> sum = new Signal<T>() {
			public void change() {
				this.setValue(function.apply(first.getValue(), second.getValue()));
				Consumer<String> c = (x) -> writeToFile((String) this.value);
				c.accept("");
			}
		};
		first.addDependency(sum);
		second.addDependency(sum);
		return sum;
	}*/

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

	public Signal<T> accumulate(BiFunction<T, T, T> function, T t) {
		this.setValue(function.apply(this.getValue(), t));
		this.change();
		return this;

	}

	public void setValue(T value) {
		//this.change();
		this.value = value;
	}

	public void addDependency(Signal signal) {
		dependentSignals.add(signal);
	}

	public void change() {
		for (Signal act : dependentSignals) {
			act.change();
		}
	}
	public void map(Consumer<T> c){
		this.change();
		c.accept(this.getValue());
	}


}
