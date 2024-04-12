package com.ngng.api.penalty.service;

import com.ngng.api.penalty.dto.CreatePenaltyRequestDTO;
import com.ngng.api.penalty.dto.CreatePenaltyResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyListResponseDTO;
import com.ngng.api.penalty.dto.ReadPenaltyResponseDTO;

import java.util.List;

public interface PenaltyService {
    List<ReadPenaltyListResponseDTO> findAll();

    ReadPenaltyResponseDTO findById(Long reportId);

    CreatePenaltyResponseDTO save(CreatePenaltyRequestDTO createPenaltyRequestDTO);

}
