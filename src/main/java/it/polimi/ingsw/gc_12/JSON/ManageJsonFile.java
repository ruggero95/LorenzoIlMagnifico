package it.polimi.ingsw.gc_12.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ManageJsonFile {
	public void tojsonFile(String gsonstring, JsonMaster jsonObj){
		FileOutputStream writeOnFile;
		File file;
		try {
			file = new File(jsonObj.getFilename()+".json");
			if (file.exists()) {
				ManageJsonFile manageObj= new ManageJsonFile();
				String existfile=manageObj.fromJsonFile(jsonObj);
				existfile=existfile.substring(1,existfile.length()-1);
				gsonstring=gsonstring.substring(0,1)+existfile+","+gsonstring.substring(1,gsonstring.length());
			}
			writeOnFile = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			  writeOnFile.write(gsonstring.getBytes());
			  writeOnFile.flush();
			  writeOnFile.close();
		} catch (Exception e) {
			  e.printStackTrace();
		}
		System.out.println("File "+jsonObj.getFilename()+".json created");
	}
	public String fromJsonFile(JsonMaster jsonobj){
		FileInputStream readFromFile;
		File file;
		int read;
		String jsonread="";
		try{
			file = new File(jsonobj.getFilename()+".json");
			readFromFile= new FileInputStream(file);
			while((read=readFromFile.read())!=-1){
				jsonread=jsonread+(char) read;
			}
			System.out.println(jsonread);
			readFromFile.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonread;
	}
}