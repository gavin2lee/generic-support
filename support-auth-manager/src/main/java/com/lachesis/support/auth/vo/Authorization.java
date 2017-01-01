package com.lachesis.support.auth.vo;

import java.util.Collection;

public interface Authorization{
	Collection<String> getRoles();
	Collection<String> getPermissions();
}
