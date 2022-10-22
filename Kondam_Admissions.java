/*
 * Siddharth Kondam
 * This project goes through a college admissions process using inheritance and queues, attempting to match a a college and student so they are both "happy"
 * 
 */

import java.util.*;
import java.io.*;

public class Kondam_Admissions {

	private Queue<Student> unmatched = new LinkedList<Student>();
	private ArrayList<College> colleges = new ArrayList<College>();
	private ArrayList<Student> students = new ArrayList<Student>();

	public Kondam_Admissions(String sNames, String cNames) {
		Scanner student = null; 
		Scanner college = null;


		//takes in the student and the array
		try {
			student = new Scanner(new File(sNames));
		}catch(FileNotFoundException e) {
			System.out.println(sNames + " not found");
			System.exit(-1);
		}

		try {
			college = new Scanner(new File(cNames));
		}catch(FileNotFoundException e) {
			System.out.println(cNames + " not found");
			System.exit(-1);
		}

		//adds to the array lists
		while(student.hasNext()) {
			students.add(new Student(student.nextLine()));
			colleges.add(new College(college.nextLine()));
		}

		//loads in the unmatched students queue and generates random rankings
		for(int i = 0; i < students.size();i++) {
			students.get(i).generateRankings(colleges);
			colleges.get(i).generateRankings(students);
			unmatched.add(students.get(i));
		}

		Algorithm();
	}

	public void Algorithm() {

		//matches students with colleges till there are no more unmatched students
		while(!unmatched.isEmpty()) {
			Student match = unmatched.remove();
			match.apply();		
		}

		//prints out students, colleges, and the matches that will occur. 
		System.out.println("Student\n--------");
		for(int i = 0; i < students.size(); i++)
			System.out.println(colleges.get(i) + ": " + students.get(i).getRankings());

		System.out.println();

		System.out.println("Colleges\n--------");
		for(int i = 0; i < colleges.size(); i++)
			System.out.println(students.get(i) + ": " + colleges.get(i).getRankings());

		System.out.println();

		System.out.println("Students     College\n-------------------");
		for(int i = 0; i < colleges.size(); i++) 
			System.out.println(students.get(i).getMatch() + ":     " + colleges.get(i).getMatch());
	}

	public class Prospect{
		private ArrayList<Prospect> ranking;
		private Prospect match = null;
		private String name;

		public Prospect(String name){
			ranking = new ArrayList<Prospect>();
			this.name = name;
		}

		public void generateRankings(ArrayList<? extends Prospect> prospectNames) {

			ArrayList<Prospect> copy = new ArrayList<Prospect>();

			for(int i = 0; i < prospectNames.size(); i++)
				copy.add(prospectNames.get(i));

			while(copy.size()!=0){
				ranking.add(copy.remove((int)(Math.random()*copy.size())));
			}
		}

		public void setMatch(Prospect val){
			match = val;
		}

		public Prospect getMatch(){
			return match;
		}

		public ArrayList<Prospect> getRankings(){
			return ranking;
		}

		public String toString() {
			return name;
		}

		//assume everyone has a diff name
		public boolean equals(Object o) {
			if(!(o instanceof Prospect))
				return false;
			Prospect other = (Prospect)o;
			return name.equals(other.name);
		}
	}


	public class Student extends Prospect{
		private int schoolCounter = 0; 

		public Student(String studentName) {
			super(studentName);
		}

		//applies student to college if it is the student's preferred unapplied school
		public boolean apply() {

			//checks if there are any rankings left
			if(schoolCounter>=getRankings().size()) {
				schoolCounter++; 
				return false;
			}	
			((College)getRankings().get(schoolCounter)).acceptStudent(this);
			return true;
		}
	}

	public class College extends Prospect{ 

		public College(String collegeName) {
			super(collegeName);
		}

		//finds college's preference of other and current student
		public boolean isPreferred(Student other) {
			int currentMatch = 0; 
			int studentMatch = 0; 

			for(int i = 0; i < getRankings().size();i++) {

				if(getRankings().get(i)==getMatch()) 
					currentMatch = i;

				else if(getRankings().get(i)==other.getMatch())
					studentMatch = i; 
			}

			if(currentMatch>studentMatch||getMatch()==null)
				return true;

			return false;
		}


		//accept the student parameter as a match unless other circumstances
		public void acceptStudent(Student stu) {

			if(!isPreferred(stu)) 
				unmatched.add(stu);

			else {	
				stu.setMatch(this);
				this.setMatch(stu);
			}
		}

	}		

	public static void main(String[]args) {
		Kondam_Admissions match = new Kondam_Admissions("schools.txt","names.txt");
	}
}






