package br.com.simpleworks.foundation.entity;

import java.io.Serializable;

public interface BaseEntity <ID extends Serializable> extends Serializable {
	ID getId();
	void setId(ID id);
}
