package eg.edu.guc.mimps.utils;

public class BinaryManiplator {

	public static boolean getBitByIndex(int value, int index) {
		return (value & (1<<index)) > 0? true: false;
	}
}
