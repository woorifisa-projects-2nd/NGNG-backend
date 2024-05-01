package com.ngng.api.user.user.dto.request;

public record AccountConfirmRequest(Long userId,
                                    String accountBank,
                                    String accountNumber) {
}
