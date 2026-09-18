package com.back.boundedContext.cash.in;

import com.back.boundedContext.cash.app.CashFacade;
import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
public class CashDataInit {
  private final CashDataInit self;
  private final CashFacade cashFacade;

  public CashDataInit(
          @Lazy CashDataInit self,
          CashFacade cashFacade
  ) {
    this.self = self;
    this.cashFacade = cashFacade;
  }

  @Bean
  @Order(2) // 게시글이나 캐시나 둘다 2번이여도 연관이 없어서 그냥 둘다 2로 했대. 둘중에 하나가 램덤으로 더 빨리 실행된대
  public ApplicationRunner cashDataInitApplicationRunner() {
    return args -> {
      self.makeBaseCredits();
    };
  }

  @Transactional
  public void makeBaseCredits() {
    CashMember user1Member = cashFacade.findMemberByUsername("user1").get();
    CashMember user2Member = cashFacade.findMemberByUsername("user2").get();

    Wallet user1Wallet = cashFacade.findWalletByHolder(user1Member).get();

    if (user1Wallet.hasBalance()) return;

    user1Wallet.credit(150_000, CashLog.EventType.충전__무통장입금);
    user1Wallet.credit(100_000, CashLog.EventType.충전__무통장입금);
    user1Wallet.credit(50_000, CashLog.EventType.충전__무통장입금);

    Wallet user2Wallet = cashFacade.findWalletByHolder(user2Member).get();

    user2Wallet.credit(150_000, CashLog.EventType.충전__무통장입금);
  }
}