package comp304_project3;

public class DirectoryContent {
	
	DirectoryTable dt;
	FileAllocationTable fat;
	private int fileID = 0;
	int size;
	private long totalTimeCreateDT = 0;
	private long totalTimeCreateFAT = 0;
	private long totalTimeAccessDT = 0;
	private long totalTimeAccessFAT = 0;
	private long totalTimeExtendDT = 0;
	private long totalTimeExtendFAT = 0;
	private long totalTimeShrinkDT = 0;
	private long totalTimeShrinkFAT = 0;
	
	
	// timelari total olarak en son bas


	public DirectoryContent() {

		dt = new DirectoryTable();
		fat = new FileAllocationTable();
		
	}

	public void create_file(int file_id, int file_length) {

		//dt
		
		long startTime = System.currentTimeMillis();

		dt.createfile(fileID, file_length);

		/*
		System.out.println("DT");
		for(int i = 0; i< dt.getList().length-1; i++) {
			System.out.println(i+ "- " +dt.getList()[i]);
		}
*/
		
		long stopTime = System.currentTimeMillis();
		
		totalTimeCreateDT += (stopTime - startTime);
		//System.out.println("Running Time for DT creation: " + (stopTime - startTime));
		
		//fat

		
		long startTime2 = System.currentTimeMillis();
		
		fat.createfile(fileID, file_length);
		/*
		System.out.println("FAT");
		for(int i = 0; i< fat.getList().length-1; i++) {
			System.out.println(i+ "- " +fat.getList()[i]);
		}
		*/
		long stopTime2 = System.currentTimeMillis();

		totalTimeCreateFAT += (stopTime2 - startTime2);
		//System.out.println("Running Time for FAT creation: " + (stopTime2 - startTime2));

		

		fileID++;
	}


	public void access(int file_id, int byte_offset) {

		int dtLocation = -1;
		int fatLocation =-1;;
		
		//dt

		
		long startTime = System.currentTimeMillis();
		dtLocation = dt.access(file_id, byte_offset);
		long stopTime = System.currentTimeMillis();
		totalTimeAccessDT += (stopTime - startTime);
		//System.out.println("Running Time for DT access: " + (stopTime - startTime));

		//fat
		
		long startTime2 = System.currentTimeMillis();
		fatLocation = fat.access(file_id, byte_offset);
		long stopTime2 = System.currentTimeMillis();
		totalTimeAccessFAT += (stopTime2 - startTime2);
		//System.out.println("Running Time for FAT access: " + (stopTime2 - startTime2));

		System.out.println("DT location: " + dtLocation);
		System.out.println("FAT location: " + fatLocation);

	}

	public void extend(int file_id, int extension) {

		//dt
		
		long startTime = System.currentTimeMillis();
	
		dt.extend(file_id, extension);
		/*
		System.out.println("DT");
		for(int i = 0; i< dt.getList().length-1; i++) {
			System.out.println(i+ "- " +dt.getList()[i]);
		}*/
		
		long stopTime = System.currentTimeMillis();
		totalTimeExtendDT += (stopTime - startTime);
		//System.out.println("Running Time for DT extend: " + (stopTime - startTime));
		
		// fat
		
		long startTime2 = System.currentTimeMillis();
		fat.extend(file_id, extension);
		/*
		System.out.println("FAT");
		for(int i = 0; i< fat.getList().length-1; i++) {
			System.out.println(i+ "- " +fat.getList()[i]);
		}
		*/
		long stopTime2 = System.currentTimeMillis();
		totalTimeExtendFAT += (stopTime2 - startTime2);
		//System.out.println("Running Time for FAT extend: " + (stopTime2 - startTime2));

		

	}

	public void shrink(int file_id, int shrinking) {

		//dt 
		
		long startTime = System.currentTimeMillis();
		
		dt.shrink(file_id, shrinking);
		/*
		System.out.println("DT");
		for(int i = 0; i< dt.getList().length-1; i++) {
			System.out.println(i+ "- " +dt.getList()[i]);
		}
*/
		 
		long stopTime = System.currentTimeMillis();
		totalTimeShrinkDT += (stopTime - startTime);
		//System.out.println("Running Time for DT shrink: " + (stopTime - startTime));

		// fat
		
		long startTime2 = System.currentTimeMillis();
		fat.shrink(file_id, shrinking);
		/*
		System.out.println("FAT");
		for(int i = 0; i< fat.getList().length-1; i++) {
			System.out.println(i+ "- " +fat.getList()[i]);
		}
		*/
		long stopTime2 = System.currentTimeMillis();
		totalTimeShrinkFAT += (stopTime2 - startTime2);
		//System.out.println("Running Time for FAT shrink: " + (stopTime2 - startTime2));


	}
	
	public void getTimeCreate() {
		System.out.println("Running Time for DT creation: " + totalTimeCreateDT);
		System.out.println("Running Time for FAT creation: " + totalTimeCreateFAT);
	}
	
	public void getTimeAccess() {
		System.out.println("Running Time for DT access: " + totalTimeAccessDT);
		System.out.println("Running Time for FAT access: " + totalTimeAccessFAT);
	}
	
	public void getTimeExtend() {
		System.out.println("Running Time for DT extension: " + totalTimeExtendDT);
		System.out.println("Running Time for FAT extension: " + totalTimeExtendFAT);
	}
	
	public void getTimeShrink() {
		System.out.println("Running Time for DT shrinking: " + totalTimeShrinkDT);
		System.out.println("Running Time for FAT shrinking: " + totalTimeShrinkFAT);
	}

	public void setSize(int size) {
		this.size = size;
		dt.setSize(size);
		fat.setSize(size);
		new DirectoryContent();
	}
	
	public int getSize() {
		return size;
	}


}
