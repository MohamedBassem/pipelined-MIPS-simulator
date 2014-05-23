package eg.edu.guc.mimps.simulator;

import java.io.StringReader;

import eg.edu.guc.mimps.assembler.Assembler;
import eg.edu.guc.mimps.components.ALU;
import eg.edu.guc.mimps.components.Controller;
import eg.edu.guc.mimps.components.DataMemory;
import eg.edu.guc.mimps.components.RegisterFile;
import eg.edu.guc.mimps.gui.GUI;
import eg.edu.guc.mimps.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mimps.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mimps.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mimps.registers.Memory;
import eg.edu.guc.mimps.registers.MemoryWritebackRegisters;
import eg.edu.guc.mimps.registers.Registers;

public class Simulator {
	
	private InstructionFetchDecodeRegisters instructionFetchDecodeRegisters;
	private InstructionDecodeExecuteRegisters instructionDecodeExecuteRegisters;
	private ExecuteMemoryRegisters executeMemoryRegisters;
	private MemoryWritebackRegisters memoryWritebackRegisters;
	
	private Assembler assembler;
	private Controller controller;
	private RegisterFile registerFile;
	private ALU alu;
	private DataMemory dataMemory;
	
	GUI gui;
	
	private int pc;
	
	public Simulator(){
		this.reset();
		// TODO
		//new GUI(this);
	}
	
	public void reset(){
		instructionFetchDecodeRegisters = new InstructionFetchDecodeRegisters();
		instructionDecodeExecuteRegisters = new InstructionDecodeExecuteRegisters();
		executeMemoryRegisters = new ExecuteMemoryRegisters();
		memoryWritebackRegisters = new MemoryWritebackRegisters();
		
		Memory memory = new Memory();
		Registers registers = new Registers();
		
		controller = new Controller(instructionFetchDecodeRegisters,instructionDecodeExecuteRegisters);
		registerFile = new RegisterFile(instructionFetchDecodeRegisters,instructionDecodeExecuteRegisters,
										memoryWritebackRegisters, registers);
		alu = new ALU(instructionDecodeExecuteRegisters,executeMemoryRegisters);
		dataMemory = new DataMemory(executeMemoryRegisters,memoryWritebackRegisters,memory);
	}
	
	public boolean assemble(int origin , String code ){
		this.reset();
		this.pc = origin;
		assembler = new Assembler(origin,new StringReader(code),instructionFetchDecodeRegisters);
		// TODO assembler.assemble()
		return true;
	}
	
	public boolean step(){
		if(!assembler.execute(pc)){
			return false;
		}
		registerFile.execute();
		controller.execute();
		alu.execute();
		dataMemory.execute();
		
		assembler.write();
		registerFile.write();
		controller.write();
		alu.write();
		dataMemory.write();
		
		gui.update();
		
		if(executeMemoryRegisters.isBranch() && !executeMemoryRegisters.isZero()){
			pc = executeMemoryRegisters.getBranchAddress();
		}else{
			pc += 4;
		}
		gui.update();
		return true;
	}
	
	public void run(int origin,String data){
		while(true){
			if(!step()){
				break;
			}
		}
	}
	
	public int getPc(){
		return this.pc;
	}
	
	public static void main(String[] args) {
		new Simulator();
	}
}
