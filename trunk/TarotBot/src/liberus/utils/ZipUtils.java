package liberus.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtils {
	public static void unzipArchive(File archive, String outputDir) {
		try
        {
            String destinationname = outputDir+".ex";
            if (!new File(destinationname).exists())
            	FileUtils.createDir(new File(destinationname));
            
            
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream(
                new FileInputStream(archive));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) 
            { 
            	byte[] buf = new byte[512];
                //for each entry to be extracted
                String entryName = zipentry.getName();
                System.out.println("entryname "+entryName);
                int n;
                FileOutputStream fileoutputstream;
                File newFile = new File(destinationname+"/"+WebUtils.md5(entryName));
                
                
                if(zipentry.isDirectory()) {
                	if (!newFile.exists()) {                		
                		FileUtils.createDir(newFile);
                	}
                	zipentry = zipinputstream.getNextEntry();
                	continue;
                }
                
                
                fileoutputstream = new FileOutputStream(
                   newFile);             
                int off = 0;
                while ((n = zipinputstream.read(buf, 0, 512)) > -1) {                	
                    fileoutputstream.write(buf, off, n);
                    fileoutputstream.flush();
                    //off += n;
                }

                fileoutputstream.close(); 
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		
		}

		private static void unzipEntry(ZipFile zipfile, ZipEntry entry,
		String outputDir) throws IOException {
		String name = entry.getName();
		System.out.println(name);
		if (entry.isDirectory()) {
			
			
			FileUtils.createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			FileUtils.createDir(outputFile.getParentFile());
		}

		
		BufferedOutputStream outputStream = new BufferedOutputStream(
		new FileOutputStream(outputFile));

		try {
		FileUtils.copyZipStream(entry,new ZipInputStream(zipfile.getInputStream(entry)), outputFile);
		} finally {
		outputStream.close();
		
		}
		}

		
}
