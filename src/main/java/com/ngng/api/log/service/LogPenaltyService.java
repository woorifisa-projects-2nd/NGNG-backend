package com.ngng.api.log.service;

import com.ngng.api.log.dto.LogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LogPenaltyService {

    private final String LOG_FILE_PATH = "logs/penalty-log.txt";
    private final int PAGE_SIZE = 10;

    public Page<LogDTO> readLog(Pageable pageable) throws IOException {
        int startIndex = pageable.getPageNumber() * PAGE_SIZE;

        List<LogDTO> logs = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(LOG_FILE_PATH))) {
            stream.skip(startIndex)
                    .limit(PAGE_SIZE)
                    .map(this::parseLogLine)
                    .filter(log -> log != null)
                    .forEach(logs::add);
        }

        long totalCount;
        try (Stream<String> stream = Files.lines(Paths.get(LOG_FILE_PATH))) {
            totalCount = stream.count();
        }

        return new PageImpl<>(logs, pageable, totalCount);
    }

    private LogDTO parseLogLine(String line) {
        try {
            // Assuming the log format: timestamp - message
            String[] parts = line.split(" \\| ");
            Timestamp ts = Timestamp.valueOf(parts[0]);
            String message = parts[parts.length - 1];

            LogDTO log = new LogDTO();
            log.setTimestamp(ts);
            log.setMessage(message);
            return log;
        } catch (Exception e) {
            // Handle parsing error or ignore malformed log lines
            return null;
        }
    }
}
