package com.amydegregorio.javabasics.watchservice;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

public class WatchServiceExample {
   private static final String WATCHED_DIR = "/Work/APC/Watch";

   public static void main(String[] args) {
      WatchServiceExample ex = new WatchServiceExample();

      try {
         ex.watchDirectoryPoll();
         //ex.watchDirectoryPollWithTimeout();
         //ex.watchDirectoryTake();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void watchDirectoryPoll() throws IOException {
      WatchService watcher = FileSystems.getDefault().newWatchService();
      Path watchDir = FileSystems.getDefault().getPath(WATCHED_DIR);
      WatchKey watchedKey = watchDir.register(watcher, 
         StandardWatchEventKinds.ENTRY_CREATE,
         StandardWatchEventKinds.ENTRY_MODIFY, 
         StandardWatchEventKinds.ENTRY_DELETE);

      while (true) {
         WatchKey eventKey = watcher.poll();
         if (eventKey != null) {
            for (WatchEvent<?> event : eventKey.pollEvents()) {

               if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                  System.out.println("Possible data loss!");
               }

               WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
               Path filename = pathEvent.context();

               if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  try {
                     System.out.printf("File '%s' created.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                     System.out.printf("File size = %d\n", Files.size(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                  try {
                     System.out.printf("File '%s' modified.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                  System.out.printf("File '%s' deleted.\n", filename.getFileName());
               }
            }

            boolean valid = eventKey.reset();
            if (!valid) {
               break;
            }
         }
      }
   }

   public void watchDirectoryPollWithTimeout() throws IOException {
      WatchService watcher = FileSystems.getDefault().newWatchService();
      Path watchDir = FileSystems.getDefault().getPath(WATCHED_DIR);
      WatchKey watchedKey = watchDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
               StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

      while (true) {
         WatchKey eventKey = null;
         try {
            eventKey = watcher.poll(500, TimeUnit.MILLISECONDS);
         } catch (InterruptedException ie) {
            break;
         }

         if (eventKey != null) {
            for (WatchEvent<?> event : eventKey.pollEvents()) {
   
               if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                  System.out.println("Possible data loss!");
               }
   
               WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
               Path filename = pathEvent.context();
   
               if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  try {
                     System.out.printf("File '%s' created.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                     System.out.printf("File size = %d\n", Files.size(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                  try {
                     System.out.printf("File '%s' modified.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                  System.out.printf("File '%s' deleted.\n", filename.getFileName());
               }
            }
   
            boolean valid = eventKey.reset();
            if (!valid) {
               break;
            }
         }
      }
   }
   
   public void watchDirectoryTake() throws IOException {
      WatchService watcher = FileSystems.getDefault().newWatchService();
      Path watchDir = FileSystems.getDefault().getPath(WATCHED_DIR);
      WatchKey watchedKey = watchDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
               StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

      while (true) {
         WatchKey eventKey = null;
         try {
            eventKey = watcher.take();
         } catch (InterruptedException ie) {
            break;
         }

         if (eventKey != null) {
            for (WatchEvent<?> event : eventKey.pollEvents()) {
   
               if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                  System.out.println("Possible data loss!");
               }
   
               WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
               Path filename = pathEvent.context();
   
               if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  try {
                     System.out.printf("File '%s' created.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                     System.out.printf("File size = %d\n", Files.size(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                  try {
                     System.out.printf("File '%s' modified.\n", filename.getFileName());
                     System.out.printf("File type = %s\n", Files.probeContentType(filename));
                  } catch (NoSuchFileException e) {
                     continue;
                  }
               } else if (pathEvent.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                  System.out.printf("File '%s' deleted.\n", filename.getFileName());
               }
            }
   
            boolean valid = eventKey.reset();
            if (!valid) {
               break;
            }
         }
      }
   }
}
