public class BadData extends Exception{

	/*
	 *   Name:               Evan Tatavitto
	 *   Course:             Dev1
	 *   Date (updated):     9/26/2023
	 *   Class:              LMS
	 *   For:                This class is the Exception class to let the user know which part of the data is not valid
	 *
	 */

	/**
	 * custom error to throw when data is invalidated
	 * @param type data type with error
	 */
	public BadData(String type){//simple Exception for throwing bad data, IE specifies what is being checked (book data) and whether its valid
			super(type);
	}
}
