package eg.edu.guc.mips.registers;

import java.lang.reflect.Field;

public class InstructionFetchDecodeRegisters {
	private int instruction;
	private int incrementedPc;
	
	
	public int getInstruction() {
		return instruction;
	}
	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public int getIncrementedPc() {
		return incrementedPc;
	}
	public void setIncrementedPc(int incrementedPc) {
		this.incrementedPc = incrementedPc;
	}
	
	public InstructionFetchDecodeRegisters clone(){
		InstructionFetchDecodeRegisters clone = new InstructionFetchDecodeRegisters();
		clone.setIncrementedPc(incrementedPc);
		clone.setInstruction(instruction);
		return clone;
	}
	
	public void replace(InstructionFetchDecodeRegisters instructionFetchDecodeRegisters){
		this.setIncrementedPc(instructionFetchDecodeRegisters.getIncrementedPc());
		this.setInstruction(instructionFetchDecodeRegisters.getInstruction());
	}
	
	public String toString() {
		  StringBuilder result = new StringBuilder();
		  String newLine = System.getProperty("line.separator");

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append(newLine);

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for ( Field field : fields  ) {
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(newLine);
		  }
		  result.append("}");

		  return result.toString();
		}
}
