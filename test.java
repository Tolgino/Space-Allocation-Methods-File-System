package comp304_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		DirectoryContent dc = new DirectoryContent();


		Scanner sc= new Scanner(System.in); 
		System.out.print("Enter file name (with .txt): ");  
		String str = sc.nextLine();
		String str1 = str;
		String[] name = str1.split("_");
		if(name[0].equals("input")) {
			dc.setSize(Integer.parseInt(name[1]));
		}

		while(!str.equals("")) {
			File myObj = new File(str);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] parts = data.split(":");
				for(int i = 0; i<parts.length;i++) {
					//System.out.println(parts[i]);
					if(parts[i].equals("c")) {
						dc.create_file(1, Integer.parseInt(parts[i+1]));
					} else if(parts[i].equals("a")) {
						dc.access(Integer.parseInt(parts[i+1]), Integer.parseInt(parts[i+2]));
					} else if(parts[i].equals("e")) {
						dc.extend(Integer.parseInt(parts[i+1]), Integer.parseInt(parts[i+2]));
					} else if(parts[i].equals("sh")) {
						dc.shrink(Integer.parseInt(parts[i+1]), Integer.parseInt(parts[i+2]));
					}
				}
			}
			myReader.close();
			
			dc.getTimeCreate();
			
			dc.getTimeAccess();
			
			dc.getTimeExtend();
			
			dc.getTimeShrink();
			
			System.out.println("Number of rejected creations for FAT: "+ dc.fat.getCreateFail());
			System.out.println("Number of rejected creations for DT: "+ dc.dt.getCreateFail());
			
			System.out.println("Number of rejected extensions for FAT: "+ dc.fat.getExtendFail());
			System.out.println("Number of rejected extensions for DT: "+ dc.dt.getExtendFail());
			
			System.out.print("Enter file name: ");  
			str = sc.nextLine();  
		}



		//DirectoryContent dc = new DirectoryContent();

		//		System.out.println("Created");
		//		dc.create_file(1, 5);

		//dc.create_file(10, 12);

		///dc.create_file(3, 3);

		//dc.create_file(4, 5);

		//dc.create_file(6, 5);

		//dc.create_file(7, 5);

		//System.out.println("Expanded");
		//dc.extend(1, 3);

		//System.out.println("Expanded");
		//dc.extend(10, 5);

		//System.out.println("Shrinked");
		//dc.shrink(1, 2);

		//dc.shrink(10, 2);

		//dc.shrink(10, 2);

		//System.out.println("Access");
		//dc.access(1, 2);

		//System.out.println("Created");
		//dc.create_file(1, 5);

		//dc.create_file(10, 12);

		//System.out.println("Access");
		//dc.access(10, 2);

		//System.out.println("Expanded");
		//dc.extend(10, 5);

		//System.out.println("Expanded");
		//dc.extend(1,3);

		//System.out.println("Expanded");
		//dc.extend(1,1);

		//System.out.println("Shrinked");
		//dc.shrink(1, 4);

		//dc.shrink(10, 3);


	}

}
