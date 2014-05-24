package eg.edu.guc.mimps.registers;

import java.lang.reflect.Field;

public class ExecuteMemoryRegisters {
	private int branchAddress;
	private boolean zero;
	private int ALUResult;
	private int registerValueToMemory;
	private int writeBackRegister;
	private int incrementedPc;
	private boolean jump;
	private int jumpAddress;
	
	// Memory Phase Signals
	private boolean memRead;
	private boolean memWrite;
	private boolean branch;
	
	// Write Back Signals
	private boolean regWrite;
	private boolean memToReg;
	
	public int getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(int branchAddress) {
		this.branchAddress = branchAddress;
	}
	public boolean isZero() {
		return zero;
	}
	public void setZero(boolean zero) {
		this.zero = zero;
	}
	public int getALUResult() {
		return ALUResult;
	}
	public void setALUResult(int aLUResult) {
		ALUResult = aLUResult;
	}
	public int getRegisterValueToMemory() {
		return registerValueToMemory;
	}
	public void setRegisterValueToMemory(int registerValueToMemory) {
		this.registerValueToMemory = registerValueToMemory;
	}
	public int getWriteBackRegister() {
		return writeBackRegister;
	}
	public void setWriteBackRegister(int writeBackRegister) {
		this.writeBackRegister = writeBackRegister;
	}
	public boolean isMemRead() {
		return memRead;
	}
	public void setMemRead(boolean memRead) {
		this.memRead = memRead;
	}
	public boolean isMemWrite() {
		return memWrite;
	}
	public void setMemWrite(boolean memWrite) {
		this.memWrite = memWrite;
	}
	public boolean isBranch() {
		return branch;
	}
	public void setBranch(boolean branch) {
		this.branch = branch;
	}
	public boolean isRegWrite() {
		return regWrite;
	}
	public void setRegWrite(boolean regWrite) {
		this.regWrite = regWrite;
	}
	public boolean isMemToReg() {
		return memToReg;
	}
	public void setMemToReg(boolean memToReg) {
		this.memToReg = memToReg;
	}
	public int getJumpAddress() {
		return jumpAddress;
	}
	public void setJumpAddress(int jumpAddress) {
		this.jumpAddress = jumpAddress;
	}
	public boolean isJump() {
		return jump;
	}
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public int getIncrementedPc() {
		return incrementedPc;
	}
	public void setIncrementedPc(int incrementedPc) {
		this.incrementedPc = incrementedPc;
	}
	
	public ExecuteMemoryRegisters clone(){
		ExecuteMemoryRegisters clone = new ExecuteMemoryRegisters();
		clone.setALUResult(ALUResult);
		clone.setBranch(branch);
		clone.setBranchAddress(branchAddress);
		clone.setMemRead(memRead);
		clone.setMemToReg(memToReg);
		clone.setMemWrite(memWrite);
		clone.setRegisterValueToMemory(registerValueToMemory);
		clone.setWriteBackRegister(writeBackRegister);
		clone.setZero(zero);
		clone.setRegWrite(regWrite);
		clone.setJump(jump);
		clone.setJumpAddress(jumpAddress);
		clone.setIncrementedPc(incrementedPc);
		return clone;
	}
	
	public void replace(ExecuteMemoryRegisters executeMemoryRegisters){
		this.setALUResult(executeMemoryRegisters.getALUResult());
		this.setBranch(executeMemoryRegisters.isBranch());
		this.setBranchAddress(executeMemoryRegisters.getBranchAddress());
		this.setMemRead(executeMemoryRegisters.isMemRead());
		this.setMemToReg(executeMemoryRegisters.isMemToReg());
		this.setMemWrite(executeMemoryRegisters.isMemWrite());
		this.setRegisterValueToMemory(executeMemoryRegisters.getRegisterValueToMemory());
		this.setWriteBackRegister(executeMemoryRegisters.getWriteBackRegister());
		this.setZero(executeMemoryRegisters.isZero());
		this.setRegWrite(executeMemoryRegisters.isRegWrite());
		this.setJump(executeMemoryRegisters.isJump());
		this.setJumpAddress(executeMemoryRegisters.getJumpAddress());
		this.setIncrementedPc(executeMemoryRegisters.getIncrementedPc());
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
