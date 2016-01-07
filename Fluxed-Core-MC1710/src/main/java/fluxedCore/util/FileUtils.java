package fluxedCore.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

	
	public static void copyFromJar(Class baseClass, String fileName, File to){
		URL baseUrl = baseClass.getResource("/assets/"+fileName);
		try{
			org.apache.commons.io.FileUtils.copyURLToFile(baseUrl, to);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 /**
     * @author McDowell - http://stackoverflow.com/questions/1399126/java-util-zip -recreating-directory-structure
     * 
     * @param directory
     *            The directory to zip the contents of. Content structure will be preserved.
     * @param zipfile
     *            The zip file to output to.
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void zipFolderContents(File directory, File zipfile) throws IOException
    {
        URI base = directory.toURI();
        Deque<File> queue = new LinkedList<File>();
        queue.push(directory);
        OutputStream out = new FileOutputStream(zipfile);
        Closeable res = out;
        try
        {
            ZipOutputStream zout = new ZipOutputStream(out);
            res = zout;
            while (!queue.isEmpty())
            {
                directory = queue.pop();
                for (File child : directory.listFiles())
                {
                    String name = base.relativize(child.toURI()).getPath();
                    if (child.isDirectory())
                    {
                        queue.push(child);
                        name = name.endsWith("/") ? name : name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    }
                    else
                    {
                        zout.putNextEntry(new ZipEntry(name));
                        copy(child, zout);
                        zout.closeEntry();
                    }
                }
            }
        }
        finally
        {
            res.close();
        }
    }
    /**@author tterrag1098
     * <p>
     * from <a href="https://github.com/tterrag1098/ttCore/blob/master/src/main/java/tterrag/core/common/util/TTFileUtils.java">this site </a>
     */
    /** @see #zipFolderContents(File, File) */
    private static void copy(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        while (true)
        {
            int readCount = in.read(buffer);
            if (readCount < 0)
            {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    /** @see #zipFolderContents(File, File) */
    private static void copy(File file, OutputStream out) throws IOException
    {
        InputStream in = new FileInputStream(file);
        try
        {
            copy(in, out);
        }
        finally
        {
            in.close();
        }
    }
}
