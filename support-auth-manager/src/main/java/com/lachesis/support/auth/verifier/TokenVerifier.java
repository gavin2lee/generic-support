package com.lachesis.support.auth.verifier;

import com.lachesis.support.objects.entity.auth.Token;

public interface TokenVerifier {
	Token verify(String token, String terminalIpAddress);
}
