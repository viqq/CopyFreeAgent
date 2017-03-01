package com.free.agent.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by antonPC on 18.06.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FreeAgentConstant {

    public static final String PERSISTENCE_CONTEXT = "free-agent-db";

    public static final String TRANSACTION_MANAGER = "free-agent-jpaTransactionManager";
}
