package com.exilant.logui.jsonutil;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

public class MergeFile {
	private static final String FILE_DIR = "/Users/partha/Desktop/Project/Fusion/LogUIEnhancement/logs/";
	private static final String FILE_TEXT_EXT = ".txt";
	FileInputStream fis;
	FileOutputStream fos;
	DataInputStream dis;
	BufferedOutputStream bos;
	File file = new File(FILE_DIR);
	File[] file_Names;
	int i = 0, ltc, file_Size, count_Files; // ltc=Length to copy till how many
											// bytes
	byte[] rw_Buffer, con_Str_Bt_Arr;
	int size;

	MergeFile() throws Exception {
		GenericExtFilter filter = new GenericExtFilter(FILE_TEXT_EXT);
		System.out.println("Enter into MergeFile  method");
		file_Names = file.listFiles(filter);
		if (file_Names != null) {
			System.out.println("got file names frm " + file + " folder ");
		}

		count_Files = file_Names.length;
		System.out.println("the total number of files in the path " + file
				+ " : " + count_Files);
		System.out.println("File names: ");

		for (int i = 0; i < file_Names.length; i++) {
			System.out.println(file_Names[i] + "\t");
		}

		if (count_Files != 0) {
			System.out.println("got file number  \n");
			fos = new FileOutputStream(FILE_DIR + "FusionLogsUI.log"); // outputs
																		// file
																		// file
																		// location
																		// stores
		}

		System.out.println("The number of files in the input folder to merge  "
				+ count_Files + "\n");

		if (fos != null) {
			System.out.println("fos created " + fos);

			bos = new BufferedOutputStream(fos);
			if (bos != null) {
				System.out.println("bos created " + bos);
			}

			size = 0;
			rw_Buffer = new byte[999000]; // read and write buffer size
			if (rw_Buffer != null) {
				System.out.println("byte array created " + rw_Buffer);
			}

			int sl;
			while (count_Files > 0) {
				System.out.println("Entring into while loop");
				String scrFile = file_Names[i].getName();
				System.out.println("<<<<<----->>" + scrFile);

				if (scrFile != null) {
					System.out.println("the file name is " + scrFile);
				}

				fis = new FileInputStream(FILE_DIR + scrFile);
				if (fis != null) {
					System.out.println("fis is " + fis);
				}

				dis = new DataInputStream(fis);
				if (dis != null) {
					System.out.println("dis is " + dis);
				}

				sl = scrFile.length();
				if (sl != 0) {
					System.out.println("sl is " + sl);
				}

				//bos.write(sl);
				if (bos != null) {
					System.out.println("bos is written   " + bos);
				} else {
					System.out.println("bos not  written" + bos);
				}
				con_Str_Bt_Arr = scrFile.getBytes(); // convetting file name in
														// string to byte array
				if (con_Str_Bt_Arr != null) {
					System.out
							.println("con_Str_Bt_Arr soruce file is conveted to bytes   "
									+ con_Str_Bt_Arr);
				} else {
					System.out
							.println("con_Str_Bt_Arr soruce file not conveted to bytes   "
									+ con_Str_Bt_Arr);
				}
				ltc = con_Str_Bt_Arr.length; // name lenght

//				bos.write(con_Str_Bt_Arr, 0, ltc); // adding file name to *.dat
													// file
				file_Size = dis.available(); // content size in file
				//bos.write(file_Size); // adding content size
				size = size + file_Size;
				System.out.println("----------->>>>" + size);
				file_Size = dis.available();
				dis.readFully(rw_Buffer, 0, file_Size); // reading data from
														// files in bytes
				System.out.println("->>> dis.readFully");
				bos.write(rw_Buffer, 0, file_Size); // writing data to files in
													// bytes
				size = size + file_Size;
				count_Files--;
				i++;
			}
			close();
		}

		return;
	}

	public void close() throws Exception {
		bos.flush();
		bos.close();
		fos.close();

	}

	public static void main(String[] args) throws Exception {
		System.out.println("enter main \n");
		new MergeFile();
		System.out.println("exit main  \n");
	}

	public void listFile(String folder, String ext) {

		GenericExtFilter filter = new GenericExtFilter(ext);

		File dir = new File(folder);

		if (dir.isDirectory() == false) {
			System.out.println("Directory does not exists : " + FILE_DIR);
			return;
		}

		// list out all the file name and filter by the extension
		String[] list = dir.list(filter);

		if (list.length == 0) {
			System.out.println("no files end with : " + ext);
			return;
		}

		for (String file : list) {
			String temp = new StringBuffer(FILE_DIR).append(File.separator)
					.append(file).toString();
			System.out.println("file : " + temp);
		}
	}

	// inner class, generic extension filter
	public class GenericExtFilter implements FilenameFilter {

		private String ext;

		public GenericExtFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(File dir, String name) {
			return (name.endsWith(ext));
		}
	}
}