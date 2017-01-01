package com.lachesis.support.objects.vo;

import java.util.Collection;

public interface SecurityContext {
	String getPrincipal();
	String getToken();
	String getTerminalIpAddress();
	Collection<String> getRoles();
	Collection<String> getPermissions();
}
