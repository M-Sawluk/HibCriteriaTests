package com.ilosci;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mike on 2017-08-01.
 */
@Repository
public interface KatRepo extends CrudRepository<Kategoria,Long> {
}
