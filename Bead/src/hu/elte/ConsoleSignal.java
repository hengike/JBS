package hu.elte;

import java.io.Console;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleSignal<T> extends Signal<T> {


	public static ConsoleSignal<String> lastInput() {
		ConsoleSignal<String> result = new ConsoleSignal<String>();
		result.setValue(new Scanner(System.in).nextLine());
		return result;

	}

}
