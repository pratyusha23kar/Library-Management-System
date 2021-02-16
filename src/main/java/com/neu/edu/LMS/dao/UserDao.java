/**
 *
 */
package com.neu.edu.LMS.dao;


import org.springframework.transaction.annotation.Transactional;

import com.neu.edu.LMS.pojo.Role;
import com.neu.edu.LMS.pojo.User;

import java.util.List;

@Transactional
public interface UserDao {

    public void createUser(User user);
    public List<User> findAllUsers();
    public User getUser(Integer id);
   // public User findUserByEmail(String usermail);
    public boolean updateUser(User user);
    boolean SaveUserDetails(User u);
    //public boolean removeUser(Integer id);
    public User GetUserByLibRegNumber(String Reg);
    List<User> FindUserFilter(String filtervalue,String filterby);
    
    List<Role> GetRoles();
}
