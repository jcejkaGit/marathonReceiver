package sectionpark;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sectionpark.timingstation.MOMReceiver;
import sectionpark.timingstation.MOMSender;

@SpringBootApplication
public class MarathonApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MarathonApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		String flag = new String("receiver");
		for(String arg:args) {
			flag = arg;
		}

		if ( flag.toLowerCase().equals("sender") )
			new MOMSender();
		else
			new MOMReceiver();


	}
}
