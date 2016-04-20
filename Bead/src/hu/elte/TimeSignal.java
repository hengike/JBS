package hu.elte;

import java.util.function.Consumer;

import hu.elte.Signal.TimeUnit;

public class TimeSignal<T> extends Signal<T> implements Runnable {
	public long start;
	TimeUnit timeUnit;
	public TimeSignal(TimeUnit timeUnit){
		this.timeUnit = timeUnit;
	}
	
	public static TimeSignal<Integer> every(TimeUnit timeUnit) {
		TimeSignal<Integer> signal = new TimeSignal<Integer>(timeUnit);
		signal.setValue(0);
		new Thread(signal).start();
		return signal;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(timeUnit.getMiliseconds());
				this.change();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};

}
