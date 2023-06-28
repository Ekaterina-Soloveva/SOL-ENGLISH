package com.example.solenglish.application.repository;

import com.example.solenglish.application.model.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends GenericRepository<Unit>{

//    @Query(
//            value = "SELECT topic_id FROM UNITS u WHERE u.level = 1",
//            nativeQuery = true)
//    Collection<User> findAllActiveUsersNative();






//    @Query(nativeQuery = true,
//            value = """
//            select distinct email
//            from users u join book_rent_info bri on u.id = bri.user_id
//            where bri.return_date < now()
//            and bri.returned = false
//            and u.is_deleted = false
//            """)
}
