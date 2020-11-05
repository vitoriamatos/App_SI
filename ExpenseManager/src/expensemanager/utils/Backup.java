package expensemanager.utils;



import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backup {

    public static final String BACKUP_DIRECTORY = "Academic Control System";
    public static final String BACKUP_FILE = "Academic Control System Backup.bkp";


    public static void backup() throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(getBackupFile()));
       // writer.writeObject(getData());
        writer.close();
    }

    private static File getAcademicDirectory() {
        File documentsDirectory = getUserDocumentsDirectory();
        File[] subdirectories = documentsDirectory.listFiles();
        File academicDirectory = null;

        // Get academic directory if exists
        if (subdirectories != null) {
            for (File directory : subdirectories) {
                if (directory.isDirectory() && directory.getName().equals(BACKUP_DIRECTORY)) {
                    academicDirectory = directory;
                    break;
                }
            }
        }

        // Create academic DIRECTORY if it doesn't exists
        if (academicDirectory == null) {
            academicDirectory = new File(documentsDirectory, BACKUP_DIRECTORY);
            academicDirectory.mkdir();
        }

        return academicDirectory;
    }

    private static File getBackupFile() throws IOException {
        File academicDirectory = getAcademicDirectory();
        File[] subdirectories = academicDirectory.listFiles();
        File backupFile = null;

        // Get backup file if exists
        if (subdirectories != null) {
            for (File file : subdirectories) {
                if (file.isFile() && file.getName().equals(BACKUP_FILE)) {
                    backupFile = file;
                    break;
                }
            }
        }

        // Create backup file if it doesn't exists
        if (backupFile == null) {
            backupFile = new File(academicDirectory, BACKUP_FILE);
            backupFile.createNewFile();
        }

        return backupFile;

    }



    // ======= HELPER METHODS =======

    private static File getUserDocumentsDirectory() {
        return new JFileChooser().getFileSystemView().getDefaultDirectory();
    }


}
