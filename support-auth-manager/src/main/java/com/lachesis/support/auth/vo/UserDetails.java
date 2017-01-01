package com.lachesis.support.auth.vo;

import java.io.Serializable;

public interface UserDetails extends Serializable{
	String getId();
	String getUsername();
}
