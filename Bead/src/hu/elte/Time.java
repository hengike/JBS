package hu.elte;

import java.util.function.Consumer;

import hu.elte.Signal.TimeUnit;

public class Time {
	public long start;
	
	public Signal<String> every(TimeUnit timeUnit) {

		
		Signal<String> signal = new Signal<String>() {
			TimeUnit tUnit = timeUnit;
			@Override
			public void run() {
				while (true) {
					this.value = String.valueOf(System.currentTimeMillis()-start);
					Consumer<String> c = (x) -> System.out.print(this.value);
					c.accept("");
					try {
						Thread.sleep(timeUnit.getMiliseconds());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
		};
		
		return signal;
	}

}
