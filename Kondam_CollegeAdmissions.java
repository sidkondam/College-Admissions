/*
 * Siddharth Kondam 
 * This project simulates a college admissions process. It uses an inner class that contains the Queue interface 
 * for the outer class which simulates many possibilities of an admissions process occurring. 
 *  
 */

import java.util.*;

public class Kondam_CollegeAdmissions{


	//for each number of applications it will run every combination of acceptance scenarios allowing each applicant to start first 
	public void showAccepted(int maxApps) {

		Application diffCombos; 

		//runs through the max amount of applications
		for(int totalApp = 2; totalApp <= maxApps; totalApp++) {

			System.out.print("\nTotal Applications:" + totalApp + "\t Accepted starting spot:");		

			for(int startApp = 1; startApp<= totalApp; startApp++) {

				//contains every combination of the application
				diffCombos = new Application(totalApp,startApp);

				if(diffCombos.accept()==startApp) 
					System.out.print(" " + startApp);		
			}
		}
	}

	public class Application {

		private int totApplicants;
		private int startingApp; 
		private int totalEl; 
		private Queue<Integer> queues = new LinkedList<Integer>();

		public Application (int t, int s) {
			totApplicants = t; 
			startingApp = s; 


			for(int i = startingApp; i<=totApplicants;i++) {
				queues.add(i);
				totalEl++; 
			}

			for(int i = 1; i < startingApp;i++) {
				queues.add(i);		
				totalEl++;
			}
		}


		//		stimulates process of officer walking around the room & 
		//		returns application # of the sole accepted student
		public int accept() {
			int counter = 0; 	
			int appSize = totalEl;

			//runs while only one application is left
			while(appSize!=1) {

				if(counter%2==0) {
					int val = queues.remove();
					queues.add(val);
				}

				else {
					queues.remove();
					appSize--; 
				}	
				counter++; 
			}
			return queues.peek();
		}
	}
}



