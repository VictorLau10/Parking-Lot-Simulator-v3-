public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
	
		int lotSize = 1;
		boolean satisfied = false;
		
		while (!satisfied) {
			System.out.println();
			System.out.println("              ==== Setting Lot Capacity to " + lotSize + " =====");
			int sum = 0;

			for (int i = 0; i < NUM_RUNS; i++) {
				ParkingLot p = new ParkingLot(lotSize);
				Simulator sim = new Simulator(p, hourlyRate, 24 * 3600);

				long start = System.currentTimeMillis();
				sim.simulate();
				long end = System.currentTimeMillis();
				sum += sim.getIncomingQueueSize();				

				System.out.println();
				System.out.println("Simulation Run " + (i + 1) + " (" + ((end - start)) + "ms): Queue length at the end of simulation run: " + sim.getIncomingQueueSize());	
			}

			double average = (double)sum / NUM_RUNS;

			if (average <= 5) {
				satisfied = true;
			} else {
				lotSize++;
			}
			System.out.println();
		}

		return lotSize;
	
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}