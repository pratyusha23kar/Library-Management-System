package com.neu.edu.LMS.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.User;

@Transactional
@Repository
public class AuthenticationDaoImpl extends DAO implements AuthenticationDao {
	
	@Override
    public User GetloginUser(String Username, String Password) {
		User user;
		try{
			begin();
			Query u = getSession().createQuery("from User where username like :usrname and password like :Passwrd")
					.setParameter("usrname", Username )
					.setParameter("Passwrd",Password);
			user = (User) u.uniqueResult();
			commit();
			//close();
			return user;
		} catch (HibernateException e) {
	        rollback();
	        //close();
	        return null;
	    }
    }
}
