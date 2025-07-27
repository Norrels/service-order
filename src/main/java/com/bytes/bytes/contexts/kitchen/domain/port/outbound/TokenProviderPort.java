package com.bytes.bytes.contexts.kitchen.domain.port.outbound;

import java.util.List;

public interface TokenProviderPort {
    String generate(String subject, List<String> roles);
}
