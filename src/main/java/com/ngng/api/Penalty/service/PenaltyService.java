package com.ngng.api.Penalty.service;

import com.ngng.api.Penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.Penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.Penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.Penalty.dto.ReadPenaltyResponseDTO;

import java.util.List;

public interface PenaltyService {
    List<ReadPenaltyListResponseDTO> findAll();

    ReadPenaltyResponseDTO findById(Long penaltyId);

    CreatePenaltyResponseDTO save(CreatePenaltyRequestDTO createPenaltyRequestDTO);

}
