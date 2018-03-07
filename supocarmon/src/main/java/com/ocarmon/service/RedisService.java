/**
 * 
 */
package com.ocarmon.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author 李浩铭
 * @since 2018年3月7日 上午9:32:21
 */
@Service
public class RedisService {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置缓存,设置过期时间
	 * 
	 * @param key
	 * @param 值
	 * @param 过期时间
	 *            (单位:分钟)
	 * @return true or false
	 * 
	 */
	public boolean set(String key, Object obj, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, obj);
			redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置缓存，永不过期设置
	 * 
	 * @param key
	 * @param 值
	 * @return true or false
	 * 
	 */
	public boolean set(String key, Object obj) {
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, obj);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return 无数据null
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

}
