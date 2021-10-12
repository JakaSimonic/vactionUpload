package com.example.vacationUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.example.vacationUpload.model.Duration;
import com.example.vacationUpload.model.Upload;
import com.example.vacationUpload.model.Uploads;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    static private ConcurrentHashMap<String, UploadTracker> uploadTrackerMap = new ConcurrentHashMap<>(50);
    final private static String SAVE_DIR = System.getProperty("java.io.tmpdir");

    @PostMapping
    public String handleUpload(HttpServletRequest request) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            throw new RuntimeException("TODO habdle this.");
        }

        String fileName = request.getHeader("X-Upload-File");
        if (fileName == null) {
            throw new RuntimeException("TODO habdle this.");
        }

        long startTime = request.getSession().getCreationTime();
        UploadTracker newTracker = new UploadTracker(fileName, startTime);

        UploadTracker previousTracker = uploadTrackerMap.get(fileName);

        if (previousTracker != null && !previousTracker.isFinished()) {
            return "Already processing " + fileName;
        }

        uploadTrackerMap.put(fileName, newTracker);

        ServletFileUpload upload = new ServletFileUpload();
        upload.setProgressListener((ProgressListener) newTracker);
        try {
            FileItemIterator it = upload.getItemIterator(request);
            while (it.hasNext()) {
                FileItemStream fis = it.next();
                if (fis.isFormField()) {
                    continue;
                }
                Path storage = Path.of(SAVE_DIR, fileName).toAbsolutePath().normalize();
                Files.copy(fis.openStream(), storage, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (FileUploadException | IOException e) {
            System.out.println(e.toString());
            if (previousTracker != null) {
                uploadTrackerMap.put(previousTracker.getpFileName(), previousTracker);
            }
            throw new RuntimeException("TODO: Handle this case");
        }

        newTracker.setUnixEndTime(System.currentTimeMillis());
        return "success handeled by " + Thread.currentThread().getName() + "\n";
    }

    @GetMapping("/duration")
    public ResponseEntity<List<Duration>> getDuration() {
        return ResponseEntity.ok()
                .body(uploadTrackerMap.values().stream().filter(tracker -> tracker.isFinished())
                        .map(tracker -> new Duration(tracker.getId(), tracker.getUploadDuration()))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/progress")
    public ResponseEntity<Uploads> getProgress() {
        return ResponseEntity.ok()
                .body(new Uploads(uploadTrackerMap.values().stream().filter(tracker -> tracker.inProgress()).map(
                        tracker -> new Upload(tracker.getId(), tracker.getpContentLength(), tracker.getpBytesRead()))
                        .collect(Collectors.toList())));
    }

}
