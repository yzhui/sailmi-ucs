package com.sailmi.system.user.mapper;

import com.sailmi.system.user.persist.AliceUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2020-11-10 20:39
 */
@Mapper
public interface AliceUserMapper {

	int insertUser(@Param("model") AliceUserModel model);

	String queryUserIdByPhone(@Param("userPhone") String userPhone);
}
