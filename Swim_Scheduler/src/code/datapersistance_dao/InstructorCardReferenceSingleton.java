package code.datapersistance_dao;

import code.dataObjects.Instructor;

public class InstructorCardReferenceSingleton {
	
	private static final InstructorCardReferenceSingleton instance = new InstructorCardReferenceSingleton();
	
	private Instructor instructorReference = null;
	
	private InstructorCardReferenceSingleton() {
		
	}
	
	public static InstructorCardReferenceSingleton getInstance() {
		return instance;
	}
	
	public void setInstructorReference(Instructor clientReference) {
		this.instructorReference = clientReference;
	}
	
	public Instructor getInstructor() {
		return this.instructorReference;
	}
}
