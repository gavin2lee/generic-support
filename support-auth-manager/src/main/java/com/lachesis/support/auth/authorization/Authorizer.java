package com.lachesis.support.auth.authorization;

import com.lachesis.support.auth.vo.AuthorizationResult;
import com.lachesis.support.objects.entity.auth.Token;

public interface Authorizer {
	AuthorizationResult authorize(Token authToken);
}
