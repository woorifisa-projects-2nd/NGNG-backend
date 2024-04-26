package com.ngng.api.point.service;

import com.ngng.api.point.dto.CreateAddPointRequestDTO;
import com.ngng.api.point.entity.PointHistory;
import com.ngng.api.point.repository.PointHistoryRepository;
import com.ngng.api.user.entity.User;
import com.ngng.api.user.service.AuthService;
import com.ngng.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic ="point-log")
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    private final AuthService authService;


    public PointHistory createInitByUser(User user){
        PointHistory pointHistory = PointHistory.builder()
                .beforeCost(0L)
                .addCost(0L)
                .cost(0L)
                .type("기본 생성")
                .typeDetail(user.getName()+" 유저가 생성 되면서 기본으로 생성된 포인트")
                .user(user)
                .build();

        PointHistory response = pointHistoryRepository.save(pointHistory);

        log.info("Success Create PointHistory id: {} User : {} ",response.getId(),response.getUser().getUserId());
        return response;
//        return pointHistory;
    }


    public PointHistory readCostByUser(User user){
        return pointHistoryRepository.findLastByUserId(user.getUserId()).orElse(null);

    }
    public PointHistory readCost(){
        User user = this.authService.readAuthUser();
        return pointHistoryRepository.findLastByUserId(user.getUserId()).orElse(null);
    }

    public List<PointHistory> readPointHistories() {
        User user = this.authService.readAuthUser();
        return pointHistoryRepository.findAllByUserId(user.getUserId());
    }

    public PointHistory updateCost(CreateAddPointRequestDTO request){
        User user = this.authService.readAuthUser();
        PointHistory lastHistory = readCostByUser(user);

        PointHistory pointHistory = PointHistory.builder()
                .beforeCost(lastHistory.getCost())
                .addCost(request.getAddCost())
                .cost((Long) lastHistory.getCost() + (Long) request.getAddCost())
                .type(request.getType())
                .typeDetail(lastHistory.getUser().getName() + " 유저가 "+request.getAddCost() + " 금액 만큼 " + request.getType() + " 하셨습니다.")
                .user(user)
                .build();

        PointHistory response = pointHistoryRepository.save(pointHistory);

        log.info("Success Charge Point UserId: {} cost : {} ",response.getUser().getUserId(),request.getAddCost());

        return response;

    }

}
