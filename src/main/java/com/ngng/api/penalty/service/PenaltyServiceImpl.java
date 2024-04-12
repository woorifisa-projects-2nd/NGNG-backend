package com.ngng.api.penalty.service;

import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyResponseDTO;
import com.ngng.api.penalty.entity.Penalty;
import com.ngng.api.penalty.entity.PenaltyLevel;
import com.ngng.api.penalty.repository.PenaltyLevelRepository;
import com.ngng.api.penalty.repository.PenaltyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final PenaltyLevelRepository penaltyLevelRepository;

    @Override
    public List<ReadPenaltyListResponseDTO> findAll() {
        List<Penalty> penalties = new ArrayList<>();
        penaltyRepository.findAll().forEach(penalties::add);

        return penalties.stream()
                .map(ReadPenaltyListResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public ReadPenaltyResponseDTO findById(Long reportId) {
        Penalty penalty = penaltyRepository.findByReportId(reportId).orElseThrow(() ->
                new EntityNotFoundException("penalty not found")
        );

        return ReadPenaltyResponseDTO.from(penalty);
    }

    @Override
    public CreatePenaltyResponseDTO save(CreatePenaltyRequestDTO createPenaltyRequestDTO) {

        Long penaltyLevelId = createPenaltyRequestDTO.getPenaltyLevelId();
        PenaltyLevel penaltyLevel = penaltyLevelRepository.findById(penaltyLevelId).orElseThrow(() ->
                new EntityNotFoundException("penaltyLevel not found")
        );


        Timestamp banDate = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();

        if(penaltyLevel.getPenaltyLevelId() == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(penaltyLevel.getPenaltyLevelDays())));
            banDate = new Timestamp(calendar.getTimeInMillis());
        } else if(penaltyLevel.getPenaltyLevelId() == 2) {
            calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(penaltyLevel.getPenaltyLevelDays())));
            banDate = new Timestamp(calendar.getTimeInMillis());
        }
        else if(penaltyLevel.getPenaltyLevelId() == 3) {
            LocalDateTime banLocalDateTime = LocalDateTime.of(2038, 1, 19, 0, 0, 0);
            banDate = Timestamp.valueOf(banLocalDateTime);
            // TIMESTAMP : 2038-01-19 03:14:07’ UTC까지의 범위
        }

        Penalty penalty = Penalty.builder()
                .endDate(banDate)
                .userId(createPenaltyRequestDTO.getUserId())
                .reporterId(createPenaltyRequestDTO.getReporterId())
                .reason(createPenaltyRequestDTO.getReason())
                .penaltyLevel(PenaltyLevel.builder()
                        .penaltyLevelId(penaltyLevel.getPenaltyLevelId())
                        .penaltyLevelName(penaltyLevel.getPenaltyLevelName())
                        .build()
                )
                .reportId(createPenaltyRequestDTO.getReportId())
                .build();

        Penalty responsepenalty = penaltyRepository.save(penalty);

        return CreatePenaltyResponseDTO.builder()
                .startDate(responsepenalty.getStartDate())
                .endDate(responsepenalty.getEndDate())
                .userId(responsepenalty.getUserId())
                .reporterId(responsepenalty.getReporterId())
                .reason(responsepenalty.getReason())
                .penaltyLevel(responsepenalty.getPenaltyLevel())
                .reportId(responsepenalty.getReportId())
                .createdAt(responsepenalty.getCreatedAt())
                .updatedAt(responsepenalty.getUpdatedAt())
                .build();

    }
}
