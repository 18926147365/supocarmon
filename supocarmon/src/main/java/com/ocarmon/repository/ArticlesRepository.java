
package com.ocarmon.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ocarmon.entity.Articles;
import com.ocarmon.entity.People;
/** 
* @author 李浩铭 
* @since 2018年3月6日 上午9:49:14
*/
@Repository
public interface ArticlesRepository extends MongoRepository<Articles,String>{
}
