package hu.elte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Signal<T> implements Runnable {

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
		Signal<Integer> asd = new Signal<Integer>() {
			@Override
			public void run() {
				while (true) {
					Consumer<String> c = (x) -> System.out.print(this.value + "---------->");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		asd.value = constantValue;
		new Thread(asd).start();
	}

	public void map(Function<T, T> function) {
		function.apply(value);
	}

	public T getValue() {
		return value;
	}

	public Signal<T> join(Signal<T> second, BiFunction<T, T, T> function) {
		Signal<T> first = this;
		Signal<T> sum = new Signal<T>() {
			@Override
			public void run() {
				while (true) {
					this.value = function.apply(first.getValue(), second.getValue());
					Consumer<String> c = (x) -> System.out.print(this.value);
					c.accept("");
				}
			}
		};
		return sum;
	}

	public Signal<T> joinFileOutput(Signal<T> second, BiFunction<T, T, T> function) {
		Signal<T> first = this;
		TimeUnit timeUnit;
		Signal<T> sum = new Signal<T>() {
			@Override
			public void run() {
				while (true) {
					this.value = function.apply(first.getValue(), second.getValue());
					Consumer<String> c = (x) -> writeToFile((String) this.value);
					c.accept("");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		return sum;
	}

	private void writeToFile(String string) {
		try {
			if(!Files.exists(Paths.get("output.joined"))){
				Files.createFile(Paths.get("output.joined"));
			}
			Files.write(Paths.get("output.joined"), string.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public Signal<T> accumulate(BiFunction<T, T, T> function, T t) {
		T mainValue = this.value;
		Signal<T> sum = new Signal<T>() {
			@Override
			public void run() {
				while (true) {
					this.value = t;
					this.value = function.apply(this.getValue(), mainValue);
				}
			}
		};
		return sum;
	}

}
