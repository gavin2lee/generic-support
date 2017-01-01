package com.lachesis.support.auth.context.comm;

import com.lachesis.support.objects.vo.auth.AuthorizationResponseVO;

public interface AuthCenterClient {
	AuthorizationResponseVO authorize(String token, String terminalIpAddress) throws AuthorizationException;
}
