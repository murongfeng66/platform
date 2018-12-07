package com.jwzhu.platform.core.user.db;

import org.springframework.stereotype.Repository;

import com.jwzhu.platform.core.user.model.Admin;

@Repository
public interface AdminDao {

    Admin getById(long id);

}
