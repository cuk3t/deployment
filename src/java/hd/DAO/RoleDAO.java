/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.Role;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author nhantd60126
 */
public class RoleDAO  implements Serializable{
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");
        EntityManager em = emf.createEntityManager();

        
     public Role getById(int id){
        Role role = new Role();
        EntityManager em =emf.createEntityManager();
        Query query = em.createNamedQuery("Role.findByRoleId");
        query.setParameter("roleId", id);
        role =(Role) query.getSingleResult();
        return role;
     }
}
