package client;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
* The purpose of this class is to write to a file the elements passed as a string.
* The file is opened and closed every time the method is called.
* @author jadiel
*
*/
public class FileWrite {

        /**
         * Writes string content into the file at filePath
         * @param filePath
         * @param content
         */
        public static void write(String filePath, String content){
        
                //1. Open file
                BufferedWriter bw=null;
                try {
                        bw=openFile(filePath);
                } catch (FileNotFoundException e) {
                        return;
                }
                
                //2. Write
                try {
                        bw.write(content);
                } catch (IOException e) {
                        
                        e.printStackTrace();
                }
                
                //3. Close file
                closeFile(bw);
        }
        
        /**
         * Appends string content into file at filePath
         * @param filePath
         * @param content
         */
        public static void append(String filePath, String content){
                //1. Open file
                BufferedWriter bw=null;
                try {
                        bw=openFile(filePath);
                } catch (FileNotFoundException e) {
                        return;
                }
                
                //2. Append
                try {
                        bw.append(content);
                } catch (IOException e) {
                        
                        e.printStackTrace();
                }
                //3. Close file
                closeFile(bw);
        }
        
        private static BufferedWriter openFile(String filePath) throws FileNotFoundException{
                File file=new File(filePath);
                
                if (!file.exists()) {
                        try {
                                file.createNewFile();
                        } catch (IOException e) {
                                e.printStackTrace();
                                
                        }
                }
                
                java.io.FileWriter fw = null;
                try {
                        fw = new java.io.FileWriter(file);
                } catch (IOException e) {
                        
                        e.printStackTrace();
                }
                
                BufferedWriter bw = new BufferedWriter(fw);
                return bw;
        }
        
        private static void closeFile(BufferedWriter br){
                try {
                        br.close();
                } catch (IOException e) {
                        //Do nothing. THis is intended here.
                }
        }
        
}

