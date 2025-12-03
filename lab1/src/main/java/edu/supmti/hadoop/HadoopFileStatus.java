package edu.supmti.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopFileStatus {

  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println("Usage: hadoop jar HadoopFileStatus.jar <chemin_dossier> <nom_fichier> <nouveau_nom>");
      System.err.println("Exemple: hadoop jar HadoopFileStatus.jar /user/root/input purchases.txt achats.txt");
      System.exit(1);
    }

    String cheminDossier = args[0];
    String nomFichier = args[1];
    String nouveauNom = args[2];

    Configuration conf = new Configuration();
    FileSystem fs;

    try {
      fs = FileSystem.get(conf);
      Path filepath = new Path(cheminDossier, nomFichier);

      if (!fs.exists(filepath)) {
        System.out.println("File does not exist: " + filepath);
        System.exit(1);
      }

      FileStatus infos = fs.getFileStatus(filepath);

      System.out.println("=== File Information ===");
      System.out.println("File Name: " + filepath.getName());
      System.out.println("File Size: " + infos.getLen() + " bytes");
      System.out.println("File Owner: " + infos.getOwner());
      System.out.println("File Permission: " + infos.getPermission());
      System.out.println("File Replication: " + infos.getReplication());
      System.out.println("File Block Size: " + infos.getBlockSize());

      System.out.println("\n=== Block Locations ===");
      BlockLocation[] blockLocations = fs.getFileBlockLocations(infos, 0, infos.getLen());
      for (int i = 0; i < blockLocations.length; i++) {
        BlockLocation blockLocation = blockLocations[i];
        String[] hosts = blockLocation.getHosts();
        System.out.println("Block " + (i + 1) + ":");
        System.out.println("  Offset: " + blockLocation.getOffset());
        System.out.println("  Length: " + blockLocation.getLength());
        System.out.print("  Hosts: ");
        for (String host : hosts) {
          System.out.print(host + " ");
        }
        System.out.println();
      }

      Path newFilepath = new Path(cheminDossier, nouveauNom);
      boolean renamed = fs.rename(filepath, newFilepath);
      System.out.println("\n=== Rename Operation ===");
      System.out.println("Rename from '" + nomFichier + "' to '" + nouveauNom + "': " + (renamed ? "SUCCESS" : "FAILED"));

      fs.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

