package com.back.shared.post.event;

import com.back.shared.payout.dto.PayoutDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PayoutCompletedEvent {
  private final PayoutDto payout;
}