package com.bytes.service.order.domain.outbound;

import java.util.List;

public interface TokenProviderPort {
    String generate(String subject, List<String> roles);
}
