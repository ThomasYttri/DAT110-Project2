package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		// create a client object and use it to
		Client client = new Client("TemperatureDevice", Common.BROKERHOST, Common.BROKERPORT);

		// - connect to the broker
		client.connect();

		// - publish the temperature(s)
		for(int i=0; i < COUNT; i++){
			String temp = String.valueOf(sn.read());
			System.out.println("READING: " + temp);
			client.publish(Common.TEMPTOPIC, temp);
		}

		// - disconnect from the broker
		client.disconnect();

		System.out.println("Temperature device stopping ... ");

	}
}
