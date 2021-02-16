
package com.neu.edu.LMS.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import com.neu.edu.LMS.dao.DAO;
import com.neu.edu.LMS.pojo.Role;
import com.neu.edu.LMS.pojo.User;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class UserDaoImpl extends DAO implements UserDao {
	@Override
    public void createUser(User userdetail) {
		try {
			begin();
			Role role = getSession().get(Role.class, userdetail.getSelect_role());
			userdetail.setRole(role);
			getSession().save(userdetail);
			commit();
			close();
		}catch (HibernateException e) {
	        rollback();
	        close();
	    }
    }

  /*  @Override
    public boolean removeUser(Integer id) {
        return true;
    }*/

    @Override
    public List<User> findAllUsers() {
    	try {
    		begin();
    		List<User> users = getSession().createQuery("from User").list();
    		commit();
    		close();
    		return users;
	    }catch (HibernateException e) {
	        rollback();
	        close();
	        return null;
	    }
    }

 
    @Override
    public User getUser(Integer id) {
    	User user;
    	try{
    		begin();
    		user = getSession().get(User.class, id);
    		commit();
    		close();
    		return user;
    	} catch (HibernateException e) {
            rollback();
            close();
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
    	try{
    		begin();
    		User u = getSession().get(User.class, user.getId());
    		u.setUsername(user.getUsername());
    		u.setPassword(user.getPassword());
    		commit();
    		close();
    		return true;
    	} catch (HibernateException e) {
            rollback();
            close();
            return false;
        }
    	//return true;
    }

    @Override
    public boolean SaveUserDetails(User user) {
    	try{
    		begin();
    		User u = getSession().get(User.class, user.getId());
    		u.setAddress(user.getAddress());
    		u.setPhonenumber(user.getPhonenumber());
    		u.setAge(user.getAge());
    		u.setActive(user.getActive());
    		commit();
    		close();
    		return true;
    	} catch (HibernateException e) {
            rollback();
            close();
            return false;
        }
    	//return true;
    }

  /*  @Override
    public User findUserByEmail(String usermail) {
    	String queryString = "select id from User u where u.useremail = ?";
    	Query query = (Query) getSession().createQuery(queryString);
        query.setParameter(1, usermail);
        List userIds = query.getResultList();
       // if (userIds.size() > 0) {
       //     User user = this.sessionFactory.getCurrentSession().get(User.class, userIds.get(0));
       //     return user;
       // }
        return null;
    }*/
    
    @SuppressWarnings("deprecation")
	@Override
    public User GetUserByLibRegNumber(String Reg) {
    	try {
	    		begin();
		    	Criteria criteria = getSession().createCriteria(User.class);
		    	criteria.add(Restrictions.eq("LibRegNum",Reg));
		        criteria.add(Expression.isNull("username"));
		        User u1 = (User) criteria.uniqueResult();
		        commit();
		        close();
		        return u1;
    		}catch (HibernateException e) {
	            rollback();
	            close();
	            return null;
    		}
    }
    
    @Override
    public List<User> FindUserFilter(String filtervalue,String filterby) {
    	try {
    		begin();
    	
	    	Criteria crit = getSession().createCriteria(User.class);
	    	if(filterby.equals("SearchByLibID")) {
	    		crit.add(Restrictions.eq("LibRegNum",filtervalue));
	    	}else if(filterby.equals("SearchByEmail")) {
	    		crit.add(Restrictions.like("email","%"+filtervalue+"%"));
	    	}else if(filterby.equals("SearchByFN")) {
	    		crit.add(Restrictions.like("name","%"+filtervalue+"%"));
	    	}else if(filterby.equals("SearchByLN")) {
	    		crit.add(Restrictions.like("lastName","%"+filtervalue+"%"));
	    	}
	
			List<User> users = crit.list();
			
			commit();
			close();
			return users;
    	} catch (HibernateException e) {
			rollback();
			close();
			return null;
		}
    }
    
    @Override
    public List<Role> GetRoles(){
    	List<Role> roles;
    	try {
    		begin();
    		roles = getSession().createCriteria(Role.class).list();
    		commit();
    		close();
    		return roles;
    	} catch (HibernateException e) {
			rollback();
			close();
			roles = null;
		}
    	return roles;
    }

}