package org.kzcw.dao;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
@Component
public class RoleDao extends BaseDao<Role>{

}
