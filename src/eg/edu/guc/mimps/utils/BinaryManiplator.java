package eg.edu.guc.mimps.utils;

import eg.edu.guc.mimps.assembler.Instruction;

public class BinaryManiplator {
	
	public static boolean getBitByIndex(int value, int index) {
		return (value & (1<<index)) > 0? true: false;
	}
	
	public static int setPartialValue(int source, int first, int last, int value) {
		value &= ((1 << (last - first + 1)) - 1);
		return (value << first) | (source & ~((1 << (last - first + 1) - 1) << first));
	}
	
	public static int setUnsignedPartialValue(int source, int first, int last, int value) {
		return (source | (value << first));
	}
	
	public static int getPartialValue(int source, int first, int last) {
		int mask = ((1 << (last - first + 1)) - 1) << first;
		source &= mask;
		source >>= first;
		mask >>= first;
		if (getBitByIndex(source, last - first)) {
			source |= ~mask;
		}

		return source;	
	}	
	
	public static int getUnsignedPartialValue(int source, int first, int last) {
		int mask = ((1 << (last - first + 1)) - 1) << first;
		source &= mask;
		source >>>= first;
		return source;	
	}	
}
