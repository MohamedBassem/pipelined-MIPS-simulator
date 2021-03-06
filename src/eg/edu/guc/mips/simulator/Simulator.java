package eg.edu.guc.mips.simulator;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.NOP;

import eg.edu.guc.mips.assembler.Assembler;
import eg.edu.guc.mips.components.ALU;
import eg.edu.guc.mips.components.Controller;
import eg.edu.guc.mips.components.DataMemory;
import eg.edu.guc.mips.components.RegisterFile;
import eg.edu.guc.mips.exceptions.SyntaxErrorException;
import eg.edu.guc.mips.gui.GUI;
import eg.edu.guc.mips.registers.ExecuteMemoryRegisters;
import eg.edu.guc.mips.registers.InstructionDecodeExecuteRegisters;
import eg.edu.guc.mips.registers.InstructionFetchDecodeRegisters;
import eg.edu.guc.mips.registers.Memory;
import eg.edu.guc.mips.registers.MemoryWritebackRegisters;
import eg.edu.guc.mips.registers.Registers;

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

	private Memory memory;
	private Registers registers;

	private int NOPcount;
	boolean done;
	private int cycles;

	GUI gui;

	private int pc;

	public Simulator() {
		this.reset();
		gui = new GUI(this);
	}

	public InstructionFetchDecodeRegisters getInstructionFetchDecodeRegisters() {
		return instructionFetchDecodeRegisters;
	}

	public InstructionDecodeExecuteRegisters getInstructionDecodeExecuteRegisters() {
		return instructionDecodeExecuteRegisters;
	}

	public ExecuteMemoryRegisters getExecuteMemoryRegisters() {
		return executeMemoryRegisters;
	}

	public MemoryWritebackRegisters getMemoryWritebackRegisters() {
		return memoryWritebackRegisters;
	}

	public void reset() {
		instructionFetchDecodeRegisters = new InstructionFetchDecodeRegisters();
		instructionDecodeExecuteRegisters = new InstructionDecodeExecuteRegisters();
		executeMemoryRegisters = new ExecuteMemoryRegisters();
		memoryWritebackRegisters = new MemoryWritebackRegisters();

		NOPcount = 0;
		done = false;

		cycles = 0;
		pc = 0;

		this.memory = new Memory();
		this.registers = new Registers();

		controller = new Controller(instructionFetchDecodeRegisters,
				instructionDecodeExecuteRegisters);
		registerFile = new RegisterFile(instructionFetchDecodeRegisters,
				instructionDecodeExecuteRegisters, memoryWritebackRegisters,
				registers);
		alu = new ALU(instructionDecodeExecuteRegisters, executeMemoryRegisters);
		dataMemory = new DataMemory(executeMemoryRegisters,
				memoryWritebackRegisters, memory);
	}

	public boolean assemble(int origin, String code)
			throws SyntaxErrorException {
		this.reset();
		this.pc = origin;
		assembler = new Assembler(origin, new StringReader(code),
				instructionFetchDecodeRegisters);
		Map<Integer, Integer> data = assembler.assemble();
		for(Integer address : data.keySet()){
			memory.put(address, data.get(address));
		}
		gui.update();
		return true;
	}

	public boolean step() {
		if (!assembler.execute(pc)) {
			if (NOPcount == 0) {
				done = true;
				NOPcount++;
			} else if (NOPcount == 6) {
				return false;
			} else {
				NOPcount++;
			}
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
		cycles++;
		gui.update();
		
		
		if(!done){
			pc += 4;
		}
		
		if(executeMemoryRegisters.isBranch() && executeMemoryRegisters.isZero()){
			pc = executeMemoryRegisters.getBranchAddress();
			NOPcount = 0;
			done = false;
		}
		
		if(executeMemoryRegisters.isJump()){
			
			pc = executeMemoryRegisters.getJumpAddress();
			NOPcount = 0;
			done = false;
		}
		gui.update();
		return true;
	}
	
	public void run(){
		while(cycles < 1000000){
			if(!step()){
				break;
			}
		}
	}

	public int getPc() {
		return this.pc;
	}

	public static void main(String[] args) {
		new Simulator();
	}

	public Registers getRegisters() {
		return registers;
	}

	public Memory getMemory() {
		return memory;
	}

	public int getCycles() {
		return cycles;
	}
}
